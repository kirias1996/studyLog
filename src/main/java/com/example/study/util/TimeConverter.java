package com.example.study.util;

public class TimeConverter {
	
	public static final double MINUTES_PER_HOUR = 60.0;
	// インスタンス生成を抑止	
	private TimeConverter() {
		
	}

	public static int getHour(double decimalTime) {
        return (int) decimalTime;
    }

    public static int getMinute(double decimalTime) {
        return (int) Math.round((decimalTime - getHour(decimalTime)) * MINUTES_PER_HOUR);
    }

    public static double toTotalMinutes(int hours, int minutes) {
        return (hours * MINUTES_PER_HOUR) + minutes;
    }
	
}
