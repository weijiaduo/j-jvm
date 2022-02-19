package com.wjd.instructions.conversions;

import com.wjd.instructions.constants.NoOperandsInstruction;
import com.wjd.rtda.stack.Frame;

/**
 * double 转 int
 * @since 2022/1/29
 */
public class D2I extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        double d = frame.getOpStack().popDouble();
        int i = (int) d;
        frame.getOpStack().pushInt(i);
    }
}
