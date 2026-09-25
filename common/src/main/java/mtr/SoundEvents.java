package mtr;

import mtr.mappings.RegistryUtilities;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;

public interface SoundEvents {

	SoundEvent TICKET_BARRIER = RegistryUtilities.createSoundEvent(Identifier.fromNamespaceAndPath(MTR.MOD_ID, "ticket_barrier"));
	SoundEvent TICKET_BARRIER_CONCESSIONARY = RegistryUtilities.createSoundEvent(Identifier.fromNamespaceAndPath(MTR.MOD_ID, "ticket_barrier_concessionary"));
	SoundEvent TICKET_PROCESSOR_ENTRY = RegistryUtilities.createSoundEvent(Identifier.fromNamespaceAndPath(MTR.MOD_ID, "ticket_processor_entry"));
	SoundEvent TICKET_PROCESSOR_ENTRY_CONCESSIONARY = RegistryUtilities.createSoundEvent(Identifier.fromNamespaceAndPath(MTR.MOD_ID, "ticket_processor_entry_concessionary"));
	SoundEvent TICKET_PROCESSOR_EXIT = RegistryUtilities.createSoundEvent(Identifier.fromNamespaceAndPath(MTR.MOD_ID, "ticket_processor_exit"));
	SoundEvent TICKET_PROCESSOR_EXIT_CONCESSIONARY = RegistryUtilities.createSoundEvent(Identifier.fromNamespaceAndPath(MTR.MOD_ID, "ticket_processor_exit_concessionary"));
	SoundEvent TICKET_PROCESSOR_FAIL = RegistryUtilities.createSoundEvent(Identifier.fromNamespaceAndPath(MTR.MOD_ID, "ticket_processor_fail"));
}
