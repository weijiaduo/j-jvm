package com.wjd.rtda.meta;

import com.wjd.classfile.ClassFile;
import com.wjd.classfile.type.Uint16;
import com.wjd.rtda.AccessFlags;
import com.wjd.rtda.Slot;
import com.wjd.rtda.heap.HeapObject;
import com.wjd.util.ArrayHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 类
 * @since 2022/1/30
 */
public class ClassMeta {

    private static String constructorName = "<init>";

    /** 源文件名称 */
    protected String sourceFile;

    /** 访问标志 */
    protected Uint16 accessFlags;
    /** 类完全限定名 */
    protected String name;
    /** 父类完全限定名 */
    protected String superClassName;
    /** 接口完全限定名 */
    protected String[] interfaceNames;
    /** 常量池 */
    protected ConstantPool constantPool;
    /** 字段 */
    protected FieldMeta[] fields;
    /** 方法 */
    protected MethodMeta[] methods;
    /** 类加载器 */
    protected ClassMetaLoader loader;
    /** 父类引用 */
    protected ClassMeta superClass;
    /** 接口引用 */
    protected ClassMeta[] interfaces;
    /** 实例变量数量 */
    protected int instanceSlotCount;
    /** 静态变量数量 */
    protected int staticSlotCount;
    /** 静态变量 */
    protected Slot[] staticVars;

    /** 类是否已初始化 */
    protected boolean initStarted;
    /** java.lang.Class实例 */
    protected HeapObject jClass;

    public ClassMeta() {
        interfaceNames = new String[0];
        fields = new FieldMeta[0];
        methods = new MethodMeta[0];
        staticVars = new Slot[0];
    }

    public static ClassMeta newClass(ClassFile classFile) {
        ClassMeta clazz = new ClassMeta();
        clazz.sourceFile = classFile.getSourceFile();
        clazz.accessFlags = classFile.getAccessFlags();
        clazz.name = classFile.getClassName();
        clazz.superClassName = classFile.getSuperClassName();
        clazz.interfaceNames = classFile.getInterfaceNames();
        clazz.constantPool = ConstantPool.newConstantPool(clazz, classFile.getConstantPool());
        clazz.fields = FieldMeta.newFields(clazz, classFile.getFields());
        clazz.methods = MethodMeta.newMethods(clazz, classFile.getMethods());
        return clazz;
    }

    public String getSourceFile() {
        return sourceFile;
    }

    public Uint16 getAccessFlags() {
        return accessFlags;
    }

    public void setAccessFlags(Uint16 accessFlags) {
        this.accessFlags = accessFlags;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSuperClassName() {
        return superClassName;
    }

    public String[] getInterfaceNames() {
        return interfaceNames;
    }

    public ConstantPool getConstantPool() {
        return constantPool;
    }

    public FieldMeta[] getFields() {
        return fields;
    }

    public MethodMeta[] getMethods() {
        return methods;
    }

    public ClassMetaLoader getLoader() {
        return loader;
    }

    public void setLoader(ClassMetaLoader loader) {
        this.loader = loader;
    }

    public ClassMeta getSuperClass() {
        return superClass;
    }

    public void setSuperClass(ClassMeta superClassMeta) {
        this.superClass = superClassMeta;
    }

    public ClassMeta[] getInterfaces() {
        return interfaces;
    }

    public void setInterfaces(ClassMeta[] interfaces) {
        this.interfaces = interfaces;
    }

    public void setInstanceSlotCount(int instanceSlotCount) {
        this.instanceSlotCount = instanceSlotCount;
    }

    public void setStaticSlotCount(int staticSlotCount) {
        this.staticSlotCount = staticSlotCount;
    }

    public int getInstanceSlotCount() {
        return instanceSlotCount;
    }

    public int getStaticSlotCount() {
        return staticSlotCount;
    }

    public Slot[] getStaticVars() {
        return staticVars;
    }

    public void setStaticVars(Slot[] staticVars) {
        this.staticVars = staticVars;
    }

    public boolean isInitStarted() {
        return initStarted;
    }

    public void startInit() {
        this.initStarted = true;
    }

    public HeapObject getjClass() {
        return jClass;
    }

    public void setjClass(HeapObject jClass) {
        this.jClass = jClass;
    }

    public boolean isAccessibleTo(ClassMeta other) {
        // 公开权限/包权限
        return isPublic() || getPackageName().equals(other.getPackageName());
    }

    public boolean isPublic() {
        return AccessFlags.isPublic(accessFlags);
    }

    public boolean isProtected() {
        return AccessFlags.isProtected(accessFlags);
    }

    public boolean isPrivate() {
        return AccessFlags.isPrivate(accessFlags);
    }

    public boolean isInterface() {
        return AccessFlags.isInterface(accessFlags);
    }

    public boolean isAbstract() {
        return AccessFlags.isAbstract(accessFlags);
    }

    public boolean isSuper() {
        return AccessFlags.isSuper(accessFlags);
    }

    public String getJavaName() {
        return name.replaceAll("/", ".");
    }

    public String getPackageName() {
        String packageName = "";
        int index = name.lastIndexOf("/");
        if (index > 0) {
            packageName = name.substring(0, index);
        }
        return packageName;
    }

    public boolean isSuperClassOf(ClassMeta child) {
        return child.isSubClassOf(this);
    }

    public boolean isSubClassOf(ClassMeta parent) {
        for (ClassMeta p = superClass; p != null; p = p.superClass) {
            if (p == parent) {
                return true;
            }
        }
        return false;
    }

    public boolean isSuperInterfaceOf(ClassMeta child) {
        return child.isSubInterfaceOf(this);
    }

    public boolean isSubInterfaceOf(ClassMeta parent) {
        for (ClassMeta i : getInterfaces()) {
            if (i == parent || i.isSubInterfaceOf(parent)) {
                return true;
            }
        }
        return false;
    }

    public boolean isImplements(ClassMeta parent) {
        for (ClassMeta c = this; c != null; c = c.superClass) {
            for (ClassMeta i : c.getInterfaces()) {
                if (i == parent || i.isSubInterfaceOf(parent)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isAssignableFrom(ClassMeta sub) {
        if (this == sub) {
            return true;
        }
        ClassMeta s = sub;
        ClassMeta t = this;
        if (!s.isArray()) {
            // 非数组类型
            if (!s.isInterface()) {
                if (!t.isInterface()) {
                    return s.isSubClassOf(t);
                } else {
                    return s.isImplements(t);
                }
            } else {
                if (!t.isInterface()) {
                    // 接口可以转成 Object
                    return t.isJlObject();
                } else {
                    return t.isSuperInterfaceOf(s);
                }
            }

        } else {
            // 数组类型
            if (!t.isArray()) {
                // 数组可以转成 Object/Cloneable/Serializable
                if (!t.isInterface()) {
                    return t.isJlObject();
                } else {
                    return t.isJlCloneable() || t.isJioSerializable();
                }
            } else {
                // 都是数组类型
                ClassMeta sc = s.getComponentClass();
                ClassMeta tc = t.getComponentClass();
                return sc == tc || tc.isAssignableFrom(sc);
            }
        }
    }

    public boolean isArray() {
        return ArrayHelper.isArray(this);
    }

    public boolean isPrimitive() {
        return PrimitiveMeta.primitiveTypes.containsKey(name);
    }

    public boolean isJlObject() {
        return this == loader.loadClass("java/lang/Object");
    }

    public boolean isJlCloneable() {
        return this == loader.loadClass("java/lang/Cloneable");
    }

    public boolean isJioSerializable() {
        return this == loader.loadClass("java/io/Serializable");
    }

    public FieldMeta[] getFields(boolean publicOnly) {
        if (!publicOnly) {
            return fields;
        }
        List<FieldMeta> fs = new ArrayList<>();
        for (FieldMeta fieldMeta : fields) {
            if (fieldMeta.isPublic()) {
                fs.add(fieldMeta);
            }
        }
        return fs.toArray(new FieldMeta[0]);
    }

    /**
     * 获取指定静态字段成员
     */
    public HeapObject getRefVar(String name, String descriptor) {
        FieldMeta fieldMeta = getStaticField(name, descriptor);
        return staticVars[fieldMeta.getSlotId()].getRef();
    }

    /**
     * 设置静态字段成员值
     */
    public void setRefVar(String name, String descriptor, HeapObject ref) {
        FieldMeta fieldMeta = getStaticField(name, descriptor);
        staticVars[fieldMeta.getSlotId()].setRef(ref);
    }

    /**
     * 设置静态字段的值
     * @param name 字段名称
     * @param descriptor 字段描述符
     * @param value 字段值
     */
    public void setStaticValue(String name, String descriptor, Slot value) {
        FieldMeta fieldMeta = getStaticField(name, descriptor);
        fieldMeta.putStaticValue(value);
    }

    /**
     * 获取静态字段
     */
    public FieldMeta getStaticField(String name, String descriptor) {
        return getField(name, descriptor, true);
    }

    /**
     * 获取实例字段
     */
    public FieldMeta getInstanceField(String name, String descriptor) {
        return getField(name, descriptor, false);
    }

    /**
     * 获取字段成员
     * @param name 字段名称
     * @param descriptor 字段描述符
     * @param isStatic 是否是静态属性
     */
    private FieldMeta getField(String name, String descriptor, boolean isStatic) {
        for (ClassMeta c = this; c != null; c = c.getSuperClass()) {
            for (FieldMeta fieldMeta : c.getFields()) {
                if (fieldMeta.isStatic() == isStatic &&
                        fieldMeta.getName().equals(name) &&
                        fieldMeta.getDescriptor().equals(descriptor)) {
                    return fieldMeta;
                }
            }
        }
        return null;
    }

    /**
     * 获取类文件中的main方法
     */
    public MethodMeta getMainMethod() {
        return getStaticMethod("main", "([Ljava/lang/String;)V");
    }

    /**
     * 获取类初始化方法
     */
    public MethodMeta getClinitMethod() {
        return getDeclaredMethod("<clinit>", "()V", true);
    }

    /**
     * 获取所有构造器函数
     * @param publicOnly 是否只要public的
     */
    public MethodMeta[] getConstructors(boolean publicOnly) {
        List<MethodMeta> constructors = new ArrayList<>();
        for (MethodMeta m : getMethods()) {
            if (!m.isStatic() && m.getName().equals(constructorName)) {
                if (!publicOnly || m.isPublic()) {
                    constructors.add(m);
                }
            }
        }
        return constructors.toArray(new MethodMeta[0]);
    }

    /**
     * 获取构造方法
     */
    public MethodMeta getConstructor(String descriptor) {
        return getDeclaredMethod(constructorName, descriptor, false);
    }

    /**
     * 获取静态方法
     */
    public MethodMeta getStaticMethod(String name, String descriptor) {
        return getMethod(name, descriptor, true);
    }

    /**
     * 获取实例方法
     */
    public MethodMeta getInstanceMethod(String name, String descriptor) {
        return getMethod(name, descriptor, false);
    }

    /**
     * 获取方法成员
     * @param name 字段名称
     * @param descriptor 字段描述符
     * @param isStatic 是否是静态属性
     */
    private MethodMeta getMethod(String name, String descriptor, boolean isStatic) {
        for (ClassMeta c = this; c != null; c = c.getSuperClass()) {
            for (MethodMeta m : getMethods()) {
                if (m.isStatic() == isStatic &&
                        name.equals(m.getName()) &&
                        descriptor.equals(m.getDescriptor())) {
                    return m;
                }
            }
        }
        return null;
    }

    /**
     * 类自己本身声明的方法
     * @param name 字段名称
     * @param descriptor 字段描述符
     * @param isStatic 是否是静态属性
     */
    private MethodMeta getDeclaredMethod(String name, String descriptor, boolean isStatic) {
        for (MethodMeta m : getMethods()) {
            if (m.isStatic() == isStatic &&
                    name.equals(m.getName()) &&
                    descriptor.equals(m.getDescriptor())) {
                return m;
            }
        }
        return null;
    }

    /**
     * 获取当前类型对应的数组类型
     */
    public ClassMeta getArrayClass() {
        String arrayClassName = ArrayHelper.getArrayClassName(name);
        return loader.loadClass(arrayClassName);
    }

    /**
     * 获取数组类型的元素类型
     */
    public ClassMeta getComponentClass() {
        String componentClassName = ArrayHelper.getComponentClassName(name);
        return loader.loadClass(componentClassName);
    }

    public HeapObject newObject() {
        return HeapObject.newObject(this);
    }

    public HeapObject newArray(int count) {
        if (!isArray()) {
            throw new IllegalStateException("Not array class: " + name);
        }
        return HeapObject.newArray(this, ArrayHelper.makeArray(name, count));
    }

}
