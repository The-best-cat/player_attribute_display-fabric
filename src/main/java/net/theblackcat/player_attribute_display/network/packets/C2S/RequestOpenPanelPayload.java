package net.theblackcat.player_attribute_display.network.packets.C2S;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.theblackcat.player_attribute_display.PlayerAttributeDisplay;
import net.theblackcat.player_attribute_display.screen.AttributeData;

import java.util.List;


public record RequestOpenPanelPayload(List<AttributeData> data) implements CustomPayload {
    public static final Id<RequestOpenPanelPayload> ID = new Id<>(PlayerAttributeDisplay.GetId("ropp"));
    public static final PacketCodec<RegistryByteBuf, RequestOpenPanelPayload> CODEC = PacketCodec.tuple(
            AttributeData.PACKET_CODEC.collect(PacketCodecs.toList()), RequestOpenPanelPayload::data,
            RequestOpenPanelPayload::new
    );

    @Override
    public Id<? extends CustomPayload> getId() { return ID; }
}
