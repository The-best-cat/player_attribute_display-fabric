package net.theblackcat.player_attribute_display.config;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;
import net.theblackcat.player_attribute_display.PlayerAttributeDisplay;
import net.theblackcat.player_attribute_display.network.packets.S2C.SyncConfigPacket;
import org.jetbrains.annotations.NotNull;

public class ServerConfig extends ModConfig {
    public boolean disableScreen;
    public boolean useServerConfig;

    public ServerConfig() {
        super("server_config");
        disableScreen = false;
        useServerConfig = false;
    }

    @Override
    public void onUpdateServer(@NotNull ServerPlayerEntity serverPlayer) {
        if (serverPlayer.getServer() != null) {
            for (var player : serverPlayer.getServer().getPlayerManager().getPlayerList()) {
                if (player.getUuid() != serverPlayer.getUuid()) {
                    ServerPlayNetworking.send(player, new SyncConfigPacket(this.data, this.disableScreen, this.useServerConfig));
                }
            }
            serverPlayer.getServer().sendMessage(PlayerAttributeDisplay.Translate("message.changed_server_config"));
        }
    }
}
