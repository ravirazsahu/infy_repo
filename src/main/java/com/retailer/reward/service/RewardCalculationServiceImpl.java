package com.retailer.reward.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.retailer.reward.dto.MonthWisePointsDTO;
import com.retailer.reward.dto.RewardsResponseDTO;
import com.retailer.reward.entity.RewardTransaction;
import com.retailer.reward.repo.RewardsRepository;

@Service
public class RewardCalculationServiceImpl implements RewardCalculationService {
	private static final Logger log = LoggerFactory.getLogger(RewardCalculationServiceImpl.class);

	@Autowired
	private RewardsRepository rewardRepo;

	@Override
	@Transactional
	public List<RewardTransaction> addUsers(@Valid List<RewardTransaction> transactions) {

		List<RewardTransaction> resplit = rewardRepo.saveAll(transactions);
		if (resplit.size() == 0) {
			throw new RuntimeException("Data insertion failed!!!!");
		}

		return resplit;
	}

	@Override
	public List<RewardsResponseDTO> calculateRewards() {

		LocalDate startDate = LocalDate.now().minusMonths(3);

		List<RewardTransaction> rewardrepolist = rewardRepo.findAll();

		Map<String, Map<String, Integer>> monthwisereward = rewardrepolist.stream()
				.filter(tr -> tr.getDate().isAfter(startDate))
				.collect(Collectors.groupingBy(trs -> trs.getCustomerName(),
						Collectors.groupingBy(trsmonth -> trsmonth.getDate().getMonth().toString(),
								Collectors.summingInt(trspoint -> calculateRewardPoints(trspoint.getAmount())))));
		log.info("monthwisereward >> " + monthwisereward.toString());

		Map<String, Integer> totalRewards = calculateMonthWiseRewardPoint(monthwisereward);

		return monthwisereward.entrySet().stream().map(entry -> {
			String username = entry.getKey();
			RewardsResponseDTO dto = new RewardsResponseDTO();
			List<MonthWisePointsDTO> monthwisepoints = entry.getValue().entrySet().stream()
					.map(mnthpoint -> new MonthWisePointsDTO(mnthpoint.getKey(), mnthpoint.getValue()))
					.collect(Collectors.toList());

			Integer year = rewardrepolist.stream().filter(tr -> tr.getCustomerName().equalsIgnoreCase(username))
					.findFirst().map(m -> m.getDate().getYear()).orElse(LocalDate.now().getYear());
			dto.setName(username);
			dto.setMonthWisePoints(monthwisepoints);
			dto.setTotalPoints(totalRewards.get(username));
			dto.setYear(year);
			return dto;
		}).collect(Collectors.toList());
	}

	private Map<String, Integer> calculateMonthWiseRewardPoint(Map<String, Map<String, Integer>> monthwiserewards) {
		return monthwiserewards.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey,
				entry -> entry.getValue().values().stream().mapToInt(Integer::intValue).sum()));
	}

	private int calculateRewardPoints(double amount) {
		int point = 0;
		if (amount > 100) {
			point = point + (int) (2 * (amount - 100));
			amount = 100;
		}
		if (amount > 50) {
			point = point + (int) (amount - 50);
		}
		return point;
	}

}
