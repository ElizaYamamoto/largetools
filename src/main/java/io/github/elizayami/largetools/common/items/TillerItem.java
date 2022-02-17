package io.github.elizayami.largetools.common.items;

import java.util.function.Function;

import net.minecraft.item.IItemTier;
import net.minecraftforge.common.ToolType;

public class TillerItem extends LargeToolItem
{ 
	public TillerItem(IItemTier tier, float attackDamageIn, float attackSpeedIn, Function<Properties, Properties> properties)
	{
		super(attackDamageIn, 
				attackSpeedIn, 
				tier, 
				properties.apply(new Properties().defaultMaxDamage((int) (tier.getMaxUses() * 1.5)).addToolType(ToolType.HOE, tier.getMaxUses()))
			 );
		EFFECTIVE= LargeToolItem.PLANTS;
	}
}
