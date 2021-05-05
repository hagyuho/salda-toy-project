package com.salda.saldahomework.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.salda.saldahomework.dto.MoveRsvReqDTO;
import com.salda.saldahomework.dto.MoveRsvResDTO;
import com.salda.saldahomework.service.MoveRsvService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/salda/v1")
@RestController
public class MoveRsvController {

	private final MoveRsvService moveRsvService;

	@GetMapping(value = "/list")
	public ResponseEntity<List<MoveRsvResDTO>> findAll() throws Exception {
		List<MoveRsvResDTO> dtoList = moveRsvService.findAll();

		return new ResponseEntity<List<MoveRsvResDTO>>(dtoList, HttpStatus.OK);
	}

	@GetMapping(value = "/rsvlist")
	public ResponseEntity<List<MoveRsvResDTO>> getRsvList(
			@RequestParam(required = false) Map<String, Object> searchRequest) throws Exception {
		List<MoveRsvResDTO> dtoList = moveRsvService.getRsvList(searchRequest);

		return new ResponseEntity<List<MoveRsvResDTO>>(dtoList, HttpStatus.OK);
	}

	@PostMapping(value = "/rsvlist")
	public String saveRsv(@RequestBody MoveRsvReqDTO moveRsvReqDTO) throws Exception {
		moveRsvService.saveRsv(moveRsvReqDTO);

		return null;
	}

	@GetMapping(value = "/rsvlist/check")
	public boolean rsvCheck(@RequestParam String dong, @RequestParam String ho, @RequestParam String hpNumber,
			@RequestParam String moveDttm) throws Exception {
		
		
		MoveRsvReqDTO moveRsvReqDTO = new MoveRsvReqDTO().builder()
					 .ho(ho)
					 .hpNumber(hpNumber)
					 .dong(dong)
					 .moveDttm(moveDttm)
					 .build();
		

		return moveRsvService.rsvCheck(moveRsvReqDTO);
	}

}
