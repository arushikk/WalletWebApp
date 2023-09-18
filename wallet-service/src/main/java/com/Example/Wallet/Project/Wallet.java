package com.Example.Wallet.Project;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int Id;

    private int userID;

    @Column(unique = true)
    private String phoneNumber ;
    private double balance ;
    private String identifierValue;

    @Enumerated(value=EnumType.STRING)

    private UserIdentifier   userIdentifier;





}
