package com.github.gusmanwidodo.atm.core.repository;

import com.github.gusmanwidodo.atm.core.constant.Status;
import com.github.gusmanwidodo.atm.core.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Query("select p from Payment p where ( p.fromAccountNumber = ?1 or p.toAccountNumber = ?1 ) and p.status = '" + Status.PENDING + "'")
    List<Payment> findByPending(String accountNumber);

    @Query("select p from Payment p where p.fromAccountNumber = ?1 and p.toAccountNumber = ?2 and p.status = '" + Status.PENDING + "'")
    Optional<Payment> findOwedToPay(String fromAccountNumber, String toAccountNumber);
}
