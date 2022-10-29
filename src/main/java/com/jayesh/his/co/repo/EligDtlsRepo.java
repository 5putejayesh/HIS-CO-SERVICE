package com.jayesh.his.co.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jayesh.his.co.entity.EligDtls;

public interface EligDtlsRepo extends JpaRepository<EligDtls, Integer> {

	public EligDtls findByCaseNo(Long caseNo);
}
