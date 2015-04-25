package com.mrodriguez.gymadmin.business;

import java.util.List;
import java.util.Map;

import com.mrodriguez.gymadmin.persistence.entities.CustomerEntity;

/**
 *
 * @author mrodriguez
 */
public interface CustomerBusiness {
    
    public List<CustomerEntity> findAll();
    
    public List<CustomerEntity> findByFilters(Map<String , String> filters);
    
    public void create(CustomerEntity e) throws Exception;
    
    public void edit(CustomerEntity e) throws Exception;
    
    public CustomerEntity get(Integer Id) throws Exception;
    
    public void delete(Integer Id) throws Exception;
    
}
