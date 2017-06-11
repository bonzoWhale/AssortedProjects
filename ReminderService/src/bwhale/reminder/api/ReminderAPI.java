package bwhale.reminder.api;

import java.time.LocalDateTime;

import bwhale.reminder.data.ReminderDB;
import bwhale.reminder.model.Reminder;

public class ReminderAPI {
	
	private ReminderDB database;
	
	public ReminderAPI(ReminderDB database) {
		this.database = database;
	}

	/**
	 *   GET /reminder/
	 */
	public String getReminders() {
		return database.getAllReminders();
	}
	
	/**
	 * POST /reminder/
	 * 
	 * @param message
	 * @param dueDate
	 * @param email
	 */
	public void putReminder(String message, LocalDateTime dueDate, String email) {
		database.createNewEmailReminder(message, dueDate, email);
	}
	
	/**
	 *  GET  /reminder/{id}
	 *  
	 * @param id
	 * @return
	 */
	public Reminder getReminder(long id) {
		return database.getReminder(id);
	}
}
