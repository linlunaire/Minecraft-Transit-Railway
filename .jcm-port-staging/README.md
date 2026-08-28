# JCM 1.21.1 / MTR 3.3 port

This is the active IntelliJ IDEA project for the recovery and port of Joban Client Mod 1.2.2 to Minecraft 1.21.1 and MTR 3.3.

The sibling `source-recovery` directory is a read-only recovery reference, not a Gradle source set. Source files will be migrated into `common`, `fabric`, and `neoforge` in small verified groups.

The project uses the local MTR 3.3 build at `../Minecraft-Transit-Railway-3.x.x/build/release`. Build MTR first whenever its API changes.
