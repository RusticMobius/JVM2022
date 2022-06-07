package vjvm.runtime.classdata;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import vjvm.runtime.JClass;
import vjvm.runtime.classdata.attribute.Attribute;
import vjvm.runtime.classdata.constant.UTF8Constant;
import vjvm.utils.UnimplementedError;

import java.io.DataInput;

import static vjvm.classfiledefs.FieldAccessFlags.*;

@RequiredArgsConstructor
public class FieldInfo {
  @Getter
  private final int accessFlags;
  @Getter
  private final String name;
  @Getter
  private final String descriptor;
  private final Attribute[] attributes;
  @Getter
  private JClass jClass;

  @SneakyThrows
  public FieldInfo(DataInput dataInput, JClass jClass) {
//    throw new UnimplementedError("TODO: get field info from constant pool");
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
    }
  }

  public int attributeCount() {
    return attributes.length;
  }

  public Attribute attribute(int index) {
    return attributes[index];
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

  public boolean transient_() {
    return (accessFlags & ACC_TRANSIENT) != 0;
  }

  public boolean synthetic() {
    return (accessFlags & ACC_SYNTHETIC) != 0;
  }

  public boolean enum_() {
    return (accessFlags & ACC_ENUM) != 0;
  }
}
