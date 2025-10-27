package net.theblackcat.player_attribute_display.network;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.theblackcat.player_attribute_display.network.packets.C2S.RequestOpenPanelPayload;
import net.theblackcat.player_attribute_display.network.packets.S2C.SyncResponsePacket;

public class PacketRegistry {
    public static void Register() {
        PayloadTypeRegistry.playC2S().register(RequestOpenPanelPayload.ID, RequestOpenPanelPayload.CODEC);

        PayloadTypeRegistry.playS2C().register(SyncResponsePacket.ID, SyncResponsePacket.CODEC);
    }
}
