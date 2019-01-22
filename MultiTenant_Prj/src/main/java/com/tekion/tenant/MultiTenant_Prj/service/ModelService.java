package com.tekion.tenant.MultiTenant_Prj.service;

import com.tekion.tenant.MultiTenant_Prj.model.Model;

public interface ModelService {
	Iterable<Model> findAll();
	Model save(Model model);
}
