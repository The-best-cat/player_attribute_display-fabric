package net.theblackcat.player_attribute_display.screen;

import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3i;
import net.theblackcat.player_attribute_display.PlayerAttributeDisplay;
import net.theblackcat.player_attribute_display.PlayerAttributeDisplayClient;
import net.theblackcat.player_attribute_display.event.ModKeyBindings;
import net.theblackcat.player_attribute_display.network.records.AttributeDataResult;

import java.util.List;

public class AttributeScreen extends Screen {
    private final Identifier BACKGROUND = PlayerAttributeDisplay.GetId("textures/gui/attribute_screen.png");
    private final int maxAttribute = 13, gap = 1;
    private final float fontScale = 0.85f;
    private final List<AttributeDataResult> results;

    private int x, y, backgroundWidth, backgroundHeight;

    public AttributeScreen(List<AttributeDataResult> results) {
        super(PlayerAttributeDisplay.Translate("screen.title"));
        this.results = results;
    }

    @Override
    protected void init() {
        this.backgroundWidth = 150;
        this.backgroundHeight = 165;
        this.x = (this.width - this.backgroundWidth) / 2;
        this.y = (this.height - this.backgroundHeight) / 2;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        this.RenderTitle(context);

        var matrices = context.getMatrices();
        matrices.pushMatrix();
        matrices.scale(fontScale);

        for (int i = 0; i < Math.min(this.maxAttribute, this.results.size()); i++) {
            var pos = this.GetBasePos(12 + gap, 24 + (this.textRenderer.fontHeight + gap) * i, fontScale);
            context.drawText(this.textRenderer, PlayerAttributeDisplayClient.ParseResultToText(this.results.get(i)), pos.getX(), pos.getY(), 0xFF373737, false);
        }

        matrices.popMatrix();
    }

    private void RenderTitle(DrawContext context) {
        float scale = 1.2f;
        Vec3i pos = this.GetBasePos((int) (this.backgroundWidth / 2f), 8, scale);
        int i = pos.getX() - this.textRenderer.getWidth(this.title) / 2;
        int j = pos.getY() - this.textRenderer.fontHeight / 2 + 3;

        context.getMatrices().pushMatrix();
        context.getMatrices().scale(scale);

        context.drawText(this.textRenderer, this.title,  i,  j, 0xFF373737, false);

        context.getMatrices().popMatrix();
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        context.drawTexture(RenderPipelines.GUI_TEXTURED, BACKGROUND, x, y, 0, 0, backgroundWidth, backgroundHeight, backgroundWidth, backgroundHeight);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (ModKeyBindings.openScreen.matchesKey(keyCode, scanCode)) {
            this.close();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean shouldPause() { return false; }

    private Vec3i GetBasePos(int x, int y, float scale) {
        return new Vec3i(Math.round((this.x + x) / scale), Math.round((this.y + y) / scale), 0);
    }
}
