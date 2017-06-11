package bwhale.reminder.http;

import bwhale.reminder.api.ReminderAPI;

public abstract class ReminderHandler /*implements HttpHandler */ {
	
		ReminderAPI api;
	
		public ReminderHandler(ReminderAPI api) {
			this.api = api;
		}
	/*
        @Override
        public abstract void handle(HttpExchange t) throws IOException;*/
}
