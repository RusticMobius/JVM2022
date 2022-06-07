package vjvm.runtime;

import vjvm.classloader.JClassLoader;
import vjvm.runtime.classdata.ConstantPool;
import vjvm.runtime.classdata.FieldInfo;
import vjvm.runtime.classdata.MethodInfo;
import vjvm.runtime.classdata.attribute.Attribute;
import vjvm.runtime.classdata.constant.ClassConstant;
import vjvm.utils.UnimplementedError;
import java.io.DataInput;
import java.io.InvalidClassException;

import lombok.Getter;
import lombok.SneakyThrows;
import lombok.var;

import static vjvm.classfiledefs.ClassAccessFlags.*;

public class JClass {
  @Getter
  private final JClassLoader classLoader;
  @Getter
  private final int minorVersion;
  @Getter
  private final int majorVersion;
  @Getter
  private final ConstantPool constantPool;
  @Getter
  private final int accessFlags;
  private final String thisClass;
  private final String superClass;
  private final String[] interfaces;

  private final FieldInfo[] fields;
  private final MethodInfo[] methods;
  private final Attribute[] attributes;

  private final int interfacesCount;
  private final int fieldsCount;
  private final int methodsCount;
  private final int attributesCount;

  @SneakyThrows
  public JClass(DataInput dataInput, JClassLoader classLoader) {
    this.classLoader = classLoader;

    // check magic number
    var magic = dataInput.readInt();
    if (magic != 0xcafebabe) {
      throw new InvalidClassException(String.format(
        "Wrong magic number, expected: 0xcafebabe, got: 0x%x", magic));
    }

    minorVersion = dataInput.readUnsignedShort();
    majorVersion = dataInput.readUnsignedShort();

    constantPool = new ConstantPool(dataInput, this);
    accessFlags = dataInput.readUnsignedShort();

    int thisClassIndex = dataInput.readUnsignedShort();
    thisClass = ((ClassConstant)constantPool.constant(thisClassIndex)).name();

    int superClassIndex = dataInput.readUnsignedShort();
    if (interface_()){
      superClass = "java/lang/Object";
    } else{
      if (superClassIndex == 0){
        superClass = "java/lang/Object";
        }else{
        superClass = ((ClassConstant)constantPool.constant(superClassIndex)).name();
      }
    }

    interfacesCount = dataInput.readUnsignedShort();
    interfaces = new String[interfacesCount];
    for(int i = 0; i < interfacesCount; i++){
      int interfaceIndex = dataInput.readUnsignedShort();
      interfaces[i] = ((ClassConstant)constantPool.constant(interfaceIndex)).name();
    }

    fieldsCount = dataInput.readUnsignedShort();
    fields = new FieldInfo[fieldsCount];
    for(int i = 0; i < fieldsCount; i++){
      fields[i] = new FieldInfo(dataInput, this);
    }

    methodsCount = dataInput.readUnsignedShort();
    methods = new MethodInfo[methodsCount];
    for(int i = 0; i < methodsCount; i++){
      methods[i] = new MethodInfo(dataInput, this);
    }

    attributesCount = dataInput.readUnsignedShort();
    attributes = new Attribute[attributesCount];
    for (int i = 0; i < attributesCount; i++) {
      attributes[i] = Attribute.constructFromData(dataInput, constantPool);
    }

//    throw new UnimplementedError(
//        "TODO: you need to construct thisClass, superClass, interfaces, fields, "
//        + "methods, and attributes from dataInput in lab 1.2; remove this for lab 1.1");
  }

  public boolean public_() {
    return (accessFlags & ACC_PUBLIC) != 0;
  }

  public boolean final_() {
    return (accessFlags & ACC_FINAL) != 0;
  }

  public boolean super_() {
    return (accessFlags & ACC_SUPER) != 0;
  }

  public boolean interface_() {
    return (accessFlags & ACC_INTERFACE) != 0;
  }

  public boolean abstract_() {
    return (accessFlags & ACC_ABSTRACT) != 0;
  }

  public boolean synthetic() {
    return (accessFlags & ACC_SYNTHETIC) != 0;
  }

  public boolean annotation() {
    return (accessFlags & ACC_ANNOTATION) != 0;
  }

  public boolean enum_() {
    return (accessFlags & ACC_ENUM) != 0;
  }

  public boolean module() {
    return (accessFlags & ACC_MODULE) != 0;
  }

  public int fieldsCount() {
    return fields.length;
  }

  public FieldInfo field(int index) {
    return fields[index];
  }

  public int methodsCount() {
    return methods.length;
  }

  public MethodInfo method(int index) {
    return methods[index];
  }

  public void dumpJClass(){
    System.out.printf("class name: %s\n" , thisClass);
    System.out.printf("minor version: %d\n" , minorVersion);
    System.out.printf("major version: %d\n" , majorVersion);
    System.out.printf("flags: 0x%x\n" , accessFlags);
    System.out.printf("this class: %s\n", thisClass);
    System.out.printf("super class: %s\n", superClass);
    System.out.printf("constant pool:\n");
    for(int i = 0; i < constantPool.size(); i++){
      if(constantPool.constant(i) == null){
        continue;
      }
      System.out.printf("#%d = " + constantPool.constant(i) + '\n',i);
    }
    System.out.printf("interfaces:\n");
    for(int i = 0; i < interfaces.length; i++) {
      System.out.println(interfaces[i]);
    }
    System.out.println("fields:");
    for(int i = 0; i < fields.length; i++) {
      System.out.printf("%s(0x%x): %s\n",fields[i].name(),fields[i].accessFlags(),fields[i].descriptor());
    }
    System.out.println("methods:");
    for(int i = 0; i < methods.length; i++) {
      System.out.printf("%s(0x%x): %s\n",methods[i].name(),methods[i].accessFlags(),methods[i].descriptor());
    }


  }
}
