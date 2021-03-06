package com.wjd.jnative.sun.misc;

import com.wjd.jnative.NativeClass;
import com.wjd.jnative.NativeMethod;
import com.wjd.jnative.NativeRegistry;
import com.wjd.rtda.stack.Frame;

/**
 * @since 2022/2/18
 */
public class Signal implements NativeClass {

    static {
        NativeRegistry.registry("sun/misc/Signal", "findSignal",
                "(Ljava/lang/String;)I", new FindSignal());
        NativeRegistry.registry("sun/misc/Signal", "handle0",
                "(IJ)J", new Handle0());
    }

    /**
     * private static native int findSignal(String sigName);
     */
    static class FindSignal implements NativeMethod {
        @Override
        public void execute(Frame frame) throws Exception {
            frame.getOpStack().pushInt(0);
        }
    }

    /**
     * private static native long handle0(int sig, long nativeH);
     */
    static class Handle0 implements NativeMethod {
        @Override
        public void execute(Frame frame) throws Exception {
            frame.getOpStack().pushLong(0);
        }
    }

}
