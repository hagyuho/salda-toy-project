package com.salda.saldahomework.dto;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MoveRsvReqDTO {
	private String dong;

	private String ho;

	private String hpNumber;
	
	@DateTimeFormat(pattern = "yyyy-mm-dd'T'HH:mm:ss")
	private LocalDateTime moveDttm;

	@Builder
	public MoveRsvReqDTO(String dong, String ho, String hpNumber, LocalDateTime moveDttm) {
		this.dong = dong;
		this.ho = ho;
		this.hpNumber = hpNumber;
		this.moveDttm = moveDttm;
	}
}
