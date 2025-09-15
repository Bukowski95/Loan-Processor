package com.berozgar.accounts.mapper;

import com.berozgar.accounts.dto.AccountsDto;
import com.berozgar.accounts.entity.Accounts;

public class AccountsMapper {

    public static AccountsDto mapAccountsToDto(Accounts accounts, AccountsDto dto) {
        dto.setAccountNumber(accounts.getAccountNumber());
        dto.setAccountType(accounts.getAccountType());
        dto.setBranchAddress(accounts.getBranchAddress());
        return dto;
    }

    public static Accounts mapDtoToAccounts(AccountsDto dto, Accounts accounts) {
        accounts.setAccountNumber(dto.getAccountNumber());
        accounts.setAccountType(dto.getAccountType());
        accounts.setBranchAddress(dto.getBranchAddress());
        return accounts;
    }
}
