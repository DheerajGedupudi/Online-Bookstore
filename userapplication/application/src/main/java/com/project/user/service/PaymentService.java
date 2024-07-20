package com.project.user.service;

import com.project.user.dao.PaymentRepository;
import com.project.user.entity.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public void savePayment(Payment payment) {
        paymentRepository.save(payment);
    }
}
