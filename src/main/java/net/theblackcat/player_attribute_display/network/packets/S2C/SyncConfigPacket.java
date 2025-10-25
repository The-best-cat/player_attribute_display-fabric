package net.theblackcat.player_attribute_display.network.packets.S2C;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.theblackcat.player_attribute_display.PlayerAttributeDisplay;
import net.theblackcat.player_attribute_display.config.ModConfig;

import java.util.List;

public record SyncConfigPacket(List<ModConfig.AttributeConfigData> data, boolean disable, boolean use) implements CustomPayload {
    public static final Id<SyncConfigPacket> ID = new Id<>(PlayerAttributeDisplay.GetId("scp"));
    public static final PacketCodec<RegistryByteBuf, SyncConfigPacket> CODEC = PacketCodec.tuple(
            ModConfig.AttributeConfigData.PACKET_CODEC.collect(PacketCodecs.toList()), SyncConfigPacket::data,
            PacketCodecs.BOOLEAN, SyncConfigPacket::disable,
            PacketCodecs.BOOLEAN, SyncConfigPacket::use,
            SyncConfigPacket::new
    );

    @Override
    public Id<? extends CustomPayload> getId() { return ID; }
}
