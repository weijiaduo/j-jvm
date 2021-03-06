package com.wjd.instructions.conversions;

import com.wjd.instructions.base.NoOperandsInstruction;
import com.wjd.rtda.stack.Frame;

/**
 * double 转 float
 * @since 2022/1/29
 */
public class D2F extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        double d = frame.getOpStack().popDouble();
        float f = (float) d;
        frame.getOpStack().pushFloat(f);
    }
}
