package com.github.gusmanwidodo.atm.core.repository;

import com.github.gusmanwidodo.atm.core.model.Account;
import com.github.gusmanwidodo.atm.core.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByCustomerId(long customerId);
    Optional<Account> findByNumber(String accountNumber);
}
