package com.wjd.rtda.meta.cons;

import com.wjd.classfile.cons.InterfaceMethodRefConstantInfo;
import com.wjd.rtda.meta.ClassMeta;
import com.wjd.rtda.meta.ConstantPool;
import com.wjd.rtda.meta.MethodMeta;

/**
 * 接口方法引用
 * @since 2022/1/30
 */
public class InterfaceMethodRef extends MemberRef {

    private MethodMeta method;

    public static InterfaceMethodRef newInterfaceMethodRef(ConstantPool constantPool,
                                                           InterfaceMethodRefConstantInfo constantInfo) {
        InterfaceMethodRef ref = new InterfaceMethodRef();
        ref.constantPool = constantPool;
        ref.copyMemberRefInfo(constantInfo);
        return ref;
    }

    /**
     * 解析接口方法符号引用
     */
    public MethodMeta resolvedInterfaceMethod() {
        if (method == null) {
            resolveInterfaceMethodRef();
        }
        return method;
    }

    /**
     * 解析接口方法符号引用
     */
    private void resolveInterfaceMethodRef() {
        ClassMeta currentClazz = constantPool.getClazz(); // 当前类
        ClassMeta refClazz = resolvedClass(); // 方法所在接口
        if (!refClazz.isInterface()) {
            throw new IncompatibleClassChangeError("Class: " + refClazz);
        }

        // 查找接口方法
        MethodMeta method = lookupInterfaceMethod(refClazz, name, descriptor);
        if (method == null) {
            throw new NoSuchMethodError("Method: " + name);
        }

        // 验证访问权限
        if (!method.isAccessibleTo(currentClazz)) {
            throw new IllegalAccessError("Method: " + method + " can not access by Class: " + currentClazz);
        }
        this.method = method;
    }

    /**
     * 查找接口方法元数据
     * @param clazz 类元数据
     * @param name 接口方法名称
     * @param descriptor 接口方法描述符
     * @return 方法元数据
     */
    public static MethodMeta lookupInterfaceMethod(ClassMeta clazz, String name, String descriptor) {
        for (MethodMeta method : clazz.getMethods()) {
            if (method.getName().equals(name) && method.getDescriptor().equals(descriptor)) {
                return method;
            }
        }
        return lookupMethodInInterfaces(clazz.getInterfaces(), name, descriptor);
    }

    private static MethodMeta lookupMethodInInterfaces(ClassMeta[] interfaces, String name, String descriptor) {
        for (ClassMeta iface : interfaces) {
            for (MethodMeta method : iface.getMethods()) {
                if (method.getName().equals(name) && method.getDescriptor().equals(descriptor)) {
                    return method;
                }
            }
            MethodMeta method = lookupMethodInInterfaces(iface.getInterfaces(), name, descriptor);
            if (method != null) {
                return method;
            }
        }
        return null;
    }

}
