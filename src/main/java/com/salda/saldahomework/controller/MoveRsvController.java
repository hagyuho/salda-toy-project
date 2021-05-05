package com.salda.saldahomework.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.salda.saldahomework.dto.MoveRsvResDTO;
import com.salda.saldahomework.service.MoveRsvService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/salda/v1")
@RestController
public class MoveRsvController {

	private final MoveRsvService moveRsvService;

	@GetMapping(value = "/list")
	public ResponseEntity<List<MoveRsvResDTO>> findAll() {
		List<MoveRsvResDTO> dtoList = moveRsvService.findAll();

		return new ResponseEntity<List<MoveRsvResDTO>>(dtoList, HttpStatus.OK);
	}

	@GetMapping(value = "/rsvlist")
	public ResponseEntity<List<MoveRsvResDTO>> getRsvList(@RequestParam(required = false) String dong,
			@RequestParam(required = false) String hpNumber, @RequestParam(required = false) LocalDate moveDttm) {
		List<MoveRsvResDTO> dtoList = moveRsvService.getRsvList(dong,hpNumber,moveDttm);

		return new ResponseEntity<List<MoveRsvResDTO>>(dtoList, HttpStatus.OK);
	}

}
