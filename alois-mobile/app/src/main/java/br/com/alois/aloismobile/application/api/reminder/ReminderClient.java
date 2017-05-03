package br.com.alois.aloismobile.application.api.reminder;

import java.util.List;

import br.com.alois.domain.entity.reminder.Reminder;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

/**
 * Created by victor on 4/23/17.
 */

public interface ReminderClient
{
    @RequestLine("POST /reminder/insert")
    @Headers({"Content-Type: application/json", "Authorization: Basic {basicAuthToken}"})
    Reminder addReminder(Reminder reminder, @Param("basicAuthToken") String basicAuthToken);

    @RequestLine("POST /reminder/sendRequest")
    @Headers({"Csontent-Type: application/json", "Authorization: Basic {basicAuthToken}"})
    Reminder sendRequest(Reminder reminder, @Param("basicAuthToken") String basicAuthToken);

    @RequestLine("POST /reminder/listReminderByPatientId")
    @Headers({"Csontent-Type: application/json", "Authorization: Basic {basicAuthToken}"})
    List<Reminder> listRemindersByPatientId(Long patientId, String userAuthToken);
}
