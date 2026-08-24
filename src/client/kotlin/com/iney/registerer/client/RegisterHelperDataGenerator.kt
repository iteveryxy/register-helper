package com.iney.registerer.client

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput

object RegisterHelperDataGenerator : DataGeneratorEntrypoint {
	override fun onInitializeDataGenerator(fabricDataGenerator: FabricDataGenerator) {
		// datagen 会在 main/client entrypoints 之后执行，
		// 此时 Registerer 实例与翻译数据已就绪，确保收集到 DataReceiver。
		RegisterHelperClient.runDataGenerationSetup()

		val pack = fabricDataGenerator.createPack()
		pack.addProvider { output: FabricDataOutput -> ModModelProvider(output) }
		// 使用 RegistryDependentFactory 传入目标语言代码，从 RegisterHelperClient.targetLanguageCode 读取
		pack.addProvider { output, registryLookup ->
			ModLanguageProvider(output, RegisterHelperClient.targetLanguageCode, registryLookup)
		}
	}
}
