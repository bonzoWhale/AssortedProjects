package bwhale.reminder.service;

import bwhale.reminder.data.ReminderDB;

public class NotificationService implements Runnable {
	
	//Thread control
	private boolean isRunning;

	//How often the service will check for new notifications to send (should be less than a minute)
	private long pollingRate;
	//How often (in mins) should upcoming Reminders be moved to the partial list (for easier access)
	private long imminenceCriteria;
	
	//DB to hold all reminders in this service TODO: add persistence
	ReminderDB database;
	
	
	/***
	 * 
	 * @param pollingRate How often (in ms) should pending notifications be sent
	 * @param imminenceCriteria Minutes ahead that a Reminder's due date will be considered imminent
	 */
	public NotificationService(long pollingRate, long imminenceCriteria) {
		super();
		this.pollingRate = pollingRate;
		this.imminenceCriteria = imminenceCriteria;
	}
	
	
	private void info(String msg) {
		System.out.println("INFO — " + msg);
	}
	
	private void debug(String msg) {
		System.out.println("DEBUG — " + msg);
	}	

	@Override
	public void run() {
		isRunning = true;
		
		info("Starting Notification Service...");
		
		database = new ReminderDB(imminenceCriteria);
		debug("Created Reminder Database");
		
		while (isRunning) {
			debug("Thread is running...");
			
			database.sendAllReminders();
			
			//TODO schedule repopulation of imminent list
			
			//Pause thread, depending on polling rate
			try {
				Thread.sleep(pollingRate);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		info("Ending Notification Service");
	}

	public void requestStop() {
		debug("Received request to stop Service");
		isRunning = false;
	}

	public ReminderDB getDatabase() {
		return this.database;
	}
	
}
