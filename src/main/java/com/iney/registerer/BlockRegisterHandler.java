package com.iney.registerer;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Settings;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * 方块注册处理器
 * 负责方块的注册、对应 BlockItem 的注册与翻译绑定，由 {@link Registerer} 委托调用。
 */
public class BlockRegisterHandler {
    /** 默认方块构造器，使用无参的 Block 构造函数 */
    public static final Function<AbstractBlock.Settings, Block> NORMAL_CONSTRUCTOR = Block::new;

    private final String modId;
    private final List<Item> items;
    private final HashMap<Block, HashMap<String, String>> translations;

    public BlockRegisterHandler(String modId, List<Item> items, HashMap<Block, HashMap<String, String>> translations) {
        this.modId = modId;
        this.items = items;
        this.translations = translations;
    }

    /**
     * 注册一个默认设置的方块（同时注册对应 BlockItem）
     * @param name 方块名称
     * @return 注册后的 Block 实例
     */
    public Block register(String name) {
        return registerWithSetting(name, NORMAL_CONSTRUCTOR, AbstractBlock.Settings.create());
    }

    /**
     * 注册一个默认设置的方块，并绑定默认语言（{@link Registerer#DEFAULT_LANGUAGE}）的翻译文本
     * @param name 方块名称
     * @param translation 翻译文本
     * @return 注册后的 Block 实例
     */
    public Block register(String name, String translation) {
        return register(name, Map.of(Registerer.DEFAULT_LANGUAGE, translation));
    }

    /**
     * 注册一个默认设置的方块，并绑定多语言翻译文本
     * @param name 方块名称
     * @param translations 翻译映射：语言代码 -> 翻译文本
     * @return 注册后的 Block 实例
     */
    public Block register(String name, Map<String, String> translations) {
        Block block = register(name);
        this.translations.put(block, new HashMap<>(translations));
        return block;
    }

    /**
     * 使用自定义构造函数和设置注册方块（同时注册对应 BlockItem）
     * @param name 方块名称
     * @param constructor 方块构造函数
     * @param settings 方块设置
     * @return 注册后的 Block 实例
     */
    public Block registerWithSetting(String name, Function<AbstractBlock.Settings, Block> constructor, AbstractBlock.Settings settings) {
        RegistryKey<Block> registryKey = RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(modId, name));
        RegistryKey<Item> itemRegistryKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(modId, name));
        settings.registryKey(registryKey);
        Block block = constructor.apply(settings);
        Registry.register(Registries.BLOCK, registryKey, block);
        BlockItem blockItem = new BlockItem(block, new Settings().registryKey(itemRegistryKey));
        Registry.register(Registries.ITEM, itemRegistryKey, blockItem);
        items.add(blockItem);
        return block;
    }

    /**
     * 使用自定义构造函数和设置注册方块，并绑定默认语言（{@link Registerer#DEFAULT_LANGUAGE}）的翻译文本
     * @param name 方块名称
     * @param translation 翻译文本
     * @param constructor 方块构造函数
     * @param settings 方块设置
     * @return 注册后的 Block 实例
     */
    public Block registerWithSetting(String name, String translation, Function<AbstractBlock.Settings, Block> constructor, AbstractBlock.Settings settings) {
        return registerWithSetting(name, Map.of(Registerer.DEFAULT_LANGUAGE, translation), constructor, settings);
    }

    /**
     * 使用自定义构造函数和设置注册方块，并绑定多语言翻译文本
     * @param name 方块名称
     * @param translations 翻译映射：语言代码 -> 翻译文本
     * @param constructor 方块构造函数
     * @param settings 方块设置
     * @return 注册后的 Block 实例
     */
    public Block registerWithSetting(String name, Map<String, String> translations, Function<AbstractBlock.Settings, Block> constructor, AbstractBlock.Settings settings) {
        Block block = registerWithSetting(name, constructor, settings);
        this.translations.put(block, new HashMap<>(translations));
        return block;
    }
}
