/**
 * This program creates a class called Time with parameters for hour, minute, and second, a no-arg
 * 	constructor that creates a Time object for the current time, a constructor that constructs a Time
 * 	object with elapsed time since midnight, January 1, 1970 in milliseconds, a constructor that
 * 	constructs a Time object with the specified hour, minute, and second, three get methods for hour,
 * 	minute, and second, and a method named setTime that sets a new time for the object using the
 * 	elapsed time.
 * The program first displays its function, before calling the time class, creating two Time objects
 * 	called time1 and time2 – the first of these printing the time since midnight, January 1, 1970,
 * 	and the second printing a custom time of 555550000 milliseconds.
 * The Time class has three constructors with the functions described previously, and three get methods
 * 	returning hour, minute, and second respectively.
 * The setTime method converts the hour to an integer, divides it by 3600000 (milliseconds) and mods
 * 	that number by 24 to create the current hour on a 24 hour interval. The minute is created by
 * 	modding elapseTime by 3600000 and dividing by 60000. The second is created by modding elapse time
 * 	by 3600000, modding this number by 60000, and dividing it by 1000.
 * @author Steven Wiener
 * @Version 1
 */
public class PrintTimeElapsed {
	public static void main(String[] args) {
		// Display function of program
		System.out.println("This program lists the time since January 1, 1970, \nas well as a custom time from 555550000 milliseconds.");
		
		/** Create and print a Time since January 1, 1970
		time1.hour and time1.getHour are interchangeable */
		Time time1 = new Time();
		System.out.println(time1.hour + ":" + time1.minute + ":" + time1.second);
		
		// Create and print a Time with custom value in milliseconds
		Time time2 = new Time(555550000);		
		System.out.println(time2.getHour() + ":" + time2.getMinute() + ":" + time2.getSecond());
	}
}	

// Define the time class with three constructors
class Time	{
	// Hour, minute, and second data fields
	int hour;
	int minute;
	int second;
	
	// Construct a time with time since January 1, 1970
	Time()	{
		setTime(System.currentTimeMillis());
	}
	
	// Construct a time with custom time in milliseconds
	Time(long elapseTime)	{
		setTime(elapseTime);
	}
	
	// Construct a time with custom time in hour:minute:second
	Time(int specefiedHour, int specefiedMinute, int specefiedSecond)	{
		hour = specefiedHour;
		minute = specefiedMinute;
		second = specefiedSecond;
	}
	
	// Return the hour of the time
	public int getHour()	{
		return hour;
	}
	
	// Return the minute of the time
	public int getMinute()	{
		return minute;
	}
	
	// Return the second of the time
	public int getSecond()	{
		return second;
	}
	
	// Set a new time with elapsed milliseconds
	private void setTime(long elapseTime)	{
		hour = (int) (elapseTime / 3600000) % 24;
		minute = (int) (elapseTime % 3600000) / 60000;
		second = (int) ((elapseTime % 3600000) % 60000) / 1000;
	}
}

