package dev.ashcorp.aetherflight.menus;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.ashcorp.aetherflight.AetherFlight;
import dev.ashcorp.aetherflight.blocks.AethergenContainer;
import dev.ashcorp.aetherflight.capabilities.CapabilityManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class AethergenScreen extends AbstractContainerScreen<AethergenContainer> {

    private final ResourceLocation GUI = new ResourceLocation(AetherFlight.MODID, "textures/gui/aethergen_gui.png");
    private int storedAether;

    public AethergenScreen(AethergenContainer container, Inventory inv, Component name) {
        super(container, inv, name);
        inv.player.getCapability(CapabilityManager.AETHER_PLAYER_CAPABILITY).ifPresent(h -> {
            storedAether = h.getStoredAether();
        });
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(PoseStack matrixStack, int mouseX, int mouseY) {

        // TODO: 12/01/2022 Fix this not displaying the correct storedAether value

        drawString(matrixStack, Minecraft.getInstance().font, "Stored Aether: " + storedAether, 20, 10, 0xffffff);
    }

    @Override
    protected void renderBg(PoseStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShaderTexture(0, GUI);
        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;
        this.blit(matrixStack, relX, relY, 0, 0, this.imageWidth, this.imageHeight);
    }
}
