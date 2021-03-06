package com.wjd.jnative.java.lang;

import com.wjd.jnative.NativeClass;
import com.wjd.jnative.NativeMethod;
import com.wjd.jnative.NativeRegistry;
import com.wjd.rtda.stack.Frame;

/**
 * @since 2022/2/13
 */
public class Float implements NativeClass {

    static {
        NativeRegistry.registry("java/lang/Float", "floatToRawIntBits",
                "(F)I", new FloatToRawIntBits());
        NativeRegistry.registry("java/lang/Float", "intBitsToFloat",
                "(I)F", new IntBitsToFloat());
    }

    /**
     * public static native int floatToRawIntBits(float value);
     */
    static class FloatToRawIntBits implements NativeMethod {
        @Override
        public void execute(Frame frame) {
            float val = frame.getLocalVars().getFloat(0);
            int bits = java.lang.Float.floatToIntBits(val);
            frame.getOpStack().pushInt(bits);
        }
    }

    /**
     * public static native float intBitsToFloat(int bits);
     */
    static class IntBitsToFloat implements NativeMethod {
        @Override
        public void execute(Frame frame) {
            int bits = frame.getLocalVars().getInt(0);
            float val = java.lang.Float.intBitsToFloat(bits);
            frame.getOpStack().pushFloat(val);
        }
    }

}
