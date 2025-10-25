package net.theblackcat.player_attribute_display.network;

import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedAny;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.theblackcat.player_attribute_display.PlayerAttributeDisplay;
import net.theblackcat.player_attribute_display.config.ModConfig;
import net.theblackcat.player_attribute_display.config.ServerConfig;
import net.theblackcat.player_attribute_display.network.packets.S2C.SyncConfigPacket;
import net.theblackcat.player_attribute_display.network.packets.S2C.SyncResponsePacket;
import net.theblackcat.player_attribute_display.screen.AttributeScreen;

@Environment(EnvType.CLIENT)
public class PacketRegistryClient {
    public static void Register() {
        ClientPlayNetworking.registerGlobalReceiver(SyncResponsePacket.ID, (packet, context) -> {
            MinecraftClient.getInstance().setScreen(new AttributeScreen(packet.results()));
        });

        ClientPlayNetworking.registerGlobalReceiver(SyncConfigPacket.ID, (packet, context) -> {
            var config = PlayerAttributeDisplay.SERVER_CONFIG;
            config.data = new ValidatedAny<>(new ModConfig.AttributeConfigData()).toList(packet.data());
            config.disableScreen = packet.disable();
            config.useServerConfig = packet.use();
        });
    }
}
