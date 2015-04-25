
package com.mrodriguez.gymadmin.business;

import com.mrodriguez.gymadmin.business.impl.CustomerBusinessImpl;

public class BusinessFactory {
    
    public static CustomerBusiness getCustomerBusiness(){
        return new CustomerBusinessImpl();
    }
    
}
