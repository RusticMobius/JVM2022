package vjvm.runtime.classdata.constant;

import lombok.var;
import lombok.SneakyThrows;
import org.apache.commons.lang3.tuple.Pair;
import vjvm.runtime.JClass;

import java.io.DataInput;

public class RefConstant extends Constant {
  private final int classIndex;
  private final int nameAndTypeIndex;
  private final JClass self;
  private final String refType;
  private String className;
  private String name;
  private String descriptor;

  @SneakyThrows
  public RefConstant(DataInput input, JClass self, String type){
    classIndex = input.readUnsignedShort();
    nameAndTypeIndex = input.readUnsignedShort();
    refType = type;
    this.self = self;
  }

  public String getClassName () {
    if (className == null) {
      className = ((ClassConstant) self.constantPool().constant(classIndex)).name();
    }
    return className;
  }

  public String getName () {
    if (name == null) {
      name = ((NameAndTypeConstant) self.constantPool().constant(nameAndTypeIndex)).name();
    }
    return name;
  }

  public String getDescriptor () {
    if (descriptor == null) {
      descriptor = ((NameAndTypeConstant) self.constantPool().constant(nameAndTypeIndex)).type();
    }
    return descriptor;
  }

  public String toString () {
    return String.format("%s: %s.%s:$s", refType, getClassName(), getName(), getDescriptor());
  }
}
