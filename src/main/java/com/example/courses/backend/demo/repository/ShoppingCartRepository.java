package com.example.courses.backend.demo.repository;

import com.example.courses.backend.demo.model.Enum.CartStatus;
import com.example.courses.backend.demo.model.ShoppingCart;
import com.example.courses.backend.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

//    Optional<ShoppingCart> findByUserIdAndStatus(Long id, CartStatus status);
//    boolean existsByUserIdAndStatus(Long id, CartStatus status);

    Optional<ShoppingCart> findByUserUsernameAndStatus(String username, CartStatus status);
    boolean existsByUserUsernameAndStatus(String username, CartStatus status);
}
