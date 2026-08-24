package com.iney.registerer;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 物品注册器
 * 用于管理 Minecraft 模组中物品与方块的注册、物品栏组创建及翻译绑定。
 * 具体注册逻辑委托给 {@link ItemRegisterHandler} 和 {@link BlockRegisterHandler}。
 */
public class Registerer {
    /** 所有 Registerer 实例，供客户端数据生成器收集翻译数据 */
    private static final List<Registerer> instances = new ArrayList<>();

    /** 模组 ID */
    public final String MOD_ID;
    /** 已注册的物品列表（含 BlockItem） */
    public List<Item> items = new ArrayList<>();
    /** 物品栏组构建器 */
    public ItemGroupBuilder itemGroupBuilder;
    /** 物品翻译映射：Item -> 翻译文本 */
    public HashMap<Item, String> itemTranslations = new HashMap<>();
    /** 方块翻译映射：Block -> 翻译文本 */
    public HashMap<Block, String> blockTranslations = new HashMap<>();
    /** 物品栏组翻译映射：翻译键 -> 翻译文本 */
    public HashMap<String, String> itemGroupTranslations = new HashMap<>();

    /** 物品注册处理器 */
    public final ItemRegisterHandler itemHandler;
    /** 方块注册处理器 */
    public final BlockRegisterHandler blockHandler;

    /**
     * 创建注册器
     * @param mod_id 模组 ID
     */
    public Registerer(String mod_id) {
        MOD_ID = mod_id;
        instances.add(this);
        itemHandler = new ItemRegisterHandler(mod_id, items, itemTranslations);
        blockHandler = new BlockRegisterHandler(mod_id, items, blockTranslations);
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
    public ItemGroup initItemGroup(String displayName, String identifierName, Item icon) {
        itemGroupBuilder = new ItemGroupBuilder(displayName, MOD_ID, identifierName);
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
}
