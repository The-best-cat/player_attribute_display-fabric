package net.theblackcat.player_attribute_display.screen;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import net.theblackcat.player_attribute_display.PlayerAttributeDisplayClient;

public class DetailButtonWidget extends ClickableWidget {
    public DetailButtonWidget(int x, int y) {
        super(x, y, 134, 137, Text.empty());
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        PlayerAttributeDisplayClient.CLIENT_CONFIG.showDetail = !PlayerAttributeDisplayClient.CLIENT_CONFIG.showDetail;
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {
    }
}
