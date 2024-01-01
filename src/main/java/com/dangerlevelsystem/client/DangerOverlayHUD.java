package com.dangerlevelsystem.client;

import com.dangerlevelsystem.DangerLevelSystem;
import com.dangerlevelsystem.core.Danger;
import com.dangerlevelsystem.event.PlayerInterface;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.stat.StatHandler;
import net.minecraft.text.OrderedText;
import net.minecraft.util.Colors;
import net.minecraft.util.Identifier;

import java.util.Optional;

import static com.dangerlevelsystem.core.Danger.computeDangerLevelByDays;


public class DangerOverlayHUD implements HudRenderCallback {

    private static final Identifier MOON_PHASES = new Identifier("textures/environment/moon_phases.png");

    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {

        Optional<MinecraftClient> client = Optional.ofNullable(MinecraftClient.getInstance());
        if(client.isPresent()){
            int height = client.get().getWindow().getScaledHeight();
            int width = client.get().getWindow().getScaledWidth();
            StatHandler playerStat = client.get().player.getStatHandler();
            if(playerStat == null) return;
            int moonPhase = computeDangerLevelByDays((Danger.fetchTimeNotSlept(client.get().player.getStatHandler())));
            TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
            String indicator = "Insomnia Level: " + moonPhase;

            drawContext.drawText(textRenderer, indicator, (width/2)+100, height-100, Colors.WHITE, true);
        }

    }

}
