package io.github.elizayami.largetools.common.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

public abstract class LargeToolEnchantment extends Enchantment
{
	public LargeToolEnchantment(String name, Rarity rarity, EnchantmentType target, EquipmentSlotType[] slotTypes)
	{
		super(rarity, target, slotTypes);
	}

	@Override
	public boolean canApply(ItemStack stack)
	{
		return super.canApply(stack);
	}

	@Override
	public boolean canVillagerTrade()
	{
		return super.canVillagerTrade();
	}

	@Override
	public boolean canGenerateInLoot()
	{
		return super.canGenerateInLoot();
	}
}
