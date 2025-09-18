package com.berozgar.accounts.controller;

import com.berozgar.accounts.dto.CustomerDetailsDto;
import com.berozgar.accounts.service.ICustomersService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import org.apache.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;

@Tag(
      name = "customer REST API",
      description = "REST API to fetch customer details"
)

@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class CustomerController {

    private final ICustomersService iCustomersService;

    public CustomerController(ICustomersService iCustomersService){
        this.iCustomersService = iCustomersService;
    }

    public ResponseEntity<CustomerDetailsDto> fetchCustomerDetails (@RequestParam
                                                                    @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be of 10 digits")
                                                                    String mobileNumber){
        CustomerDetailsDto customerDetailsDto = iCustomersService.fetchCustomerDetails(mobileNumber);
        return ResponseEntity.status(HttpStatus.SC_OK).body(customerDetailsDto);
    }
}
