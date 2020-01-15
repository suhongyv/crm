package com.shy.crm.dao;

import com.shy.base.BaseMapper;
import com.shy.crm.vo.Customer;

public interface CustomerMapper extends BaseMapper<Customer,Integer> {

    Customer  queryCustomerByCusName(String name);
}