package com.Example.Wallet.Project;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    @Autowired
    TxnService txnService;

    @PostMapping("/txn")
    public String initiateTxn(@RequestParam("sender") String sender ,@RequestParam("receiver") String receiver ,
                              @RequestParam("reason") String reason , @RequestParam("amount") Double amount ) throws JsonProcessingException {

        return txnService.initiateTxn(sender,receiver,reason,amount);
    }
}
