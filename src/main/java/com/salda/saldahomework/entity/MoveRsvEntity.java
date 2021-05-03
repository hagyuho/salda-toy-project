package com.salda.saldahomework.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "apartment")
public class MoveRsvEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String rsvId;

	private String dong;

	private String ho;

	private String rsvYn;

	private String hpNumber;

	private LocalDateTime rsvDttm;

	private LocalDateTime moveDttm;

	@Builder
	public MoveRsvEntity(String rsvId, String dong, String ho, String rsvYn, String hpNumber, LocalDateTime rsvDttm,
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
