package com.iney.registerer.client

import net.fabricmc.api.ClientModInitializer
import net.minecraft.block.Block
import net.minecraft.item.Item

object RegisterHelperClient : ClientModInitializer {
    @JvmStatic
    val DataReceiver = DataReceiver(hashMapOf<Block, String>(), hashMapOf<Item, String>())

    /**
     * 数据生成时使用的目标语言代码，默认为 "en_us"。
     * 用户可在调用数据生成器之前修改此值，以生成对应语言的 lang 文件。
     */
    @JvmStatic
    var targetLanguageCode: String = "en_us"


	override fun onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
	}

	public fun setDataReceiver(blocks: HashMap<Block, String>, items: HashMap<Item, String>){
		DataReceiver.blockStringHashMap = blocks
		DataReceiver.itemStringHashMap = items
	}

}