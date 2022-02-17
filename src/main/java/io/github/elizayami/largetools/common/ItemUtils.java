package io.github.elizayami.largetools.common;

import java.util.Map;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;

public class ItemUtils
{
	public static void applyEnchantment(ItemStack stack, Enchantment enchantment, int level)
	{
		Map<Enchantment, Integer> enchants = EnchantmentHelper.getEnchantments(stack);
		enchants.put(enchantment, level);
		EnchantmentHelper.setEnchantments(enchants, stack);
	}

	public static boolean hasEnchantment(ItemStack stack, Enchantment enchantment)
	{
		Map<Enchantment, Integer> enchants = EnchantmentHelper.getEnchantments(stack);
		return enchants.containsKey(enchantment);
	}
}
