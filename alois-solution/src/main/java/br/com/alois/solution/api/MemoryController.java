package br.com.alois.solution.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.alois.domain.entity.memory.Memory;
import br.com.alois.solution.domain.service.MemoryService;

@RestController
@RequestMapping("/api/memory")
public class MemoryController {
	//=====================================INJECTIONS=======================================
	@Autowired
	MemoryService memoryService;

	//======================================================================================

	//=====================================BEHAVIOUR========================================
	@RequestMapping(method=RequestMethod.GET, value="/listByPatient/{patientId}")
	public List<Memory> listPatientsByCaregiverId(@PathVariable("patientId") Long patientId)
	{
		return this.memoryService.listMemoryByPatient(patientId);
	}
	
	//======================================================================================

}
