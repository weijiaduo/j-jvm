package com.wjd.instructions.conversions;

import com.wjd.instructions.base.NoOperandsInstruction;
import com.wjd.rtda.stack.Frame;

/**
 * int 转 long
 * @since 2022/1/29
 */
public class I2L extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        long l = frame.getOpStack().popInt();
        frame.getOpStack().pushLong(l);
    }
}
