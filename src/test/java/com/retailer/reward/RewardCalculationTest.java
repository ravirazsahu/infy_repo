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
import java.util.stream.Collectors;

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
import com.retailer.reward.dto.MonthWisePointsDTO;
import com.retailer.reward.dto.RewardsResponseDTO;
import com.retailer.reward.dto.UserRequestDTO;
import com.retailer.reward.dto.UserResponseDTO;
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

		List<MonthWisePointsDTO> roxymonthwisepoints = new ArrayList<>();
		Collections.addAll(roxymonthwisepoints, new MonthWisePointsDTO("JULY", 90),
				new MonthWisePointsDTO("AUGUST", 15));

		List<MonthWisePointsDTO> jackmonthwisepoints = new ArrayList<>();
		Collections.addAll(jackmonthwisepoints, new MonthWisePointsDTO("JULY", 110),
				new MonthWisePointsDTO("AUGUST", 90));

		// Create mock request data
		List<RewardsResponseDTO> mockRewards = new ArrayList<>();
		mockRewards.add(new RewardsResponseDTO("Roxy", 2024, 40L, roxymonthwisepoints));
		mockRewards.add(new RewardsResponseDTO("Jack", 2024, 110L, jackmonthwisepoints));

		// Mock the service call
		Mockito.when(rewardCalculationService.calculateRewards()).thenReturn(mockRewards);

		// Perform the GET request and verify the response
		mockMvc.perform(get("/api/reward/fetch/all").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("Data Retrieved Successfully."))
				.andExpect(jsonPath("$.data[0].name").value("Roxy")).andExpect(jsonPath("$.data[0].year").value(2024))
				.andExpect(jsonPath("$.data[0].totalPoints").value(40))
				.andExpect(jsonPath("$.data[0].monthWisePoints[0].month").value("JULY"))
				.andExpect(jsonPath("$.data[0].monthWisePoints[0].points").value(90))
				.andExpect(jsonPath("$.data[0].monthWisePoints[1].month").value("AUGUST"))
				.andExpect(jsonPath("$.data[0].monthWisePoints[1].points").value(15))
				.andExpect(jsonPath("$.data[1].name").value("Jack")).andExpect(jsonPath("$.data[0].year").value(2024))
				.andExpect(jsonPath("$.data[1].totalPoints").value(110))
				.andExpect(jsonPath("$.data[1].monthWisePoints[0].month").value("JULY"))
				.andExpect(jsonPath("$.data[1].monthWisePoints[0].points").value(110))
				.andExpect(jsonPath("$.data[1].monthWisePoints[1].month").value("AUGUST"))
				.andExpect(jsonPath("$.data[1].monthWisePoints[1].points").value(90));

	}

	@Test
	public void fetch_reward_points_NoData() throws Exception {
		// Mock the service call to return an empty list
		Mockito.when(rewardCalculationService.calculateRewards()).thenReturn(Collections.emptyList());

		// Perform the GET request and verify the response
		mockMvc.perform(get("/api/reward/fetch/all").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("Data is not available !"))
				.andExpect(jsonPath("$.status").value(200));
	}

	@Test
	public void fetch_reward_points_amount_lessthan_50() throws Exception {

		List<MonthWisePointsDTO> roxymonthwisepoints = new ArrayList<>();
		Collections.addAll(roxymonthwisepoints, new MonthWisePointsDTO("JULY", 9), new MonthWisePointsDTO("AUGUST", 1));

		List<MonthWisePointsDTO> jackmonthwisepoints = new ArrayList<>();
		Collections.addAll(jackmonthwisepoints, new MonthWisePointsDTO("JULY", 5), new MonthWisePointsDTO("AUGUST", 4));

		// Create mock request data
		List<RewardsResponseDTO> mockRewards = new ArrayList<>();
		mockRewards.add(new RewardsResponseDTO("Roxy", 2024, 0L, roxymonthwisepoints));
		mockRewards.add(new RewardsResponseDTO("Jack", 2024, 0L, jackmonthwisepoints));

		// Mock the service call
		Mockito.when(rewardCalculationService.calculateRewards()).thenReturn(mockRewards);

		// Perform the GET request and verify the response
		mockMvc.perform(get("/api/reward/fetch/all").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("Data Retrieved Successfully."))
				.andExpect(jsonPath("$.data[0].name").value("Roxy")).andExpect(jsonPath("$.data[0].year").value(2024))
				.andExpect(jsonPath("$.data[0].totalPoints").value(0))
				.andExpect(jsonPath("$.data[0].monthWisePoints[0].month").value("JULY"))
				.andExpect(jsonPath("$.data[0].monthWisePoints[0].points").value(9))
				.andExpect(jsonPath("$.data[0].monthWisePoints[1].month").value("AUGUST"))
				.andExpect(jsonPath("$.data[0].monthWisePoints[1].points").value(1))
				.andExpect(jsonPath("$.data[1].name").value("Jack")).andExpect(jsonPath("$.data[0].year").value(2024))
				.andExpect(jsonPath("$.data[1].totalPoints").value(0))
				.andExpect(jsonPath("$.data[1].monthWisePoints[0].month").value("JULY"))
				.andExpect(jsonPath("$.data[1].monthWisePoints[0].points").value(5))
				.andExpect(jsonPath("$.data[1].monthWisePoints[1].month").value("AUGUST"))
				.andExpect(jsonPath("$.data[1].monthWisePoints[1].points").value(4));

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
		rewardlist.add(new RewardTransaction(null, "Roxy", 120, LocalDate.of(2024, 8, 10)));

		List<UserResponseDTO> resplist = rewardlist.stream().map(
				tr -> new UserResponseDTO("1", tr.getCustomerName(), (int) tr.getAmount(), tr.getDate().toString()))
				.collect(Collectors.toList());

		UserRequestDTO mockRequest = new UserRequestDTO();
		mockRequest.setTransactions(rewardlist);

		// Mock the service call
		when(rewardCalculationService.addUsers(mockRequest.getTransactions())).thenReturn(resplist);

		// Convert the request to JSON
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		String jsonRequest = objectMapper.writeValueAsString(mockRequest);

		// Perform the POST request and verify the response
		mockMvc.perform(post("/api/reward/bulk/entry").contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value(200)).andExpect(jsonPath("$.data[0].customerId").value("1"))
				.andExpect(jsonPath("$.data[0].customerName").value("Roxy"))
				.andExpect(jsonPath("$.data[0].amount").value(120))
				.andExpect(jsonPath("$.data[0].date").value("2024-08-10"));
	}

}
