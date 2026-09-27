package mtr.data;

import java.lang.classfile.ClassFile;
import java.lang.classfile.Attributes;
import java.lang.classfile.AttributedElement;
import java.lang.classfile.constantpool.ClassEntry;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.regex.Pattern;

/** Exercises the same transaction interface as the scoreboard adapter, without Minecraft. */
public final class TicketTransactionCheck {
    private static int assertions;
    private static final RuntimeException FAILURE = new IllegalStateException("ticket boundary failure");

    public static void main(String[] args) throws Exception {
        try {
            Class.forName("net.minecraft.world.scores.Scoreboard");
            throw new AssertionError("Ticket domain test must run without Minecraft on its classpath");
        } catch (ClassNotFoundException expected) {
            assertions++;
        }
        checkDependencies(Path.of(args[1]));
        int records = 0;
        for (String line : Files.readAllLines(Path.of(args[0]))) {
            if (!line.startsWith("ticket:")) continue;
            String[] parts = line.substring(line.indexOf('\t') + 1).split("->", 2);
            String[] input = parts[0].split(":"), expected = parts[1].split(":", 5);
            int balance = Integer.parseInt(input[0]), entry = Integer.parseInt(input[1]), zone = Integer.parseInt(input[2]);
            boolean entrance = Boolean.parseBoolean(input[3]), exit = Boolean.parseBoolean(input[4]);
            boolean remind = Boolean.parseBoolean(input[5]), concession = Boolean.parseBoolean(input[6]);
            Account account = new Account(balance, entry);
            List<TicketTransaction.Notice> notices = new ArrayList<>();
            List<Integer> fares = new ArrayList<>();
            int[] concessionReads = {0};
            TicketTransaction transaction = new TicketTransaction(account, () -> { concessionReads[0]++; return concession; },
                    (notice, fare) -> { notices.add(notice); fares.add(fare); });
            TicketTransaction.Outcome result = transaction.pass(zone, entrance, exit, remind);
            boolean entering = entrance && (!exit || entry == 0);
            require(result.entering == entering, "Direction differs: " + line);
            require(result.allowed == !expected[1].equals("CLOSED"), "Admission differs: " + line);
            require(account.balance == Integer.parseInt(expected[2]), "Balance differs: " + line);
            require(account.entryZone == Integer.parseInt(expected[3]), "Entry zone differs: " + line);
            require(notices.equals(List.of(expectedNotice(expected[4]))), "Notice differs: " + line);
            require(concessionReads[0] == (!entering && entry != 0 ? 1 : 0), "Concession was not lazy: " + line);
            if (notices.getFirst() == TicketTransaction.Notice.EXITED) {
                var matcher = Pattern.compile("exit_barrier:\\[.*?, (-?\\d+), (-?\\d+)\\]:true").matcher(expected[4]);
                require(matcher.find(), "Missing legacy fare: " + line);
                require(fares.getFirst() == Integer.parseInt(matcher.group(1)), "Fare differs: " + line);
            }
            records++;
        }
        require(records == 160, "The complete original-Java ticket corpus must be covered");
        orderingAndFailures();
        fareAdjustments();
        System.out.println("PASS: pure ticket transaction, " + records + " original-Java records / " + assertions + " assertions; no Minecraft, ASM, Unsafe or reflection into transaction state");
    }

    private static TicketTransaction.Notice expectedNotice(String record) {
        if (record.contains("already_entered")) return TicketTransaction.Notice.ALREADY_ENTERED;
        if (record.contains("already_exited")) return TicketTransaction.Notice.ALREADY_EXITED;
        if (record.contains("insufficient_balance")) return TicketTransaction.Notice.INSUFFICIENT_BALANCE;
        if (record.contains("enter_barrier")) return TicketTransaction.Notice.ENTERED;
        if (record.contains("exit_barrier")) return TicketTransaction.Notice.EXITED;
        throw new AssertionError("Unknown legacy notice " + record);
    }

    private static void orderingAndFailures() {
        Account entered = new Account(1000, 4);
        List<String> events = entered.events;
        BooleanSupplier unexpected = () -> { throw new AssertionError("Unexpected concession query"); };
        TicketTransaction remind = new TicketTransaction(entered, unexpected, (notice, fare) -> events.add("notice:" + notice));
        require(!remind.pass(7, true, false, true).allowed, "Repeat entry reminder must deny");
        require(events.equals(List.of("getEntry", "notice:ALREADY_ENTERED")), "Reminder touched money: " + events);

        Account missing = new Account(50, 0);
        TicketTransaction noEntry = new TicketTransaction(missing, unexpected, (notice, fare) -> missing.events.add("notice:" + notice));
        require(!noEntry.pass(4, false, true, true).allowed, "Missing-entry reminder must deny");
        require(missing.events.equals(List.of("getEntry", "notice:ALREADY_EXITED")), "Exit reminder touched money");

        Account exited = new Account(1000, 4);
        TicketTransaction exit = new TicketTransaction(exited, () -> { exited.events.add("concession"); return false; },
                (notice, fare) -> { exited.events.add("notice:" + notice + ":" + fare); throw FAILURE; });
        expectFailure(() -> exit.pass(7, false, true, false));
        require(exited.balance == 994 && exited.entryZone == 0, "A failed notice rolled back an already charged exit");
        require(exited.events.equals(List.of("getEntry", "concession", "setEntry:0", "getBalance", "setBalance:994", "notice:EXITED:6")), "Exit effect order changed: " + exited.events);

        Account concessionFailure = new Account(1000, 4);
        TicketTransaction failedConcession = new TicketTransaction(concessionFailure, () -> { throw FAILURE; }, (notice, fare) -> { throw new AssertionError("Unexpected notice"); });
        expectFailure(() -> failedConcession.pass(7, false, true, false));
        require(concessionFailure.balance == 1000 && concessionFailure.entryZone == 4, "Concession failure changed account");

        Account fine = new Account(1000, 4);
        TicketTransaction reenter = new TicketTransaction(fine, unexpected, (notice, fare) -> { throw FAILURE; });
        expectFailure(() -> reenter.pass(-2, true, false, false));
        require(fine.balance == 500 && fine.entryZone == -2, "Fine/entry state must precede failed feedback");

        Account writeFailure = new Account(1000, 4) {
            @Override public void setBalance(int value) { throw FAILURE; }
        };
        TicketTransaction failedWrite = new TicketTransaction(writeFailure, () -> false, (notice, fare) -> { throw new AssertionError("Feedback followed a failed write"); });
        expectFailure(() -> failedWrite.pass(7, false, true, false));
        require(writeFailure.entryZone == 0 && writeFailure.balance == 1000, "Partial write ordering changed");
    }

    private static void fareAdjustments() {
        Account credited = new Account(100, 4);
        TicketTransaction transaction = new TicketTransaction(credited, () -> true,
                (notice, fare) -> credited.events.add("notice:" + notice + ":" + fare),
                fare -> { credited.events.add("adjust:" + fare); credited.setBalance(credited.getBalance() + 2); });
        require(transaction.pass(7, false, true, true).allowed, "Recorded journey rejected");
        require(credited.balance == 99 && credited.entryZone == 0, "Credit or concession was applied twice");
        require(credited.events.equals(List.of("getEntry", "adjust:3", "getBalance", "setBalance:102", "setEntry:0", "getBalance", "setBalance:99", "notice:EXITED:3")), "Adjustment must precede payment: " + credited.events);
        Account failed = new Account(100, 4);
        expectFailure(() -> new TicketTransaction(failed, () -> false, (notice, fare) -> { throw new AssertionError("Unexpected notice"); },
                fare -> { failed.setBalance(102); throw FAILURE; }).pass(7, false, true, true));
        require(failed.balance == 102 && failed.entryZone == 4, "Failure must retain prior credit without charging or clearing entry");
        for (boolean reminder : List.of(false, true)) {
            Account missing = new Account(1000, 0);
            new TicketTransaction(missing, () -> false, (notice, fare) -> {},
                    fare -> { throw new AssertionError("No discount on evasion"); }).pass(7, false, true, reminder);
            require(missing.balance == (reminder ? 1000 : 500), "Evasion behavior changed");
        }
        Account entrance = new Account(100, 0);
        new TicketTransaction(entrance, () -> false, (notice, fare) -> {},
                fare -> { throw new AssertionError("No fare adjustment on entry"); }).pass(7, true, false, true);
        require(entrance.balance == 100, "Entry charged a fare");
    }

    private static void checkDependencies(Path classDirectory) throws Exception {
        try (var paths = Files.list(classDirectory.resolve("mtr/data"))) {
            var files = paths.filter(path -> path.getFileName().toString().startsWith("TicketTransaction") && path.toString().endsWith(".class")).toList();
            require(!files.isEmpty(), "Missing production ticket domain classes");
            for (Path file : files) {
                var model = ClassFile.of().parse(file);
                for (var entry : model.constantPool()) if (entry instanceof ClassEntry reference) checkType(reference.asInternalName());
                checkSignature(model);
                for (var field : model.fields()) { checkDescriptor(field.fieldType().stringValue()); checkSignature(field); }
                for (var method : model.methods()) { checkDescriptor(method.methodType().stringValue()); checkSignature(method); }
            }
        }
        for (String descriptor : List.of("Lnet/minecraft/world/level/Level;", "([Lnet/minecraft/world/level/Level;)V", "Ljava/util/List<Lnet/minecraft/world/level/Level;>;")) {
            boolean rejected = false;
            try { checkDescriptor(descriptor); } catch (AssertionError expected) { rejected = true; }
            require(rejected, "Dependency guard accepted forbidden field/method/generic descriptor " + descriptor);
        }
    }
    private static void checkType(String name) {
        while (name.startsWith("[")) name = name.substring(1);
        if (name.startsWith("L") && name.endsWith(";")) name = name.substring(1, name.length() - 1);
        require(name.startsWith("java/") || name.startsWith("kotlin/") || name.equals("mtr/data/TicketTransaction") || name.startsWith("mtr/data/TicketTransaction$") || name.length() == 1 && "ZBCIJFDS".contains(name), "Ticket domain acquired an implementation dependency: " + name);
    }
    private static void checkDescriptor(String descriptor) {
        var references = Pattern.compile("L([^;<]+)").matcher(descriptor);
        while (references.find()) checkType(references.group(1));
    }
    private static void checkSignature(AttributedElement model) {
        model.findAttribute(Attributes.signature()).ifPresent(signature -> checkDescriptor(signature.signature().stringValue()));
    }

    private static void expectFailure(Runnable action) {
        try { action.run(); throw new AssertionError("Expected failure"); }
        catch (RuntimeException failure) { require(failure == FAILURE, "Changed failure identity"); }
    }
    private static void require(boolean condition, String message) { assertions++; if (!condition) throw new AssertionError(message); }

    private static class Account implements TicketTransaction.Account {
        int balance, entryZone;
        final List<String> events = new ArrayList<>();
        Account(int balance, int entryZone) { this.balance = balance; this.entryZone = entryZone; }
        @Override public int getBalance() { events.add("getBalance"); return balance; }
        @Override public void setBalance(int value) { events.add("setBalance:" + value); balance = value; }
        @Override public int getEntryZone() { events.add("getEntry"); return entryZone; }
        @Override public void setEntryZone(int value) { events.add("setEntry:" + value); entryZone = value; }
    }
}
