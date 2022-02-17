package io.github.elizayami.largetools.common.items;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.ToolItem;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class PaxelItem extends ToolItem
{
	private static final Set<Material> EFFECTIVE_ON_MATERIALS = Sets.newHashSet(Material.WOOD, Material.NETHER_WOOD,
			Material.PLANTS, Material.TALL_PLANTS, Material.BAMBOO, Material.GOURD);
	private static final Map<Block, BlockState> PATH_STUFF = Maps
			.newHashMap(ImmutableMap.of(Blocks.GRASS_BLOCK, Blocks.GRASS_PATH.getDefaultState()));

	public PaxelItem(IItemTier tier, float attackDamageIn, float attackSpeedIn, Function<Properties, Properties> properties)
	{
		super(attackDamageIn, attackSpeedIn, tier, new HashSet<>(),
				properties.apply(new Properties().defaultMaxDamage((int) (tier.getMaxUses() * 1.5))
						.addToolType(ToolType.PICKAXE, tier.getMaxUses())
						.addToolType(ToolType.SHOVEL, tier.getMaxUses())
						.addToolType(ToolType.AXE, tier.getMaxUses())
						.addToolType(ToolType.HOE, tier.getMaxUses())));
	}

	@Override
	public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items)
	{

		super.fillItemGroup(group, items);
	}

	@Override
	public boolean canHarvestBlock(BlockState state)
	{
		int i = this.getTier().getHarvestLevel();
		if (state.getHarvestTool() == ToolType.PICKAXE)
			return i >= state.getHarvestLevel();

		Material material = state.getMaterial();
		return material == Material.ROCK || material == Material.IRON || material == Material.ANVIL
				|| state.isIn(Blocks.SNOW) || state.isIn(Blocks.SNOW_BLOCK);
	}

	@Override
	public float getDestroySpeed(ItemStack stack, BlockState state)
	{
		Material material = state.getMaterial();
		return material != Material.IRON && material != Material.ANVIL && material != Material.ROCK
				&& !EFFECTIVE_ON_MATERIALS.contains(material) ? super.getDestroySpeed(stack, state) : this.efficiency;
	}
	

	@Override
	public ActionResultType onItemUse(ItemUseContext context)
	{
		World world = context.getWorld();
		BlockPos pos = context.getPos();
		PlayerEntity player = context.getPlayer();
		ItemStack stack = context.getItem();

		BlockState state = world.getBlockState(pos);

		BlockState strippable = state.getToolModifiedState(world, pos, player, stack, ToolType.AXE);

		BlockState tillable = state.getToolModifiedState(world, pos, player, stack, ToolType.HOE);

		BlockState pathable = state.getToolModifiedState(world, pos, player, stack, ToolType.SHOVEL);

		boolean success = false;

		if (strippable != null)
		{
			world.playSound(player, pos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);
			world.setBlockState(pos, strippable, 11);
			success = true;
		}
		else if (context.getFace() != Direction.DOWN && world.getBlockState(pos.up()).isAir(world, pos.up()))
		{
			boolean hoe = stack.getToolTypes().contains(ToolType.HOE);
			boolean shovel = stack.getToolTypes().contains(ToolType.SHOVEL);
			
			if (player.isSneaking())
			{
				if (tillable != null)
				{
					world.playSound(player, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
					if (!world.isRemote)
					{
						world.setBlockState(pos, tillable, 11);
						success = true;
					}
				}
			}
			else
			{
				if (pathable != null)
				{
					world.playSound(player, pos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);

					if (!world.isRemote())
					{
						world.setBlockState(pos, pathable, 11);
						success = true;
					}
				}
			}
			if (success)
			{
				if (player != null)
				{
					stack.damageItem(1, player, entity ->
					{
						entity.sendBreakAnimation(context.getHand());
					});
				}
				return ActionResultType.func_233537_a_(world.isRemote);
			}
		}
		return ActionResultType.PASS;
	}
}
