package br.com.alois.solution.configuration;

public class NotificationServerConfiguration {
	/**
     * Server address
     */
    public static final String SERVER_ADDRESS = "fcm.googleapis.com";
    /**
     *
     */
    public static final String HTTP_HOST = "http://"+SERVER_ADDRESS;
    /**
     *
     */
    private static final String API_CONTEXT = "/fcm";
    /**
     *
     */
    public static final String API_ENDPOINT = HTTP_HOST + API_CONTEXT;
    /**
     * Google Api registration token
     */
    public static final String FIREBASE_TOKEN = "AIzaSyCRCBo9gqrkftSH_5ir6T1c8c385tgZ4Z4";
    
}
