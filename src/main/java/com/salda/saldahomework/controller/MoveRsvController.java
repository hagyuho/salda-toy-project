package com.salda.saldahomework.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.salda.saldahomework.dto.MoveRsvResDTO;
import com.salda.saldahomework.service.MoveRsvService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/salda/v1")
@RestController
public class MoveRsvController {

	private final MoveRsvService moveRsvService;
	
	
	@GetMapping(value = "/List")
	public ResponseEntity<List<MoveRsvResDTO>> findAll(){
		List<MoveRsvResDTO> dtoList = moveRsvService.findAll();
		
		return new ResponseEntity<List<MoveRsvResDTO>>(dtoList, HttpStatus.OK);
	}
	
}
