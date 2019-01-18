package com.tekion.jan_17.tenant_jan17.service;

import com.tekion.jan_17.tenant_jan17.model.Model;

public interface ModelService {
	Iterable<Model> findAll();
	Model save(Model model);
}
