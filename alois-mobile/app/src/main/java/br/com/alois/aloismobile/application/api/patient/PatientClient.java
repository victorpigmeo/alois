package br.com.alois.aloismobile.application.api.patient;

import java.util.List;

import br.com.alois.domain.entity.user.Patient;
import feign.Body;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

/**
 * Created by victor on 3/17/17.
 */

public interface PatientClient
{
    @RequestLine("GET /patient/listByCaregiverId/{caregiverId}")
    @Headers({"Content-Type: application/json", "Authorization: Basic {basicAuthToken}"})
    List<Patient> listPatientsByCaregiverId(@Param("caregiverId") Long caregiverId, @Param("basicAuthToken") String basicAuthToken);

    @RequestLine("GET /patient/findById/{patientId}")
    @Headers({"Content-Type: application/json", "Authorization: Basic {basicAuthToken}"})
    Patient findById(@Param("patientId") Long patientId, @Param("basicAuthToken") String basicAuthToken);

    @RequestLine("POST /patient/insert")
    @Headers({"Content-Type: application/json", "Authorization: Basic {basicAuthToken}"})
    Patient addPatient(Patient patient, @Param("basicAuthToken") String basicAuthToken);

    @RequestLine("PUT /patient/update")
    @Headers({"Content-Type: application/json", "Authorization: Basic {basicAuthToken}"})
    Patient updatePatient(Patient patient, @Param("basicAuthToken") String basicAuthToken);
}
