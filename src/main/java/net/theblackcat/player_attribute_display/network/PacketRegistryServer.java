package net.theblackcat.player_attribute_display.network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.theblackcat.player_attribute_display.PlayerAttributeDisplay;
import net.theblackcat.player_attribute_display.network.packets.C2S.RequestSyncPayload;
import net.theblackcat.player_attribute_display.network.packets.S2C.SyncResponsePacket;

public class PacketRegistryServer {
    public static void Register() {
        ServerPlayNetworking.registerGlobalReceiver(RequestSyncPayload.ID, (packet, context) -> {
            var data = PlayerAttributeDisplay.GetAttributeValues(context.player(), packet.data());
            ServerPlayNetworking.send(context.player(), new SyncResponsePacket(data));
        });
    }
}

