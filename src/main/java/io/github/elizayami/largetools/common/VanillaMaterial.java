package io.github.elizayami.largetools.common;

import io.github.elizayami.largetools.LargeTools;
import io.github.elizayami.largetools.common.items.BackhoeItem;
import io.github.elizayami.largetools.common.items.BackhawItem;
import io.github.elizayami.largetools.common.items.HammerItem;
import io.github.elizayami.largetools.common.items.PaxelItem;
import io.github.elizayami.largetools.common.items.SawItem;
import io.github.elizayami.largetools.common.items.TillerItem;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraftforge.fml.RegistryObject;

public class VanillaMaterial 
{
	public final Item axe;
	public final Item pickaxe;
	public final Item shovel;
	
	public final RegistryObject<Item> paxel;

	public final RegistryObject<Item> hammer;
	public final RegistryObject<Item> saw;
	public final RegistryObject<Item> backhoe;
	public final RegistryObject<Item> tiller;
	public final RegistryObject<Item> backhaw;
	
	public final String name;
	
	public VanillaMaterial(String name, IItemTier material) 
	{
		this.name = name;
		
		axe = getAxe(name);
		pickaxe = getPickaxe(name);
		shovel = getShovel(name);

		paxel = ItemInit.registerItem(name + "_paxel", () -> new PaxelItem(material,  1, -2.8F, p -> p.group(LargeTools.toolGroup)));

		backhoe = ItemInit.registerItem(name + "_backhoe", () -> new BackhoeItem(material, 2.5F, -3F, p -> p.group(LargeTools.toolGroup)));
		hammer = ItemInit.registerItem(name + "_hammer", () -> new HammerItem(material, 2f, -3.2F, p -> p.group(LargeTools.toolGroup)));
		saw = ItemInit.registerItem(name + "_saw", () -> new SawItem(material, 6.5F, -3.5F, p -> p.group(LargeTools.toolGroup)));
		tiller = ItemInit.registerItem(name + "_tiller", () -> new TillerItem(material, 1, -3F, p -> p.group(LargeTools.toolGroup)));
		backhaw = ItemInit.registerItem(name + "_backhaw", () -> new BackhawItem(material, 4, -3F, p -> p.group(LargeTools.toolGroup)));
	}
	

	public Item getAxe(String name) 
	{
		if (name == "wooden")
		{
			return Items.WOODEN_AXE;
		}
		else if (name == "stone")
		{
			return Items.STONE_AXE;
		}
		else if (name == "iron")
		{
			return Items.IRON_AXE;
		}
		else if (name == "gold")
		{
			return Items.GOLDEN_AXE;
		}
		else if (name == "diamond")
		{
			return Items.DIAMOND_AXE;
		}
		else
		{
			return Items.NETHERITE_AXE;
		}
	}
	
	public Item getShovel(String name) 
	{
		if (name == "wooden")
		{
			return Items.WOODEN_SHOVEL;
		}
		else if (name == "stone")
		{
			return Items.STONE_SHOVEL;
		}
		else if (name == "iron")
		{
			return Items.IRON_SHOVEL;
		}
		else if (name == "gold")
		{
			return Items.GOLDEN_SHOVEL;
		}
		else if (name == "diamond")
		{
			return Items.DIAMOND_SHOVEL;
		}
		else
		{
			return Items.NETHERITE_SHOVEL;
		}
	}
	
	public Item getPickaxe(String name) 
	{
		if (name == "wooden")
		{
			return Items.WOODEN_PICKAXE;
		}
		else if (name == "stone")
		{
			return Items.STONE_PICKAXE;
		}
		else if (name == "iron")
		{
			return Items.IRON_PICKAXE;
		}
		else if (name == "gold")
		{
			return Items.GOLDEN_PICKAXE;
		}
		else if (name == "diamond")
		{
			return Items.DIAMOND_PICKAXE;
		}
		else
		{
			return Items.NETHERITE_PICKAXE;
		}
	}
}
