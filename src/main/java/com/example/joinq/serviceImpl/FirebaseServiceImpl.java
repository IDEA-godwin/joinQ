package com.example.joinq.serviceImpl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class FirebaseServiceImpl {

    @Value("${app.firebase-config-file}")
    private String firebaseConfigPath;

//    @PostConstruct
//    public void initializer() {
//        FireB
//    }

}
