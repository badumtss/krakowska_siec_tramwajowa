package timeTableParser;

public class ScheduleTest {

	public static void main(String[] args) {
		Schedule schedule = new Schedule("src/newdata");
		for(String i : schedule.schedules.keySet())
		System.out.println(schedule.schedules.get(i));
	}

}
