import java.nio.file.Files
import java.nio.file.Path
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

def targetRoot = new File(args[0]).canonicalFile
def archiveClass = new GroovyClassLoader(getClass().classLoader)
        .parseClass(new File(targetRoot, 'gradle/ReleaseArchive.groovy'))
def fixtureParent = new File(targetRoot, 'build/release-archive-check').toPath()
Files.createDirectories(fixtureParent)
def fixture = Files.createTempDirectory(fixtureParent, 'fixture-')
int assertions = 0
def verify = { boolean condition, String message ->
    assertions++
    assert condition : message
}
def expectFailure = { Closure operation ->
    try {
        operation()
        assert false : 'A failed candidate must not be published'
    } catch (IOException | IllegalArgumentException expected) {
        assertions++
    }
}
def writeJar = { Path path, String loader, String version, String marker, String id = 'mtr' ->
    new ZipOutputStream(Files.newOutputStream(path)).withCloseable { jar ->
        jar.putNextEntry(new ZipEntry(loader == 'fabric' ? 'fabric.mod.json' : 'META-INF/neoforge.mods.toml'))
        String metadata = loader == 'fabric'
                ? "{\"id\":\"${id}\",\"version\":\"${version}\"}"
                : "[[mods]]\nmodId = \"${id}\"\nversion = \"${version}\"\n[[dependencies.mtr]]\nmodId = \"minecraft\"\n"
        jar.write(metadata.getBytes('UTF-8'))
        jar.closeEntry()
        jar.putNextEntry(new ZipEntry('marker.txt'))
        jar.write(marker.getBytes('UTF-8'))
        jar.closeEntry()
    }
    path.toFile()
}
def release = Files.createDirectory(fixture.resolve('release'))
def version = '1.21.1-3.3.8'
def currentName = "MTR-neoforge-${version}.jar"
def previousNames = ['MTR-neoforge-1.21.1-3.3.5.jar', 'MTR-neoforge-1.21.1-3.3.7.jar', 'MTR-forge-1.21.1-3.3.2.jar']
def retained = [
        'MTR-fabric-1.21.1-3.3.5.jar', 'MTR-neoforge-26.2-3.3.2.jar',
        'MTR-neoforge-1.21.10-3.3.2.jar', 'MTR-ANTE-neoforge-1.1.1-1.21.1-beta.4.jar',
        'MTR-server-neoforge-1.21.1-3.3.7.jar', 'Modern-Elevators-Escalators-neoforge-1.21.1-3.3.7.jar', 'notes.txt'
]
(previousNames + retained).each { Files.writeString(release.resolve(it), it) }
def candidate = writeJar(fixture.resolve('candidate.jar'), 'neoforge', version, 'checked')
def archived = archiveClass.publish(candidate, release.toFile(), 'MTR', 'neoforge', version)
verify(archived.size() == previousNames.size(), 'Only this loader/game/family must be archived')
previousNames.each { name ->
    verify(!Files.exists(release.resolve(name)), "Top-level obsolete release remains: ${name}")
    verify(archived.any { it.fileName.toString() == name && Files.readString(it) == name }, "Lost previous JAR: ${name}")
}
retained.each { verify(Files.readString(release.resolve(it)) == it, "Changed another release family: ${it}") }
verify(Files.mismatch(candidate.toPath(), release.resolve(currentName)) == -1, 'Published bytes differ from the checked source')
def publishedTime = Files.getLastModifiedTime(release.resolve(currentName))
verify(archiveClass.publish(candidate, release.toFile(), 'MTR', 'neoforge', version).empty, 'Repeated publication must be idempotent')
verify(Files.getLastModifiedTime(release.resolve(currentName)) == publishedTime, 'Unchanged candidates must not rewrite the current JAR')

// Rebuilding the same version preserves its previous contents too.
def rebuilt = writeJar(fixture.resolve('rebuilt.jar'), 'neoforge', version, 'rebuilt')
def replaced = archiveClass.publish(rebuilt, release.toFile(), 'MTR', 'neoforge', version)
verify(replaced.size() == 1 && replaced[0].fileName.toString() == currentName, 'Same-version replacement needs a recoverable copy')
verify(Files.mismatch(replaced[0], candidate.toPath()) == -1, 'The replaced same-version JAR was lost')
verify(Files.mismatch(rebuilt.toPath(), release.resolve(currentName)) == -1, 'The rebuilt candidate was not published')
Files.writeString(release.resolve(previousNames[0]), 'second previous build')
def archivedAgain = archiveClass.publish(rebuilt, release.toFile(), 'MTR', 'neoforge', version)
verify(archivedAgain.size() == 1 && !archived.contains(archivedAgain[0]), 'Archive batches must not overwrite history')
verify(Files.readString(archivedAgain[0]) == 'second previous build', 'Newest archived history is missing')
verify(Files.readString(archived.find { it.fileName.toString() == previousNames[0] }) == previousNames[0], 'Earlier archive was overwritten')

def fabric = writeJar(fixture.resolve('fabric.jar'), 'fabric', version, 'fabric')
def fabricArchived = archiveClass.publish(fabric, release.toFile(), 'MTR', 'fabric', version)
verify(fabricArchived.size() == 1, 'Fabric must archive only its own prior release')
verify(Files.mismatch(rebuilt.toPath(), release.resolve(currentName)) == -1, 'Fabric publication changed NeoForge')
def serverArchived = archiveClass.publish(rebuilt, release.toFile(), 'MTR-server', 'neoforge', version)
verify(serverArchived.size() == 1, 'Server variation must have its own archive scope')
verify(Files.mismatch(rebuilt.toPath(), release.resolve(currentName)) == -1, 'Server variation changed normal MTR')

// Missing/empty/broken/mismatched candidates preserve all existing releases.
def badCandidates = [
        fixture.resolve('missing.jar').toFile(), Files.createFile(fixture.resolve('empty.jar')).toFile(),
        Files.writeString(fixture.resolve('broken.jar'), 'not a zip').toFile(),
        writeJar(fixture.resolve('wrong-version.jar'), 'neoforge', '1.21.1-3.3.7', 'wrong'),
        writeJar(fixture.resolve('wrong-loader.jar'), 'fabric', version, 'wrong'),
        writeJar(fixture.resolve('wrong-mod.jar'), 'neoforge', version, 'wrong', 'mtrsteamloco')
]
badCandidates.eachWithIndex { bad, index ->
    def target = Files.createDirectory(fixture.resolve("failure-${index}"))
    def old = Files.writeString(target.resolve(previousNames[0]), 'previous')
    expectFailure { archiveClass.publish(bad, target.toFile(), 'MTR', 'neoforge', version) }
    verify(Files.readString(old) == 'previous', 'Failed candidate removed the old release')
    verify(!Files.exists(target.resolve(currentName)), 'Failed candidate became current')
    verify(!Files.exists(target.resolve('archive')), 'Failed candidate mutated release history')
}

def blocked = Files.createDirectory(fixture.resolve('blocked-archive'))
def existing = writeJar(blocked.resolve(currentName), 'neoforge', version, 'previous current')
def existingBytes = Files.readAllBytes(existing.toPath())
Files.writeString(blocked.resolve(previousNames[0]), 'previous')
Files.writeString(blocked.resolve('archive'), 'unrelated')
expectFailure { archiveClass.publish(candidate, blocked.toFile(), 'MTR', 'neoforge', version) }
verify(Files.readAllBytes(existing.toPath()) == existingBytes, 'Archive failure overwrote the current release')
verify(Files.readString(blocked.resolve(previousNames[0])) == 'previous', 'Archive failure discarded a prior release')
verify(Files.readString(blocked.resolve('archive')) == 'unrelated', 'Archive failure overwrote unrelated data')

def nonRegular = Files.createDirectory(fixture.resolve('non-regular-release'))
Files.createDirectory(nonRegular.resolve(previousNames[0]))
expectFailure { archiveClass.publish(candidate, nonRegular.toFile(), 'MTR', 'neoforge', version) }
verify(!Files.exists(nonRegular.resolve(currentName)), 'Invalid previous path must fail before publication')
expectFailure { archiveClass.publish(candidate, release.toFile(), 'MTR', 'forge', version) }
expectFailure { archiveClass.publish(candidate, release.toFile(), 'MTR-ANTE', 'neoforge', version) }
expectFailure { archiveClass.publish(candidate, release.toFile(), 'MTR', 'neoforge', '../outside') }
Files.walk(fixture).withCloseable { entries ->
    verify(entries.noneMatch { it.fileName.toString().startsWith('.publish-') }, 'Temporary candidates must be removed on every path')
}
// A renamed public release retains its separate addon-compatible loader version.
def renamed = Files.createDirectory(fixture.resolve('renamed-release'))
Files.writeString(renamed.resolve('MTR-neoforge-1.21.1-3.3.9.jar'), 'old brand')
Files.writeString(renamed.resolve('MTR-fabric-1.21.1-3.3.9.jar'), 'other loader')
def branded = writeJar(fixture.resolve('branded.jar'), 'neoforge', '1.21.1-3.3.9+yanlingmtr.1.0.0', 'new brand')
def brandArchive = archiveClass.publish(branded, renamed.toFile(), 'YanlingMTR', 'neoforge', '1.21.1-1.0.0', '1.21.1-3.3.9+yanlingmtr.1.0.0')
verify(brandArchive.size() == 1 && Files.readString(brandArchive[0]) == 'old brand', 'Renaming must preserve the previous brand in archive')
verify(Files.readString(renamed.resolve('MTR-fabric-1.21.1-3.3.9.jar')) == 'other loader', 'Renaming changed another loader')
verify(Files.mismatch(branded.toPath(), renamed.resolve('YanlingMTR-neoforge-1.21.1-1.0.0.jar')) == -1, 'Renaming lost checked bytes')
expectFailure { archiveClass.publish(branded, renamed.toFile(), 'YanlingMTR', 'neoforge', '1.21.1-1.0.1', 'wrong-version') }
println "PASS: ${assertions} release publication/archive assertions, including recovery and failure paths"
