package tat.com.eduhub.rest;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tat.com.eduhub.dto.TrainingProgramDTO;
import tat.com.eduhub.entity.Major;
import tat.com.eduhub.entity.TrainingProgram;
import tat.com.eduhub.service.MajorService;
import tat.com.eduhub.service.TrainingProgramService;

@RestController
@RequestMapping(value = "/uc/api/training-program")
public class TrainingProgramRestController {

	@Autowired
	private TrainingProgramService tpService;
	
	@Autowired
	private MajorService majorService;
	
	private ModelMapper mapper = new ModelMapper();
	
	@GetMapping(value = "/industry/{id}")
	public List<TrainingProgramDTO> listTPByIDIndustry(@PathVariable(name = "id") String id){
		Long idIndustry = Long.valueOf(id);
		List<TrainingProgram> trainingPrograms = tpService.findByIdIndustry(idIndustry);
		//List<TrainingProgramDTO> trainingProgramDTOs = trainingPrograms.stream().map(t -> mapper.map(trainingPrograms, TrainingProgramDTO.class)).toList();
		List<TrainingProgramDTO> trainingProgramDTOs = new ArrayList<>();
//		for(TrainingProgram t : trainingPrograms) {
//			TrainingProgramDTO dto = new TrainingProgramDTO();
//			dto.setName(t.getName());
//			dto.setId(t.getId());
//			dto.setCohort(t.getCohort());
//			dto.setType(t.getType());
//			dto.setLevel(t.getLevel());
//			dto.setDuration(t.getDuration());
//			dto.setDomain(t.getSchool().getDomain());
//			trainingProgramDTOs.add(dto);
//		}
		return convertEntityToDTO(trainingPrograms);
	}
	
	@GetMapping(value = "/major/{id}")
	public List<TrainingProgramDTO> listTPByIDMajor(@PathVariable(name = "id") String id){
		Long idMajor = Long.valueOf(id);
		Major major = majorService.get(idMajor);
		
		List<TrainingProgram> trainingPrograms = tpService.findByMajor(major);
		
		return convertEntityToDTO(trainingPrograms);
	}
	
	public List<TrainingProgramDTO> convertEntityToDTO(List<TrainingProgram> trainingPrograms){
		List<TrainingProgramDTO> trainingProgramDTOs = new ArrayList<>();
		for(TrainingProgram t : trainingPrograms) {
			TrainingProgramDTO dto = new TrainingProgramDTO();
			dto.setName(t.getName());
			dto.setId(t.getId());
			dto.setCohort(t.getCohort());
			dto.setType(t.getType());
			dto.setLevel(t.getLevel());
			dto.setDuration(t.getDuration());
			dto.setDomain(t.getSchool().getDomain());
			trainingProgramDTOs.add(dto);
		}
		return trainingProgramDTOs;
	}
	
}
