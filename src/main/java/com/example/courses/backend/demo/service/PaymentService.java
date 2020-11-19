package com.example.courses.backend.demo.service;

import com.example.courses.backend.demo.model.Dto.ChargeRequest;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;

public interface PaymentService {

    String charge(ChargeRequest chargeRequest) throws StripeException;

}
