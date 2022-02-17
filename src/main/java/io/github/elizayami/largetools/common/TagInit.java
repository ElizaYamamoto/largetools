package io.github.elizayami.largetools.common;

import io.github.elizayami.largetools.LargeTools;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;

public class TagInit
{
	// MOD BLOCK TAGS
	public static final ITag.INamedTag<Block> PackedPlanks = makeModBlockTag("packed_planks");

	public static final ITag.INamedTag<Item> PackedPlanksItem = makeModItemTag("packed_planks_item");

	public static ITag.INamedTag<Block> makeModBlockTag(final String name)
	{
		return BlockTags.makeWrapperTag(new ResourceLocation(LargeTools.MOD_ID, name).toString());
	}

	public static ITag.INamedTag<Item> makeModItemTag(final String name)
	{
		return ItemTags.makeWrapperTag(new ResourceLocation(LargeTools.MOD_ID, name).toString());
	}

	public static ITag.INamedTag<Block> makeBlockTag(String namespace, String name)
	{
		return BlockTags.makeWrapperTag(new ResourceLocation(namespace, name).toString());
	}

	public static ITag.INamedTag<Item> makeItemTag(String namespace, String name)
	{
		return ItemTags.makeWrapperTag(new ResourceLocation(namespace, name).toString());
	}
}
