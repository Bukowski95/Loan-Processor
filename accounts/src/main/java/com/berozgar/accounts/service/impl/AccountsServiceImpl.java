package com.berozgar.accounts.service.impl;

import com.berozgar.accounts.constants.AccountsConstants;
import com.berozgar.accounts.dto.AccountsDto;
import com.berozgar.accounts.dto.CustomerDto;
import com.berozgar.accounts.entity.Accounts;
import com.berozgar.accounts.entity.Customer;
import com.berozgar.accounts.execption.CustomerAlreadyExistsException;
import com.berozgar.accounts.execption.ResourceNotFoundException;
import com.berozgar.accounts.mapper.AccountsMapper;
import com.berozgar.accounts.mapper.CustomerMapper;
import com.berozgar.accounts.repository.AccountsRepository;
import com.berozgar.accounts.repository.CustomerRepository;
import com.berozgar.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;

    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapCustomerDtoToCustomer(customerDto, new Customer());

        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customer.getMobileNumber());
        if(optionalCustomer.isPresent()){
            throw new CustomerAlreadyExistsException("Customer with mobile number " + customer.getMobileNumber() + " already exists");
        }

        Customer savedCustomer = customerRepository.save(customer);

        Accounts account = createNewAccount(savedCustomer);
        accountsRepository.save(account);
    }

    @Override
    public CustomerDto fetchAccountDetails(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobile number", mobileNumber)
        );

        Accounts account = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customer id", customer.getCustomerId().toString())
        );

        CustomerDto dto = CustomerMapper.mapCustomerToCustomerDto(customer, new CustomerDto());
        dto.setAccountsDto(AccountsMapper.mapAccountsToDto(account, new AccountsDto()));
        return dto;
    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;

        AccountsDto accountsDto = customerDto.getAccountsDto();
        if (accountsDto != null) {
            Accounts account = accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "account number", accountsDto.getAccountNumber().toString())
            );
            AccountsMapper.mapDtoToAccounts(accountsDto, account);
            account = accountsRepository.save(account);

            Long customerId = account.getCustomerId();
            Customer customer = customerRepository.findById(account.getCustomerId()).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "customer Id", customerId.toString())
            );
            CustomerMapper.mapCustomerDtoToCustomer(customerDto, customer);
            customerRepository.save(customer);
            isUpdated = true;
        }
        return isUpdated;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobile number", mobileNumber)
        );

        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }

    private Accounts createNewAccount(Customer customer) {
        Accounts account = new Accounts();
        account.setCustomerId(customer.getCustomerId());

        long randomAccountNumber = 1000000000L + new Random().nextInt(900000000);

        account.setAccountNumber(randomAccountNumber);
        account.setAccountType(AccountsConstants.SAVINGS);
        account.setBranchAddress(AccountsConstants.ADDRESS);
        return account;
    }



}
