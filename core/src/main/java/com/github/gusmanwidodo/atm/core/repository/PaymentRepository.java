package com.github.gusmanwidodo.atm.core.repository;

import com.github.gusmanwidodo.atm.core.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
