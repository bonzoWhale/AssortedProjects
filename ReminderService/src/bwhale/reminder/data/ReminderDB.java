package bwhale.reminder.data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import bwhale.reminder.model.EmailNotifier;
import bwhale.reminder.model.Notifier;
import bwhale.reminder.model.Reminder;

/***
 *	Placeholder class that stores Reminder objects
 *	Should be replaced with a proper Database
 */

public class ReminderDB {
	
	//We'll hold the Reminders in Lists, for now
	private List<Reminder> openReminders;
	
	//We hold a list of upcoming reminders, for easy access
	private List<Reminder> imminentReminders;
	
	// Criteria for an upcoming Reminder due date to be considered imminent
	private long imminenceCriteria;
	
	private long reminderId = 0;
	
	
	/***
	 * Constructor: initializes all internal lists and sets the imminence Criteria
	 */
	public ReminderDB(long imminenceCriteria) {
		//Start up all lists
		openReminders = new ArrayList<Reminder>();
		imminentReminders = new ArrayList<Reminder>();
		this.imminenceCriteria = imminenceCriteria;
	}
	
	/**
	 * Public method to create a reminder which will use "email" as a notifier
	 * @param message
	 * @param email
	 */
	public void createNewEmailReminder(String message, LocalDateTime dueDate, String email) {
		Notifier emailNotifier = new EmailNotifier(email);
		createNewReminder(message, dueDate, emailNotifier);
	}
	
	/**
	 * Internal method to create reminders with a new ID (Reminder factory)
	 * @param message Reminder message
	 * @param n notifier
	 * @return
	 */
	private Reminder createNewReminder(String message, LocalDateTime dueDate, Notifier n) {
		Reminder newReminder = new Reminder(++reminderId, message, dueDate, n);
		addNewReminder(newReminder);
		return newReminder;
	}

	/**
	 * Add new Reminder to the DB
	 * @param r
	 */
	private void addNewReminder(Reminder r) {
		openReminders.add(r);
		
		//Check if the reminder's due date is near
		if (reminderIsImminent(r)) {
			imminentReminders.add(r);
		}
	}
	
	/**
	 * This method will empty the imminent Reminder list and repopulate it
	 * Should be used to enforce periodic requests of the Imminent Reminder list
	 */
	public void repopulateImminentReminders() {
		//TODO Streams can be used here
		
		synchronized (imminentReminders) {
			//Clear list
			imminentReminders.clear();
			
			//Add reminders with imminent due dates
			for (Reminder r : openReminders) {
				if (reminderIsImminent(r)) {
					imminentReminders.add(r);
				}
			}
		}
	}
	
	/**
	 * 
	 * @param timeDifference
	 */
	public void changeImminenceCriteria(long minutesAhead) {
		imminenceCriteria = minutesAhead;
	}
	
	private boolean reminderIsImminent(Reminder r) {
		if (r.hasReminderBeenSent()) {
			//Sent reminders are never imminent
			return false;
		}
		
		if (r.getDueDate().compareTo(LocalDateTime.now().plusMinutes(imminenceCriteria)) <= 0) {
			//Reminder is due within X minutes
			return true;
		}
		
		return false;
	}

	/**
	 * A list of all reminders currently in the DB
	 * @return
	 */
	public String getAllReminders() {
		// TODO order list with streams
		
		String reminderList = "";
		synchronized (openReminders) {
			for (Reminder r : openReminders) {
				reminderList += r.toString() +  '\n';
			}
		}
		return reminderList;
	}

	public void sendAllReminders() {
		synchronized (imminentReminders) {
			for (Reminder r : imminentReminders) {
				if (r.getDueDate().compareTo(LocalDateTime.now()) <= 0){
					//It's time to send reminder
					r.sendReminder();
				}
			}
			imminentReminders.clear();
		}
	}

	public Reminder getReminder(long id) {
		//TODO Return reminder with given ID
		
		return null; //temporary TODO delete
		
	}
}
