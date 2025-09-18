package com.berozgar.accounts.mapper;

import com.berozgar.accounts.dto.CustomerDetailsDto;
import com.berozgar.accounts.dto.CustomerDto;
import com.berozgar.accounts.entity.Customer;

public class CustomerMapper {

    public static CustomerDto mapCustomerToCustomerDto(Customer customer, CustomerDto dto) {
        dto.setName(customer.getName());
        dto.setEmail(customer.getEmail());
        dto.setMobileNumber(String.valueOf(customer.getMobileNumber()));
        return dto;
    }

    public static Customer mapCustomerDtoToCustomer(CustomerDto customerDto, Customer customer) {
        customer.setName(customerDto.getName());
        customer.setEmail(customerDto.getEmail());
        customer.setMobileNumber(customerDto.getMobileNumber());
        return customer;
    }

    public static CustomerDetailsDto mapToCustomerDetailsDto(Customer customer, CustomerDetailsDto customerDetailsDto) {
        customerDetailsDto.setName(customer.getName());
        customerDetailsDto.setEmail(customer.getEmail());
        customerDetailsDto.setMobileNumber(customer.getMobileNumber());
        return customerDetailsDto;
    }
}
