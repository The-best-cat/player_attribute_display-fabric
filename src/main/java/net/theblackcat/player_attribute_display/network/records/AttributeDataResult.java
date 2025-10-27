package net.theblackcat.player_attribute_display.network.records;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

public record AttributeDataResult(String translationKey, String prefix, double baseValue, double currentValue, boolean percentage, int dp, String unit, InvalidReason reason) {
    public static final PacketCodec<RegistryByteBuf, AttributeDataResult> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.STRING, AttributeDataResult::translationKey,
            PacketCodecs.STRING, AttributeDataResult::prefix,
            PacketCodecs.DOUBLE, AttributeDataResult::baseValue,
            PacketCodecs.DOUBLE, AttributeDataResult::currentValue,
            PacketCodecs.BOOLEAN, AttributeDataResult::percentage,
            PacketCodecs.INTEGER, AttributeDataResult::dp,
            PacketCodecs.STRING, AttributeDataResult::unit,
            InvalidReason.PACKET_CODEC, AttributeDataResult::reason,
            AttributeDataResult::new
    );

    public enum InvalidReason {
        NONE,
        REPEATED_ATTRIBUTE,
        INVALID_DECIMAL_PLACES,
        ATTRIBUTE_NOT_EXIST,
        ATTRIBUTE_NOT_FOUND,
        INVALID_MATHS_EXPRESSION;

        public static final PacketCodec<RegistryByteBuf, InvalidReason> PACKET_CODEC = PacketCodec.ofStatic(
                (buf, value) -> buf.writeString(value.name()),
                buf -> InvalidReason.valueOf(buf.readString())
        );

        public static String GetTranslationKey(InvalidReason reason) {
            return "invalid." + switch (reason) {
                case NONE -> "";
                case REPEATED_ATTRIBUTE -> "repeated_attribute";
                case INVALID_DECIMAL_PLACES -> "invalid_decimal_place";
                case ATTRIBUTE_NOT_EXIST -> "attribute_not_present";
                case ATTRIBUTE_NOT_FOUND -> "dont_have_attribute";
                case INVALID_MATHS_EXPRESSION -> "invalid_expression";
            };
        }
    }
}
