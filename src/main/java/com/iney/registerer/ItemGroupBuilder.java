package com.iney.registerer;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;

/**
 * 物品栏组构建器
 * 用于构建并注册 Minecraft 的物品栏组（ItemGroup），支持链式调用设置图标和物品列表。
 */
public class ItemGroupBuilder {
    /** 物品栏组显示名称 */
    public String name;
    /** 物品栏组标识符名称（用于生成 Identifier） */
    public String identifierName;
    /** 模组 ID */
    public String MOD_ID;
    /** 物品栏组图标物品 */
    public Item icon;

    /** 物品栏组中包含的物品堆栈列表 */
    public List<ItemStack> itemStacks;


    /**
     * 构造物品栏组构建器
     * @param name 显示名称
     * @param MOD_ID 模组 ID
     * @param identifierName 标识符名称
     */
    public ItemGroupBuilder(String name,String MOD_ID, String identifierName) {
        this.name = name;
        this.MOD_ID = MOD_ID;
        this.identifierName = identifierName;
    }

    /**
     * 设置物品栏组的图标物品
     * @param itemAsIcon 用作图标的物品
     */
    public void setIcon(Item itemAsIcon) {
        this.icon = itemAsIcon;
    }

    /**
     * 提交物品列表，将其转换为 ItemStack 列表
     * @param items 物品列表
     */
    public void submit(List<Item> items){
        itemStacks = items.stream().map(Item::getDefaultStack).toList();
    }



    /**
     * 构建并注册物品栏组
     * @return 注册后的 ItemGroup 实例
     * @throws RuntimeException 如果未调用 submit() 或 setIcon()
     */
    public ItemGroup build(){
        if (itemStacks == null)
            throw new RuntimeException("ItemGroupBuilder must be submitted with items before building");

        if (icon == null)
            throw new RuntimeException("ItemGroupBuilder must be setIconed with an icon before building");


        ItemGroup build = FabricItemGroup.builder().displayName(Text.of(name))
                .icon(icon::getDefaultStack)
                .entries(this::pack)
                .build();

        RegistryKey<ItemGroup> registryKey = RegistryKey.of(RegistryKeys.ITEM_GROUP, Identifier.of(MOD_ID, identifierName));

        return Registry.register(Registries.ITEM_GROUP, registryKey, build);
    }

    /**
     * 将物品堆栈添加到物品栏组条目中
     * @param displayContext 显示上下文
     * @param entries 条目容器
     */
    private void pack(ItemGroup.DisplayContext displayContext, ItemGroup.Entries entries) {
        entries.addAll(itemStacks);
    }
}
