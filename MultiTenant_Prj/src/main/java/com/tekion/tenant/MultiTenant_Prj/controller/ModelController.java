package com.tekion.tenant.MultiTenant_Prj.controller;


import java.sql.SQLException;
import java.util.Random;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.tekion.tenant.MultiTenant_Prj.model.Model;
import com.tekion.tenant.MultiTenant_Prj.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import com.tekion.tenant.MultiTenant_Prj.multiTenant.MultiTenantManager;
import com.tekion.tenant.MultiTenant_Prj.multiTenant.TenantNotFoundException;
import com.tekion.tenant.MultiTenant_Prj.multiTenant.TenantResolvingException;

@Slf4j
@RestController
@RequestMapping("/models")
public class ModelController {

	private final ModelService modelService;
	private final MultiTenantManager tenantManager;


	@Autowired
	public ModelController(ModelService modelService, MultiTenantManager tenantManager) {
		this.modelService = modelService;
		this.tenantManager = tenantManager;
	}

	// Get using tenant Id in an api call
	@GetMapping("/get/{tenantId}")
	public ResponseEntity<?> getAll(@PathVariable String tenantId) throws SQLException {
		setTenant(tenantId);
		return ResponseEntity.ok(modelService.findAll());
	}

	// Post using tenant Id in an api call
	@PostMapping("/save/{tenantId}")
	public ResponseEntity<?> save(@RequestBody Model model, @PathVariable String tenantId) throws SQLException{
		setTenant(tenantId);
		model.setTenant(tenantId);
		return ResponseEntity.ok(modelService.save(model));
	}

	//Post by hashcode value
	@PostMapping("/create")
	public ResponseEntity<?> create(@RequestBody Model model) throws SQLException {
		String tenantId="tenant"+getTenantId(model);
		setTenant(tenantId);
		model.setTenant(tenantId);
		return ResponseEntity.ok(modelService.save(model));
	}

	//Setting TenantId to Thread Local
	private void setTenant(String tenantId) throws SQLException {
			tenantManager.setCurrentTenant(tenantId);
		}

	// Used to create tenant Id using hashcode value
	private long getTenantId(Model model){
		 long id=Integer.toUnsignedLong(model.hashCode())%10;
		 if(id==0){
			 Random rn = new Random();
			 id = rn.nextInt(9) + 1;
		 }
		return id;
		}

}

//	Try to get using Thread local value
//	@GetMapping("/collect")
//	public ResponseEntity<?> get() throws SQLException, TenantResolvingException, TenantNotFoundException {
//		return ResponseEntity.ok(modelService.findAll());
//	}
