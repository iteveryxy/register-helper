package com.iney.registerer.client

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput

object RegisterHelperDataGenerator : DataGeneratorEntrypoint {
	override fun onInitializeDataGenerator(fabricDataGenerator: FabricDataGenerator) {
		val pack = fabricDataGenerator.createPack()
		pack.addProvider { output: FabricDataOutput -> ModModelProvider(output) }
		// 使用 RegistryDependentFactory 传入目标语言代码，从 RegisterHelperClient.targetLanguageCode 读取
		pack.addProvider { output, registryLookup ->
			ModLanguageProvider(output, RegisterHelperClient.targetLanguageCode, registryLookup)
		}
	}
}
