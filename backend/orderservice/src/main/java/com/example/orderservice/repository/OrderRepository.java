package com.example.orderservice.repository;

import com.example.orderservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);

    //Generate a Custom query to fetch all Order and orderItems in one go
    @Query("SELECT o FROM Order o JOIN FETCH o.items")
    List<Order> findAllWithItems();

    //Generate a Custom query to fetch Order by id and orderItems in one go
    //When : and ? should be used in JPQL queries?
    //Use : for named parameters and ? for positional parameters.
    // Named parameters (:) are more readable and flexible, while positional parameters (?) are simpler but less clear.
    @Query("SELECT o FROM Order o JOIN FETCH o.items WHERE o.id = :id")
    List<Order> findByIdWithItems(Long id);
}
