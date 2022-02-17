package io.github.elizayami.largetools.common.data;

import io.github.elizayami.largetools.LargeTools;
import io.github.elizayami.largetools.common.BlockInit;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockStates extends BlockStateProvider
{
	public ModBlockStates(DataGenerator gen, ExistingFileHelper exFileHelper) 
	{
		super(gen, LargeTools.MOD_ID, exFileHelper);
	}

	@Override
	protected void registerStatesAndModels() 
	{
		makeBlockItemFromExistingModel(BlockInit.PACKED_ACACIA.get());
		makeBlockItemFromExistingModel(BlockInit.PACKED_BIRCH.get());
		makeBlockItemFromExistingModel(BlockInit.PACKED_CRIMSON.get());
		makeBlockItemFromExistingModel(BlockInit.PACKED_DARK_OAK.get());
		makeBlockItemFromExistingModel(BlockInit.PACKED_JUNGLE.get());
		makeBlockItemFromExistingModel(BlockInit.PACKED_OAK.get());
		makeBlockItemFromExistingModel(BlockInit.PACKED_SPRUCE.get());
		makeBlockItemFromExistingModel(BlockInit.PACKED_WARPED.get());
		
		makeBlockItemFromExistingModel(BlockInit.PACKED_COBBLESTONE.get());
	}
	
	private void makeBlockItemFromExistingModel(Block block)
	{
		simpleBlock(block);
		final ModelFile model = models().getExistingFile(block.getRegistryName());
		simpleBlockItem(block, model);
	}
}
