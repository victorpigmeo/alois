package br.com.alois.aloismobile.application.preference;

/**
 * Created by victor on 2/27/17.
 * Class containing the server configurations
 */

public class ServerConfiguration
{
    /**
     * Server address
     */
    public static final String SERVER_ADDRESS = "192.168.25.14:8080";
    /**
     *
     */
    public static final String HTTP_HOST = "http://"+SERVER_ADDRESS;
    /**
     *
     */
    private static final String API_CONTEXT = "/api";
    /**
     *
     */
    public static final String API_ENDPOINT = HTTP_HOST + API_CONTEXT;

}
