package com.wjd.instructions.conversions;

import com.wjd.instructions.base.NoOperandsInstruction;
import com.wjd.rtda.stack.Frame;

/**
 * double 转 long
 * @since 2022/1/29
 */
public class D2L extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        double d = frame.getOpStack().popDouble();
        long l = (long) d;
        frame.getOpStack().pushLong(l);
    }
}
