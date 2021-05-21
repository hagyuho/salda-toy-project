package com.salda.saldahomework.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "apartment")
public class MoveReservationEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int reservationId;

	private String dong;

	private String ho;

	private String reservationYn;

	private String hpNumber;
	
	private LocalDateTime reservationDttm;
	
	private LocalDateTime moveDttm;

	@Builder
	public MoveReservationEntity(int reservationId, String dong, String ho, String reservationYn, String hpNumber, LocalDateTime reservationDttm,
			LocalDateTime moveDttm) {
		this.reservationId = reservationId;
		this.dong = dong;
		this.ho = ho;
		this.reservationYn = reservationYn;
		this.hpNumber = hpNumber;
		this.reservationDttm = reservationDttm;
		this.moveDttm = moveDttm;
	}

}
