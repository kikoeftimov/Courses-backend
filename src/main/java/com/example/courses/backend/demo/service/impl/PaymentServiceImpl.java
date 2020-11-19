package com.example.courses.backend.demo.service.impl;

import com.example.courses.backend.demo.model.Dto.ChargeRequest;
import com.example.courses.backend.demo.service.PaymentService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Value("${STRIPE_S_KEY}")
    public String secretKey;

    @PostConstruct
    public void init(){
        Stripe.apiKey = this.secretKey;
    }

    @Override
    public String charge(ChargeRequest chargeRequest) throws StripeException {
        Map<String, Object> chargeMap = new HashMap<>();
        chargeMap.put("amount", chargeRequest.getAmount());
        chargeMap.put("currency", ChargeRequest.Currency.EUR);
        chargeMap.put("source", chargeRequest.getStripeToken());
        chargeMap.put("description", chargeRequest.getDescription());
        Charge charge = Charge.create(chargeMap);
        return charge.getId();
    }
}
