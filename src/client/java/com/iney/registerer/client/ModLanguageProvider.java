package com.iney.registerer.client;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

/**
 * 语言文件生成器：生成 {@code lang/<languageCode>.json}，
 * 数据来自 {@link DataReceiver} 中对应语言的翻译条目。
 */
public class ModLanguageProvider extends FabricLanguageProvider {
    private final String languageCode;

    public ModLanguageProvider(FabricDataOutput dataOutput, String languageCode, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, languageCode, registryLookup);
        this.languageCode = languageCode;
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup registryLookup, TranslationBuilder translationBuilder) {
        RegisterHelperClient.getDataReceiver().getLanguage(languageCode).forEach(translationBuilder::add);
    }
}
