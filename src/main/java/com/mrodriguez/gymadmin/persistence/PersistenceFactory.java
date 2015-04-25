package com.mrodriguez.gymadmin.persistence;

import com.mrodriguez.gymadmin.persistence.daos.CustomerDao;
import com.mrodriguez.gymadmin.persistence.daos.impl.CustomerDaoImpl;

/**
 *
 * @author mrodriguez
 */
public class PersistenceFactory {
    
    public static CustomerDao getCustomerDao(){
        return new CustomerDaoImpl();
    }   
    
}
