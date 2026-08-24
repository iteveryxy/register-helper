package com.iney.registerer;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Settings;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

/**
 * 物品注册器
 * 用于管理 Minecraft 模组中物品的注册与物品栏组创建。
 */
public class Registerer {
    /** 默认物品构造器，使用无参的 Item 构造函数 */
    public static final Function<Settings, Item> NORMAL_CONSTRUCTOR = Item::new;

    /** 所有 Registerer 实例，供客户端数据生成器收集翻译数据 */
    private static final List<Registerer> instances = new ArrayList<>();

    /** 模组 ID */
    public final String MOD_ID;
    /** 已注册的物品列表 */
    public List<Item> items = new ArrayList<>();
    /** 物品栏组构建器 */
    public ItemGroupBuilder itemGroupBuilder;
    /** 物品翻译映射：Item -> 翻译文本 */
    public HashMap<Item, String> itemTranslations = new HashMap<>();
    /** 方块翻译映射：Block -> 翻译文本 */
    public HashMap<Block, String> blockTranslations = new HashMap<>();
    /** 物品栏组翻译映射：翻译键 -> 翻译文本 */
    public HashMap<String, String> itemGroupTranslations = new HashMap<>();

    /**
     * 创建注册器
     * @param mod_id 模组 ID
     */
    public Registerer(String mod_id) {
        MOD_ID = mod_id;
        instances.add(this);
    }

    /** 收集所有 Registerer 实例的物品翻译数据 */
    public static HashMap<Item, String> collectAllItemTranslations() {
        HashMap<Item, String> all = new HashMap<>();
        for (Registerer r : instances) {
            all.putAll(r.itemTranslations);
        }
        return all;
    }

    /** 收集所有 Registerer 实例的方块翻译数据 */
    public static HashMap<Block, String> collectAllBlockTranslations() {
        HashMap<Block, String> all = new HashMap<>();
        for (Registerer r : instances) {
            all.putAll(r.blockTranslations);
        }
        return all;
    }

    /** 收集所有 Registerer 实例的物品栏组翻译数据 */
    public static HashMap<String, String> collectAllItemGroupTranslations() {
        HashMap<String, String> all = new HashMap<>();
        for (Registerer r : instances) {
            all.putAll(r.itemGroupTranslations);
        }
        return all;
    }

    /**
     * 初始化并注册物品栏组，displayName 同时作为翻译文本。
     * 生成的翻译键为 {@code itemGroup.<MOD_ID>.<identifierName>}。
     * @param displayName 显示名称（翻译文本）
     * @param identifierName 标识符名称
     * @param icon 图标物品
     * @return 注册后的 ItemGroup 实例
     */
    public ItemGroup initItemGroup(String displayName, String identifierName,Item icon){
        itemGroupBuilder = new ItemGroupBuilder(displayName,MOD_ID,identifierName);
        itemGroupBuilder.setIcon(icon);
        itemGroupBuilder.submit(items);
        String translationKey = "itemGroup." + MOD_ID + "." + identifierName;
        itemGroupTranslations.put(translationKey, displayName);
        return itemGroupBuilder.build();
    }

    /**
     * 获取模组 ID
     * @return 模组 ID
     */
    public String getModId() {
        return MOD_ID;
    }

    /**
     * 注册一个默认设置的主物品
     * @param name 物品名称
     * @return 注册后的 Item 实例
     */
    public Item register(String name){
        return registerWithSetting(name, NORMAL_CONSTRUCTOR, new Settings());
    }

    /**
     * 注册一个默认设置的主物品，并绑定翻译文本
     * @param name 物品名称
     * @param translation 翻译文本
     * @return 注册后的 Item 实例
     */
    public Item register(String name, String translation){
        Item item = register(name);
        itemTranslations.put(item, translation);
        return item;
    }

    /**
     * 使用自定义构造函数和设置注册物品
     * @param name 物品名称
     * @param constructor 物品构造函数
     * @param settings 物品设置
     * @return 注册后的 Item 实例
     */
    public Item registerWithSetting(String name, Function<Settings, Item> constructor, Settings settings){
        RegistryKey<Item> registryKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(MOD_ID,name));
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
    public Item registerWithSetting(String name, String translation, Function<Settings, Item> constructor, Settings settings){
        Item item = registerWithSetting(name, constructor, settings);
        itemTranslations.put(item, translation);
        return item;
    }

    /**
     * 内部方法：将物品注册到系统并将其添加到物品列表中
     * @param name 物品名称
     * @param item 物品实例
     * @return 注册后的 Item 实例
     */
    private Item registerItem(String name, Item item){
        RegistryKey<Item> registryKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(MOD_ID,name));
        Item item1 = Registry.register(Registries.ITEM, registryKey, item);
        items.add(item1);
        return item1;
    }


}
