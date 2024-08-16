package com.retailer.reward.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {

	private String customerId;
	private String customerName;
	private Integer amount;
	private String date;

}