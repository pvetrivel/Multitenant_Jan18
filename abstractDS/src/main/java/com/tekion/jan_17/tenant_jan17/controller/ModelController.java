package com.tekion.jan_17.tenant_jan17.controller;

//import com.tekion.jan_17.tenant_jan17.Config;
import com.tekion.jan_17.tenant_jan17.model.Model;
import com.tekion.jan_17.tenant_jan17.multitenant.MultiTenantManager;
import com.tekion.jan_17.tenant_jan17.multitenant.TenantNotFoundException;
import com.tekion.jan_17.tenant_jan17.multitenant.TenantResolvingException;
import com.tekion.jan_17.tenant_jan17.service.ModelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RestController
@RequestMapping("/models")
public class ModelController {

	private final ModelService modelService;
	private final MultiTenantManager tenantManager;
	//private final Config config;

	@Autowired
	public ModelController(ModelService modelService, MultiTenantManager tenantManager) {
		this.modelService = modelService;
		this.tenantManager = tenantManager;
		//this.config = config;
	}
	
	@GetMapping("/get/{tenantId}")
	public ResponseEntity<?> getAll(@PathVariable String tenantId) throws SQLException, TenantResolvingException, TenantNotFoundException {
		//System.out.println(config.toString());
		setTenant(tenantId);
		return ResponseEntity.ok(modelService.findAll());
	}
	
	@PostMapping("/create/{tenantId}")
	public ResponseEntity<?> create(@RequestBody Model model,@PathVariable String tenantId) throws SQLException, TenantResolvingException, TenantNotFoundException {
		setTenant(tenantId);
		System.out.println("tenat set---->");
		model.setTenant(tenantId);
		return ResponseEntity.ok(modelService.save(model));
	}

	private void setTenant(String tenantId) throws SQLException, TenantResolvingException, TenantNotFoundException {
		System.out.println("setTenant---->"+tenantId);
			tenantManager.setCurrentTenant(tenantId);
		}
}
