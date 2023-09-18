package com.Example.Wallet.Project;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service

public class WalletService {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;


    @KafkaListener(topics = {CommonConstants.USER_TOPIC},groupId = "group123")
    public void CreateWallet(String args) throws JsonProcessingException {

        JSONObject data = objectMapper.readValue(args, JSONObject.class);
        Integer userId = (Integer)data.get(CommonConstants.USER_TOPIC_userId);
        String phoneNumber= (String) data.get(CommonConstants.USER_TOPIC_phone_Number);
        String Identifier= (String)data.get(CommonConstants.USER_TOPIC_user_identifier);
        String IdentifierValue=(String)data.get(CommonConstants.USER_TOPIC_identifier_value);



        Wallet wallet = Wallet.builder()
                .userID(userId)
                .phoneNumber(phoneNumber)
                .userIdentifier(UserIdentifier.valueOf(Identifier))
                .identifierValue(IdentifierValue)
                .balance(10.0)
                .build();

        walletRepository.save(wallet);

    }

    @KafkaListener(topics = {CommonConstants.TXN_CREATED_TOPIC},groupId = "group123")
    public void UpdateWalletFOrTxn(String args) throws JsonProcessingException {

        JSONObject data = objectMapper.readValue(args, JSONObject.class);
    String sender = (String)data.get("sender");
        String receiver= (String) data.get("receiver");
        Double amount= (Double)data.get("amount");
        String txnID= (String)data.get("txnId");





        Wallet senderWallet = walletRepository.findbyPhoneNumber(sender);
        Wallet receiverWallet = walletRepository.findbyPhoneNumber(receiver);

        JSONObject jsonObject= new JSONObject();
        jsonObject.put("txnID" , txnID);
        if(senderWallet==null || senderWallet.getBalance()<=0){
            //fail
            jsonObject.put("walletUpdatedStatus" , "FAILED");
            jsonObject.put("sender" , sender);
            jsonObject.put("receiver", receiver);

            kafkaTemplate.send(CommonConstants.WALLET_UPDATED_TOPIC ,objectMapper.writeValueAsString(jsonObject) );
        }

        walletRepository.updateWallet(receiver, amount);
        walletRepository.updateWallet(sender,0-amount);
        jsonObject.put("walletUpdatedStatus" , "SUCCESS");

        kafkaTemplate.send(CommonConstants.WALLET_UPDATED_TOPIC ,objectMapper.writeValueAsString(jsonObject) );
    }
}
