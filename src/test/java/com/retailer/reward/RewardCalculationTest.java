package com.retailer.reward;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.retailer.reward.controller.RewardCalculationController;
import com.retailer.reward.dto.RewardsResponseDTO;
import com.retailer.reward.dto.UserRequestDTO;
import com.retailer.reward.entity.RewardTransaction;
import com.retailer.reward.service.RewardCalculationService;

public class RewardCalculationTest {

	private MockMvc mockMvc;

	@Mock
	private RewardCalculationService rewardCalculationService;

	@InjectMocks
	private RewardCalculationController rewardCalculationController;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(rewardCalculationController).build();
	}

	@Test
	public void fetch_reward_points_Successful() throws Exception {
		// Create mock request data
		List<RewardsResponseDTO> mockRewards = new ArrayList<>();
		mockRewards.add(new RewardsResponseDTO("Roxy", Integer.valueOf(2024), 950.0, null));

		// Mock the service call
		Mockito.when(rewardCalculationService.calculateRewards()).thenReturn(mockRewards);

		// Perform the GET request and verify the response
		mockMvc.perform(get("/api/reward").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("Data Retrieved Successfully."))
				.andExpect(jsonPath("$.data[0].name").value("Roxy")).andExpect(jsonPath("$.data[0].year").value(2024))
				.andExpect(jsonPath("$.data[0].totalPoints").value(950.0))
				.andExpect(jsonPath("$.data[0].monthWisePoints").isEmpty());

	}

	@Test
	public void fetch_reward_points_NoData() throws Exception {
		// Mock the service call to return an empty list
		Mockito.when(rewardCalculationService.calculateRewards()).thenReturn(Collections.emptyList());

		// Perform the GET request and verify the response
		mockMvc.perform(get("/api/reward").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("Data is not available !"))
				.andExpect(jsonPath("$.status").value(200));
	}

	@Test
	public void fetch_reward_points_NoRewards() throws Exception {
		// Prepare mock data: A customer with transactions <= 50
		List<RewardsResponseDTO> mockRewards = new ArrayList<>();
		mockRewards.add(new RewardsResponseDTO("Sam", Integer.valueOf(2024), 47.0, null));

		// Mock the service call
		Mockito.when(rewardCalculationService.calculateRewards()).thenReturn(mockRewards);

		// Perform the GET request and verify the response
		mockMvc.perform(get("/api/reward").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("Data Retrieved Successfully."))
				.andExpect(jsonPath("$.data[0].name").value("Sam")).andExpect(jsonPath("$.data[0].year").value(2024))
				.andExpect(jsonPath("$.data[0].totalPoints").value(47.0))
				.andExpect(jsonPath("$.data[0].monthWisePoints").isEmpty());
	}

	@Test
	void testInsertBulkRecords_Empty_Data() throws Exception {
		// Create mock request data
		UserRequestDTO mockRequest = new UserRequestDTO();
		mockRequest.setTransactions(Collections.emptyList());

		// Mock the service call
		when(rewardCalculationService.addUsers(mockRequest.getTransactions())).thenReturn(Collections.emptyList());

		// Convert the request to JSON
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonRequest = objectMapper.writeValueAsString(mockRequest);

		// Perform the POST request and verify the response
		mockMvc.perform(post("/api/reward/bulk/entry").contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.message").value("Invalid Data!!!"))
				.andExpect(jsonPath("$.status").value(400));
		;
	}

	@Test
	void testInsertBulkRecords_SuccessfulEntry() throws Exception {
		// Create mock request data
		List<RewardTransaction> rewardlist = new ArrayList<>();
		rewardlist.add(new RewardTransaction("1", "Roxy", 120.0, LocalDate.of(2024, 8, 10)));

		UserRequestDTO mockRequest = new UserRequestDTO();
		mockRequest.setTransactions(rewardlist);

		// Mock the service call
		when(rewardCalculationService.addUsers(mockRequest.getTransactions())).thenReturn(rewardlist);

		// Convert the request to JSON
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		String jsonRequest = objectMapper.writeValueAsString(mockRequest);

		// Perform the POST request and verify the response
		mockMvc.perform(post("/api/reward/bulk/entry").contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value(200)).andExpect(jsonPath("$.data[0].customerId").value("1"))
				.andExpect(jsonPath("$.data[0].customerName").value("Roxy"))
				.andExpect(jsonPath("$.data[0].amount").value(120.0))
				.andExpect(jsonPath("$.data[0].date[0]").value(2024)).andExpect(jsonPath("$.data[0].date[1]").value(8))
				.andExpect(jsonPath("$.data[0].date[2]").value(10));
		;
	}

}
