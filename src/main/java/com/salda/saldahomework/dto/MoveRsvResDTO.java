package com.salda.saldahomework.dto;

import java.time.LocalDateTime;

import com.salda.saldahomework.entity.MoveRsvEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class MoveRsvResDTO {
	private int rsvId;

	private String dong;

	private String ho;

	private String rsvYn;

	private String hpNumber;

	private String rsvDttm;

	private String moveDttm;

}
