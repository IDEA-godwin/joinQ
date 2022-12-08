package com.example.joinq.service;

import com.example.joinq.domain.entities.Queue;
import com.example.joinq.domain.entities.ServiceCenter;
import com.example.joinq.domain.entities.WaitingLine;
import com.example.joinq.domain.entities.User;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface ServiceCenterService {

    void createServiceCenter(ServiceCenter serviceCenter);

    void assignCashier(User user);

    Queue buildQueue();

    Queue getQueue();

    void openQueue();

    void closeQueue();
    
    Set<WaitingLine> getUsersOnQueue();

    WaitingLine callNext(WaitingLine user);





//    private ServiceCenterRepository centerRepository;
//
//    public ServiceCenterService(ServiceCenterRepository centerRepository) {
//        this.centerRepository = centerRepository;
//    }
}
