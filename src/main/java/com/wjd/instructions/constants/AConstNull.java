package com.wjd.instructions.constants;

import com.wjd.instructions.base.NoOperandsInstruction;
import com.wjd.rtda.stack.Frame;

/**
 * 把null推入栈顶
 *
 * @since 2021/12/1
 */
public class AConstNull extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        frame.getOpStack().pushRef(null);
    }

}
