package com.tekion.tenant.MultiTenant_Prj.controller;

import com.tekion.tenant.MultiTenant_Prj.multiTenant.MultiTenantManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/tenants")
public class TenantController {
//	private String url = "  ";
//	private String user = "root";
//	private String password = "Spring@123";
//
	@Autowired
	private final MultiTenantManager tenantManager;

	public TenantController(MultiTenantManager tenantManager) {
		this.tenantManager = tenantManager;
	}


	@GetMapping
	public ResponseEntity getAll() {
		return ResponseEntity.ok(tenantManager.getTenantList());
	}

	@PostMapping
	public ResponseEntity add(@RequestBody Map<String, String> dbProperty) {
		String tenantId = dbProperty.get("tenantId");
		String url = dbProperty.get("url");
		String username = dbProperty.get("username");
		String password = dbProperty.get("password");

		if (tenantId == null || url == null || username == null || password == null) {
			throw new AssertionError("Invalid details...");
		}

		try {
			tenantManager.addTenant(tenantId, url, username, password);
			return ResponseEntity.ok(dbProperty);
		} catch (SQLException e) {
			throw new AssertionError("Tenant not added");		}
	}
}
