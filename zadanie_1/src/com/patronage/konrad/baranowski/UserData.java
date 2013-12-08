package com.patronage.konrad.baranowski;

public class UserData {
	public static long time = 0;
	public static long result = 0;
	public static boolean isLose = false;
	public static void flush(){
		time = 0;
		result = 0;
		isLose = false;
	}
}
