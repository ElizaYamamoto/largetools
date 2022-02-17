package io.github.elizayami.largetools.common;

import io.github.elizayami.largetools.LargeTools;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockInit
{
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
			LargeTools.MOD_ID);

	public static final RegistryObject<Block> PACKED_ACACIA = registerWoodWithDefaultItem("packed_acacia");
	public static final RegistryObject<Block> PACKED_BIRCH = registerWoodWithDefaultItem("packed_birch");
	public static final RegistryObject<Block> PACKED_CRIMSON = registerWoodWithDefaultItem("packed_crimson");
	public static final RegistryObject<Block> PACKED_DARK_OAK = registerWoodWithDefaultItem("packed_dark_oak");
	public static final RegistryObject<Block> PACKED_JUNGLE = registerWoodWithDefaultItem("packed_jungle");
	public static final RegistryObject<Block> PACKED_OAK = registerWoodWithDefaultItem("packed_oak");
	public static final RegistryObject<Block> PACKED_SPRUCE = registerWoodWithDefaultItem("packed_spruce");
	public static final RegistryObject<Block> PACKED_WARPED = registerWoodWithDefaultItem("packed_warped");

	public static final RegistryObject<Block> PACKED_COBBLESTONE = registerStoneWithDefaultItem("packed_cobblestone");

	// helpers

	public static <T extends Block> RegistryObject<T> registerWoodWithDefaultItem(String name)
	{
		AbstractBlock.Properties materialStone = AbstractBlock.Properties.create(Material.ROCK).setRequiresTool()
				.hardnessAndResistance(5.0F, 12.0F);

		RegistryObject<T> block = (RegistryObject<T>) BLOCKS.register(name,
				() -> new Block(AbstractBlock.Properties.from(Blocks.COAL_BLOCK).setLightLevel(s -> 15)
						.setNeedsPostProcessing((bs, br, bp) -> true).setEmmisiveRendering((bs, br, bp) -> true)));
		ItemInit.ITEMS.register(name,
				() -> new BlockItem(block.get(), new Item.Properties().group(LargeTools.toolGroup)));
		return block;
	}

	public static <T extends Block> RegistryObject<T> registerStoneWithDefaultItem(String name)
	{
		RegistryObject<T> block = (RegistryObject<T>) BLOCKS.register(name,
				() -> new Block(AbstractBlock.Properties.create(Material.ROCK).
                        setRequiresTool().
                        hardnessAndResistance(5.0F, 12.0F)));
		ItemInit.ITEMS.register(name,
				() -> new BlockItem(block.get(), new Item.Properties().group(LargeTools.toolGroup)));
		return block;
	}
}
