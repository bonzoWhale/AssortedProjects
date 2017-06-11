package bwhale.reminder.http;

import bwhale.reminder.api.ReminderAPI;

public class RequestMapper implements Runnable {
	
	ReminderAPI api;
	
	public RequestMapper(ReminderAPI api) {
		this.api = api;
	}

	@Override
	public void run() {
		/*
		//Start http server which will map requests to the service
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        
        //Create contexts
        server.createContext("/reminders/", new ReminderHandler(api));        
        
        server.start();
        
        */
	}

}
