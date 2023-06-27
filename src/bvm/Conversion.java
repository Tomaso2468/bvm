package bvm;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Conversion {
	public static long bytesToLong(byte[] array) {
		ByteBuffer bb = ByteBuffer.wrap(array);
		bb.order(ByteOrder.BIG_ENDIAN);
		return bb.getLong();
	}
	public static double bytesToDouble(byte[] array) {
		ByteBuffer bb = ByteBuffer.wrap(array);
		bb.order(ByteOrder.BIG_ENDIAN);
		return bb.getDouble();
	}
	public static float bytesToFloat(byte[] array) {
		ByteBuffer bb = ByteBuffer.wrap(array);
		bb.order(ByteOrder.BIG_ENDIAN);
		return bb.getFloat();
	}
	public static int bytesToInt(byte[] array) {
		ByteBuffer bb = ByteBuffer.wrap(array);
		bb.order(ByteOrder.BIG_ENDIAN);
		return bb.getInt();
	}
	public static short bytesToShort(byte[] array) {
		ByteBuffer bb = ByteBuffer.wrap(array);
		bb.order(ByteOrder.BIG_ENDIAN);
		return bb.getShort();
	}
	public static byte[] doubleToBytes(double d) {
		ByteBuffer bb = ByteBuffer.allocate(Double.BYTES);
		bb.putDouble(d);
		bb.order(ByteOrder.BIG_ENDIAN);
		return bb.array();
	}
	public static byte[] longToBytes(long l) {
		ByteBuffer bb = ByteBuffer.allocate(Long.BYTES);
		bb.putLong(l);
		bb.order(ByteOrder.BIG_ENDIAN);
		return bb.array();
	}
	public static byte[] floatToBytes(float f) {
		ByteBuffer bb = ByteBuffer.allocate(Float.BYTES);
		bb.putFloat(f);
		bb.order(ByteOrder.BIG_ENDIAN);
		return bb.array();
	}
	public static byte[] intToBytes(int i) {
		ByteBuffer bb = ByteBuffer.allocate(Integer.BYTES);
		bb.putInt(i);
		bb.order(ByteOrder.BIG_ENDIAN);
		return bb.array();
	}
	public static byte[] shortToBytes(short s) {
		ByteBuffer bb = ByteBuffer.allocate(Short.BYTES);
		bb.putShort(s);
		bb.order(ByteOrder.BIG_ENDIAN);
		return bb.array();
	}
	public static double longToDouble(long l) {
		return bytesToDouble(longToBytes(l));
	}
	public static float intToFloat(int i) {
		return bytesToFloat(intToBytes(i));
	}
	public static float longToFloat(long l) {
		return bytesToFloat(intToBytes((int) l));
	}
	public static long doubleToLong(double d) {
		return bytesToLong(doubleToBytes(d));
	}
	public static long floatToInt(float f) {
		return bytesToInt(floatToBytes(f));
	}
}
