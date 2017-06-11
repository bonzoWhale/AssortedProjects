package bwhale.reminder.model;

import bwhale.reminder.model.Notifier;

public class EmailNotifier implements Notifier {

	//destinatary email address
	private String emailAddress;
	
	/**
	 * Constructor
	 * @param emailAddress
	 */
	public EmailNotifier (String emailAddress) {
		setDestinatary(emailAddress);
	}
	
	@Override
	public boolean sendNotification(String message) {
		if (emailAddress != null) {
			// TODO Actually send an Email
			System.out.println("[To:" + emailAddress + "\nMessage: " + message + ']');
			return true;
		}
		
		return false;
	}

	@Override
	public void setDestinatary(String destinatary) {
		//TODO email validation
		emailAddress = destinatary;
	}

}
