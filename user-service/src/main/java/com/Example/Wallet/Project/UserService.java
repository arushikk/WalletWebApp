package com.Example.Wallet.Project;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {


     @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    ObjectMapper objectMapper;
    @Override
    public User loadUserByUsername(String phoneno) throws UsernameNotFoundException {
        return userRepository.findByPhoneNumber(phoneno);
    }

    public void create(UserCreateRequest userCreateRequest) throws JsonProcessingException {
        User user= userCreateRequest.to();
        user.setPassword(encryptPWD(user.getPassword()));

        userRepository.save(user);

        //TODO --publish  --should be on basis of operation done by producer
//topic should be what is done by this fn

        JSONObject jsonobj= new JSONObject();
        jsonobj.put("user_id", user.getId());
        jsonobj.put("phone_Number" , user.getPhoneNumber());
        jsonobj.put("identifier_value", user.getIdentifierValue());
        jsonobj.put("user_identifier", user.getUserIdentifier());

        kafkaTemplate.send(CommonConstants.USER_TOPIC, objectMapper.writeValueAsString(jsonobj));

    }

    public void get(String username) {  //instead used loadbyusername
    }

    private String encryptPWD(String rawPWD){
        return passwordEncoder.encode(rawPWD);
    }

    public List<User> getAll() {

        return  userRepository.findAll();
    }
}
