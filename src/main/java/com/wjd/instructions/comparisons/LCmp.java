package com.wjd.instructions.comparisons;

import com.wjd.instructions.constants.NoOperandsInstruction;
import com.wjd.rtda.Frame;

/**
 * long 比较
 * @since 2022/1/29
 */
public class LCmp extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        long v2 = frame.getOperandStack().popLong();
        long v1 = frame.getOperandStack().popLong();
        int v = CmpUtil.cmpLong(v1, v2);
        frame.getOperandStack().pushInt(v);
    }
}