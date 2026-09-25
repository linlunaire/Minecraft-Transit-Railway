package mtr.mappings;

import java.io.IOException;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/** Publish completely encoded MessagePack data without truncating the previous save. */
public final class SaveFileMapper {

	private SaveFileMapper() {
	}

	public static void write(Path target, byte[] data) throws IOException {
		final Path temporary = Files.createTempFile(target.toAbsolutePath().getParent(), ".mtr-save-", ".tmp");
		try {
			Files.write(temporary, data);
			try {
				Files.move(temporary, target, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
			} catch (AtomicMoveNotSupportedException exception) {
				Files.move(temporary, target, StandardCopyOption.REPLACE_EXISTING);
			}
		} finally {
			Files.deleteIfExists(temporary);
		}
	}
}
