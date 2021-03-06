package com.wjd.instructions.comparisons;

import com.wjd.instructions.base.BranchInstruction;
import com.wjd.rtda.stack.Frame;

/**
 * 等于 null 跳转
 * @since 2022/1/29
 */
public class IfNonNull extends BranchInstruction {

    @Override
    public void execute(Frame frame) {
        Object v1 = frame.getOpStack().popRef();
        if (v1 != null) {
            branch(frame);
        }
    }

}
