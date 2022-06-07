package vjvm.runtime.classdata.constant;

import lombok.SneakyThrows;
import org.apache.commons.text.StringEscapeUtils;
import vjvm.runtime.JClass;

import java.io.DataInput;

public class StringConstant extends Constant {
  // 类似nameAndType
  private final int nameIndex;
  private final JClass self;
  private String value;

  @SneakyThrows
  public StringConstant(DataInput input, JClass jClass) {
    nameIndex = input.readUnsignedShort();
    this.self = jClass;
  }

  public String name () {
    if (value == null) {
      value = ((UTF8Constant) self.constantPool().constant(nameIndex)).value();
    }
    return value;
  }

  public String toString() {
    return String.format("String: \"%s\"", StringEscapeUtils.escapeJava(name()));
  }

}
