package net.theblackcat.player_attribute_display;

import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import me.fzzyhmstrs.fzzy_config.api.RegisterType;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.theblackcat.player_attribute_display.config.ClientConfig;
import net.theblackcat.player_attribute_display.event.ModKeyBindings;
import net.theblackcat.player_attribute_display.network.PacketRegistryClient;
import net.theblackcat.player_attribute_display.network.packets.C2S.RequestOpenPanelPayload;
import net.theblackcat.player_attribute_display.network.records.AttributeDataResult;
import net.theblackcat.player_attribute_display.screen.AttributeData;

import java.text.DecimalFormat;
import java.util.List;

public class PlayerAttributeDisplayClient implements ClientModInitializer {
    public static final ClientConfig CLIENT_CONFIG = ConfigApiJava.registerAndLoadConfig(ClientConfig::new, RegisterType.CLIENT);

    @Override
    public void onInitializeClient() {
        ModKeyBindings.Register();
        PacketRegistryClient.Register();

        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            if (client != null && client.player != null && client.world != null && !client.isPaused()) {
                if (!PlayerAttributeDisplay.SERVER_CONFIG.useServerConfig || !PlayerAttributeDisplay.SERVER_CONFIG.disableScreen) {
                    while (ModKeyBindings.openScreen.wasPressed()) {
                        ClientPlayNetworking.send(new RequestOpenPanelPayload(GetData()));
                    }
                }
            }
        });
    }

    private List<AttributeData> GetData() {
        return PlayerAttributeDisplay.SERVER_CONFIG.useServerConfig ? PlayerAttributeDisplay.SERVER_CONFIG.GetData() : CLIENT_CONFIG.GetData();
    }

    public static Text GetInvalidReason(AttributeDataResult result) {
        MutableText text = Text.empty();
        String invalidKey = AttributeDataResult.InvalidReason.GetTranslationKey(result.reason());
        text.append(PlayerAttributeDisplay.Translate(invalidKey));
        text.formatted(Formatting.DARK_RED);
        return text;
    }

    public static Quartet<Text, Text, Text, Text> ParseResultToText(AttributeDataResult result) {
        boolean showDetail = PlayerAttributeDisplayClient.CLIENT_CONFIG.showDetail;
    
        MutableText name = Text.empty()
                .append(Text.translatable(result.translationKey()))
                .append(": ");
    
        MutableText base = Text.empty()
                .append(result.prefix())
                .append(DecimalFormat(result.dp(), showDetail ? result.baseValue() : result.currentValue()));
   
        MutableText detail = Text.empty();
        if (showDetail) {
            double changed = result.currentValue() - result.baseValue();
            if (changed != 0) {
                detailText.append("(")
                        .append(changed > 0 ? "+" : "")
                        .append(DecimalFormat(result.dp(), changed))
                        .append(")");
            }
        }
    
        MutableText unit = Text.empty();
        if (result.percentage()) {
            unitText.append("%");
        }
        unitText.append(result.unit());
    
        return new Quartet<>(nameText, baseText, detailText, unitText);
    }

    private static String DecimalFormat(int dp, double value) {
        if (dp == 0) {
            return String.valueOf(Math.round(value));
        }
        return new DecimalFormat("0." + "#".repeat(Math.max(0, dp))).format(value);
    }
}
