package br.com.alois.aloismobile.application.api.reminder;

import java.util.List;

import br.com.alois.domain.entity.reminder.Reminder;
import br.com.alois.domain.entity.reminder.ReminderStatus;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

/**
 * Created by victor on 4/23/17.
 */

public interface ReminderClient
{
    @RequestLine("POST /reminder/sendRequest")
    @Headers({"Content-Type: application/json", "Authorization: Basic {basicAuthToken}"})
    Reminder addPendingReminder(Reminder reminder, @Param("basicAuthToken") String basicAuthToken);

    @RequestLine("GET /reminder/listReminderByPatientId/{patientId}")
    @Headers({"Content-Type: application/json", "Authorization: Basic {basicAuthToken}"})
    List<Reminder> listRemindersByPatientId(@Param("patientId") Long patientId, @Param("basicAuthToken") String userAuthToken);

    @RequestLine("POST /reminder/update")
    @Headers({"Content-Type: application/json", "Authorization: Basic {basicAuthToken}"})
    Reminder updateReminder(Reminder reminder, @Param("basicAuthToken") String loggedUserAuthToken);

    @RequestLine("POST /reminder/deleteRequest/{reminderId}")
    @Headers({"Content-Type: application/json", "Authorization: Basic {basicAuthToken}"})
    void deleteReminderRequest(@Param("reminderId") Long reminder, @Param("basicAuthToken") String userAuthToken);

    @RequestLine("POST /reminder/delete")
    @Headers({"Content-Type: application/json", "Authorization: Basic {basicAuthToken}"})
    void deleteReminder(Reminder reminder, @Param("basicAuthToken") String loggedUserAuthToken);

    @RequestLine("POST /reminder/updateAtivoReminder/{reminderId}/{reminderStatus}")
    @Headers({"Content-Type: application/json", "Authorization: Basic {basicAuthToken}"})
    void updateStatusReminder(@Param("reminderId") Long reminderId, @Param("reminderStatus") ReminderStatus reminderStatus, @Param("basicAuthToken") String loggedUserAuthToken);

    @RequestLine("GET /reminder/listActiveRemindersByPatientId/{patientId}")
    @Headers({"Content-Type: application/json", "Authorization: Basic {basicAuthToken}"})
    List<Reminder> listActiveRemindersByPatientId(@Param("patientId") Long patientId, @Param("basicAuthToken") String loggedUserAuthToken);
}
