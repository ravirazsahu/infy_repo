package com.retailer.reward.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.retailer.reward.entity.RewardTransaction;

public interface RewardsRepository extends JpaRepository<RewardTransaction, Long> {

	List<RewardTransaction> findBycustomerIdAndDateBetween(String customerId, LocalDate startDate, LocalDate endDate);

}
