package org.sunyata.game.server.message;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * Created by leo on 17/4/17.
 */
public abstract class AbstractOctopusRawMessage implements OctopusRawMessage {
    private int cmd;//0 4
    private int serial;//1 4
    private float version = 1.0f;//2 4

    private int destServerId = -1;
    private int length;//3 4
    private byte[] bytes;

    @Override
    public int getDestServerId() {
        return destServerId;
    }

    public AbstractOctopusRawMessage setDestServerId(int destServerId) {
        this.destServerId = destServerId;
        return this;
    }

//    public int getLength() {
//        return length;
//    }

//    public AbstractOctopusRawMessage setLength(int length) {
//        this.length = length;
//        return this;
//    }


    public AbstractOctopusRawMessage setCmd(int cmd) {
        this.cmd = cmd;
        return this;
    }

    public int getCmd() {
        return cmd;
    }

    public AbstractOctopusRawMessage setSerial(int serial) {
        this.serial = serial;
        return this;
    }

    public int getSerial() {
        return serial;
    }

    public AbstractOctopusRawMessage setVersion(float version) {
        this.version = version;
        return this;
    }

    public float getVersion() {
        return version;
    }

    public AbstractOctopusRawMessage setBody(byte[] bytes) {
        this.bytes = bytes;
        return this;
    }

    public byte[] getBody() {
        return bytes;
    }

    @Override
    public OctopusRawMessage fromBytes(byte[] byteArray) {
        ByteBuf byteBuf = Unpooled.wrappedBuffer(byteArray);
        return fromByteBuf(byteBuf);

    }

    @Override
    public OctopusRawMessage fromByteBuf(ByteBuf buf) {
        this.setCmd(buf.readInt());//4
        this.setSerial(buf.readInt());//4
        this.setVersion(buf.readFloat());//4
        this.setDestServerId(buf.readInt());//4
        int length = buf.readInt();
        this.setBody(null);
        if (length > 0) {
            byte[] bytes = new byte[length];
            buf.readBytes(bytes, 0, length);
            this.setBody(bytes);
        }
        return this;
    }

    @Override
    public byte[] toBytes() {
        ByteBuf buffer = Unpooled.buffer();
        encode(buffer);
//        buffer.writeInt(this.getCmd());
//        buffer.writeInt(this.getSerial());
//        buffer.writeFloat(this.getVersion());
//        buffer.writeInt(this.getDestServerId());
//        buffer.writeInt(this.getLength());
//        buffer.writeBytes(bytes);
        byte[] bytes = new byte[buffer.readableBytes()];
        buffer.readBytes(bytes);
        return bytes;
    }

    @Override
    public void encode(ByteBuf buffer) {
        buffer.writeInt(this.getCmd());
        buffer.writeInt(this.getSerial());
        buffer.writeFloat(this.getVersion());
        buffer.writeInt(this.getDestServerId());
//        buffer.writeInt(this.getLength());
//        buffer.writeBytes(bytes);
        encodeBody(buffer);
    }

    public abstract void encodeBody(ByteBuf buf);

}
