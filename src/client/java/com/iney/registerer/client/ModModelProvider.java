package com.iney.registerer.client;

import com.iney.registerer.Registerer;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.client.data.Models;
import net.minecraft.item.Item;

public class ModModelProvider extends FabricModelProvider {

    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        Registerer.getInstances().forEach(r ->
                r.blockTranslations.keySet()
                        .forEach(blockStateModelGenerator::registerSimpleCubeAll));
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        Registerer.getInstances().forEach(r ->
                r.items.forEach(item -> itemModelGenerator.register(item, Models.GENERATED)));
    }
}
