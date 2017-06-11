package bwhale.reminder.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * This object type holds a reminder that the end-user will be notified about when the time comes.
 *
 */
public class Reminder {
	
	private final long reminderId;
	private LocalDateTime dueDate;
	private String reminderMessage;
	private Notifier assignedNotifier;
	private boolean status;
	
	public Reminder(long id, String message, LocalDateTime dueDate, Notifier n) {
		reminderId = id;
		setDueDate(dueDate);
		reminderMessage = message;
		assignedNotifier = n;
		status = false;
	}
	
	@Override
	public String toString() {
		return "Reminder [ID: " + reminderId + ", Due: " + dueDate + ", Sent: " + status + "]";
	}

	/**
	 * Send Reminder using the currently assigned Notifier
	 */
	public void sendReminder() {
		if (assignedNotifier.sendNotification(reminderMessage)) {
			status = true;
		}
	}
	
	/**
	 * 
	 * @return true if Reminder has been processed
	 */
	public boolean hasReminderBeenSent() {
		return status;
	}

	
	
	/**  GETTERS AND SETTERS **/
	
	public LocalDateTime getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDateTime dueDate) {
		this.dueDate = dueDate.truncatedTo(ChronoUnit.MINUTES);
	}

	public String getReminderMessage() {
		return reminderMessage;
	}

	public void setReminderMessage(String reminderMessage) {
		this.reminderMessage = reminderMessage;
	}

	public Notifier getAssignedNotifier() {
		return assignedNotifier;
	}

	public void setAssignedNotifier(Notifier assignedNotifier) {
		this.assignedNotifier = assignedNotifier;
	}

	public long getReminderId() {
		return reminderId;
	}
}
