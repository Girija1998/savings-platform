package com.saving.service.repository;

import com.saving.service.entity.SavingAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SavingRepository extends JpaRepository<SavingAccount, Long> {
    boolean existsByMobile(String mobile);


    Optional<SavingAccount>
        findByMobile(String mobile);

    List<SavingAccount> findByCustomerName(
            String customerName
    );
}
