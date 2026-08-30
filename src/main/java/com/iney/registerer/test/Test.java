package com.iney.registerer.test;

import com.iney.registerer.ItemRegisterHandler;
import com.iney.registerer.NetworkRegisterHandler;
import com.iney.registerer.Registerer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.Item;

import java.util.Map;

public class Test {
    public static void onInit(){
        Registerer registerer = new Registerer("test");

        // String 重载绑定默认语言（Registerer.DEFAULT_LANGUAGE，默认 en_us）
        registerer.itemHandler.registerWithSetting("item1", "Test Item 1", ItemRegisterHandler.NORMAL_CONSTRUCTOR, new Item.Settings());
        Item item2 = registerer.itemHandler.register("item2", "Test Item 2");

        // Map 重载绑定多语言
        registerer.itemHandler.registerWithSetting("shear", Map.of(
                "en_us", "Test Shears",
                "zh_cn", "测试剪刀"
        ), TestItem::new, new Item.Settings().maxCount(1).maxDamage(100));

        registerer.blockHandler.register("block1", Map.of(
                "en_us", "Test Block",
                "zh_cn", "测试方块"
        ));

        // C2S 网络包：客户端发送、服务端处理（自动完成 codec 注册 + receiver 注册）
        // 服务端示例逻辑：校验数据后回显给发送者
        NetworkRegisterHandler.registerC2S(TestPayload.ID, TestPayload.CODEC,
                (payload, context) -> {
                    if (!payload.flag()) return; // 演示：flag 为 false 时忽略该包
                    String reply = "pong: " + payload.message() + " (number=" + payload.number() + ")";
                    ServerPlayNetworking.send(context.player(), new TestPayload(reply, payload.number(), true));
                });

        // 最后一个注册，确保所有物品已经加载
        registerer.initItemGroup(Map.of(
                "en_us", "Test Group",
                "zh_cn", "测试物品组"
        ), "test", item2);
    }
}
