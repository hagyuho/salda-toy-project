package com.salda.saldahomework.dto;

import java.time.LocalDateTime;

import com.salda.saldahomework.entity.MoveReservationEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class MoveReservationResponseDTO {
	private int reservationId;

	private String dong;

	private String ho;

	private String reservationYn;

	private String hpNumber;

	private LocalDateTime reservationDttm;

	private LocalDateTime moveDttm;

}
