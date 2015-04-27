/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mrodriguez.gymadmin.persistence.daos;

import java.util.List;
import java.util.Map;

import com.mrodriguez.gymadmin.persistence.entities.CustomerEntity;
import com.mrodriguez.gymadmin.repository.Dao;


/**
 *
 * @author mrodriguez
 */
public interface CustomerDao extends Dao<Integer , CustomerEntity> {
	
	public List<CustomerEntity> findByFilters(Map<String , String> filters);
}
