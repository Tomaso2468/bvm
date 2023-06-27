package bvm;

import java.util.Random;
import java.util.Scanner;

public class Core extends Thread {
	public static boolean EXIT_ON_HALT = false;

	public static final byte I_HALT = 0x00;
	public static final byte I_HALT_ERR = 0x01;

	public static final byte I_LOAD = 0x10;
	public static final byte I_STORE = 0x11;
	public static final byte I_LOAD_OFFSET = 0x12;
	public static final byte I_STORE_OFFSET = 0x13;
	public static final byte I_LOAD_POINTER = 0x14;
	public static final byte I_STORE_POINTER = 0x15;
	public static final byte I_LOAD_POINTER_OFFSET = 0x16;
	public static final byte I_STORE_POINTER_OFFSET = 0x17;

	public static final byte I_DOUT = 0x20;
	public static final byte I_DIN = 0x21;
	public static final byte I_OUT = 0x22;
	public static final byte I_IN = 0x23;

	public static final byte I_ADD = 0x30;
	public static final byte I_SUBTRACT = 0x31;
	public static final byte I_MUL = 0x32;
	public static final byte I_DIV = 0x33;
	public static final byte I_MOD = 0x34;
	public static final byte I_AND = 0x35;
	public static final byte I_OR = 0x36;
	public static final byte I_XOR = 0x37;
	public static final byte I_NOT = 0x38;
	public static final byte I_NOR = 0x39;
	public static final byte I_NAND = 0x3A;
	public static final byte I_RND = 0x3B;
	public static final byte I_UNIX = 0x3C;
	public static final byte I_INC = 0x3D;
	public static final byte I_DEC = 0x3E;
	public static final byte I_LONG_CAST = 0x3F;

	public static final byte I8_LOAD = 0x40;
	public static final byte I8_STORE = 0x41;
	public static final byte I8_LOAD_OFFSET = 0x42;
	public static final byte I8_STORE_OFFSET = 0x43;
	public static final byte I8_LOAD_POINTER = 0x44;
	public static final byte I8_STORE_POINTER = 0x45;
	public static final byte I8_LOAD_POINTER_OFFSET = 0x46;
	public static final byte I8_STORE_POINTER_OFFSET = 0x47;
	public static final byte I8_BYTE_CAST = 0x4F;

	public static final byte I16_LOAD = 0x50;
	public static final byte I16_STORE = 0x51;
	public static final byte I16_LOAD_OFFSET = 0x52;
	public static final byte I16_STORE_OFFSET = 0x53;
	public static final byte I16_LOAD_POINTER = 0x54;
	public static final byte I16_STORE_POINTER = 0x55;
	public static final byte I16_LOAD_POINTER_OFFSET = 0x56;
	public static final byte I16_STORE_POINTER_OFFSET = 0x57;
	public static final byte I16_SHORT_CAST = 0x5F;

	public static final byte I32_LOAD = 0x60;
	public static final byte I32_STORE = 0x61;
	public static final byte I32_LOAD_OFFSET = 0x62;
	public static final byte I32_STORE_OFFSET = 0x63;
	public static final byte I32_LOAD_POINTER = 0x64;
	public static final byte I32_STORE_POINTER = 0x65;
	public static final byte I32_LOAD_POINTER_OFFSET = 0x66;
	public static final byte I32_STORE_POINTER_OFFSET = 0x67;
	public static final byte I32_INT_CAST = 0x6F;

	public static final byte IF_ADD = 0x70;
	public static final byte IF_SUBTRACT = 0x71;
	public static final byte IF_MUL = 0x72;
	public static final byte IF_DIV = 0x73;
	public static final byte IF_MOD = 0x74;

	public static final byte ID_ADD = (byte) 0x80;
	public static final byte ID_SUBTRACT = (byte) 0x81;
	public static final byte ID_MUL = (byte) 0x82;
	public static final byte ID_DIV = (byte) 0x83;
	public static final byte ID_MOD = (byte) 0x84;
	public static final byte ID_POW = (byte) 0x85;
	public static final byte ID_SIN = (byte) 0x86;
	public static final byte ID_COS = (byte) 0x87;
	public static final byte ID_TAN = (byte) 0x88;
	public static final byte ID_SQRT = (byte) 0x89;
	public static final byte ID_CBRT = (byte) 0x8A;
	public static final byte ID_ASIN = (byte) 0x8B;
	public static final byte ID_ACOS = (byte) 0x8C;
	public static final byte ID_ATAN = (byte) 0x8D;
	public static final byte ID_LOG = (byte) 0x8E;
	public static final byte ID_IN = (byte) 0x8F;

	public static final byte I_CALL = (byte) 0x90;
	public static final byte I_RETURN = (byte) 0x91;
	public static final byte I_INTERRUPT = (byte) 0x92;
	public static final byte I_INTERRUPT_R = (byte) 0x93;
	public static final byte I_CALL_POINTER = (byte) 0x94;

	public static final byte I_JUMP = (byte) 0xA0;
	public static final byte I_JUMP_POS = (byte) 0xA1;
	public static final byte I_JUMP_ZERO = (byte) 0xA2;
	public static final byte I_JUMP_NEG = (byte) 0xA3;
	public static final byte I_JUMP_TRUE = (byte) 0xA4;
	public static final byte I_JUMP_FALSE = (byte) 0xA5;
	public static final byte I_JUMP_EQU = (byte) 0xA6;
	public static final byte I_JUMP_NEQU = (byte) 0xA7;
	public static final byte I_JUMP_PTR = (byte) 0xA8;
	public static final byte I_JUMP_EQU_PTR = (byte) 0xA9;
	public static final byte I_JUMP_NEQU_PTR = (byte) 0xAA;
	public static final byte I_JUMP_ZERO_PTR = (byte) 0xAB;
	public static final byte I_JUMP_NEG_PTR = (byte) 0xAC;
	public static final byte I_JUMP_POS_PTR = (byte) 0xAD;
	public static final byte I_JUMP_NAN = (byte) 0xAE;
	public static final byte I_JUMP_NAN_PTR = (byte) 0xAF;

	public static final byte IC_BYTE = (byte) 0xB0;
	public static final byte IC_SHORT = (byte) 0xB1;
	public static final byte IC_INT = (byte) 0xB2;
	public static final byte IC_LONG = (byte) 0xB3;
	public static final byte IC_FLOAT = (byte) 0xB4;
	public static final byte IC_DOUBLE = (byte) 0xB5;

	public static final byte IA_COPY = (byte) 0xC0;
	public static final byte IA_COPY_PTR = (byte) 0xC1;

	public static final byte IS_SETUPSTACK = (byte) 0xD0;
	public static final byte IS_INTERRUPT = (byte) 0xD1;

	public static final byte IP_JUMP_LESS = (byte) 0xE0;
	public static final byte IP_JUMP_MORE = (byte) 0xE1;
	public static final byte IP_JUMP_LESS_PTR = (byte) 0xE2;
	public static final byte IP_JUMP_MORE_PTR = (byte) 0xE3;
	public static final byte IP_LSHIFT = (byte) 0xE4;
	public static final byte IP_RSHIFT = (byte) 0xE5;
	public static final byte IP_LSHIFT_SAFE = (byte) 0xE6;
	public static final byte IP_RSHIFT_SAFE = (byte) 0xE7;
	public static final byte IP_JUMP_LESS_EQU = (byte) 0xE8;
	public static final byte IP_JUMP_MORE_EQU = (byte) 0xE9;
	public static final byte IP_JUMP_LESS_PTR_EQU = (byte) 0xEA;
	public static final byte IP_JUMP_MORE_PTR_EQU = (byte) 0xEB;

	public static final byte IE_POWER = (byte) 0xF0;
	public static final byte IE_64BIT_RAM = (byte) 0xF1;
	public static final byte IE_IO = (byte) 0xF2;
	public static final byte IE_MATH = (byte) 0xF3;
	public static final byte IE_8BIT_RAM = (byte) 0xF4;
	public static final byte IE_16BIT_RAM = (byte) 0xF5;
	public static final byte IE_32BIT_RAM = (byte) 0xF6;
	public static final byte IE_32BIT_FLOATING = (byte) 0xF7;
	public static final byte IE_64BIT_FLOATING = (byte) 0xF8;
	public static final byte IE_CALLING = (byte) 0xF9;
	public static final byte IE_JUMP = (byte) 0xFA;
	public static final byte IE_CAST = (byte) 0xFB;
	public static final byte IE_ARRAY = (byte) 0xFC;
	public static final byte IE_STACK = (byte) 0xFD;
	public static final byte IE_ADDITIONAL_OPS = (byte) 0xFE;
	public static final byte IE_EXT = (byte) 0xFF;

	public static final byte OP_POWER_HALT = 1;
	public static final byte OP_POWER_SHORT_REST = 1 << 2;
	public static final byte OP_POWER_LONG_REST = 1 << 3;
	public static final byte OP_POWER_REBOOT = 1 << 4;
	public static final byte OP_POWER_ERR = 1 << 5;
	public static final byte OP_POWER = OP_POWER_HALT | OP_POWER_ERR;

	public static final long FALSE = 0;
	public static final long TRUE = ~FALSE;

	private static long id = -1;

	private static synchronized long getNextID() {
		id += 1;
		return id;
	}

	private double speed = 1024 * 1024;

	private void waitUntilNext() {
		try {
			Thread.sleep((long) (1000 / speed));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private boolean run = false;
	/**
	 * <li>
	 * <ul>
	 * 0 - PC
	 * </ul>
	 * <ul>
	 * 1 - Thread ID
	 * </ul>
	 * <ul>
	 * 2 - Zero
	 * </ul>
	 * <ul>
	 * 3 - One
	 * </ul>
	 * <ul>
	 * 4 - Negative One
	 * </ul>
	 * <ul>
	 * 5 - Stack
	 * </ul>
	 * <ul>
	 * 6 - Interrupt Table
	 * </ul>
	 * <ul>
	 * 7 - Current Instruction
	 * </ul>
	 * <ul>
	 * 8 - Interrupt Stack
	 * </ul>
	 * <ul>
	 * 9 - Temp Register
	 * </ul>
	 * <ul>
	 * 10 - Return register
	 * </ul>
	 * </li>
	 */
	private long[] r = new long[64];
	private boolean interrupt;
	private byte interrupt_id = -1;

	public Core(long pc) {
		r[0] = pc;
		r[1] = getNextID();
		r[2] = 0;
		r[3] = 1;
		r[4] = -1;
		r[5] = -1;
		r[6] = -1;
		r[8] = r[6] + 2048;
	}

	@Override
	public void run() {
		waitUntilNext();
		while (true) {
			if (run) {
				if (interrupt) {
					pushInterruptStack();
					r[0] = r[6] + interrupt_id * 8;
					interrupt = false;
				} else {
					performAction(Memory.get(r[0]));
				}
			}
			waitUntilNext();
		}
	}

	public void pushInterruptStack() {
		long pointer = r[8];
		for (int i = 0; i < r.length; i++) {
			Memory.setArray(pointer + (i * 8), Conversion.longToBytes(r[i]));
			waitUntilNext();
		}
		r[8] += r.length * 8;
		waitUntilNext();
	}

	public void returnInterruptStack() {
		long pointer = r[8] - r.length * 8;
		for (int i = 0; i < r.length; i++) {
			r[i] = Conversion.bytesToLong(Memory.getArray(pointer + (i * 8), 8));
			waitUntilNext();
		}
	}

	public void enable(long pc) {
		r[0] = pc;
		run = true;
	}

	public void incrementPC(long v) {
		waitUntilNext();
		r[0] += v;
	}

	public void performAction(byte ins) {
		if (System.getProperty("bvm.debug8bit", "false").equalsIgnoreCase("true")) {
			System.out.println(
					Long.toHexString(r[0]).toUpperCase() + "->" + Long.toHexString(Memory.getLong(r[0])).toUpperCase());
		}
		r[7] = ins;
		long pc = r[0];
		switch (ins) {
		case I_HALT:
			run = false;
			if (EXIT_ON_HALT) {
				System.exit(0);
			}
			incrementPC(8);
			break;
		case I_LOAD:
			r[Memory.get(r[0] + 1)] = Memory.getLong(Memory.getLong(r[0] + 8));
			incrementPC(16);
			break;
		case I_STORE:
			Memory.setLong(Memory.getLong(r[0] + 8), r[Memory.get(r[0] + 1)]);
			incrementPC(16);
			break;
		case I_LOAD_OFFSET:
			r[Memory.get(r[0] + 1)] = Memory.getLong(Memory.getLong(r[0] + 8) + Memory.get(r[0] + 2));
			incrementPC(16);
			break;
		case I_STORE_OFFSET:
			Memory.setLong(Memory.getLong(r[0] + 8) + Memory.get(r[0] + 2), r[Memory.get(r[0] + 1)]);
			incrementPC(16);
			break;
		case I_LOAD_POINTER:
			r[Memory.get(r[0] + 1)] = Memory.getLong(r[Memory.get(r[0] + 2)]);
			incrementPC(8);
			break;
		case I_STORE_POINTER:
			Memory.setLong(r[Memory.get(r[0] + 2)], r[Memory.get(r[0] + 1)]);
			incrementPC(8);
			break;
		case I_LOAD_POINTER_OFFSET:
			r[Memory.get(r[0] + 1)] = Memory.getLong(r[Memory.get(r[0] + 2)] + Memory.get(r[0] + 3));
			incrementPC(8);
			break;
		case I_STORE_POINTER_OFFSET:
			Memory.setLong(r[Memory.get(r[0] + 2)] + Memory.get(r[0] + 3), r[Memory.get(r[0] + 1)]);
			incrementPC(8);
			break;
		case I_DOUT:
			System.err.println(r[Memory.get(r[0] + 1)]);
			incrementPC(8);
			break;
		case I_DIN:
			Scanner din_scanner = new Scanner(System.in);
			r[Memory.get(r[0] + 1)] = din_scanner.nextLong();
			din_scanner.close();
			incrementPC(8);
			break;
		case I_ADD:
			r[Memory.get(r[0] + 3)] = r[Memory.get(r[0] + 1)] + r[Memory.get(r[0] + 2)];
			incrementPC(8);
			break;
		case I_SUBTRACT:
			r[Memory.get(r[0] + 3)] = r[Memory.get(r[0] + 1)] - r[Memory.get(r[0] + 2)];
			incrementPC(8);
			break;
		case I_DIV:
			r[Memory.get(r[0] + 3)] = r[Memory.get(r[0] + 1)] / r[Memory.get(r[0] + 2)];
			incrementPC(8);
			break;
		case I_MUL:
			r[Memory.get(r[0] + 3)] = r[Memory.get(r[0] + 1)] * r[Memory.get(r[0] + 2)];
			incrementPC(8);
			break;
		case I_MOD:
			r[Memory.get(r[0] + 3)] = r[Memory.get(r[0] + 1)] % r[Memory.get(r[0] + 2)];
			incrementPC(8);
			break;
		case I_AND:
			r[Memory.get(r[0] + 3)] = r[Memory.get(r[0] + 1)] & r[Memory.get(r[0] + 2)];
			incrementPC(8);
			break;
		case I_OR:
			r[Memory.get(r[0] + 3)] = r[Memory.get(r[0] + 1)] | r[Memory.get(r[0] + 2)];
			incrementPC(8);
			break;
		case I_XOR:
			r[Memory.get(r[0] + 3)] = r[Memory.get(r[0] + 1)] ^ r[Memory.get(r[0] + 2)];
			incrementPC(8);
			break;
		case I_NOT:
			r[Memory.get(r[0] + 2)] = ~r[Memory.get(r[0] + 1)];
			incrementPC(8);
			break;
		case I_NOR:
			r[Memory.get(r[0] + 3)] = ~(r[Memory.get(r[0] + 1)] | r[Memory.get(r[0] + 2)]);
			incrementPC(8);
			break;
		case I_NAND:
			r[Memory.get(r[0] + 3)] = ~(r[Memory.get(r[0] + 1)] & r[Memory.get(r[0] + 2)]);
			incrementPC(8);
			break;
		case I_RND:
			r[Memory.get(r[0] + 1)] = new Random().nextLong();
			incrementPC(8);
			break;
		case I_UNIX:
			r[Memory.get(r[0] + 1)] = System.currentTimeMillis();
			incrementPC(8);
			break;
		case I_INC:
			r[Memory.get(r[0] + 2)] = r[Memory.get(r[0] + 1)] + 1;
			incrementPC(8);
			break;
		case I_DEC:
			r[Memory.get(r[0] + 2)] = r[Memory.get(r[0] + 1)] - 1;
			incrementPC(8);
			break;
		case I_LONG_CAST:
			r[Memory.get(r[0] + 2)] = r[Memory.get(r[0] + 1)] & 0xFFFFFFFFFFFFFFFFL;
			incrementPC(8);
			break;
		case I8_LOAD:
			r[Memory.get(r[0] + 1)] = Memory.get(Memory.getLong(r[0] + 8));
			incrementPC(16);
			break;
		case I8_STORE:
			Memory.set(Memory.getLong(r[0] + 8), (byte) r[Memory.get(r[0] + 1)]);
			incrementPC(16);
			break;
		case I8_LOAD_OFFSET:
			r[Memory.get(r[0] + 1)] = Memory.get(Memory.getLong(r[0] + 8) + Memory.get(r[0] + 2));
			incrementPC(16);
			break;
		case I8_STORE_OFFSET:
			Memory.set(Memory.getLong(r[0] + 8) + Memory.get(r[0] + 2), (byte) r[Memory.get(r[0] + 1)]);
			incrementPC(16);
			break;
		case I8_LOAD_POINTER:
			r[Memory.get(r[0] + 1)] = Memory.get(r[Memory.get(r[0] + 2)]);
			incrementPC(8);
			break;
		case I8_STORE_POINTER:
			Memory.set(r[Memory.get(r[0] + 2)], (byte) r[Memory.get(r[0] + 1)]);
			incrementPC(8);
			break;
		case I8_LOAD_POINTER_OFFSET:
			r[Memory.get(r[0] + 1)] = Memory.get(r[Memory.get(r[0] + 2)] + Memory.get(r[0] + 3));
			incrementPC(8);
			break;
		case I8_STORE_POINTER_OFFSET:
			Memory.set(r[Memory.get(r[0] + 2)] + Memory.get(r[0] + 3), (byte) r[Memory.get(r[0] + 1)]);
			incrementPC(8);
			break;
		case I8_BYTE_CAST:
			r[Memory.get(r[0] + 2)] = r[Memory.get(r[0] + 1)] & 0xFF;
			incrementPC(8);
			break;
		case I16_LOAD:
			r[Memory.get(r[0] + 1)] = Memory.getShort(Memory.getLong(r[0] + 8));
			incrementPC(16);
			break;
		case I16_STORE:
			Memory.setShort(Memory.getLong(r[0] + 8), (byte) r[Memory.get(r[0] + 1)]);
			incrementPC(16);
			break;
		case I16_LOAD_OFFSET:
			r[Memory.get(r[0] + 1)] = Memory.getShort(Memory.getLong(r[0] + 8) + Memory.get(r[0] + 2));
			incrementPC(16);
			break;
		case I16_STORE_OFFSET:
			Memory.setShort(Memory.getLong(r[0] + 8) + Memory.get(r[0] + 2), (byte) r[Memory.get(r[0] + 1)]);
			incrementPC(16);
			break;
		case I16_LOAD_POINTER:
			r[Memory.get(r[0] + 1)] = Memory.getShort(r[Memory.get(r[0] + 2)]);
			incrementPC(8);
			break;
		case I16_STORE_POINTER:
			Memory.setShort(r[Memory.get(r[0] + 2)], (byte) r[Memory.get(r[0] + 1)]);
			incrementPC(8);
			break;
		case I16_LOAD_POINTER_OFFSET:
			r[Memory.get(r[0] + 1)] = Memory.getShort(r[Memory.get(r[0] + 2)] + Memory.get(r[0] + 3));
			incrementPC(8);
			break;
		case I16_STORE_POINTER_OFFSET:
			Memory.setShort(r[Memory.get(r[0] + 2)] + Memory.get(r[0] + 3), (byte) r[Memory.get(r[0] + 1)]);
			incrementPC(8);
			break;
		case I16_SHORT_CAST:
			r[Memory.get(r[0] + 2)] = r[Memory.get(r[0] + 1)] & 0xFFFF;
			incrementPC(8);
			break;
		case I32_LOAD:
			r[Memory.get(r[0] + 1)] = Memory.getInt(Memory.getLong(r[0] + 8));
			incrementPC(16);
			break;
		case I32_STORE:
			Memory.setInt(Memory.getLong(r[0] + 8), (byte) r[Memory.get(r[0] + 1)]);
			incrementPC(16);
			break;
		case I32_LOAD_OFFSET:
			r[Memory.get(r[0] + 1)] = Memory.getInt(Memory.getLong(r[0] + 8) + Memory.get(r[0] + 2));
			incrementPC(16);
			break;
		case I32_STORE_OFFSET:
			Memory.setInt(Memory.getLong(r[0] + 8) + Memory.get(r[0] + 2), (byte) r[Memory.get(r[0] + 1)]);
			incrementPC(16);
			break;
		case I32_LOAD_POINTER:
			r[Memory.get(r[0] + 1)] = Memory.getInt(r[Memory.get(r[0] + 2)]);
			incrementPC(8);
			break;
		case I32_STORE_POINTER:
			Memory.setInt(r[Memory.get(r[0] + 2)], (byte) r[Memory.get(r[0] + 1)]);
			incrementPC(8);
			break;
		case I32_LOAD_POINTER_OFFSET:
			r[Memory.get(r[0] + 1)] = Memory.getInt(r[Memory.get(r[0] + 2)] + Memory.get(r[0] + 3));
			incrementPC(8);
			break;
		case I32_STORE_POINTER_OFFSET:
			Memory.setInt(r[Memory.get(r[0] + 2)] + Memory.get(r[0] + 3), (byte) r[Memory.get(r[0] + 1)]);
			incrementPC(8);
			break;
		case I32_INT_CAST:
			r[Memory.get(r[0] + 2)] = r[Memory.get(r[0] + 1)] & 0xFFFFFFFF;
			incrementPC(8);
			break;
		case IF_ADD:
			r[Memory.get(r[0] + 3)] = Conversion.floatToInt(
					Conversion.longToFloat(r[Memory.get(r[0] + 1)]) + Conversion.longToFloat(r[Memory.get(r[0] + 2)]));
			incrementPC(8);
			break;
		case IF_SUBTRACT:
			r[Memory.get(r[0] + 3)] = Conversion.floatToInt(
					Conversion.longToFloat(r[Memory.get(r[0] + 1)]) - Conversion.longToFloat(r[Memory.get(r[0] + 2)]));
			incrementPC(8);
			break;
		case IF_DIV:
			r[Memory.get(r[0] + 3)] = Conversion.floatToInt(
					Conversion.longToFloat(r[Memory.get(r[0] + 1)]) / Conversion.longToFloat(r[Memory.get(r[0] + 2)]));
			incrementPC(8);
			break;
		case IF_MUL:
			r[Memory.get(r[0] + 3)] = Conversion.floatToInt(
					Conversion.longToFloat(r[Memory.get(r[0] + 1)]) * Conversion.longToFloat(r[Memory.get(r[0] + 2)]));
			incrementPC(8);
			break;
		case IF_MOD:
			r[Memory.get(r[0] + 3)] = Conversion.floatToInt(
					Conversion.longToFloat(r[Memory.get(r[0] + 1)]) % Conversion.longToFloat(r[Memory.get(r[0] + 2)]));
			incrementPC(8);
			break;
		case ID_ADD:
			r[Memory.get(r[0] + 3)] = Conversion.doubleToLong(Conversion.longToDouble(r[Memory.get(r[0] + 1)])
					+ Conversion.longToDouble(r[Memory.get(r[0] + 2)]));
			incrementPC(8);
			break;
		case ID_SUBTRACT:
			r[Memory.get(r[0] + 3)] = Conversion.doubleToLong(Conversion.longToDouble(r[Memory.get(r[0] + 1)])
					- Conversion.longToDouble(r[Memory.get(r[0] + 2)]));
			incrementPC(8);
			break;
		case ID_DIV:
			r[Memory.get(r[0] + 3)] = Conversion.doubleToLong(Conversion.longToDouble(r[Memory.get(r[0] + 1)])
					/ Conversion.longToDouble(r[Memory.get(r[0] + 2)]));
			incrementPC(8);
			break;
		case ID_MUL:
			r[Memory.get(r[0] + 3)] = Conversion.doubleToLong(Conversion.longToDouble(r[Memory.get(r[0] + 1)])
					* Conversion.longToDouble(r[Memory.get(r[0] + 2)]));
			incrementPC(8);
			break;
		case ID_MOD:
			r[Memory.get(r[0] + 3)] = Conversion.doubleToLong(Conversion.longToDouble(r[Memory.get(r[0] + 1)])
					% Conversion.longToDouble(r[Memory.get(r[0] + 2)]));
			incrementPC(8);
			break;
		case ID_POW:
			r[Memory.get(r[0] + 3)] = Conversion.doubleToLong(Math.pow(Conversion.longToDouble(r[Memory.get(r[0] + 1)]),
					Conversion.longToDouble(r[Memory.get(r[0] + 2)])));
			incrementPC(8);
			break;
		case ID_SIN:
			r[Memory.get(r[0] + 2)] = Conversion
					.doubleToLong(Math.sin(Conversion.longToDouble(r[Memory.get(r[0] + 1)])));
			incrementPC(8);
			break;
		case ID_COS:
			r[Memory.get(r[0] + 2)] = Conversion
					.doubleToLong(Math.cos(Conversion.longToDouble(r[Memory.get(r[0] + 1)])));
			incrementPC(8);
			break;
		case ID_TAN:
			r[Memory.get(r[0] + 2)] = Conversion
					.doubleToLong(Math.tan(Conversion.longToDouble(r[Memory.get(r[0] + 1)])));
			incrementPC(8);
			break;
		case ID_SQRT:
			r[Memory.get(r[0] + 2)] = Conversion
					.doubleToLong(Math.sqrt(Conversion.longToDouble(r[Memory.get(r[0] + 1)])));
			incrementPC(8);
			break;
		case ID_CBRT:
			r[Memory.get(r[0] + 2)] = Conversion
					.doubleToLong(Math.cbrt(Conversion.longToDouble(r[Memory.get(r[0] + 1)])));
			incrementPC(8);
			break;
		case ID_ASIN:
			r[Memory.get(r[0] + 2)] = Conversion
					.doubleToLong(Math.asin(Conversion.longToDouble(r[Memory.get(r[0] + 1)])));
			incrementPC(8);
			break;
		case ID_ACOS:
			r[Memory.get(r[0] + 2)] = Conversion
					.doubleToLong(Math.acos(Conversion.longToDouble(r[Memory.get(r[0] + 1)])));
			incrementPC(8);
			break;
		case ID_ATAN:
			r[Memory.get(r[0] + 2)] = Conversion
					.doubleToLong(Math.atan(Conversion.longToDouble(r[Memory.get(r[0] + 1)])));
			incrementPC(8);
			break;
		case ID_LOG:
			r[Memory.get(r[0] + 2)] = Conversion
					.doubleToLong(Math.log10(Conversion.longToDouble(r[Memory.get(r[0] + 1)])));
			incrementPC(8);
			break;
		case ID_IN:
			r[Memory.get(r[0] + 2)] = Conversion
					.doubleToLong(Math.log(Conversion.longToDouble(r[Memory.get(r[0] + 1)])));
			incrementPC(8);
			break;
		case I_OUT:
			output(r[Memory.get(r[0] + 1)], r[Memory.get(r[0] + 2)]);
			incrementPC(8);
			break;
		case I_IN:
			r[Memory.get(r[0] + 2)] = input(r[Memory.get(r[0] + 1)]);
			incrementPC(8);
			break;
		case IE_POWER:
			r[Memory.get(r[0] + 1)] = OP_POWER;
			incrementPC(8);
			break;
		case IE_64BIT_RAM:
			r[Memory.get(r[0] + 1)] = TRUE;
			incrementPC(8);
			break;
		case IE_32BIT_RAM:
			r[Memory.get(r[0] + 1)] = TRUE;
			incrementPC(8);
			break;
		case IE_16BIT_RAM:
			r[Memory.get(r[0] + 1)] = TRUE;
			incrementPC(8);
			break;
		case IE_8BIT_RAM:
			r[Memory.get(r[0] + 1)] = TRUE;
			incrementPC(8);
			break;
		case IE_IO:
			r[Memory.get(r[0] + 1)] = TRUE;
			incrementPC(8);
			break;
		case IE_MATH:
			r[Memory.get(r[0] + 1)] = TRUE;
			incrementPC(8);
			break;
		case IE_64BIT_FLOATING:
			r[Memory.get(r[0] + 1)] = TRUE;
			incrementPC(8);
			break;
		case IE_32BIT_FLOATING:
			r[Memory.get(r[0] + 1)] = TRUE;
			incrementPC(8);
			break;
		case I_CALL:
			System.out.println("Calling " + Long.toHexString(Memory.directGetLong(r[0] + 8)));
			incrementPC(16);
			Memory.setLong(r[5], r[0]);
			r[5] += 8;
			r[0] = Memory.getLong(pc + 8);
			
			System.out.println("Stack dump:");
			long offset = 0;
			for (long i = r[5] - 8; i >= r[5] - 256; i -= 8) {
				System.out.println("-" + Long.toHexString(offset) + " " + Long.toHexString(Memory.directGetLong(i)));
				offset += 8;
			}
			break;
		case I_CALL_POINTER:
			incrementPC(8);
			r[0] = r[Memory.get(pc + 1)];
			break;
		case I_INTERRUPT_R:
			incrementPC(8);
			returnInterruptStack();
			break;
		case I_RETURN:
			incrementPC(8);
			System.out.println("Returning to " + Memory.directGetLong(r[5] - 8));
			r[5] -= 8;
			r[0] = Memory.getLong(r[5]);
			System.out.println("Stack dump:");
			long offset2 = 0;
			for (long i = r[5] + 8; i >= r[5] - 1024; i -= 8) {
				System.out.println("-" + Long.toHexString(offset2) + " " + Long.toHexString(Memory.directGetLong(i)));
				offset2 += 8;
			}
			if (r[0] == 0) {
				run = false;
			}
			break;
		case I_INTERRUPT:
			incrementPC(8);
			interrupt(Memory.get(pc + 1));
			break;
		case I_JUMP:
			r[0] = Memory.getLong(r[0] + 8);
			break;
		case I_JUMP_EQU:
			if (r[Memory.get(r[0] + 1)] == r[Memory.get(r[0] + 2)]) {
				r[0] = Memory.getLong(r[0] + 8);
			} else {
				incrementPC(16);
			}
			break;
		case I_JUMP_NEQU:
			if (r[Memory.get(r[0] + 1)] != r[Memory.get(r[0] + 2)]) {
				r[0] = Memory.getLong(r[0] + 8);
			} else {
				incrementPC(16);
			}
			break;
		case I_JUMP_EQU_PTR:
			if (r[Memory.get(r[0] + 1)] == r[Memory.get(r[0] + 2)]) {
				r[0] = r[Memory.get(r[0] + 3)];
			} else {
				incrementPC(8);
			}
			break;
		case I_JUMP_NEQU_PTR:
			if (r[Memory.get(r[0] + 1)] != r[Memory.get(r[0] + 2)]) {
				r[0] = r[Memory.get(r[0] + 3)];
			} else {
				incrementPC(8);
			}
			break;
		case I_JUMP_POS:
			if (r[Memory.get(r[0] + 1)] > 0) {
				r[0] = Memory.getLong(r[0] + 8);
			} else {
				incrementPC(16);
			}
			break;
		case I_JUMP_NEG:
			if (r[Memory.get(r[0] + 1)] < 0) {
				r[0] = Memory.getLong(r[0] + 8);
			} else {
				incrementPC(16);
			}
			break;
		case I_JUMP_ZERO:
			if (r[Memory.get(r[0] + 1)] == 0) {
				r[0] = Memory.getLong(r[0] + 8);
			} else {
				incrementPC(16);
			}
			break;
		case I_JUMP_TRUE:
			if (r[Memory.get(r[0] + 1)] == TRUE) {
				r[0] = Memory.getLong(r[0] + 8);
			} else {
				incrementPC(16);
			}
			break;
		case I_JUMP_FALSE:
			if (r[Memory.get(r[0] + 1)] == FALSE) {
				r[0] = Memory.getLong(r[0] + 8);
			} else {
				incrementPC(16);
			}
			break;
		case I_JUMP_POS_PTR:
			if (r[Memory.get(r[0] + 1)] > 0) {
				r[0] = r[Memory.get(r[0] + 2)];
			} else {
				incrementPC(8);
			}
			break;
		case I_JUMP_NEG_PTR:
			if (r[Memory.get(r[0] + 1)] < 0) {
				r[0] = r[Memory.get(r[0] + 2)];
			} else {
				incrementPC(8);
			}
			break;
		case I_JUMP_ZERO_PTR:
			if (r[Memory.get(r[0] + 1)] == 0) {
				r[0] = r[Memory.get(r[0] + 2)];
			} else {
				incrementPC(8);
			}
			break;
		case I_JUMP_NAN:
			if (r[Memory.get(r[0] + 1)] == Conversion.doubleToLong(Double.NaN)) {
				r[0] = Memory.getLong(r[0] + 8);
			} else {
				incrementPC(16);
			}
			break;
		case I_JUMP_NAN_PTR:
			if (r[Memory.get(r[0] + 1)] == Conversion.doubleToLong(Double.NaN)) {
				r[0] = r[Memory.get(r[0] + 1)];
			} else {
				incrementPC(8);
			}
			break;
		case I_JUMP_PTR:
			r[0] = r[Memory.get(r[0] + 1)];
			break;
		case IE_CALLING:
			r[Memory.get(r[0] + 1)] = TRUE;
			incrementPC(8);
			break;
		case IE_JUMP:
			r[Memory.get(r[0] + 1)] = TRUE;
			incrementPC(8);
			break;
		case IE_CAST:
			r[Memory.get(r[0] + 1)] = TRUE;
			incrementPC(8);
			break;
		case IC_BYTE:
			r[Memory.get(r[0] + 2)] = ((byte) r[Memory.get(r[0] + 1)]) & 0xFF;
			incrementPC(8);
			break;
		case IC_SHORT:
			r[Memory.get(r[0] + 2)] = ((short) r[Memory.get(r[0] + 1)]) & 0xFFFF;
			incrementPC(8);
			break;
		case IC_INT:
			r[Memory.get(r[0] + 2)] = ((short) r[Memory.get(r[0] + 1)]) & 0xFFFFFFFF;
			incrementPC(8);
			break;
		case IC_LONG:
			r[Memory.get(r[0] + 2)] = ((long) r[Memory.get(r[0] + 1)]);
			incrementPC(8);
			break;
		case IC_FLOAT:
			r[Memory.get(r[0] + 2)] = Conversion.floatToInt((float) r[Memory.get(r[0] + 1)]);
			incrementPC(8);
			break;
		case IC_DOUBLE:
			r[Memory.get(r[0] + 2)] = Conversion.doubleToLong((double) r[Memory.get(r[0] + 1)]);
			incrementPC(8);
			break;
		case IE_ARRAY:
			r[Memory.get(r[0] + 1)] = TRUE;
			incrementPC(8);
			break;
		case I_HALT_ERR:
			System.err.println(Memory.get(r[0] + 1));
			System.exit(Memory.get(r[0] + 1));
			incrementPC(8);
			break;
		case IE_STACK:
			r[Memory.get(r[0] + 1)] = TRUE;
			incrementPC(8);
			break;
		case IS_INTERRUPT:
			r[6] = Memory.getLong(r[0] + 8);
			r[8] = r[6] + 2048;
			incrementPC(16);
			break;
		case IS_SETUPSTACK:
			r[5] = Memory.getLong(r[0] + 8);
			incrementPC(16);
			break;
		case IE_ADDITIONAL_OPS:
			r[Memory.get(r[0] + 1)] = TRUE;
			incrementPC(8);
			break;
		case IP_JUMP_LESS:
			if (r[Memory.get(r[0] + 1)] < r[Memory.get(r[0] + 2)]) {
				r[0] = Memory.getLong(r[0] + 8);
			} else {
				incrementPC(16);
			}
			break;
		case IP_JUMP_MORE:
			if (r[Memory.get(r[0] + 1)] > r[Memory.get(r[0] + 2)]) {
				r[0] = Memory.getLong(r[0] + 8);
			} else {
				incrementPC(16);
			}
			break;
		case IP_JUMP_LESS_EQU:
			if (r[Memory.get(r[0] + 1)] <= r[Memory.get(r[0] + 2)]) {
				r[0] = Memory.getLong(r[0] + 8);
			} else {
				incrementPC(16);
			}
			break;
		case IP_JUMP_MORE_EQU:
			if (r[Memory.get(r[0] + 1)] >= r[Memory.get(r[0] + 2)]) {
				r[0] = Memory.getLong(r[0] + 8);
			} else {
				incrementPC(16);
			}
			break;
		case IP_JUMP_LESS_PTR:
			if (r[Memory.get(r[0] + 1)] < r[Memory.get(r[0] + 2)]) {
				r[0] = r[Memory.get(r[0] + 3)];
			} else {
				incrementPC(8);
			}
			break;
		case IP_JUMP_MORE_PTR:
			if (r[Memory.get(r[0] + 1)] > r[Memory.get(r[0] + 2)]) {
				r[0] = r[Memory.get(r[0] + 3)];
			} else {
				incrementPC(8);
			}
			break;
		case IP_JUMP_LESS_PTR_EQU:
			if (r[Memory.get(r[0] + 1)] <= r[Memory.get(r[0] + 2)]) {
				r[0] = r[Memory.get(r[0] + 3)];
			} else {
				incrementPC(8);
			}
			break;
		case IP_JUMP_MORE_PTR_EQU:
			if (r[Memory.get(r[0] + 1)] >= r[Memory.get(r[0] + 2)]) {
				r[0] = r[Memory.get(r[0] + 3)];
			} else {
				incrementPC(8);
			}
			break;
		case IP_LSHIFT:
			r[Memory.get(r[0] + 3)] = r[Memory.get(r[0] + 1)] >>> -r[Memory.get(r[0] + 2)];
			incrementPC(8);
			break;
		case IP_RSHIFT:
			r[Memory.get(r[0] + 3)] = r[Memory.get(r[0] + 1)] >>> r[Memory.get(r[0] + 2)];
			incrementPC(8);
			break;
		case IP_LSHIFT_SAFE:
			r[Memory.get(r[0] + 3)] = r[Memory.get(r[0] + 1)] << r[Memory.get(r[0] + 2)];
			incrementPC(8);
			break;
		case IP_RSHIFT_SAFE:
			r[Memory.get(r[0] + 3)] = r[Memory.get(r[0] + 1)] >> r[Memory.get(r[0] + 2)];
			incrementPC(8);
			break;
		default:
			System.err.println("Invalid Instruction: 0x" + Integer.toHexString(ins));
			break;
		}
	}

	@Override
	public void interrupt() {
		interrupt((byte) 0);
	}

	public void interrupt(byte id) {
		interrupt = true;
		interrupt_id = id;
	}

	public void output(long port, long data) {

	}

	public long input(long port) {
		return 0;
	}
}
