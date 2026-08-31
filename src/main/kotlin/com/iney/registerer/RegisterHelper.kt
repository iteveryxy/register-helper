package com.iney.registerer

import net.fabricmc.api.ModInitializer
import net.minecraft.util.Identifier
import org.slf4j.LoggerFactory

object RegisterHelper : ModInitializer {
	const val MOD_ID: String = "register-helper"

	private val LOGGER = LoggerFactory.getLogger(MOD_ID)

	override fun onInitialize() {
		LOGGER.info("RegisterHelper initialized")
	}

	fun id(path: String): Identifier
		= Identifier.of(MOD_ID, path)
}
