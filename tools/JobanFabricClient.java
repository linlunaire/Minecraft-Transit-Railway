package com.jsblock;

import com.jsblock.particle.LightBlockParticle;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;

/** Client-only Fabric registrations. */
public final class JobanFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        JobanClient.init();
        ParticleFactoryRegistry.getInstance().register(Particles.LIGHT_BLOCK.get(), LightBlockParticle.Provider::new);
    }
}
