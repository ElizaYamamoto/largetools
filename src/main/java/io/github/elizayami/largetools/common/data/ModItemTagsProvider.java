package io.github.elizayami.largetools.common.data;

import io.github.elizayami.largetools.LargeTools;
import io.github.elizayami.largetools.common.BlockInit;
import io.github.elizayami.largetools.common.TagInit;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemTagsProvider extends ItemTagsProvider
{
	public ModItemTagsProvider(DataGenerator dataGenerator, BlockTagsProvider blockTagProvider, ExistingFileHelper existingFileHelper) 
	{
		super(dataGenerator, blockTagProvider, LargeTools.MOD_ID, existingFileHelper);
	}
	
	@Override
	protected void registerTags() 
	{
		getOrCreateBuilder(TagInit.PackedPlanksItem).add(BlockInit.PACKED_ACACIA.get().asItem());
		getOrCreateBuilder(TagInit.PackedPlanksItem).add(BlockInit.PACKED_BIRCH.get().asItem());
		getOrCreateBuilder(TagInit.PackedPlanksItem).add(BlockInit.PACKED_CRIMSON.get().asItem());
		getOrCreateBuilder(TagInit.PackedPlanksItem).add(BlockInit.PACKED_DARK_OAK.get().asItem());
		getOrCreateBuilder(TagInit.PackedPlanksItem).add(BlockInit.PACKED_JUNGLE.get().asItem());
		getOrCreateBuilder(TagInit.PackedPlanksItem).add(BlockInit.PACKED_OAK.get().asItem());
		getOrCreateBuilder(TagInit.PackedPlanksItem).add(BlockInit.PACKED_SPRUCE.get().asItem());
		getOrCreateBuilder(TagInit.PackedPlanksItem).add(BlockInit.PACKED_WARPED.get().asItem());
	}
	
}
