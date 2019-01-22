package com.tekion.tenant.MultiTenant_Prj.service;



import com.tekion.tenant.MultiTenant_Prj.model.Model;
import com.tekion.tenant.MultiTenant_Prj.repo.ModelRepo;
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
