package com.jayesh.his.co.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jayesh.his.co.entity.CoTriggerEntity;

public interface CoTriggerRepo extends JpaRepository<CoTriggerEntity, Integer> {

	public List<CoTriggerEntity> findByTriggerStatus(String triggerStatus);
}
