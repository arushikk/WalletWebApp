package com.Example.Wallet.Project;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpHeaders;
import java.util.UUID;


@Service
public class TxnService  implements UserDetailsService {



    @Autowired
    KafkaTemplate<String , String> kafkaTemplate;
    @Autowired
    TxnRepository txnRepository;

    private static Logger logger = LoggerFactory.getLogger(TxnService.class);
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    public String initiateTxn(String sender , String receiver , String reason, Double amount ) throws JsonProcessingException {
        Transaction transaction = Transaction.builder()

                .sender(sender)
                .receiver(receiver)
                .purpose(reason)
                .amount(amount)
                .txnId(UUID.randomUUID().toString())
                .txnStatus(TxnStatus.PENDING)
                .build();

        txnRepository.save(transaction);


        ObjectMapper objectMapper= new ObjectMapper();
        //todo - publish kafka event

        JSONObject jsonObject= new JSONObject();
        jsonObject.put("sender", sender);
        jsonObject.put("receiver", receiver);
        jsonObject.put("amount", amount);
        jsonObject.put("txnId" , transaction.getTxnId());

        kafkaTemplate.send(CommonConstants.TXN_CREATED_TOPIC , objectMapper.writeValueAsString(jsonObject));

        return transaction.getTxnId();
    }


    ObjectMapper objectMapper= new ObjectMapper();

    @KafkaListener(topics=CommonConstants.TXN_UPDATED_TOPIC, groupId = "group123")
    public void updateTxn(String msg ) throws JsonProcessingException {
        JSONObject data = objectMapper.readValue(msg, JSONObject.class);
        String txnId = (String)data.get("txnId");
        String sender = (String)data.get("sender");
        String receiver= (String)data.get("receiver");
        WalletUpdateStatus walletUpdateStatus= WalletUpdateStatus.valueOf((String)data.get("walletUpdateStatus"));


        if(walletUpdateStatus== WalletUpdateStatus.SUCCESS){
            txnRepository.updateTxn(txnId, TxnStatus.SUCCESSFUL);
        }

            else {
            txnRepository.updateTxn(txnId, TxnStatus.FAILED);
        }
    }











    }

