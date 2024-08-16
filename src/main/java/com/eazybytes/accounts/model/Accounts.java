package com.eazybytes.accounts.model;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
// other imports

import lombok.*;

@Entity
@Table(name = "Accounts")
@Data
public class Accounts {

	@Id //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_number")
	private long accountNumber;
	@Column(name = "customer_id")
	private int customerId;
	@Column(name="account_type")
	private String accountType;
	@Column(name = "branch_address")
	private String branchAddress;
	@Column(name = "create_dt")
	private LocalDate createDt;
	
}
