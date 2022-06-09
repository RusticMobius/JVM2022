package vjvm.runtime.classdata;

import lombok.Getter;
import lombok.SneakyThrows;
import vjvm.classfiledefs.MethodDescriptors;
import vjvm.runtime.JClass;
import vjvm.runtime.classdata.attribute.Attribute;

import vjvm.runtime.classdata.attribute.Code;

import vjvm.runtime.classdata.constant.UTF8Constant;

import vjvm.utils.UnimplementedError;

import java.io.DataInput;

import static vjvm.classfiledefs.MethodAccessFlags.*;

public class MethodInfo {
  @Getter
  private final int accessFlags;
  @Getter
  private final String name;
  @Getter
  private final String descriptor;
  private final Attribute[] attributes;

  @Getter
  private JClass jClass;

  // if this method doesn't hava code attribute
  // (which is the case of native methods), then code is null.
  @Getter
  private Code code;

  @SneakyThrows
  public MethodInfo(DataInput dataInput, JClass jClass) {
//    throw new UnimplementedError("TODO: Get method information from constant pool");
    this.jClass = jClass;
    accessFlags = dataInput.readUnsignedShort();
    int nameIndex = dataInput.readUnsignedShort();
    int desIndex = dataInput.readUnsignedShort();
    int attributeCount = dataInput.readUnsignedShort();
    name = ((UTF8Constant)jClass.constantPool().constant(nameIndex)).value();
    descriptor = ((UTF8Constant)jClass.constantPool().constant(desIndex)).value();
    attributes = new Attribute[attributeCount];
    for (int i = 0; i < attributeCount; i++) {
      attributes[i] = Attribute.constructFromData(dataInput, jClass.constantPool());
      if (attributes[i] instanceof Code){
        code = (Code) attributes[i];
      }
    }
  }

  public int argc() {

    return MethodDescriptors.argc(descriptor);
  }

  public boolean public_() {
    return (accessFlags & ACC_PUBLIC) != 0;
  }

  public boolean private_() {
    return (accessFlags & ACC_PRIVATE) != 0;
  }

  public boolean protected_() {
    return (accessFlags & ACC_PROTECTED) != 0;
  }

  public boolean static_() {
    return (accessFlags & ACC_STATIC) != 0;
  }

  public boolean final_() {
    return (accessFlags & ACC_FINAL) != 0;
  }

  public boolean synchronized_() {
    return (accessFlags & ACC_SYNCHRONIZED) != 0;
  }

  public boolean bridge() {
    return (accessFlags & ACC_BRIDGE) != 0;
  }

  public boolean vaargs() {
    return (accessFlags & ACC_VARARGS) != 0;
  }

  public boolean native_() {
    return (accessFlags & ACC_NATIVE) != 0;
  }

  public boolean abstract_() {
    return (accessFlags & ACC_ABSTRACT) != 0;
  }

  public boolean strict() {
    return (accessFlags & ACC_STRICT) != 0;
  }

  public boolean synthetic() {
    return (accessFlags & ACC_SYNTHETIC) != 0;
  }


}
