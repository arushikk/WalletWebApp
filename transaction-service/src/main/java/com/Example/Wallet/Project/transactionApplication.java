package com.Example.Wallet.Project;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = { })
public class transactionApplication {
    public static void main(String [] args){
        SpringApplication.run(transactionApplication.class, args);
    }

}
