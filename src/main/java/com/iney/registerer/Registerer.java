package com.iney.registerer;

import net.minecraft.item.Item;
import net.minecraft.item.Item.Settings;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.function.Function;

/**
 * 物品注册器
 * 用于管理 Minecraft 模组中物品的注册与物品栏组创建。
 */
public class Registerer {
    /** 默认物品构造器，使用无参的 Item 构造函数 */
    public static final Function<Settings, Item> NORMAL_CONSTRUCTOR = Item::new;

    /** 模组 ID */
    public final String MOD_ID;
    /** 已注册的物品列表 */
    public List<Item> items = new java.util.ArrayList<>();
    /** 物品栏组构建器 */
    public ItemGroupBuilder itemGroupBuilder;

    /**
     * 创建注册器
     * @param mod_id 模组 ID
     */
    public Registerer(String mod_id) {
        MOD_ID = mod_id;
    }

    /**
     * 初始化并注册物品栏组
     * @param displayName 显示名称
     * @param identifierName 标识符名称
     * @param icon 图标物品
     * @return 注册后的 ItemGroup 实例
     */
    public ItemGroup initItemGroup(String displayName, String identifierName,Item icon){
        itemGroupBuilder = new ItemGroupBuilder(displayName,MOD_ID,identifierName);
        itemGroupBuilder.setIcon(icon);
        itemGroupBuilder.submit(items);
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
