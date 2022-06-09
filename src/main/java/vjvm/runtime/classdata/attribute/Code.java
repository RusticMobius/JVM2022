package vjvm.runtime.classdata.attribute;

import lombok.var;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;
import vjvm.runtime.JClass;
import vjvm.runtime.classdata.ConstantPool;
import vjvm.utils.UnimplementedError;

import java.io.DataInput;

@Getter
public class Code extends Attribute {
  private final int maxStack;
  private final int maxLocals;
  private final byte[] code; // the bytecode represented as raw bytes
  private final Attribute[] attributes;

  @SneakyThrows
  Code(DataInput input, ConstantPool constantPool) {
    // TODO: construct code
    //在attributes 中被消耗了
//    throw new UnimplementedError();
//    int attributeNameIndex = input.readUnsignedShort();
//    int attributeLength = input.readInt();
    maxStack = input.readUnsignedShort();
    maxLocals = input.readUnsignedShort();

    int codeLength = input.readInt();
    code = new byte[codeLength];
    for (int i = 0; i < codeLength; i++) {
      code[i] = input.readByte();
    }

    int exceptionTableLength = input.readUnsignedShort();
    for(int i = 0; i < exceptionTableLength; i ++) {
      int startPC = input.readUnsignedShort();
      int endPC = input.readUnsignedShort();
      int handlePC = input.readUnsignedShort();
      int catchType = input.readUnsignedShort();
    }

    int attributesCount = input.readUnsignedShort();

    attributes = new Attribute[attributesCount];
    for (int i = 0; i < attributesCount; i++) {
      attributes[i] = Attribute.constructFromData(input, constantPool);
    }
  }
}
