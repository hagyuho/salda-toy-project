package com.salda.saldahomework.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.salda.saldahomework.entity.MoveReservationEntity;

public interface MoveReservationRepository extends JpaRepository<MoveReservationEntity, Integer>, JpaSpecificationExecutor<MoveReservationEntity>{
	
	public List<MoveReservationEntity> findAllByReservationYnAndMoveDttmGreaterThanEqual(String ReservationYn, LocalDateTime moveDttm);

	public List<MoveReservationEntity> findAllByHpNumberAndReservationYn(String hpNumber, String ReservationYn);

	public MoveReservationEntity findByHpNumberAndReservationYn(String hpNumber, String reservationYn);

	
}
