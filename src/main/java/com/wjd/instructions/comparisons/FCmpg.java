package com.wjd.instructions.comparisons;

import com.wjd.instructions.constants.NoOperandsInstruction;
import com.wjd.rtda.Frame;

/**
 * float 比较
 * @since 2022/1/29
 */
public class FCmpg extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        float v2 = frame.getOperandStack().popFloat();
        float v1 = frame.getOperandStack().popFloat();
        int v = CmpUtil.cmpFloat(v1, v2, true);
        frame.getOperandStack().pushInt(v);
    }
}