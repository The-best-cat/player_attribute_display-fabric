package net.theblackcat.player_attribute_display.screen;

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
}
