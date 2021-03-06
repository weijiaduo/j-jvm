package com.wjd.rtda.meta.cons;

import com.wjd.classfile.cons.ClassConstantInfo;
import com.wjd.rtda.meta.ConstantPool;

/**
 * 类引用常量
 * @since 2022/1/30
 */
public class ClassRef extends SymbolRef {

    public static ClassRef newClassRef(ConstantPool constantPool, ClassConstantInfo classConstantInfo) {
        ClassRef classRef = new ClassRef();
        classRef.constantPool = constantPool;
        classRef.className = classConstantInfo.getName();
        return classRef;
    }

}
