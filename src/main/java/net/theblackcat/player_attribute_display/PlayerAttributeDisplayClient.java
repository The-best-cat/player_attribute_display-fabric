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
import net.theblackcat.player_attribute_display.network.packets.C2S.RequestSyncPayload;
import net.theblackcat.player_attribute_display.network.records.AttributeDataResult;
import net.theblackcat.player_attribute_display.screen.AttributeData;

import java.security.InvalidParameterException;
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
                        ClientPlayNetworking.send(new RequestSyncPayload(GetData()));
                    }
                }
            }
        });
    }

    private List<AttributeData> GetData() {
        return PlayerAttributeDisplay.SERVER_CONFIG.useServerConfig ? PlayerAttributeDisplay.SERVER_CONFIG.GetData() : CLIENT_CONFIG.GetData();
    }

    public static Text ParseResultToText(AttributeDataResult result) {
        MutableText text = Text.empty();

        if (result.reason() != AttributeDataResult.InvalidReason.NONE) {
            String invalidKey = AttributeDataResult.InvalidReason.GetTranslationKey(result.reason());
            text.append(PlayerAttributeDisplay.Translate(invalidKey));
            text.formatted(Formatting.DARK_RED);
            return text;
        }

        text.append(Text.translatable(result.translationKey()));
        //text.formatted(Formatting.byColorIndex(CLIENT_CONFIG.attributeColour.toInt()));
        text.append(": ");

        text.append(result.prefix());
        text.append(DecimalFormat(result.dp(), result.value()));
        if (result.percentage()) text.append("%");
        text.append(result.unit());
        //text.formatted(Formatting.byColorIndex(CLIENT_CONFIG.valueColour.toInt()));
        return text;
    }

    private static String DecimalFormat(int dp, double value) {
        if (dp == 0) {
            return String.valueOf(Math.round(value));
        }
        return new DecimalFormat("0." + "#".repeat(Math.max(0, dp))).format(value);
    }
}
