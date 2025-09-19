package com.berozgar.accounts.service.impl;

import com.berozgar.accounts.dto.AccountsDto;
import com.berozgar.accounts.dto.CardsDto;
import com.berozgar.accounts.dto.CustomerDetailsDto;
import com.berozgar.accounts.dto.LoansDto;
import com.berozgar.accounts.entity.Accounts;
import com.berozgar.accounts.entity.Customer;
import com.berozgar.accounts.execption.ResourceNotFoundException;
import com.berozgar.accounts.mapper.AccountsMapper;
import com.berozgar.accounts.mapper.CustomerMapper;
import com.berozgar.accounts.repository.AccountsRepository;
import com.berozgar.accounts.repository.CustomerRepository;
import com.berozgar.accounts.service.ICustomersService;
import com.berozgar.accounts.service.client.CardsFeignClient;
import com.berozgar.accounts.service.client.LoansFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomersServiceImpl implements ICustomersService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;
    private CardsFeignClient cardsFeignClient;
    private LoansFeignClient loansFeignClient;

    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber, String correlationId) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobile Number", mobileNumber)
        );

        Accounts account = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "Customer Id", customer.getCustomerId().toString())
        );

        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());
        customerDetailsDto.setAccountsDto(AccountsMapper.mapAccountsToDto(account, new AccountsDto()));

        ResponseEntity<LoansDto> loansDtoResponseEntity = loansFeignClient.fetchLoanDetails(correlationId, mobileNumber);
        customerDetailsDto.setLoansDto(loansDtoResponseEntity.getBody());

        ResponseEntity<CardsDto> cardsDtoResponseEntity = cardsFeignClient.fetchCardDetails(correlationId, mobileNumber);
        customerDetailsDto.setCardsDto(cardsDtoResponseEntity.getBody());

        return customerDetailsDto;
    }
}
