package com.example.courses.backend.demo.service;

import com.example.courses.backend.demo.model.Dto.ChargeRequest;
import com.example.courses.backend.demo.model.ShoppingCart;

public interface ShoppingCartService {
    ShoppingCart findActiveShoppingCartByUsername(String username);

    ShoppingCart getActiveShoppingCart(String username);

    ShoppingCart createCart(String username);

    ShoppingCart addCourseToCart(String username, Long courseId);

    ShoppingCart removeCourseFromCart(String username, Long courseId);

    ShoppingCart cancelCart(String username);

    ShoppingCart checkoutCart(String username, ChargeRequest chargeRequest);
//    ShoppingCart checkoutCart(String username);

    ShoppingCart clearCart(String username);
}
