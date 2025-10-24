package net.theblackcat.player_attribute_display.network.packets.C2S;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.math.Vec3d;
import net.theblackcat.player_attribute_display.PlayerAttributeDisplay;


public record RequestSyncPayload() implements CustomPayload {
    public static final Id<RequestSyncPayload> ID = new Id<>(PlayerAttributeDisplay.GetId("sbd"));
    public static final PacketCodec<RegistryByteBuf, RequestSyncPayload> CODEC = PacketCodec.unit(new RequestSyncPayload());

    @Override
    public Id<? extends CustomPayload> getId() { return ID; }
}
