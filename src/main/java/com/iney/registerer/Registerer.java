package com.iney.registerer;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 物品注册器
 * 用于管理 Minecraft 模组中物品与方块的注册、物品栏组创建及翻译绑定。
 * 具体注册逻辑委托给 {@link ItemRegisterHandler} 和 {@link BlockRegisterHandler}。
 */
public class Registerer {
    /** 所有 Registerer 实例，供客户端数据生成器收集翻译数据 */
    private static final List<Registerer> instances = new ArrayList<>();

    /**
     * 默认语言代码：String 翻译重载绑定的目标语言。
     * 用户可修改，例如设为 "zh_cn" 后 String 翻译直接写入 zh_cn 条目。
     */
    public static String DEFAULT_LANGUAGE = "en_us";

    /** 模组 ID */
    public final String MOD_ID;
    /** 已注册的物品列表（含 BlockItem） */
    public List<Item> items = new ArrayList<>();
    /** 物品栏组构建器 */
    public ItemGroupBuilder itemGroupBuilder;
    /** 物品翻译映射：Item -> (语言代码 -> 翻译文本) */
    public HashMap<Item, HashMap<String, String>> itemTranslations = new HashMap<>();
    /** 方块翻译映射：Block -> (语言代码 -> 翻译文本) */
    public HashMap<Block, HashMap<String, String>> blockTranslations = new HashMap<>();
    /** 物品栏组翻译映射：翻译键 -> (语言代码 -> 翻译文本) */
    public HashMap<String, HashMap<String, String>> itemGroupTranslations = new HashMap<>();

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

    /** 获取所有 Registerer 实例 */
    public static List<Registerer> getInstances() {
        return instances;
    }

    /**
     * 收集所有 Registerer 实例的翻译数据，按语言分组。
     * @return 语言代码 -> (翻译键 -> 翻译文本)
     */
    public static HashMap<String, HashMap<String, String>> collectAllLanguageEntries() {
        HashMap<String, HashMap<String, String>> byLanguage = new HashMap<>();
        for (Registerer r : instances) {
            r.itemTranslations.forEach((item, langs) ->
                    langs.forEach((lang, text) ->
                            byLanguage.computeIfAbsent(lang, k -> new HashMap<>())
                                    .put(item.getTranslationKey(), text)));
            r.blockTranslations.forEach((block, langs) ->
                    langs.forEach((lang, text) ->
                            byLanguage.computeIfAbsent(lang, k -> new HashMap<>())
                                    .put(block.getTranslationKey(), text)));
            r.itemGroupTranslations.forEach((key, langs) ->
                    langs.forEach((lang, text) ->
                            byLanguage.computeIfAbsent(lang, k -> new HashMap<>())
                                    .put(key, text)));
        }
        return byLanguage;
    }

    /**
     * 初始化并注册物品栏组，displayName 作为默认语言的翻译文本。
     * 生成的翻译键为 {@code itemGroup.<MOD_ID>.<identifierName>}。
     * @param displayName 显示名称（默认语言翻译文本）
     * @param identifierName 标识符名称
     * @param icon 图标物品
     * @return 注册后的 ItemGroup 实例
     */
    public ItemGroup initItemGroup(String displayName, String identifierName, Item icon) {
        return initItemGroup(Map.of(DEFAULT_LANGUAGE, displayName), identifierName, icon);
    }

    /**
     * 初始化并注册物品栏组，支持多语言显示名称。
     * 生成的翻译键为 {@code itemGroup.<MOD_ID>.<identifierName>}。
     * @param displayNames 显示名称映射：语言代码 -> 翻译文本
     * @param identifierName 标识符名称
     * @param icon 图标物品
     * @return 注册后的 ItemGroup 实例
     */
    public ItemGroup initItemGroup(Map<String, String> displayNames, String identifierName, Item icon) {
        itemGroupBuilder = new ItemGroupBuilder(displayNames.getOrDefault(DEFAULT_LANGUAGE, ""), MOD_ID, identifierName);
        itemGroupBuilder.setIcon(icon);
        itemGroupBuilder.submit(items);
        String translationKey = "itemGroup." + MOD_ID + "." + identifierName;
        itemGroupTranslations.put(translationKey, new HashMap<>(displayNames));
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
