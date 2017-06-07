package br.com.alois.solution.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import br.com.alois.domain.entity.memory.Memory;
import br.com.alois.solution.domain.repository.IMemoryRepository;

@Service
public class MemoryService {
	//=====================================ATTRIBUTES=======================================

	//======================================================================================
		
	//=====================================INJECTIONS=======================================
	@Autowired
	IMemoryRepository memoryRepository;

	//======================================================================================

	//=====================================BEHAVIOUR========================================
	public List<Memory> listMemoryByPatient(Long patientId)
	{
		List<Memory> l = this.memoryRepository.listMemoryByPatientId(patientId);
		for(Memory m : l){
			m.generateBase64PhotoThumbnail();
		}
		return l;
	}
	
	public Memory findById(Long memoryId) 
	{
		Memory memory = new Memory();
		memory = this.memoryRepository.findById(memoryId);
		
		return memory;
	}

	public Memory insert(Memory memory) 
	{
		Assert.notNull(memory);
		if(this.memoryRepository.findByTitle(memory.getTitle()) == null){
			memory = this.memoryRepository.save(memory);
		}
		return memory;
	}
	
	public Memory update(Memory memory) 
	{
		Memory mem = this.memoryRepository.save(memory);
		mem.setPatient(memory.getPatient());
		return mem;
	}

	/*public void requestDeleteMemory(Memory memory)
	{
		this.memoryRepository.requestDelete(memory);
	}*/
	//======================================================================================

}
