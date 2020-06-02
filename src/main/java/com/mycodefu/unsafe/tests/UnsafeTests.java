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
	System.out.println();
}
}
