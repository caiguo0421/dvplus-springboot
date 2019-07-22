package cn.sf_soft.vehicle.customer.service.impl;

import cn.sf_soft.vehicle.customer.model.InterestedCustomers;
import com.google.gson.JsonObject;

public interface ICustomerPaser {

    public void parseCustomer(InterestedCustomers customer, JsonObject jo_customer);
}
