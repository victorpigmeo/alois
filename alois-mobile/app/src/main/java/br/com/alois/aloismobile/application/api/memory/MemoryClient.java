package br.com.alois.aloismobile.application.api.memory;

import java.util.List;

import br.com.alois.domain.entity.memory.Memory;
import br.com.alois.domain.entity.user.Patient;
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

    @RequestLine("POST /memory/insert")
    @Headers({"Content-Type: application/json", "Authorization: Basic {basicAuthToken}"})
    Memory addMemory(Memory memory, @Param("basicAuthToken") String basicAuthToken);

    @RequestLine("POST /memory/update")
    @Headers({"Content-Type: application/json", "Authorization: Basic {basicAuthToken}"})
    Memory updateMemory(Memory memory, @Param("basicAuthToken") String basicAuthToken);

    @RequestLine("GET /memory/findById/{memoryId}")
    @Headers({"Content-Type: application/json", "Authorization: Basic {basicAuthToken}"})
    Memory findMemoryById(@Param("memoryId") Long memoryId, @Param("basicAuthToken") String basicAuthToken);
}
