package br.com.alois.aloismobile.application.api.login;

import com.fasterxml.jackson.databind.node.ObjectNode;

import br.com.alois.domain.entity.user.User;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

/**
 * Created by victor on 3/1/17.
 */

public interface LoginClient {
    @RequestLine("POST /login/authenticate")
    @Headers({"Content-Type: application/json", "Authorization: Basic {basicAuthToken}"})
    User doLogin(ObjectNode params, @Param("basicAuthToken") String basicAuthToken);
}
