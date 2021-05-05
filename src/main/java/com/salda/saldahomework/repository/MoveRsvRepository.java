package com.salda.saldahomework.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.salda.saldahomework.entity.MoveRsvEntity;

public interface MoveRsvRepository extends JpaRepository<MoveRsvEntity, Integer>, JpaSpecificationExecutor<MoveRsvEntity>{
	
	public List<MoveRsvEntity> findAllByRsvYnAndMoveDttmGreaterThanEqual(String RsvYn, String moveDttm);

	
}
