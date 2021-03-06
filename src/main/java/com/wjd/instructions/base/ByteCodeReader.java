package com.wjd.instructions.base;

import com.wjd.classfile.type.Uint16;
import com.wjd.classfile.type.Uint32;
import com.wjd.classfile.type.Uint8;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * @since 2021/11/6
 */
public class ByteCodeReader {

    /** 字节码字节数组 */
    private byte[] bytes;
    /** 字节缓冲读取 */
    private ByteBuffer buf;

    public ByteCodeReader() {}

    public ByteCodeReader(byte[] bytes) {
        reset(bytes, 0);
    }

    public void reset(byte[] bytes, int position) {
        this.bytes = bytes;
        buf = ByteBuffer.wrap(bytes);
        buf.order(ByteOrder.BIG_ENDIAN); // 大端
        buf.position(position);
    }

    public void setPosition(int position) {
        buf.position(position);
    }

    public int getPosition() {
        return buf.position();
    }

    public byte readByte() {
        return buf.get();
    }

    public int readShort() {
        return buf.getShort();
    }

    public int readInt() {
        return buf.getInt();
    }

    public long readLong() {
        return buf.getLong();
    }

    public float readFloat() {
        return buf.getFloat();
    }

    public double readDouble() {
        return buf.getDouble();
    }

    public int readInt8() {
        return readByte();
    }

    public Uint8 readUint8() {
        byte b = buf.get();
        int val = 0x0FF & b;
        return new Uint8(val);
    }

    public int readInt16() {
        return readShort();
    }

    public Uint16 readUint16() {
        short s = buf.getShort();
        int val = 0x0FFFF & s;
        return new Uint16(val);
    }

    public Uint32 readUint32() {
        int i = buf.getInt();
        long val = 0x0FFFFFFFFL & i;
        return new Uint32(val);
    }

    public byte[] readBytes(Uint32 length) {
        int len = (int) length.value();
        byte[] bytes = new byte[len];
        buf.get(bytes);
        return bytes;
    }

    public int[] readInts(int length) {
        int[] ints = new int[length];
        for (int i = 0; i < length; i++) {
            ints[i] = readInt();
        }
        return ints;
    }

    public void skipPadding() {
        while (getPosition() % 4 != 0) {
            readUint8();
        }
    }

}
