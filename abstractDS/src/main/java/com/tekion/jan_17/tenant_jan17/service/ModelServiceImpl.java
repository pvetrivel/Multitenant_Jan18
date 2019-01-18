package com.tekion.jan_17.tenant_jan17.service;


import com.tekion.jan_17.tenant_jan17.model.Model;
import com.tekion.jan_17.tenant_jan17.repo.ModelRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ModelServiceImpl implements ModelService {
	
	private final ModelRepo modelRepo;
	
	public ModelServiceImpl(ModelRepo modelRepo) {
		this.modelRepo = modelRepo;
	}
	
	@Override
	public Iterable<Model> findAll() {
		return modelRepo.findAll();
	}
	
	@Transactional
	@Override
	public Model save(Model model) {
		return modelRepo.save(model);
	}
}
