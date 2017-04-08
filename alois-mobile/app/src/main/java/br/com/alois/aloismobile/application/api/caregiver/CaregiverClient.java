package br.com.alois.aloismobile.application.api.caregiver;

import java.util.List;

import br.com.alois.domain.entity.user.Caregiver;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

/**
 * Created by sarah on 3/29/17.
 */

public interface CaregiverClient
{
    @RequestLine("GET /caregiver/listAll")
    @Headers({"Content-Type: application/json", "Authorization: Basic {basicAuthToken}"})
    List<Caregiver> getCaregiverList(@Param("basicAuthToken") String basicAuthToken);

    @RequestLine("POST /caregiver/updateNotificationToken/{caregiverId}/{notificationToken}")
    @Headers({"Content-Type: application/json", "Authorization: Basic {basicAuthToken}"})
    void updateNotificationToken(@Param("notificationToken") String notificationToken, @Param("caregiverId") Long caregiverId, @Param("basicAuthToken") String basicAuthToken);
}
