package io.github.elizayami.largetools.common.items;

import com.google.common.collect.Sets;

import io.github.elizayami.largetools.common.ItemUtils;
import io.github.elizayami.largetools.common.enchantment.EnchantmentInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
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

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class LargeToolItem extends ToolItem
{
	protected static final Set<Material> WOODS = Sets.newHashSet(Material.WOOD, Material.NETHER_WOOD, Material.PLANTS,
			Material.TALL_PLANTS, Material.BAMBOO, Material.GOURD);

	protected static final Set<Material> STONES = Sets.newHashSet(Material.IRON, Material.ANVIL, Material.ROCK);

	protected static final Set<Material> PLANTS = Sets.newHashSet(Material.ORGANIC, Material.SPONGE, Material.LEAVES);

	protected static final Set<Material> SOILS = Sets.newHashSet(Material.CLAY, Material.EARTH, Material.SAND);

	protected Set<Material> EFFECTIVE;

	public LargeToolItem(float attackDamageIn, float attackSpeedIn, IItemTier tier, Item.Properties apply)
	{
		super(attackDamageIn, attackSpeedIn, tier, new HashSet<>(), apply);
		EFFECTIVE = null;
	}

	@Override
	public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items)
	{
		super.fillItemGroup(group, items);
	}

	@Override
	public boolean canHarvestBlock(BlockState blockIn)
	{
		int i = this.getTier().getHarvestLevel();
		if (blockIn.getHarvestTool() == ToolType.PICKAXE)
		{
			return i >= blockIn.getHarvestLevel();
		}

		Material material = blockIn.getMaterial();
		return material == Material.ROCK || material == Material.IRON || material == Material.ANVIL
				|| blockIn.isIn(Blocks.SNOW) || blockIn.isIn(Blocks.SNOW_BLOCK);
	}

	public float getDestroySpeed(ItemStack stack, BlockState state)
	{
		Material material = state.getMaterial();
		return material != Material.IRON && material != Material.ANVIL && material != Material.ROCK
				&& !EFFECTIVE.contains(material) ? super.getDestroySpeed(stack, state) : this.efficiency;
	}

	@Nullable
	private static ToolType getAction(World level, BlockPos pos, PlayerEntity player, ItemStack stack, BlockState state,
			Direction face)
	{
		Set<ToolType> toolTypes = stack.getToolTypes();
		if (toolTypes.contains(ToolType.AXE)
				&& state.getToolModifiedState(level, pos, player, stack, ToolType.AXE) != null)
			return ToolType.AXE;
		if (face != Direction.DOWN)
		{
			if (player.isSecondaryUseActive())
			{
				if (toolTypes.contains(ToolType.HOE)
						&& state.getToolModifiedState(level, pos, player, stack, ToolType.HOE) != null)
					return ToolType.HOE;
				if (toolTypes.contains(ToolType.SHOVEL)
						&& state.getToolModifiedState(level, pos, player, stack, ToolType.SHOVEL) != null)
					return ToolType.SHOVEL;
			}
			else
			{
				if (toolTypes.contains(ToolType.SHOVEL)
						&& state.getToolModifiedState(level, pos, player, stack, ToolType.SHOVEL) != null)
					return ToolType.SHOVEL;
				if (toolTypes.contains(ToolType.HOE)
						&& state.getToolModifiedState(level, pos, player, stack, ToolType.HOE) != null)
					return ToolType.HOE;
			}
		}
		return null;
	}

	@Override
	public ActionResultType onItemUse(ItemUseContext context)
	{
		World world = context.getWorld();
		BlockPos pos = context.getPos();
		PlayerEntity player = context.getPlayer();
		ItemStack stack = context.getItem();
		BlockState state = world.getBlockState(pos);
		Direction face = context.getFace();

		ToolType action = getAction(world, pos, player, stack, state, face);

		if (action != null)
		{
			if (action == ToolType.AXE)
				world.playSound(player, pos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);
			else if (action == ToolType.HOE)
				world.playSound(player, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
			else if (action == ToolType.SHOVEL)
				world.playSound(player, pos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);

			if (!world.isRemote)
			{
				rightClick(stack, world, pos, player, face, action);
				if (player != null)
					stack.damageItem(1, player, entity -> entity.sendBreakAnimation(context.getHand()));
			}
			return ActionResultType.func_233537_a_(world.isRemote);
		}
		else
		{
			return ActionResultType.PASS;
		}
	}

	public void rightClick(ItemStack stack, World world, BlockPos pos, PlayerEntity player, Direction face,
			ToolType action)
	{
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();

		int radius = 1 + EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.EXPAND.get(), stack);

		Iterable<BlockPos> area;
		if (face.getAxis() == Direction.Axis.Y || action == ToolType.HOE || action == ToolType.SHOVEL)
		{
			area = BlockPos.getAllInBoxMutable(x - radius, y, z - radius, x + radius, y, z + radius);
		}
		else if (face.getAxis() == Direction.Axis.Z)
		{
			area = BlockPos.getAllInBoxMutable(x - radius, y - radius, z, x + radius, y + radius, z);
		}
		else if (face.getAxis() == Direction.Axis.X)
		{
			area = BlockPos.getAllInBoxMutable(x, y - radius, z - radius, x, y + radius, z + radius);
		}
		else
		{
			area = Collections.singleton(pos); // probably unreachable but here's a fallback
		}
		for (BlockPos pos2 : area)
		{
			BlockState state = world.getBlockState(pos2);
			BlockState modifiedState = state.getToolModifiedState(world, pos2, player, stack, action);
			if (modifiedState != null)
				world.setBlockState(pos2, modifiedState, 11);
		}
	}

	@Override
	public boolean onBlockDestroyed(ItemStack itemstack, World world, BlockState state, BlockPos pos,
			LivingEntity entity)
	{
		boolean retval = super.onBlockDestroyed(itemstack, world, state, pos, entity);

		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();

		int expandLevel = 0;

		Material material = state.getMaterial();

		if (ItemUtils.hasEnchantment(itemstack, EnchantmentInit.EXPAND.get()))
		{
			expandLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.EXPAND.get(), itemstack);
		}

		int size = 3 + (expandLevel * 2);

		double start = -1 - expandLevel;
		double sx = start;
		double sy = start;
		double sz = start;

		if (entity.rotationPitch > 40 || entity.rotationPitch < -40)
		{
			for (int loopsX = 0; loopsX < size; loopsX++)
			{
				sz = start;
				for (int loopsZ = 0; loopsZ < size; loopsZ++)
				{
					if (canHarvestBlock(world.getBlockState(new BlockPos(x + sx, y, z + sz)))
							|| EFFECTIVE.contains(material))
					{
						Block.spawnDrops(world.getBlockState(new BlockPos(x + sx, y, z + sz)), world,
								new BlockPos(x + sx, y, z + sz));
						world.destroyBlock(new BlockPos(x + sx, y, z + sz), false);
					}
					sz++;
				}
				sx++;
			}
		}
		else if (entity.getHorizontalFacing() == Direction.NORTH || entity.getHorizontalFacing() == Direction.SOUTH)
		{
			for (int loopsX = 0; loopsX < size; loopsX++)
			{
				sy = start;
				for (int loopsY = 0; loopsY < size; loopsY++)
				{
					if (canHarvestBlock(world.getBlockState(new BlockPos(x + sx, y + sy, z)))
							|| EFFECTIVE.contains(material))
					{
						Block.spawnDrops(world.getBlockState(new BlockPos(x + sx, y + sy, z)), world,
								new BlockPos(x + sx, y + sy, z));
						world.destroyBlock(new BlockPos(x + sx, y + sy, z), false);
					}
					sy++;
				}
				sx++;
			}
		}
		else if (entity.getHorizontalFacing() == Direction.WEST || entity.getHorizontalFacing() == Direction.EAST)
		{
			for (int loopsZ = 0; loopsZ < size; loopsZ++)
			{
				sy = start;
				for (int loopsY = 0; loopsY < size; loopsY++)
				{
					if (canHarvestBlock(world.getBlockState(new BlockPos(x, y + sy, z + sz)))
							|| EFFECTIVE.contains(material))
					{
						Block.spawnDrops(world.getBlockState(new BlockPos(x, y + sy, z + sz)), world,
								new BlockPos(x, y + sy, z + sz));
						world.destroyBlock(new BlockPos(x, y + sy, z + sz), false);
					}
					sy++;
				}
				sz++;
			}
		}

		return retval;
	}
}
