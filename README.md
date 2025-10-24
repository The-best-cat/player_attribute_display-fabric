# Player Attribute Display
This mod adds a panel that displays a list of attribute values.

*In order to obtain accurate values, this mod requires communication between the client and server, therefore **MUST** be installed on **BOTH** sides in multiplayers.

This mod includes a server config and a client config. When it is installed on a server, it can be disabled for all players, and all players will display the same attributes. This can be toggled off and let the players configure their panels individually.

### List Configuration
The panel can display up to 13 attributes at most.

In each entry, the identifier of the attribute must be provided.

You can optionally add a prefix and a unit to the value.

You can configure the number format of the displayed value:
- Raw: Default. No changes.
- Percentage: The value is multiplied by 100 and the % symbol is added.
  
You can configure the decimal place of the value, which ranges from 0 to 5 (inclusive). If it is 0, the value is displayed as a rounded integer.

If the value requires some calculations before being displayed, you can provide a mathematical expression. Use 'x' to represent the original value. Unfortunately, substituting other dynamic values is not supported at the moment.

### This mod is for fabric only. There is no current plan to make a forge/neoforge verion.
