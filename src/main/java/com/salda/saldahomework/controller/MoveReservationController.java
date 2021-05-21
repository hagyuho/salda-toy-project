package com.salda.saldahomework.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.salda.saldahomework.dto.MoveReservationRequestDTO;
import com.salda.saldahomework.dto.MoveReservationResponseDTO;
import com.salda.saldahomework.service.MoveReservationService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/salda/v1")
@RestController
public class MoveReservationController {

	private final MoveReservationService moveReservationService;
	
	/**
	 * @author      : hagyuho
	 * @date        : 2021. 05. 21
	 * @method      : findAll
	 * @description : 전체조회 
	 */
	@GetMapping(value = "/list")
	public ResponseEntity<List<MoveReservationResponseDTO>> findAll() throws Exception {
		List<MoveReservationResponseDTO> dtoList = moveReservationService.findAll();
		return new ResponseEntity<List<MoveReservationResponseDTO>>(dtoList, HttpStatus.OK);
	}

	/**
	 * @author      : hagyuho
	 * @date        : 2021. 05. 21
	 * @method      : getReservationList
	 * @description : 예약목록조회 
	 */	
	@GetMapping(value = "/rsvlist")
	public ResponseEntity<List<MoveReservationResponseDTO>> getReservationList(
			@RequestParam(required = false) Map<String, Object> searchRequest) throws Exception {
		List<MoveReservationResponseDTO> dtoList = moveReservationService.getReservationList(searchRequest);
		return new ResponseEntity<List<MoveReservationResponseDTO>>(dtoList, HttpStatus.OK);
	}

	/**
	 * @author      : hagyuho
	 * @date        : 2021. 05. 21
	 * @method      : saveReservation
	 * @description : 예약처리 
	 */	
	@ResponseBody
	@PostMapping(value = "/rsvlist")
	public String saveReservation(@RequestBody MoveReservationRequestDTO moveReservationRequestDTO) throws Exception {
		return moveReservationService.saveReservation(moveReservationRequestDTO);
	}

	/**
	 * @author      : hagyuho
	 * @date        : 2021. 05. 21
	 * @method      : canselReservation
	 * @description : 예약취소 
	 */	
	@PutMapping(value = "/rsvlist")
	public String canselReservation(@RequestBody MoveReservationRequestDTO moveReservationRequestDTO) throws Exception {
		return moveReservationService.cancelReservation(moveReservationRequestDTO);
	}	
}
