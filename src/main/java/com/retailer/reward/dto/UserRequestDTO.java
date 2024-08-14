package com.retailer.reward.dto;

import java.util.List;

import com.retailer.reward.entity.RewardTransaction;

import lombok.Data;

@Data
public class UserRequestDTO {

	private List<RewardTransaction> transactions;

}