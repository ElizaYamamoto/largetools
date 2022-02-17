package io.github.elizayami.largetools.common.enchantment;

import io.github.elizayami.largetools.common.items.LargeToolItem;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

public class ExpandEnchantment extends LargeToolEnchantment
{
	public ExpandEnchantment(Rarity veryRare, EnchantmentType digger, EquipmentSlotType[] equipmentSlotTypes)
	{
		super("expand", Rarity.UNCOMMON, EnchantmentType.DIGGER, new EquipmentSlotType[]
		{ EquipmentSlotType.MAINHAND });
	}

	@Override
	public int getMinEnchantability(int level)
	{
		return 10;
	}

	@Override
	public int getMaxLevel()
	{
		return 1;
	}

	@Override
	public boolean isTreasureEnchantment()
	{
		return true;
	}

	@Override
	public boolean canApply(ItemStack stack)
	{
		return stack.getItem() instanceof LargeToolItem;
	}
}
