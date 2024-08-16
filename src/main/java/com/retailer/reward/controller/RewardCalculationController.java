package com.retailer.reward.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.retailer.reward.dto.RewardsResponseDTO;
import com.retailer.reward.dto.UserRequestDTO;
import com.retailer.reward.dto.UserResponseDTO;
import com.retailer.reward.handlers.ResponseHandler;
import com.retailer.reward.service.RewardCalculationService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/reward")
public class RewardCalculationController {

	@Autowired
	private RewardCalculationService rewardservice;

	@ApiOperation(value = "Returns a Transactions of 3 months by default for all the retailer customers")
	@GetMapping("/fetch/all")
	public ResponseEntity<Object> getRewards() {

		List<RewardsResponseDTO> calculatedRewards = rewardservice.calculateRewards();
		if (calculatedRewards.isEmpty()) {
			return ResponseHandler.createResponse("Data is not available !", HttpStatus.OK, null);
		}
		return ResponseHandler.createResponse("Data Retrieved Successfully.", HttpStatus.OK, calculatedRewards);

	}

	@ApiOperation(value = "Insert Bulk Records for the retailer customers")
	@PostMapping("/bulk/entry")
	public ResponseEntity<Object> insertBulkRecords(@RequestBody UserRequestDTO reqdto) {

		try {
			if (reqdto.getTransactions() == null || reqdto.getTransactions().isEmpty()) {
				throw new RuntimeException("Invalid Data!!!");
			}
			List<UserResponseDTO> savedTransactions = rewardservice.addUsers(reqdto.getTransactions());
			return ResponseHandler.createResponse("Record Inserted Successfully.", HttpStatus.OK, savedTransactions);
		} catch (RuntimeException e) {
			return ResponseHandler.createResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
		}

	}

}
