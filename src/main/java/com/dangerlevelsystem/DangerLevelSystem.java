package com.dangerlevelsystem;

import com.dangerlevelsystem.client.DangerOverlayHUD;
import com.dangerlevelsystem.core.Insomnia;
import com.dangerlevelsystem.event.InsomniaSystemCallback;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DangerLevelSystem implements ModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("dangerlevelsystem");

	@Override
	public void onInitialize() {

		ServerTickEvents.START_WORLD_TICK.register(new InsomniaSystemCallback());
		ServerLivingEntityEvents.AFTER_DEATH.register(new InsomniaSystemCallback());

		HudRenderCallback.EVENT.register(new DangerOverlayHUD());
	}
}