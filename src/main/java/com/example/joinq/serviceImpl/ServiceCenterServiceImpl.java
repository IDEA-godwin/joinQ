package com.example.joinq.serviceImpl;

import com.example.joinq.domain.entities.Queue;
import com.example.joinq.domain.entities.ServiceCenter;
import com.example.joinq.domain.entities.WaitingLine;
import com.example.joinq.repository.ServiceCenterRepository;
import com.example.joinq.service.ServiceCenterService;
import com.example.joinq.domain.entities.User;

import java.util.Set;

public class ServiceCenterServiceImpl implements ServiceCenterService {

    private final ServiceCenterRepository serviceCenterRepository;

    public ServiceCenterServiceImpl(ServiceCenterRepository serviceCenterRepository) {
        this.serviceCenterRepository = serviceCenterRepository;
    }

    @Override
    public void createServiceCenter(ServiceCenter serviceCenter) {
        serviceCenterRepository.save(serviceCenter);
    }

    @Override
    public void assignCashier(User user) {

    }

    @Override
    public Queue buildQueue() {
        return null;
    }

    @Override
    public Queue getQueue() {
        return null;
    }

    @Override
    public void openQueue() {

    }

    @Override
    public void closeQueue() {

    }

    @Override
    public Set<WaitingLine> getUsersOnQueue() {
        return null;
    }

    @Override
    public WaitingLine callNext(WaitingLine user) {
        return null;
    }
}
