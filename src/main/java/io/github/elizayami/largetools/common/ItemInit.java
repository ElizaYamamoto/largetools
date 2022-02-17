package io.github.elizayami.largetools.common;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import io.github.elizayami.largetools.LargeTools;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTier;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit
{
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,
			LargeTools.MOD_ID);

	private static List<VanillaMaterial> vanillaMaterials;
	
	public static final VanillaMaterial WOOD = createVanillaMaterial("wooden", ItemTier.WOOD);
	public static final VanillaMaterial STONE = createVanillaMaterial("stone", ItemTier.STONE);
	public static final VanillaMaterial IRON = createVanillaMaterial("iron", ItemTier.IRON);
	public static final VanillaMaterial GOLD = createVanillaMaterial("gold", ItemTier.GOLD);
	public static final VanillaMaterial DIAMOND = createVanillaMaterial("diamond", ItemTier.DIAMOND);
	public static final VanillaMaterial NETHERITE = createVanillaMaterial("netherite", ItemTier.NETHERITE);

	
	// helpers
	public static <T extends Item> RegistryObject<T> registerItem(String name, Supplier<? extends T> itemSupplier)
	{
		return ITEMS.register(name, itemSupplier);
	}

	public static VanillaMaterial createVanillaMaterial(String name, IItemTier itemTier)
	{
		if (vanillaMaterials == null)
			vanillaMaterials = new ArrayList<>();

		VanillaMaterial material = new VanillaMaterial(name, itemTier);
		vanillaMaterials.add(material);
		return material;
	}
}
