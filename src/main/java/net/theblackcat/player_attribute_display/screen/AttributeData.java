package net.theblackcat.player_attribute_display.screen;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.Identifier;

public class AttributeData {
    public String prefix;
    public Identifier attributeId;
    public boolean percentage;
    public int decimalPlaces;
    public String unit;
    public String expression;

    public AttributeData(String prefix, Identifier attributeId, boolean percentage, int decimalPlaces, String unit, String expression) {
        this.prefix = prefix;
        this.attributeId = attributeId;
        this.percentage = percentage;
        this.decimalPlaces = decimalPlaces;
        this.unit = unit;
        this.expression = expression;
    }

    public static final PacketCodec<RegistryByteBuf, AttributeData> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.STRING, data -> data.prefix,
            Identifier.PACKET_CODEC, data -> data.attributeId,
            PacketCodecs.BOOLEAN, data -> data.percentage,
            PacketCodecs.INTEGER, data -> data.decimalPlaces,
            PacketCodecs.STRING, data -> data.unit,
            PacketCodecs.STRING, data -> data.expression,
            AttributeData::new
    );
}
