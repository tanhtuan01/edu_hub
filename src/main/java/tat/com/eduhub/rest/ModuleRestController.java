package tat.com.eduhub.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tat.com.eduhub.entity.Modules;
import tat.com.eduhub.service.ModuleService;

@RestController
@RequestMapping(value = "/uc/api/module")
public class ModuleRestController {
	
	@Autowired
	private ModuleService moduleService;

	@GetMapping(value = "/major/{idmajor}")
	public List<Modules> getModuleByMajorId(@PathVariable(name = "idmajor") String id){
		List<Modules> modules = new ArrayList<>();
		
		return modules;
	}
	
}
