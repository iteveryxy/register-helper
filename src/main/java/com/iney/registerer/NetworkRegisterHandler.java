package com.iney.registerer;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

import java.util.function.BiConsumer;

/**
 * 网络包注册处理器（通用侧）
 * 自动完成 PayloadTypeRegistry 的 codec 注册与服务端 receiver 注册。
 *
 * <p>C2S 包（客户端发送、服务端处理）在 main 源集的初始化中调用 {@link #registerC2S}。
 * S2C 包（服务端发送、客户端处理）请在 client 源集调用
 * {@code ClientNetworkRegisterHandler.registerS2C}。
 */
public class NetworkRegisterHandler {
    /**
     * 注册一个 C2S（客户端到服务端）网络包
     * @param id 包的 {@link CustomPayload.Id}
     * @param codec 包的编解码器
     * @param serverHandler 服务端处理逻辑（在逻辑服务端线程执行）
     * @param <P> 包类型
     */
    public static <P extends CustomPayload> void registerC2S(
            CustomPayload.Id<P> id,
            PacketCodec<? super RegistryByteBuf, P> codec,
            BiConsumer<P, ServerPlayNetworking.Context> serverHandler
    ) {
        PayloadTypeRegistry.playC2S().register(id, codec);
        ServerPlayNetworking.registerGlobalReceiver(id, serverHandler::accept);
    }
}
