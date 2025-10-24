package net.theblackcat.player_attribute_display;

import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import me.fzzyhmstrs.fzzy_config.api.RegisterType;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.theblackcat.player_attribute_display.config.ClientConfig;
import net.theblackcat.player_attribute_display.event.ModKeyBindings;
import net.theblackcat.player_attribute_display.network.PacketRegistryClient;
import net.theblackcat.player_attribute_display.network.packets.C2S.RequestSyncPayload;

public class PlayerAttributeDisplayClient implements ClientModInitializer {
    public static final ClientConfig CLIENT_CONFIG = ConfigApiJava.registerAndLoadConfig(ClientConfig::new, RegisterType.CLIENT);

    @Override
    public void onInitializeClient() {
        ModKeyBindings.Register();
        PacketRegistryClient.Register();

        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            if (client != null && client.player != null && client.world != null && !client.isPaused()) {
                if (!PlayerAttributeDisplay.SERVER_CONFIG.useServerConfig.get() || !PlayerAttributeDisplay.SERVER_CONFIG.disableScreen.get()) {
                    while (ModKeyBindings.openScreen.wasPressed()) {
                        //PlayerAttributeDisplay.CHANNEL.clientHandle().send(new RequestSyncPacket());
                        ClientPlayNetworking.send(new RequestSyncPayload());
                    }
                }
            }
        });
    }
}
