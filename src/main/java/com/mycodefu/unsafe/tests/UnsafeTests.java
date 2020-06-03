package com.mycodefu.unsafe.tests;

import java.util.Arrays;

import sun.misc.Unsafe;

public class UnsafeTests {
public static void main(String[] args) throws Exception {
	var unsafeConstructor = Unsafe.class.getDeclaredConstructor();
	unsafeConstructor.setAccessible(true);
	Unsafe unsafe = unsafeConstructor.newInstance();
	byte[] bytes = new byte[16];
	for(byte b : bytes) {
		System.out.printf("%s, ", Byte.toString(b));
	}
	System.out.println("\n");
	unsafe.putLong(bytes, unsafe.arrayBaseOffset(byte[].class), Long.MAX_VALUE);
	for(byte b : bytes) {
		System.out.printf("%s, ", Byte.toString(b));
	}
	System.out.println("\n");
	unsafe.putChar(bytes, unsafe.arrayBaseOffset(bytes.getClass()), 'h');
	for(byte b : bytes) {
		System.out.printf("%s, ", Byte.toString(b));
	}
	System.out.println("\n");
	System.out.println(Long.MAX_VALUE);
	System.out.println(unsafe.getLong(bytes, unsafe.arrayBaseOffset(bytes.getClass())));
	long address = unsafe.allocateMemory(8);
	System.out.println(address);
	unsafe.putInt(address, Integer.MAX_VALUE);
	System.out.println(unsafe.getInt(address));
	System.gc();
	System.out.println(unsafe.getInt(address));
	unsafe.freeMemory(address);
}
}
