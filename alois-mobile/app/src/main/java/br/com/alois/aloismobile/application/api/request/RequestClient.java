package br.com.alois.aloismobile.application.api.request;

import java.util.List;

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

    @RequestLine("GET /request/approveLogoffRequest")
    @Headers({"Content-Type: application/json", "Authorization: Basic {basicAuthToken}"})
    Request approveLogoffRequest(Request request, @Param("basicAuthToken") String basicAuthToken);

    @RequestLine("GET /request/discardLogoffRequest")
    @Headers({"Content-Type: application/json", "Authorization: Basic {basicAuthToken}"})
    Request discardLogoffRequest(Request request, @Param("basicAuthToken") String basicAuthToken);
}
