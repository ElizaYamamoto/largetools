package io.github.elizayami.largetools.common.data;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import io.github.elizayami.largetools.LargeTools;
import io.github.elizayami.largetools.common.BlockInit;
import net.minecraft.block.Block;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlockLootTables extends BlockLootTables
{
	@Override
	protected Iterable<Block> getKnownBlocks()
	{
		return StreamSupport.stream(ForgeRegistries.BLOCKS.spliterator(), false)
				.filter(entry -> entry.getRegistryName() != null
						&& entry.getRegistryName().getNamespace().equals(LargeTools.MOD_ID))
				.collect(Collectors.toSet());
	}

	@Override
	protected void addTables()
	{
		registerDropSelfLootTable(BlockInit.PACKED_ACACIA.get());
		registerDropSelfLootTable(BlockInit.PACKED_BIRCH.get());
		registerDropSelfLootTable(BlockInit.PACKED_CRIMSON.get());
		registerDropSelfLootTable(BlockInit.PACKED_DARK_OAK.get());
		registerDropSelfLootTable(BlockInit.PACKED_JUNGLE.get());
		registerDropSelfLootTable(BlockInit.PACKED_OAK.get());
		registerDropSelfLootTable(BlockInit.PACKED_SPRUCE.get());;
		registerDropSelfLootTable(BlockInit.PACKED_WARPED.get());;
		registerDropSelfLootTable(BlockInit.PACKED_COBBLESTONE.get());
	}
}
