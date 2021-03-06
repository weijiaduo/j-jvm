package com.wjd.rtda.meta.cons;

import com.wjd.classfile.cons.MemberConstantInfo;

/**
 * 成员引用
 * @since 2022/1/30
 */
public class MemberRef extends SymbolRef {

    /** 成员名称 */
    protected String name;
    /** 成员描述符 */
    protected String descriptor;

    /**
     * 复制成员信息
     */
    protected void copyMemberRefInfo(MemberConstantInfo memberConstantInfo) {
        className = memberConstantInfo.getClassName();
        name = memberConstantInfo.getName();
        descriptor = memberConstantInfo.getDescriptor();
    }

    public String getName() {
        return name;
    }

    public String getDescriptor() {
        return descriptor;
    }
}
