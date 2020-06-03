package com.mycodefu.unsafe.tests;

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
	unsafe.reallocateMemory(address, 16);
	System.out.println(unsafe.getInt(address));
	unsafe.freeMemory(address);
	System.out.println("memory freed!");
	dynamicArray da = new dynamicArray(4, unsafe);
	da.add(Integer.MAX_VALUE);
	da.add(4);
	da.add(Integer.MAX_VALUE);
	System.out.println();
	System.out.println(da.get(0));
	System.out.println(da.get(1));
	System.out.println(da.get(2));
}

public static class dynamicArray {
	long address;
	long currentBytes;
	long currentIndex = 0;
	Unsafe unsafe;

	public dynamicArray(long initialCapacity, Unsafe unsafe) {
		this.currentBytes = initialCapacity;
		this.address = unsafe.allocateMemory(initialCapacity);
		this.unsafe = unsafe;
	}

public void add(int i) {
	if(currentIndex+Integer.BYTES > currentBytes) {
		unsafe.reallocateMemory(address, currentBytes<<1);
		currentBytes = currentBytes<<1;
	}
	unsafe.putInt(address+currentIndex, i);
	currentIndex+=Integer.BYTES;
}
public int get(int index) {
	return unsafe.getInt(address+Integer.BYTES*index);
}
public void free() {
	unsafe.freeMemory(address);
}
}
}
