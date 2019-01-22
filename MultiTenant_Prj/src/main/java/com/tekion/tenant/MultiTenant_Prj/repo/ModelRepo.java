package com.tekion.tenant.MultiTenant_Prj.repo;


import com.tekion.tenant.MultiTenant_Prj.model.Model;
import org.springframework.data.repository.CrudRepository;

public interface ModelRepo extends CrudRepository<Model, Long> {
}
