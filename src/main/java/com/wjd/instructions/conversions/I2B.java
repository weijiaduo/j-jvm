package com.wjd.instructions.conversions;

import com.wjd.instructions.base.NoOperandsInstruction;
import com.wjd.rtda.stack.Frame;

/**
 * int 转 byte
 * @since 2022/1/29
 */
public class I2B extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        int i = frame.getOpStack().popInt();
        byte b = (byte) i;
        frame.getOpStack().pushInt(b);
    }
}
