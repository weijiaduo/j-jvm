package com.wjd.instructions.control;

import com.wjd.instructions.constants.NoOperandsInstruction;
import com.wjd.rtda.Frame;
import com.wjd.rtda.Thread;

/**
 * 返回单精度浮点数
 * @since 2022/2/7
 */
public class FReturn extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        Thread thread = frame.getThread();
        Frame currentFrame = thread.popFrame();
        Frame invokeFrame = thread.topFrame();
        float returnVal = currentFrame.getOperandStack().popFloat();
        invokeFrame.getOperandStack().pushFloat(returnVal);
    }
}
