package com.iney.registerer.client;

import com.iney.registerer.test.TestPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

/**
 * 客户端测试入口（client 源集），由 RegisterHelperClient 调用。
 * 演示 S2C 包的注册与 C2S 包的发送。
 */
public class TestClient {
    public static void onInitClient() {
        // S2C 包：服务端发送、客户端处理（必须在 client 源集注册，避免专用服务器崩溃）
        // handler 在客户端主线程执行，可直接操作 GUI / 世界等客户端对象
        ClientNetworkRegisterHandler.registerS2C(TestPayload.ID, TestPayload.CODEC,
                (payload, context) -> {
                    MinecraftClient client = context.client();
                    if (client.player != null) {
                        client.player.sendMessage(Text.of(payload.message()), false);
                    }
                });

        // C2S 包发送示例：必须在游戏内才能发包，
        // 因此用 JOIN 事件在加入世界后发送 "ping"，服务端会回 "pong: ..."
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) ->
                ClientPlayNetworking.send(new TestPayload("ping", 42, true)));
    }
}
