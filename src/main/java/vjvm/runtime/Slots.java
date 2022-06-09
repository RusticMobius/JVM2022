package vjvm.runtime;

import java.nio.ByteBuffer;
import java.util.Optional;

import vjvm.utils.UnimplementedError;

/**
 * Slots represents an array of JVM slots as defined in the specification. It
 * supports getting and putting primitive data types, including address.
 */
public class Slots {
  private final ByteBuffer buffer;
  public Slots(int slotSize) {
    // TODO: initialize data structures

    buffer = ByteBuffer.allocate(slotSize * 4);
  }

  public int int_(int index) {
    // TODO: return the int at index
    // throw new UnimplementedError();
    return  buffer.getInt(index * 4);
  }

  public void int_(int index, int value) {
    // TODO: set the int at index
    // throw new UnimplementedError();
    buffer.putInt(index * 4, value);
  }

  public long long_(int index) {
    // TODO: return the long at index
    // throw new UnimplementedError();
    return buffer.getLong(index * 8);
  }

  public void long_(int index, long value) {
    // TODO: set the long at index
    // throw new UnimplementedError();
    buffer.putLong(index * 8, value);
  }

  public float float_(int index) {
    // TODO: return the float at index
    // throw new UnimplementedError();
    return buffer.getFloat(index * 4);
  }

  public void float_(int index, float value) {
    // TODO: set the float at index
    // throw new UnimplementedError();
    buffer.putFloat(index * 4);
  }

  public double double_(int index) {
    // TODO: return the double at index
    // throw new UnimplementedError();
    return buffer.getDouble(index * 4);
  }

  public void double_(int index, double value) {
    // TODO: set the double at index
    // throw new UnimplementedError();
    buffer.putDouble(index * 4);
  }

  public byte byte_(int index) {
    // TODO: return the byte at index
    //throw new UnimplementedError();
    return (byte) buffer.getInt(index * 4);
  }

  public void byte_(int index, byte value) {
    // TODO: set the byte at index
    // throw new UnimplementedError();
    buffer.putInt(index * 4, value);
  }

  public char char_(int index) {
    // TODO: return the char at index
    // throw new UnimplementedError();
    return (char) buffer.getInt(index * 4);
  }

  public void char_(int index, char value) {
    // TODO: set the char at index
    // throw new UnimplementedError();
    buffer.putInt(index * 4, value);
  }

  public short short_(int index) {
    // TODO: return the short at index
    // throw new UnimplementedError();
    return (short) buffer.getInt(index * 4);
  }

  public void short_(int index, short value) {
    // TODO: set the short at index
    // throw new UnimplementedError();
    buffer.putInt(index * 4, value);
  }

  public Optional<Object> value(int index) {
    // TODO(optional): return the value at index, or null if there is no value stored at index
    return Optional.empty();
  }

  public int size() {
    // TODO: return the size in the number of slots
    // throw new UnimplementedError();
    return  buffer.limit() / 4;
  }

  public void copyTo(int begin, int length, Slots dest, int destBegin) {
    // TODO: copy from this:[begin, begin+length) to dest:[destBegin,destBegin+length)
    // throw new UnimplementedError();

    if (destBegin > begin && this == dest) {
      for (int i = length - 1; i >= 0; --i){
        dest.int_(destBegin + i, int_(begin + i));
      }
    }else{
      for (int i = 0; i < length; ++i) {
        dest.int_(destBegin + i, int_(begin + i));
      }
    }
  }
}
