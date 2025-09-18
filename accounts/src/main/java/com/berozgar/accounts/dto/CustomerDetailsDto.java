package com.berozgar.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
        name = "CustomerDetails",
        description = "Schema to hold Customer, Accounts, Cards ans Loans info"
)
public class CustomerDetailsDto {

    @NotEmpty(message = "Name can not be null or empty")
    @Size(min = 5, max = 30, message = "name length must be between 5 and 30")
    private String name;

    @NotEmpty(message = "Email can not be null or empty")
    @Email(message = "Email address should have valid format")
    private String email;

    @NotEmpty(message = "Must provide mobile number")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be of 10 digits")
    private String mobileNumber;

    @Schema(
            description = "Accounts details of the customer"
    )
    private AccountsDto accountsDto;

    @Schema(
            description = "Cards details of the customer"
    )
    private CardsDto cardsDto;

    @Schema(
            description = "Loans details of the customer"
    )
    private LoansDto loansDto;
}
