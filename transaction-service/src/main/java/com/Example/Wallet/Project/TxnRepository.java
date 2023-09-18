package com.Example.Wallet.Project;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@Transactional

public interface TxnRepository extends JpaRepository<Transaction,Integer> {

    @Modifying
    @Query("update transaction t set t.txnStatus= ?2 where t.txnId =  ?1")
    void updateTxn(String txnId , TxnStatus txnStatus);
}
