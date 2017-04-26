package br.com.alois.aloismobile.application.api.memory;

import java.util.List;

import br.com.alois.domain.entity.memory.Memory;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

/**
 * Created by thiago on 08/04/17.
 */

public interface MemoryClient {
    @RequestLine("GET /memory/listByPatient/{patientId}")
    @Headers({"Content-Type: application/json", "Authorization: Basic {basicAuthToken}"})
    List<Memory> listMemoryByPatient(@Param("patientId") Long patientId, @Param("basicAuthToken") String basicAuthToken);
}
