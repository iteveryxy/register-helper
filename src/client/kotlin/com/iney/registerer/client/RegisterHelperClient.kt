package com.iney.registerer.client

import com.iney.registerer.Registerer
import net.fabricmc.api.ClientModInitializer

object RegisterHelperClient : ClientModInitializer {
    @JvmStatic
    val DataReceiver = DataReceiver()

    /**
     * 数据生成时要生成的语言代码集合。
     * 为空集合时（默认）自动生成所有已绑定翻译的语言。
     * 用户可显式设置以限定生成范围，例如 setOf("en_us", "zh_cn")。
     */
    @JvmStatic
    var targetLanguages: MutableSet<String> = mutableSetOf()


	override fun onInitializeClient() {
		runDataGenerationSetup()
	}

	/**
	 * 从所有 Registerer 实例收集翻译数据并填充到 DataReceiver。
	 * 游戏启动和数据生成时都会调用此方法。
	 */
	public fun runDataGenerationSetup() {
		DataReceiver.languageEntries = Registerer.collectAllLanguageEntries()
	}

	/** 计算数据生成应使用的语言列表 */
	public fun resolveTargetLanguages(): Set<String> {
		val collected = DataReceiver.languageEntries.keys
		return if (targetLanguages.isEmpty()) collected else targetLanguages
	}
}
