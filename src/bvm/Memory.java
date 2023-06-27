package bvm;

public class Memory {
	private static final int CL = 9;
	private static final int tRCD = 9;
	private static final int tRP = 9;
	private static final int tRAS = 24;
	private static final double SPEED = 1024;
	private static byte[] ram = new byte[1024 * 1024];

	private static void waitUntilNext() {
		try {
			Thread.sleep((long) (1000 / SPEED));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static void waitUntilNext(int repeats) {
		for (int i = 0; i < repeats; i++) {
			waitUntilNext();
		}
	}

	private static void waitFirst() {
		waitLatency();
		waitLineDelay();
	}

	private static void waitLast() {
		waitLineAfterDelay();
		waitNextGet();
	}

	private static void waitFirst(long repeats) {
		for (long i = 0; i < repeats; i++) {
			waitFirst();
		}
	}

	private static void waitLast(long repeats) {
		for (long i = 0; i < repeats; i++) {
			waitLast();
		}
	}

	private static void waitLatency() {
		waitUntilNext(CL);
	}

	private static void waitLineDelay() {
		waitUntilNext(tRCD);
	}

	private static void waitLineAfterDelay() {
		waitUntilNext(tRP);
	}

	private static void waitNextGet() {
		waitUntilNext(tRAS);
	}

	public static byte[] directGetArray(long address, long length) {
		byte[] array = new byte[(int) length];
		System.arraycopy(ram, (int) address, array, 0, (int) length);
		return array;
	}
	
	public static synchronized byte[] getArray(long address, long length) {
		waitFirst(length / 16);
		byte[] array = new byte[(int) length];
		System.arraycopy(ram, (int) address, array, 0, (int) length);
		waitLast(length / 16);
		return array;
	}

	public static synchronized byte[] setArray(long address, byte[] array) {
		waitFirst(array.length / 16);
		System.arraycopy(array, 0, ram, (int) address, array.length);
		waitLast(array.length / 16);
		return array;
	}

	public static synchronized byte directGet(long address) {
		byte b = ram[(int) address];
		return b;
	}
	
	public static synchronized byte get(long address) {
		waitFirst();
		byte b = ram[(int) address];
		waitLast();
		return b;
	}

	public static int directGetInt(long address) {
		return Conversion.bytesToInt(directGetArray(address, 4));
	}
	
	public static synchronized int getInt(long address) {
		return Conversion.bytesToInt(getArray(address, 4));
	}

	public static synchronized void setInt(long address, short v) {
		setArray(address, Conversion.intToBytes(v));
	}

	public static short directGetShort(long address) {
		return Conversion.bytesToShort(directGetArray(address, 2));
	}
	
	public static synchronized short getShort(long address) {
		return Conversion.bytesToShort(getArray(address, 2));
	}

	public static synchronized void setShort(long address, short v) {
		setArray(address, Conversion.shortToBytes(v));
	}
	
	public static long directGetLong(long address) {
		return Conversion.bytesToLong(directGetArray(address, 8));
	}

	public static synchronized long getLong(long address) {
		return Conversion.bytesToLong(getArray(address, 8));
	}

	public static synchronized void setLong(long address, long v) {
		setArray(address, Conversion.longToBytes(v));
	}

	public static synchronized void set(long address, byte value) {
		waitFirst();
		ram[(int) address] = value;
		waitLast();
	}
}
