package com.Example.Wallet.Project;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCreateRequest {


    @NotBlank
    private String name;

    @NotBlank
    private String password ;

    @NotBlank
    private String phoneNumber;  // will be used as username
    private String email;
    private String dob ;
    @NotBlank
    private String identifierValue ;
    private String country ;

    @NotNull
    private UserIdentifier userIdentifier;


    public User to(){
        return User.builder()
                .name(this.name)
                .password(this.password)
                .phoneNumber(this.phoneNumber)
                .email(this.email)
                .dob(this.dob)
                .identifierValue(this.identifierValue)
                .country(this.country)
                .userIdentifier(this.userIdentifier)

                .build();

    }

}
