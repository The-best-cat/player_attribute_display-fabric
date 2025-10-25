package net.theblackcat.player_attribute_display.network.packets.C2S;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.math.Vec3d;
import net.theblackcat.player_attribute_display.PlayerAttributeDisplay;
import net.theblackcat.player_attribute_display.screen.AttributeData;

import java.util.List;


public record RequestSyncPayload(List<AttributeData> data) implements CustomPayload {
    public static final Id<RequestSyncPayload> ID = new Id<>(PlayerAttributeDisplay.GetId("sbd"));
    public static final PacketCodec<RegistryByteBuf, RequestSyncPayload> CODEC = PacketCodec.tuple(
            AttributeData.PACKET_CODEC.collect(PacketCodecs.toList()), RequestSyncPayload::data,
            RequestSyncPayload::new
    );

    @Override
    public Id<? extends CustomPayload> getId() { return ID; }
}
