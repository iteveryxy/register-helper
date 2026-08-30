package com.iney.registerer.client;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

import java.util.function.BiConsumer;

/**
 * 网络包注册处理器（客户端侧）
 * 自动完成 PayloadTypeRegistry 的 codec 注册与客户端 receiver 注册。
 *
 * <p>S2C 包（服务端发送、客户端处理）必须在 client 源集的初始化中调用 {@link #registerS2C}，
 * 以避免专用服务器加载客户端类导致崩溃。
 */
public class ClientNetworkRegisterHandler {
    /**
     * 注册一个 S2C（服务端到客户端）网络包
     * @param id 包的 {@link CustomPayload.Id}
     * @param codec 包的编解码器
     * @param clientHandler 客户端处理逻辑（在客户端线程执行）
     * @param <P> 包类型
     */
    public static <P extends CustomPayload> void registerS2C(
            CustomPayload.Id<P> id,
            PacketCodec<? super RegistryByteBuf, P> codec,
            BiConsumer<P, ClientPlayNetworking.Context> clientHandler
    ) {
        PayloadTypeRegistry.playS2C().register(id, codec);
        ClientPlayNetworking.registerGlobalReceiver(id, clientHandler::accept);
    }
}
