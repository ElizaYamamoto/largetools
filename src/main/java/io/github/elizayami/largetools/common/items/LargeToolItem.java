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
		
		boolean hoe = stack.getToolTypes().contains(ToolType.HOE);
		boolean shovel = stack.getToolTypes().contains(ToolType.SHOVEL);
		boolean axe = stack.getToolTypes().contains(ToolType.AXE);

		if (strippable != null && axe)
		{
			world.playSound(player, pos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);
			if (!world.isRemote)
			{
				rightClick(stack, world, strippable, pos, player);
				success = true;
			}
		}
		else if (context.getFace() != Direction.DOWN && world.getBlockState(pos.up()).isAir(world, pos.up()))
		{
			if (hoe)
			{
				if (!shovel || (shovel && player.isSneaking() && tillable != null))
				{
					world.playSound(player, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
					if (!world.isRemote)
					{
						rightClick(stack, world, tillable, pos, player);
						success = true;
					}
				}
			}
			if (shovel)
			{
				if (!hoe || (!hoe && !player.isSneaking() && pathable != null))
				{
					world.playSound(player, pos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
					if (!world.isRemote)
					{
						rightClick(stack, world, pathable, pos, player);
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

	public void rightClick(ItemStack stack, World world, BlockState state, BlockPos pos, PlayerEntity player)
	{
		BlockState strippable = state.getToolModifiedState(world, pos, player, stack, ToolType.AXE);

		BlockState tillable = state.getToolModifiedState(world, pos, player, stack, ToolType.HOE);

		BlockState pathable = state.getToolModifiedState(world, pos, player, stack, ToolType.SHOVEL);

		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();

		int expandLevel = 0;

		if (ItemUtils.hasEnchantment(stack, EnchantmentInit.EXPAND.get()))
		{
			expandLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.EXPAND.get(), stack);
		}

		int size = 3 + (expandLevel * 2);

		double start = -1 - expandLevel;
		double sx = -1;
		double sy = -1;
		double sz = -1;

		if ((player.rotationPitch > 40 || player.rotationPitch < -40) || state != strippable)
		{
			for (int loopsX = 0; loopsX < size; loopsX++)
			{
				sz = start;
				for (int loopsZ = 0; loopsZ < size; loopsZ++)
				{
					BlockState block = world.getBlockState(new BlockPos(x + sx, y, z + sz));
					if (block.getToolModifiedState(world, new BlockPos(x + sx, y, z + sz), player, stack, ToolType.AXE) != null 
							|| block.getToolModifiedState(world, new BlockPos(x + sx, y, z + sz), player, stack, ToolType.HOE) != null
							|| block.getToolModifiedState(world, new BlockPos(x + sx, y, z + sz), player, stack, ToolType.SHOVEL) != null)
					{
						world.setBlockState(new BlockPos(x + sx, y, z + sz), state, 11);
					}
					sz++;
				}
				sx++;
			}
		}
		else if (player.getHorizontalFacing() == Direction.NORTH || player.getHorizontalFacing() == Direction.SOUTH)
		{
			for (int loopsX = 0; loopsX < size; loopsX++)
			{
				sy = start;
				for (int loopsY = 0; loopsY < size; loopsY++)
				{
					BlockState block = world.getBlockState(new BlockPos(x + sx, y + sy, z));
					if (block.getToolModifiedState(world, new BlockPos(x + sx, y + sy, z), player, stack, ToolType.AXE) != null)
					{
						world.setBlockState(new BlockPos(x + sx, y + sy, z), state, 11);
					}
					sy++;
				}
				sx++;
			}
		}
		else if (player.getHorizontalFacing() == Direction.WEST || player.getHorizontalFacing() == Direction.EAST)
		{
			for (int loopsZ = 0; loopsZ < size; loopsZ++)
			{
				sy = start;
				for (int loopsY = 0; loopsY < size; loopsY++)
				{
					BlockState block = world.getBlockState(new BlockPos(x, y + sy, z + sz));
					if (block.getToolModifiedState(world, new BlockPos(x, y + sy, z + sz), player, stack, ToolType.AXE) != null)
					{
						world.setBlockState(new BlockPos(x, y + sy, z + sz), state, 11);
					}
					sy++;
				}
				sz++;
			}
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
