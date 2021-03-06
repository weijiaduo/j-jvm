package com.wjd.instructions.control;

import com.wjd.instructions.base.NoOperandsInstruction;
import com.wjd.rtda.stack.Frame;
import com.wjd.rtda.Thread;
import com.wjd.rtda.heap.HeapObject;

/**
 * 返回对象引用
 * @since 2022/2/7
 */
public class AReturn extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        Thread thread = frame.getThread();
        Frame currentFrame = thread.popFrame();
        Frame invokeFrame = thread.topFrame();
        HeapObject returnVal = currentFrame.getOpStack().popRef();
        invokeFrame.getOpStack().pushRef(returnVal);
    }
}
