package com.github.gusmanwidodo.atm.core.repository;

import com.github.gusmanwidodo.atm.core.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
