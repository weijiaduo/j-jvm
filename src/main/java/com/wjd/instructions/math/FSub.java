package com.wjd.instructions.math;

import com.wjd.instructions.base.NoOperandsInstruction;
import com.wjd.rtda.stack.Frame;

/**
 * 减法
 * @since 2021/12/5
 */
public class FSub extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        float val1 = frame.getOpStack().popFloat();
        float val2 = frame.getOpStack().popFloat();
        float val = val2 - val1;
        frame.getOpStack().pushFloat(val);
    }
}
