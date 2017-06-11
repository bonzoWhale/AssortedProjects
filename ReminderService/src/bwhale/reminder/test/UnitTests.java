package bwhale.reminder.test;

import java.time.LocalDateTime;
import bwhale.reminder.data.ReminderDB;
import bwhale.reminder.service.NotificationService;


public class UnitTests {

	/**
	 * Start a new notification service
	 * Run all unit tests
	 * @param args
	 */
	public static void main(String[] args) {
		//Start service
		NotificationService svc = startService();
		
		//Will not print anything
		printAllReminders(svc.getDatabase());
		
		//START TESTING
		
		testAddNewEmailReminder(svc.getDatabase());
		printAllReminders(svc.getDatabase());
		
		waitABit(5);
		
		
		addReminder(svc.getDatabase(), "First", 3);
		addReminder(svc.getDatabase(), "Second", 0);
		addReminder(svc.getDatabase(), "Third", 1);
		printAllReminders(svc.getDatabase());
		waitABit(5);
		
		
		
		waitABit(10*60);
		printAllReminders(svc.getDatabase());
		svc.requestStop();
	}
	
	private static void waitABit(int seconds) {
		try {
			Thread.sleep(seconds*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
	}
	
	/**
	 * Start Test Notification Service
	 * @return
	 */
	private static NotificationService startService() {
		long pollingRate = (60*1000/2); //Twice per minute
		long criteria = 5; //5 minutes imminence criteria
		NotificationService svc = new NotificationService(pollingRate, criteria);
		
		Thread serviceThread = new Thread(svc);
		serviceThread.start();
		
		//Give the service a second to start
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return svc;
	}
	
	/**
	 * Easy method to print all existing Reminders
	 * @param database
	 */
	private static void printAllReminders(ReminderDB database) {
		System.out.println(database.getAllReminders());
	}
	
	//Internal testing method
	private static void addReminder(ReminderDB database, String genericId, long dueInMinutes) {
		LocalDateTime dueDate = LocalDateTime.now().plusMinutes(dueInMinutes);
		database.createNewEmailReminder("Reminder " + genericId, dueDate, ""+ dueInMinutes + "address@unit.com");
	}
	
	
	/**
	 * Add new Email reminder; this should work regardless of new Reminder/Notifier functionality 
	 * @param database
	 */
	public static void testAddNewEmailReminder(ReminderDB database) {
		LocalDateTime dueDate = LocalDateTime.now().plusMinutes(5);
		database.createNewEmailReminder("Unit Test 1 Reminder!", dueDate, "address@unit.com");
	}

}
