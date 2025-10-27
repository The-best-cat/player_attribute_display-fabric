package net.theblackcat.player_attribute_display.network.packets.S2C;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.theblackcat.player_attribute_display.PlayerAttributeDisplay;
import net.theblackcat.player_attribute_display.network.records.AttributeDataResult;

import java.util.List;

public record SyncResponsePacket(List<AttributeDataResult> results) implements CustomPayload {
    public static final Id<SyncResponsePacket> ID = new Id<>(PlayerAttributeDisplay.GetId("srp"));
    public static final PacketCodec<RegistryByteBuf, SyncResponsePacket> CODEC = PacketCodec.tuple(
            AttributeDataResult.PACKET_CODEC.collect(PacketCodecs.toList()), SyncResponsePacket::results,
            SyncResponsePacket::new
    );

    @Override
    public Id<? extends CustomPayload> getId() { return ID; }
}
