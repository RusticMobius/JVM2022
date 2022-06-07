package vjvm.runtime.classdata.constant;
import lombok.var;
import lombok.Getter;
import lombok.SneakyThrows;

import java.io.DataInput;

public class LongConstant extends Constant{
  @Getter
  private final long value;

  @SneakyThrows
  public LongConstant(DataInput input){
    value = input.readLong();
  }


  public String toString(){
    return String.format("Long: %d", value);
  }
}
