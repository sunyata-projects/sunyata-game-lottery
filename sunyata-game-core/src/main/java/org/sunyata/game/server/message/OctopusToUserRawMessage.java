package org.sunyata.game.server.message;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * Created by leo on 17/4/17.
 */
public class OctopusToUserRawMessage implements OctopusRawMessage {

    private int cmd;//0 4
    private int serial;//1 4
    private int code;
    private int length;//3 4
    private byte[] bytes;

    private float version = 1.0f;//2 4
    private int destServerId = -1;


    public int getCode() {
        return code;
    }

    public OctopusToUserRawMessage setCode(int code) {
        this.code = code;
        return this;
    }


    @Override
    public int getDestServerId() {
        return destServerId;
    }

    public OctopusRawMessage setDestServerId(int destServerId) {
        this.destServerId = destServerId;
        return this;
    }

    public int getLength() {
        return length;
    }

    public OctopusRawMessage setLength(int length) {
        this.length = length;
        return this;
    }


    public OctopusRawMessage setCmd(int cmd) {
        this.cmd = cmd;
        return this;
    }

    public int getCmd() {
        return cmd;
    }

    public OctopusRawMessage setSerial(int serial) {
        this.serial = serial;
        return this;
    }

    public int getSerial() {
        return serial;
    }

    public OctopusRawMessage setVersion(float version) {
        this.version = version;
        return this;
    }

    public float getVersion() {
        return version;
    }

    public OctopusRawMessage setBody(byte[] bytes) {
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
        //this.setVersion(buf.readFloat());//4
        //this.setDestServerId(buf.readInt());//4
        this.setCode(buf.readInt());
        this.setLength(buf.readInt());//4
        int length = this.getLength();
        byte[] bytes = new byte[length];
        this.setBody(null);
        if (length != 0) {
            buf.readBytes(bytes, 0, length);
            this.setBody(bytes);
        }

        return this;
    }

    @Override
    public byte[] toBytes() {
        ByteBuf buffer = Unpooled.buffer();
        buffer.writeInt(this.getCmd());
        buffer.writeInt(this.getSerial());
//        buffer.writeFloat(this.getVersion());
//        buffer.writeInt(this.getDestServerId());
        buffer.writeInt(this.getCode());
        //buffer.writeInt(this.getLength());
        if (bytes != null) {
            buffer.writeInt(bytes.length);
            buffer.writeBytes(bytes);
        } else {
            buffer.writeInt(0);
        }
        byte[] bytes = new byte[buffer.readableBytes()];
        buffer.readBytes(bytes);
        return bytes;
    }

    @Override
    public void encode(ByteBuf buffer) {
        buffer.writeInt(this.getCmd());
        buffer.writeInt(this.getSerial());
//        buffer.writeFloat(this.getVersion());
//        buffer.writeInt(this.getDestServerId());
        buffer.writeInt(this.getCode());
        buffer.writeInt(this.getLength());
        buffer.writeBytes(bytes);
//        byte[] bytes = new byte[buffer.readableBytes()];
//        buffer.readBytes(bytes);
//        return bytes;
    }

}
