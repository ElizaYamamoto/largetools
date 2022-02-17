package io.github.elizayami.largetools.common.data;

import io.github.elizayami.largetools.LargeTools;
import io.github.elizayami.largetools.common.ItemInit;
import io.github.elizayami.largetools.common.VanillaMaterial;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModels extends ItemModelProvider
{
	public ModItemModels(DataGenerator generator, ExistingFileHelper existingFileHelper)
	{
		super(generator, LargeTools.MOD_ID, existingFileHelper);
	}

	@Override
	protected void registerModels()
	{
		registerVanillaMaterialItemModels(ItemInit.WOOD);
		registerVanillaMaterialItemModels(ItemInit.STONE);
		registerVanillaMaterialItemModels(ItemInit.IRON);
		registerVanillaMaterialItemModels(ItemInit.GOLD);
		registerVanillaMaterialItemModels(ItemInit.DIAMOND);
		registerVanillaMaterialItemModels(ItemInit.NETHERITE);
	}

	private void registerVanillaMaterialItemModels(VanillaMaterial wood)
	{
		// Tools and armor
		getBuilder(wood.name + "_paxel").parent(new ModelFile.UncheckedModelFile("item/handheld")).texture("layer0",
				modLoc("item/" + wood.name + "_paxel"));

		getBuilder(wood.name + "_hammer").parent(new ModelFile.UncheckedModelFile("item/handheld")).texture("layer0",
				modLoc("item/" + wood.name + "_hammer"));

		getBuilder(wood.name + "_saw").parent(new ModelFile.UncheckedModelFile("item/handheld")).texture("layer0",
				modLoc("item/" + wood.name + "_saw"));

		getBuilder(wood.name + "_backhoe").parent(new ModelFile.UncheckedModelFile("item/handheld")).texture("layer0",
				modLoc("item/" + wood.name + "_backhoe"));

		getBuilder(wood.name + "_tiller").parent(new ModelFile.UncheckedModelFile("item/handheld")).texture("layer0",
				modLoc("item/" + wood.name + "_tiller"));

		getBuilder(wood.name + "_backhaw").parent(new ModelFile.UncheckedModelFile("item/handheld")).texture("layer0",
				modLoc("item/" + wood.name + "_backhaw"));
	}
}
