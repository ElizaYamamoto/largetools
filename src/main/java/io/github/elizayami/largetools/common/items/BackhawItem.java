package io.github.elizayami.largetools.common.items;

import java.util.function.Function;

import net.minecraft.item.IItemTier;
import net.minecraftforge.common.ToolType;

public class BackhawItem extends LargeToolItem
{
	public BackhawItem(IItemTier tier, float attackDamageIn, float attackSpeedIn, Function<Properties, Properties> properties)
	{
		super(attackDamageIn, 
				attackSpeedIn, 
				tier, 
				properties.apply(new Properties().defaultMaxDamage((int) (tier.getMaxUses() * 1.5))
						.addToolType(ToolType.PICKAXE, tier.getMaxUses())
						.addToolType(ToolType.SHOVEL, tier.getMaxUses())
						.addToolType(ToolType.AXE, tier.getMaxUses())
						.addToolType(ToolType.HOE, tier.getMaxUses())
						));
		EFFECTIVE = LargeToolItem.STONES;
		EFFECTIVE = LargeToolItem.WOODS;
		EFFECTIVE = LargeToolItem.SOILS;
		EFFECTIVE = LargeToolItem.PLANTS;
	}
}
