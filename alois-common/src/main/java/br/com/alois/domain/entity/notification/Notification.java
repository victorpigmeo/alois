package br.com.alois.domain.entity.notification;

public abstract class Notification 
{
	public static String toJson(String title, String message, String patientName, String deviceToken)
	{
		return "{ \"notification\": "
					+ "{"
						+ "\"title\": \""+title+"\","
						+ "\"text\": \""+message+"\","
						+ "\"sound\": \"default\","
					+ "},"
				+ "\"to\" : \""+deviceToken+"\","
				+ "\"data\" : {"
					+ "\"patient_name\": \""+patientName+"\","
					+ "\"type\":\"PATIENT_OUT_OF_ROUTE\""
				+ "}"
			+ "}";
	}
}
