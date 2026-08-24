package com.iney.registerer.client

import com.iney.registerer.Registerer
import net.fabricmc.api.ClientModInitializer
import net.minecraft.block.Block
import net.minecraft.item.Item

object RegisterHelperClient : ClientModInitializer {
    @JvmStatic
    val DataReceiver = DataReceiver(hashMapOf<Block, String>(), hashMapOf<Item, String>(), hashMapOf())

    /**
     * 数据生成时使用的目标语言代码，默认为 "en_us"。
     * 用户可在调用数据生成器之前修改此值，以生成对应语言的 lang 文件。
     */
    @JvmStatic
    var targetLanguageCode: String = "en_us"


	override fun onInitializeClient() {
		runDataGenerationSetup()
	}

	/**
	 * 从所有 Registerer 实例收集翻译数据并填充到 DataReceiver。
	 * 游戏启动和数据生成时都会调用此方法。
	 */
	public fun runDataGenerationSetup() {
		val blocks = Registerer.collectAllBlockTranslations()
		val items = Registerer.collectAllItemTranslations()
		val groups = Registerer.collectAllItemGroupTranslations()
		setDataReceiver(blocks, items, groups)
	}

	public fun setDataReceiver(
		blocks: HashMap<Block, String>,
		items: HashMap<Item, String>,
		stringEntries: HashMap<String, String>
	){
		DataReceiver.blockStringHashMap = blocks
		DataReceiver.itemStringHashMap = items
		DataReceiver.stringStringHashMap = stringEntries
	}

}