package com.Example.Wallet.Project;


import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

@Transactional
public interface WalletRepository extends JpaRepository<Wallet,Integer> {

 Wallet findbyPhoneNumber (String Phoneno);

 @Modifying
 @Query("update Wallet w set w.balance=w.balance + ?2 where w.phoneNumber= ?1")
 Wallet updateWallet(String phoneNumber, Double amount);



}
