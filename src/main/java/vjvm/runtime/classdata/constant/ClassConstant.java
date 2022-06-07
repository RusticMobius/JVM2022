package vjvm.runtime.classdata.constant;

import lombok.var;
import lombok.SneakyThrows;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.text.StringEscapeUtils;
import vjvm.runtime.JClass;

import java.io.DataInput;

public class ClassConstant extends Constant {
  // 类似nameAndType
  private final int nameIndex;
  private final JClass self;
  private String value;

  @SneakyThrows
  public ClassConstant(DataInput input, JClass jClass) {
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
    return String.format("Class: %s", name());
  }

}
