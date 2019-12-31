package com.louddt.tag.utils;

public class UUID {

	public static String uuId() {
		java.util.UUID uuid = java.util.UUID.randomUUID();
		String result = uuid.toString();
		return result.replaceAll("-", "");
	}
	
	public static void main(String[] args) {
		System.out.println(uuId());
	}
}
