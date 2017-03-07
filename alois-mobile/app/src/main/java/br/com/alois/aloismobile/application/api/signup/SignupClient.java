package br.com.alois.aloismobile.application.api.signup;

import br.com.alois.domain.entity.user.Caregiver;
import feign.Headers;
import feign.RequestLine;

/**
 * Created by victor on 3/1/17.
 */

public interface SignupClient {
    @RequestLine("POST /login/signup")
    @Headers({"Content-Type: application/json"})
    Caregiver signup(Caregiver caregiver);
}
