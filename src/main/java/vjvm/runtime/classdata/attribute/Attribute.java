package vjvm.runtime.classdata.attribute;

import lombok.var;
import lombok.SneakyThrows;
import vjvm.runtime.classdata.ConstantPool;
import vjvm.runtime.classdata.constant.UTF8Constant;

import java.io.DataInput;

public abstract class Attribute {

  @SneakyThrows
  public static Attribute constructFromData(DataInput input, ConstantPool constantPool) {
    var nameIndex = input.readUnsignedShort();
    var attrLength = Integer.toUnsignedLong(input.readInt());

    String attributeName = ((UTF8Constant) constantPool.constant(nameIndex)).value();
    // TODO: detect and construct Code attribute
    switch (attributeName) {
      case "Code":
        return new Code(input, constantPool);
      default:
        return new UnknownAttribute(input, attrLength);
    }
  }
}
