package net.theblackcat.player_attribute_display.network.packets.S2C;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.text.Text;
import net.minecraft.text.TextCodecs;
import net.theblackcat.player_attribute_display.PlayerAttributeDisplay;

import java.util.List;

public record SyncResponsePacket(List<Text> values) implements CustomPayload {
    public static final Id<SyncResponsePacket> ID = new Id<>(PlayerAttributeDisplay.GetId("sbd"));
    public static final PacketCodec<RegistryByteBuf, SyncResponsePacket> CODEC = PacketCodec.tuple(
            TextCodecs.PACKET_CODEC.collect(PacketCodecs.toList()), SyncResponsePacket::values,
            SyncResponsePacket::new
    );

    @Override
    public Id<? extends CustomPayload> getId() { return ID; }
}
