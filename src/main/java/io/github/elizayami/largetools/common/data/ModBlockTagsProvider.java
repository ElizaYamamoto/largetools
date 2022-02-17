package io.github.elizayami.largetools.common.data;

import io.github.elizayami.largetools.LargeTools;
import io.github.elizayami.largetools.common.BlockInit;
import io.github.elizayami.largetools.common.TagInit;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockTagsProvider extends BlockTagsProvider
{
	public ModBlockTagsProvider(DataGenerator generatorIn, ExistingFileHelper existingFileHelper) 
	{
		super(generatorIn, LargeTools.MOD_ID, existingFileHelper);
	}

	@Override
	protected void registerTags() 
	{
		getOrCreateBuilder(BlockTags.PLANKS).add(BlockInit.PACKED_ACACIA.get());
		getOrCreateBuilder(BlockTags.PLANKS).add(BlockInit.PACKED_BIRCH.get());
		getOrCreateBuilder(BlockTags.PLANKS).add(BlockInit.PACKED_CRIMSON.get());
		getOrCreateBuilder(BlockTags.PLANKS).add(BlockInit.PACKED_DARK_OAK.get());
		getOrCreateBuilder(BlockTags.PLANKS).add(BlockInit.PACKED_JUNGLE.get());
		getOrCreateBuilder(BlockTags.PLANKS).add(BlockInit.PACKED_OAK.get());
		getOrCreateBuilder(BlockTags.PLANKS).add(BlockInit.PACKED_SPRUCE.get());
		getOrCreateBuilder(BlockTags.PLANKS).add(BlockInit.PACKED_WARPED.get());
		
		getOrCreateBuilder(TagInit.PackedPlanks).add(BlockInit.PACKED_ACACIA.get());
		getOrCreateBuilder(TagInit.PackedPlanks).add(BlockInit.PACKED_BIRCH.get());
		getOrCreateBuilder(TagInit.PackedPlanks).add(BlockInit.PACKED_CRIMSON.get());
		getOrCreateBuilder(TagInit.PackedPlanks).add(BlockInit.PACKED_DARK_OAK.get());
		getOrCreateBuilder(TagInit.PackedPlanks).add(BlockInit.PACKED_JUNGLE.get());
		getOrCreateBuilder(TagInit.PackedPlanks).add(BlockInit.PACKED_OAK.get());
		getOrCreateBuilder(TagInit.PackedPlanks).add(BlockInit.PACKED_SPRUCE.get());
		getOrCreateBuilder(TagInit.PackedPlanks).add(BlockInit.PACKED_WARPED.get());
		
		getOrCreateBuilder(Tags.Blocks.COBBLESTONE).add(BlockInit.PACKED_COBBLESTONE.get());
	}
	
}
