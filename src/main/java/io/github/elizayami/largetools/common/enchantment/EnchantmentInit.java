package io.github.elizayami.largetools.common.enchantment;

import io.github.elizayami.largetools.LargeTools;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EnchantmentInit
{
	public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister
			.create(ForgeRegistries.ENCHANTMENTS, LargeTools.MOD_ID);

	public static final RegistryObject<Enchantment> EXPAND = ENCHANTMENTS.register("expand",
			() -> new ExpandEnchantment(Enchantment.Rarity.RARE, EnchantmentType.DIGGER, new EquipmentSlotType[]
			{ EquipmentSlotType.MAINHAND }));

}
