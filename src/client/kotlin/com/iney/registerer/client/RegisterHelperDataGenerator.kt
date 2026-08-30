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
		// 为每个目标语言注册一个语言生成器：
		// targetLanguages 为空时自动生成所有已绑定翻译的语言
		RegisterHelperClient.resolveTargetLanguages().forEach { languageCode ->
			pack.addProvider { output, registryLookup ->
				ModLanguageProvider(output, languageCode, registryLookup)
			}
		}
	}
}
