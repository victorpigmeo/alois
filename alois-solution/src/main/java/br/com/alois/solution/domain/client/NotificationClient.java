package br.com.alois.solution.domain.client;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface NotificationClient {

	@RequestLine("POST /send")
	@Headers({"Content-Type: application/json", "Authorization: key={firebaseToken}"})
	String sendNotification(String notification, @Param("firebaseToken") String firebaseToken);
	
}
