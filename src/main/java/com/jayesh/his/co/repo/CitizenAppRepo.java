package com.jayesh.his.co.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jayesh.his.co.entity.CitizenAppEntity;


public interface CitizenAppRepo extends JpaRepository<CitizenAppEntity, Integer> {

}
