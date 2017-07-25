package com.ga.mapper;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ga.entity.ForgotPassword;

public interface ForgotPasswordRepository extends
		JpaRepository<ForgotPassword, Integer> {

	public ForgotPassword findByUserId(Integer userId);
}
