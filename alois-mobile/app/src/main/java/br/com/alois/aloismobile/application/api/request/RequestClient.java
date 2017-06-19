package br.com.alois.aloismobile.application.api.request;

import java.util.List;
import br.com.alois.domain.entity.memory.Memory;
import br.com.alois.domain.entity.user.Patient;
import br.com.alois.domain.entity.user.Request;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

/**
 * Created by victor on 6/1/17.
 */

public interface RequestClient
{
    @RequestLine("GET /request/listRequestByPatientId/{patientId}")
    @Headers({"Content-Type: application/json", "Authorization: Basic {basicAuthToken}"})
    List<Request> listLogoffRequestsByPatientId(@Param("patientId") Long patientId, @Param("basicAuthToken") String basicAuthToken);

    @RequestLine("GET /request/listMemoryRequestsByPatientId/{patientId}")
    @Headers({"Content-Type: application/json", "Authorization: Basic {basicAuthToken}"})
    List<Request> listMemoryRequestsByPatientId(@Param("patientId") Long patientId, @Param("basicAuthToken") String basicAuthToken);

    @RequestLine("GET /request/approveLogoffRequest")
    @Headers({"Content-Type: application/json", "Authorization: Basic {basicAuthToken}"})
    Request approveLogoffRequest(Request request, @Param("basicAuthToken") String basicAuthToken);

    @RequestLine("GET /request/discardLogoffRequest")
    @Headers({"Content-Type: application/json", "Authorization: Basic {basicAuthToken}"})
    Request discardLogoffRequest(Request request, @Param("basicAuthToken") String basicAuthToken);

    @RequestLine("POST /request/requestLogoff")
    @Headers({"Content-Type: application/json", "Authorization: Basic {basicAuthToken}"})
    Request requestLogoff(Request patient, @Param("basicAuthToken") String basicAuthToken);

    @RequestLine("GET /request/getPatientLogoffApprovedRequest/{patientId}")
    @Headers({"Content-type: application/json", "Authorization: Basic {basicAuthToken}"})
    Request getPatientLogoffApprovedRequest(@Param("patientId") Long patientId, @Param("basicAuthToken") String basicAuthToken);

    @RequestLine("GET /request/updateUsedPatientLogoffRequest/{patientId}")
    @Headers({"Content-type: application/json", "Authorization: Basic {basicAuthToken}"})
    void updateUsedPatientLogoffRequest(@Param("patientId") Long patientId, @Param("basicAuthToken") String basicAuthToken);

    @RequestLine("POST /request/memoryDeleteRequest")
    @Headers({"Content-Type: application/json", "Authorization: Basic {basicAuthToken}"})
    Request memoryDeleteRequest(Request request, @Param("basicAuthToken") String basicAuthToken);

    @RequestLine("GET /request/approveMemoryRequest")
    @Headers({"Content-Type: application/json", "Authorization: Basic {basicAuthToken}"})
    Request approveMemoryRequest(Request request, @Param("basicAuthToken") String basicAuthToken);

    @RequestLine("GET /request/discardMemoryRequest")
    @Headers({"Content-Type: application/json", "Authorization: Basic {basicAuthToken}"})
    Request discardMemoryRequest(Request request, @Param("basicAuthToken") String basicAuthToken);
}
