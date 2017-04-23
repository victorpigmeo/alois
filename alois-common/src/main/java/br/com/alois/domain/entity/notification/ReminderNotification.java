package br.com.alois.domain.entity.notification;

public class ReminderNotification 
{
	public String data;
	
	public ReminderNotification setData(String jsonData)
	{
		data = jsonData;
		return this;
	}
	
	public String toJson( String deviceToken)
	{
		return "{\"to\" : \""+deviceToken+"\","
				+ "\"data\" : {"
					+ "\"type\": \"ADD_REMINDER_REQUEST\","
					+ "\"reminder\": "+this.data
				+ "}"
			+ "}";
	}
}
