package com.salda.saldahomework.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class MoveRsvReqDTO {
	private String rsvId;

	private String dong;

	private String ho;

	private String rsvYn;

	private String hpNumber;

	private LocalDateTime rsvDttm;

	private LocalDateTime moveDttm;

	@Builder
	public MoveRsvReqDTO(String rsvId, String dong, String ho, String rsvYn, String hpNumber, LocalDateTime rsvDttm,
			LocalDateTime moveDttm) {
		this.rsvId = rsvId;
		this.dong = dong;
		this.ho = ho;
		this.rsvYn = rsvYn;
		this.hpNumber = hpNumber;
		this.rsvDttm = rsvDttm;
		this.moveDttm = moveDttm;
	}
}
