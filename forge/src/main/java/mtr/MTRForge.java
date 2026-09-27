package mtr.neoforge;

import mtr.*;
import mtr.client.CustomResources;
import mtr.item.ItemBlockEnchanted;
import mtr.item.ItemWithCreativeTabBase;
import mtr.mappings.BlockEntityMapper;
import mtr.mappings.DeferredRegisterHolder;
import mtr.neoforge.mappings.ForgeUtilities;
import mtr.mappings.RegistryUtilities;
import mtr.render.RenderDrivingOverlay;
import mtr.render.RenderLift;
import mtr.render.RenderTrains;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;

@Mod(MTR.MOD_ID)
public class MTRForge {

	private static final DeferredRegisterHolder<Item> ITEMS = new DeferredRegisterHolder<>(MTR.MOD_ID, ForgeUtilities.registryGetItem());
	private static final DeferredRegisterHolder<Block> BLOCKS = new DeferredRegisterHolder<>(MTR.MOD_ID, ForgeUtilities.registryGetBlock());
	private static final DeferredRegisterHolder<BlockEntityType<?>> BLOCK_ENTITY_TYPES = new DeferredRegisterHolder<>(MTR.MOD_ID, ForgeUtilities.registryGetBlockEntityType());
	private static final DeferredRegisterHolder<EntityType<?>> ENTITY_TYPES = new DeferredRegisterHolder<>(MTR.MOD_ID, ForgeUtilities.registryGetEntityType());
	private static final DeferredRegisterHolder<SoundEvent> SOUND_EVENTS = new DeferredRegisterHolder<>(MTR.MOD_ID, ForgeUtilities.registryGetSoundEvent());

	static {
		MTR.init(MTRForge::registerItem, MTRForge::registerBlock, MTRForge::registerBlock, MTRForge::registerEnchantedBlock, MTRForge::registerBlockEntityType, MTRForge::registerEntityType, MTRForge::registerSoundEvent);
	}

	public MTRForge(IEventBus eventBus) {
		ForgeUtilities.registerModEventBus(eventBus);

		ITEMS.register();
		BLOCKS.register();
		BLOCK_ENTITY_TYPES.register();
		ENTITY_TYPES.register();
		SOUND_EVENTS.register();

		eventBus.register(MTRModEventBus.class);
		if (FMLEnvironment.dist == Dist.CLIENT) {
			ForgeUtilities.renderTickAction(MTRClient::incrementGameTick);
			ForgeUtilities.registerEntityRenderer(EntityTypes.SEAT::get, RenderTrains::new);
			ForgeUtilities.registerEntityRenderer(EntityTypes.LiftType.SIZE_2_2.registryObject::get, RenderLift::new);
			ForgeUtilities.registerEntityRenderer(EntityTypes.LiftType.SIZE_2_2_DOUBLE_SIDED.registryObject::get, RenderLift::new);
			ForgeUtilities.registerEntityRenderer(EntityTypes.LiftType.SIZE_3_2.registryObject::get, RenderLift::new);
			ForgeUtilities.registerEntityRenderer(EntityTypes.LiftType.SIZE_3_2_DOUBLE_SIDED.registryObject::get, RenderLift::new);
			ForgeUtilities.registerEntityRenderer(EntityTypes.LiftType.SIZE_3_3.registryObject::get, RenderLift::new);
			ForgeUtilities.registerEntityRenderer(EntityTypes.LiftType.SIZE_3_3_DOUBLE_SIDED.registryObject::get, RenderLift::new);
			ForgeUtilities.registerEntityRenderer(EntityTypes.LiftType.SIZE_4_3.registryObject::get, RenderLift::new);
			ForgeUtilities.registerEntityRenderer(EntityTypes.LiftType.SIZE_4_3_DOUBLE_SIDED.registryObject::get, RenderLift::new);
			ForgeUtilities.registerEntityRenderer(EntityTypes.LiftType.SIZE_4_4.registryObject::get, RenderLift::new);
			ForgeUtilities.registerEntityRenderer(EntityTypes.LiftType.SIZE_4_4_DOUBLE_SIDED.registryObject::get, RenderLift::new);
			ForgeUtilities.renderGameOverlayAction(RenderDrivingOverlay::render);
			NeoForge.EVENT_BUS.register(ForgeUtilities.Events.class);
			eventBus.register(ForgeUtilities.ClientsideEvents.class);
		}
	}

	private static void registerItem(String path, RegistryObject<Item> item) {
		ITEMS.register(path, () -> {
			final Item itemObject = item.get();
			if (itemObject instanceof ItemWithCreativeTabBase) {
				Registry.registerCreativeModeTab(((ItemWithCreativeTabBase) itemObject).creativeModeTab.resourceLocation, itemObject);
			} else if (itemObject instanceof ItemWithCreativeTabBase.ItemPlaceOnWater) {
				Registry.registerCreativeModeTab(((ItemWithCreativeTabBase.ItemPlaceOnWater) itemObject).creativeModeTab.resourceLocation, itemObject);
			}
			return itemObject;
		});
	}

	private static void registerBlock(String path, RegistryObject<Block> block) {
		BLOCKS.register(path, block::get);
	}

	private static void registerBlock(String path, RegistryObject<Block> block, CreativeModeTabs.Wrapper creativeModeTabWrapper) {
		registerBlock(path, block);
		ITEMS.register(path, () -> {
			final BlockItem blockItem = new BlockItem(block.get(), RegistryUtilities.createItemProperties(creativeModeTabWrapper::get));
			Registry.registerCreativeModeTab(creativeModeTabWrapper.resourceLocation, blockItem);
			return blockItem;
		});
	}

	private static void registerEnchantedBlock(String path, RegistryObject<Block> block, CreativeModeTabs.Wrapper creativeModeTab) {
		registerBlock(path, block);
		ITEMS.register(path, () -> {
			final ItemBlockEnchanted itemBlockEnchanted = new ItemBlockEnchanted(block.get(), RegistryUtilities.createItemProperties(creativeModeTab::get));
			Registry.registerCreativeModeTab(creativeModeTab.resourceLocation, itemBlockEnchanted);
			return itemBlockEnchanted;
		});
	}

	private static void registerBlockEntityType(String path, RegistryObject<? extends BlockEntityType<? extends BlockEntityMapper>> blockEntityType) {
		BLOCK_ENTITY_TYPES.register(path, blockEntityType::get);
	}

	private static void registerEntityType(String path, RegistryObject<? extends EntityType<? extends Entity>> entityType) {
		ENTITY_TYPES.register(path, entityType::get);
	}

	private static void registerSoundEvent(String path, SoundEvent soundEvent) {
		SOUND_EVENTS.register(path, () -> soundEvent);
	}

	private static class MTRModEventBus {

		@SubscribeEvent
		public static void onClientSetupEvent(FMLClientSetupEvent event) {
			MTRClient.init();
			event.enqueueWork(MTRClient::initItemModelPredicate);
			ForgeUtilities.registerTextureStitchEvent(textureAtlas -> {
				if (((TextureAtlas) textureAtlas).location().getPath().equals("textures/atlas/blocks.png")) {
					CustomResources.reload(Minecraft.getInstance().getResourceManager());
				}
			});
		}
	}
}
