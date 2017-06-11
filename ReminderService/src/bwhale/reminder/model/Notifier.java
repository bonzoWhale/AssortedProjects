package bwhale.reminder.model;

public interface Notifier {
	/**
	 * Send notification to destinatary
	 * @return true if Notification was successful
	 */
	public boolean sendNotification(String message);
	
	/**
	 * Provide the Notifier who the notification will be for
	 * @param destinatary
	 */
	public void setDestinatary(String destinatary);
}
