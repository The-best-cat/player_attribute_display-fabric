# Player Attribute Display
This mod adds a panel that displays a configurable list of attribute values.  

To obtain the most accurate values, this mod requires communication between the client and the server, so it **must** be installed on the server in multi-player.

This mod includes a server config and a client config. When it is installed on a server, it can be disabled for all players, and all players will display the same attributes. This can be toggled off and let the players configure their panel individually.

### List Configuration
The panel can display up to 13 attributes at most. 

In each entry, the identifier of the attribute must be provided.

You can optionally add a prefix and a unit to the value.

You can configure the number format of the displayed value:
* Raw: Default. No changes.
* Percentage: The value is multiplied by 100 and the % symbol is added.

You can configure the decimal place of the value. It ranges from 0 to 5 (inclusive). If it is 0, the value is just displayed as an integer.

If the value requires certain calculation before being displayed, you can provide a mathematical expression. Use x to represent the original value. Unfortunately, substitution for other dynamic values is not supported at the moment.
