package mtr.block;

import mtr.data.TransportMode;
import mtr.mappings.RegistrationContext;
import net.minecraft.SharedConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.Bootstrap;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.EmptyBlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;

import java.lang.reflect.Method;
import java.util.IdentityHashMap;

/** Runs vanilla's actual fluid-spread admission against real node states, without a server or save. */
public final class RailNodeFluidCompatibilityCheck {
    private static int assertions;

    public static void main(String[] args) throws Exception {
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();
        var frozen = MappedRegistry.class.getDeclaredField("frozen");
        frozen.setAccessible(true);
        frozen.set(BuiltInRegistries.BLOCK, false);
        var intrusive = MappedRegistry.class.getDeclaredField("unregisteredIntrusiveHolders");
        intrusive.setAccessible(true);
        intrusive.set(BuiltInRegistries.BLOCK, new IdentityHashMap<>());
        Method canHoldFluid = FlowingFluid.class.getDeclaredMethod("canHoldFluid",
                BlockGetter.class, BlockPos.class, BlockState.class, Fluid.class);
        canHoldFluid.setAccessible(true);
        require(admits(canHoldFluid, Blocks.AIR.defaultBlockState(), Fluids.WATER), "Air control must admit water");
        require(!admits(canHoldFluid, Blocks.STONE.defaultBlockState(), Fluids.WATER), "Stone control must reject water");
        for (TransportMode mode : TransportMode.values()) {
            Identifier id = Identifier.fromNamespaceAndPath("test", "node_" + mode.name().toLowerCase(java.util.Locale.ROOT));
            BlockNode node = RegistrationContext.construct(id, () -> new BlockNode(mode));
            Registry.register(BuiltInRegistries.BLOCK, id, node);
            checkNode(canHoldFluid, node);
        }
        // Optional actual addon classes use the same inherited properties; no ANTE test double.
        for (String name : args) {
            Identifier id = Identifier.fromNamespaceAndPath("test", "addon_node");
            Block block = RegistrationContext.construct(id, () -> {
                try { return (Block) Class.forName(name).getConstructor().newInstance(); }
                catch (ReflectiveOperationException ex) { throw new IllegalStateException(ex); }
            });
            Registry.register(BuiltInRegistries.BLOCK, id, block);
            checkNode(canHoldFluid, block);
        }
        require(((java.util.Map<?, ?>) intrusive.get(BuiltInRegistries.BLOCK)).isEmpty(), "Unregistered test blocks");
        frozen.set(BuiltInRegistries.BLOCK, true);
        intrusive.set(BuiltInRegistries.BLOCK, null);
        System.out.println("PASS: node fluid-spread admission, empty collision and positive break hardness; " + assertions + " assertions");
    }

    private static void checkNode(Method admission, Block block) throws Exception {
        // Newly registered fixture holders have no datapack tags; nodes are not vanilla signs/portals.
        var bindTags = net.minecraft.core.Holder.Reference.class.getDeclaredMethod("bindTags", java.util.Collection.class);
        bindTags.setAccessible(true);
        bindTags.invoke(block.builtInRegistryHolder(), java.util.Set.of());
        for (BlockState state : block.getStateDefinition().getPossibleStates()) {
            state.initCache();
            for (Fluid fluid : new Fluid[]{Fluids.WATER, Fluids.FLOWING_WATER, Fluids.LAVA, Fluids.FLOWING_LAVA}) {
                require(!admits(admission, state, fluid), "Fluid can replace node " + state + " with " + fluid);
            }
            require(state.getCollisionShape(EmptyBlockGetter.INSTANCE, BlockPos.ZERO, CollisionContext.empty()).isEmpty(),
                    "Fluid protection added a physical obstacle");
            require(state.getDestroySpeed(EmptyBlockGetter.INSTANCE, BlockPos.ZERO) == 2F, "Node break hardness changed");
            require(!state.getShape(EmptyBlockGetter.INSTANCE, BlockPos.ZERO).isEmpty(), "Node is not selectable");
        }
    }

    private static boolean admits(Method method, BlockState state, Fluid fluid) throws Exception {
        return (boolean) method.invoke(null, EmptyBlockGetter.INSTANCE, BlockPos.ZERO, state, fluid);
    }

    private static void require(boolean condition, String message) {
        assertions++;
        if (!condition) throw new AssertionError(message);
    }
}
