package com.mrodriguez.gymadmin.persistence.daos.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;

import com.mrodriguez.gymadmin.persistence.daos.CustomerDao;
import com.mrodriguez.gymadmin.persistence.entities.CustomerEntity;
import com.mrodriguez.gymadmin.repository.DaoImpl;
import com.mrodriguez.gymadmin.repository.HibernateUtil;

/**
 *
 * @author mrodriguez
 */
public class CustomerDaoImpl extends DaoImpl<Integer , CustomerEntity> implements CustomerDao {
    
    public CustomerDaoImpl(){
        super();
    }
    
    public CustomerEntity findByName(String name) {		
		Query namedQuery = HibernateUtil.getSessionFactory().getCurrentSession().getNamedQuery("PlanEntity.findByName");
		namedQuery.setParameter("name", name);
		return (CustomerEntity)namedQuery.uniqueResult();		
	}

	@SuppressWarnings("unchecked")
	public List<CustomerEntity> findByFilters(Map<String, String> filters) {		
		Query namedQuery = HibernateUtil.getSessionFactory().getCurrentSession().getNamedQuery("CustomerEntity.findByFilters");
		namedQuery.setParameter("name", "%" + filters.get("name").toUpperCase() + "%");
		namedQuery.setParameter("surname", "%" + filters.get("surname").toUpperCase() + "%");
		namedQuery.setParameter("email", "%" + filters.get("email").toUpperCase() + "%");
		return (List<CustomerEntity>)namedQuery.list();
	}
    
}
