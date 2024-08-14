package com.retailer.reward.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "transaction")
@AllArgsConstructor
@NoArgsConstructor
public class RewardTransaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String customerId;
	@NotNull(message = "Customer Name should not be null")
	private String customerName;
	@NotNull(message = "Amount should not be null")
	private double amount;
	@NotNull(message = "Date should not be null")
	private LocalDate date;

}
