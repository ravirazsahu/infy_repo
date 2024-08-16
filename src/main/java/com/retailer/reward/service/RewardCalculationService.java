package com.retailer.reward.service;

import java.util.List;

import com.retailer.reward.dto.RewardsResponseDTO;
import com.retailer.reward.dto.UserResponseDTO;
import com.retailer.reward.entity.RewardTransaction;

public interface RewardCalculationService {
	public List<RewardsResponseDTO> calculateRewards();

	public List<UserResponseDTO> addUsers(List<RewardTransaction> list);
}
