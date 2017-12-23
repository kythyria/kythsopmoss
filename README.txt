This is a small mod that adds one item and one modifier to Tinker's Construct.

It's specifically written against Minecraft 1.12 and TCon 2.8.1.49

The item, Non-Eutactic Moss, provides a modifier to the tool it's applied to,
which in turn follows the following logic:

+ Tools heal if in the hotbar but not actually selected.
+ Tools will not heal if less than a certain time elapsed since the last usage.
  + If the tool is partially used, this is four seconds.
  + If it's broken, 12 seconds.
  + The weaker the tool, the longer you actually wait (50s minus ticks equal to
    the tool's maximum durability).
+ Once healing starts, it takes approximately eight minutes to repair fully,
  regardless of how much durability that is.
  
LICENSE: LGPLv3.

The icon for the moss is a recolour of the Mending Moss icon from TCon, the latter
being under the MIT license.
