package com.iney.registerer;

import net.minecraft.item.Item;
import net.minecraft.item.Item.Settings;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

/**
 * 物品注册处理器
 * 负责物品的注册与翻译绑定，由 {@link Registerer} 委托调用。
 */
public class ItemRegisterHandler {
    /** 默认物品构造器，使用无参的 Item 构造函数 */
    public static final Function<Settings, Item> NORMAL_CONSTRUCTOR = Item::new;

    private final String modId;
    private final List<Item> items;
    private final HashMap<Item, String> translations;

    public ItemRegisterHandler(String modId, List<Item> items, HashMap<Item, String> translations) {
        this.modId = modId;
        this.items = items;
        this.translations = translations;
    }

    /**
     * 注册一个默认设置的主物品
     * @param name 物品名称
     * @return 注册后的 Item 实例
     */
    public Item register(String name) {
        return registerWithSetting(name, NORMAL_CONSTRUCTOR, new Settings());
    }

    /**
     * 注册一个默认设置的主物品，并绑定翻译文本
     * @param name 物品名称
     * @param translation 翻译文本
     * @return 注册后的 Item 实例
     */
    public Item register(String name, String translation) {
        Item item = register(name);
        translations.put(item, translation);
        return item;
    }

    /**
     * 使用自定义构造函数和设置注册物品
     * @param name 物品名称
     * @param constructor 物品构造函数
     * @param settings 物品设置
     * @return 注册后的 Item 实例
     */
    public Item registerWithSetting(String name, Function<Settings, Item> constructor, Settings settings) {
        RegistryKey<Item> registryKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(modId, name));
        settings.registryKey(registryKey);
        return registerItem(name, constructor.apply(settings));
    }

    /**
     * 使用自定义构造函数和设置注册物品，并绑定翻译文本
     * @param name 物品名称
     * @param translation 翻译文本
     * @param constructor 物品构造函数
     * @param settings 物品设置
     * @return 注册后的 Item 实例
     */
    public Item registerWithSetting(String name, String translation, Function<Settings, Item> constructor, Settings settings) {
        Item item = registerWithSetting(name, constructor, settings);
        translations.put(item, translation);
        return item;
    }

    /**
     * 内部方法：将物品注册到系统并将其添加到物品列表中
     * @param name 物品名称
     * @param item 物品实例
     * @return 注册后的 Item 实例
     */
    private Item registerItem(String name, Item item) {
        RegistryKey<Item> registryKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(modId, name));
        Item registered = Registry.register(Registries.ITEM, registryKey, item);
        items.add(registered);
        return registered;
    }
}
