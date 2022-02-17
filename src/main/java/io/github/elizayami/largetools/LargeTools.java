package io.github.elizayami.largetools;

import org.apache.logging.log4j.Logger;

import io.github.elizayami.largetools.common.BlockInit;
import io.github.elizayami.largetools.common.ItemInit;
import io.github.elizayami.largetools.common.enchantment.EnchantmentInit;

import org.apache.logging.log4j.LogManager;

import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.common.MinecraftForge;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

@Mod("largetools")
public class LargeTools
{
	public static final String MOD_ID = "largetools";

	public static final Logger LOGGER = LogManager.getLogger();

	public static ResourceLocation createID(String id)
	{
		return new ResourceLocation(MOD_ID, id);
	}

	public LargeTools()
	{
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		IEventBus forgeBus = MinecraftForge.EVENT_BUS;

		ItemInit.ITEMS.register(bus);
		BlockInit.BLOCKS.register(bus);
		EnchantmentInit.ENCHANTMENTS.register(bus);

		forgeBus.register(this);
	}

	public static final ItemGroup toolGroup = new LargeToolGroup("tooltab");

	public static class LargeToolGroup extends ItemGroup
	{

		public LargeToolGroup(String label)
		{
			super(label);
		}

		@Override
		public ItemStack createIcon()
		{
			return new ItemStack(ItemInit.DIAMOND.paxel.get());
		}
	}
}
