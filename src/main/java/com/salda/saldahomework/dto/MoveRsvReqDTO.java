package com.salda.saldahomework.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MoveRsvReqDTO {
	private String dong;

	private String ho;

	private String hpNumber;

	private String moveDttm;

	@Builder
	public MoveRsvReqDTO(String dong, String ho, String hpNumber, String moveDttm) {
		this.dong = dong;
		this.ho = ho;
		this.hpNumber = hpNumber;
		this.moveDttm = moveDttm;
	}
}
