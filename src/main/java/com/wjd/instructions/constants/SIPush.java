package com.wjd.instructions.constants;

import com.wjd.instructions.base.ByteCodeReader;
import com.wjd.instructions.base.Instruction;
import com.wjd.rtda.stack.Frame;

/**
 * 读入短整型 short，转成整型 int 后，再推入栈顶
 * @since 2021/12/1
 */
public class SIPush implements Instruction {

    private int val;

    @Override
    public void execute(Frame frame) {
        frame.getOpStack().pushInt(val);
    }

    @Override
    public void fetchOperands(ByteCodeReader reader) {
        // 注意是16位有符号整数
        val = reader.readInt16();
    }
}
