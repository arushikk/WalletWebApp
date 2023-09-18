package com.Example.Wallet.Project;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Transaction {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id ;

    private String txnId;

    private String sender ;
    private String receiver;

    private String purpose;

    @Enumerated(value=EnumType.STRING)
    private TxnStatus txnStatus;

    private double amount ;


    @CreationTimestamp
    private Date createdOn;


    @UpdateTimestamp
    private Date updatedon;

}
