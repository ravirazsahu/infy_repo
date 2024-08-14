package com.retailer.reward.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RewardsResponseDTO {

	private String name;
	private Integer year;
	private double totalPoints;
	private List<MonthWisePointsDTO> monthWisePoints;

}
