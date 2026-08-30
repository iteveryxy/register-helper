package com.iney.registerer.test;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

/**
 * 完整网络包示例（双向）。
 *
 * <p>链路演示：客户端加入世界发送 {@code ping}（含数字与标志位）
 * -> 服务端校验并回显 {@code pong} -> 客户端在聊天栏显示。
 *
 * <p>定义一个网络包需要三样东西：
 * <ol>
 *   <li>{@code ID}：全网唯一的 {@link CustomPayload.Id}，命名空间建议用自己的 modId</li>
 *   <li>{@code CODEC}：编解码器，定义字段如何写入/读出 {@link PacketByteBuf}</li>
 *   <li>实现 {@link CustomPayload}，{@code getId()} 返回 ID</li>
 * </ol>
 *
 * @param message 文本消息
 * @param number  整数字段（演示多字段 codec）
 * @param flag    布尔字段（演示多字段 codec）
 */
public record TestPayload(String message, int number, boolean flag) implements CustomPayload {
    /** 1. 包的唯一标识 */
    public static final CustomPayload.Id<TestPayload> ID =
            new CustomPayload.Id<>(Identifier.of("test", "test_payload"));

    /**
     * 2. 编解码器：使用 {@link PacketCodec#tuple} 组合多字段。
     * <p>书写规则：字段 codec 与 getter 引用交替出现，顺序 = 写入顺序 = 读取顺序，
     * 最后一个参数是组合函数（通常直接引用 record 构造器）。
     * 常用内置 codec 见 {@link PacketCodecs}：STRING、VAR_INT、BOOLEAN、FLOAT、BYTE_ARRAY 等。
     * 提示：VAR_INT 比 INTEGER 更省流量，是 Minecraft 的惯例选择。
     */
    public static final PacketCodec<PacketByteBuf, TestPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.STRING, TestPayload::message,      // 写入 String
            PacketCodecs.VAR_INT, TestPayload::number,      // 写入 int（变长编码）
            PacketCodecs.BOOLEAN, TestPayload::flag,        // 写入 boolean
            TestPayload::new                                // 按相同顺序读出并构造
    );

    /** 3. 实现 getId() 返回 ID */
    @Override
    public CustomPayload.Id<? extends CustomPayload> getId() {
        return ID;
    }
}
