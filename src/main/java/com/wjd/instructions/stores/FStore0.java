package com.wjd.instructions.stores;

import com.wjd.instructions.base.NoOperandsInstruction;
import com.wjd.rtda.stack.Frame;

/**
 * 把指定位置的本地变量推入栈顶
 *
 * @since 2021/12/1
 */
public class FStore0 extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        StoreUtil.fstore(frame, 0);
    }

}
