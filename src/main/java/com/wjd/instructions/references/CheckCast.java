package com.wjd.instructions.references;

import com.wjd.instructions.base.Index16Instruction;
import com.wjd.rtda.Frame;
import com.wjd.rtda.OperandStack;
import com.wjd.rtda.heap.Class;
import com.wjd.rtda.heap.ConstantPool;
import com.wjd.rtda.heap.Object;
import com.wjd.rtda.heap.cons.ClassRef;

/**
 * @since 2022/2/2
 */
public class CheckCast extends Index16Instruction {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        Object ref = stack.popRef();
        stack.pushRef(ref);
        if (ref == null) {
            return;
        }
        ConstantPool cp = frame.getMethod().getClazz().getConstantPool();
        ClassRef classRef = (ClassRef) cp.getConstant(index);
        Class clazz = classRef.resolvedClass();
        if (!ref.isInstanceOf(clazz)) {
            throw new ClassCastException("Cast Class: " + clazz);
        }
    }
}