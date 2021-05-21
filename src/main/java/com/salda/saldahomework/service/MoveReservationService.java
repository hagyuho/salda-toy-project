package com.salda.saldahomework.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.salda.saldahomework.dto.MoveReservationRequestDTO;
import com.salda.saldahomework.dto.MoveReservationResponseDTO;
import com.salda.saldahomework.entity.MoveReservationEntity;
import com.salda.saldahomework.repository.MoveReservationRepository;
import com.salda.saldahomework.repository.MoveRsvSpecs;
import com.salda.saldahomework.repository.MoveRsvSpecs.SearchKey;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MoveReservationService {

	private final MoveReservationRepository moveReservationRepository;

	
	/**
	 * @author      : hagyuho
	 * @date        : 2021. 05. 21
	 * @method      : findAll
	 * @description : 전체조회 
	 */
	@Transactional(readOnly = true)
	public List<MoveReservationResponseDTO> findAll() throws Exception {
		List<MoveReservationEntity> entities = moveReservationRepository.findAll();
		return toDTOList(entities);
	}

	/**
	 * @author      : hagyuho
	 * @date        : 2021. 05. 21
	 * @method      : findByHpNumber
	 * @description : 전화번호로 예약건 조회 
	 */	
	@Transactional(readOnly = true)
	public List<MoveReservationResponseDTO> findByHpNumber(MoveReservationResponseDTO moveRsvReqDTO) throws Exception {
		List<MoveReservationResponseDTO> rsvDTO = toDTOList(moveReservationRepository.findAllByHpNumberAndReservationYn(moveRsvReqDTO.getHpNumber(), "Y"));
		return rsvDTO;
	}

	
	
	/**
	 * @author      : hagyuho
	 * @date        : 2021. 05. 21
	 * @method      : getReservationList
	 * @description : 조회조건별 예약목록조회 
	 *                1. 조회조건 존재하지 않을 시 전체조회
	 *                2. 조회조건 존재 할 시 해당 조건으로 조회
	 */	
	@Transactional(readOnly = true)
	public List<MoveReservationResponseDTO> getReservationList(Map<String, Object> searchRequest) throws Exception {
		List<MoveReservationEntity> entities = new ArrayList<MoveReservationEntity>();

		Map<SearchKey, Object> searchKeys = new HashMap<>();
		for (String key : searchRequest.keySet()) {
			searchKeys.put(SearchKey.valueOf(key.toUpperCase()), searchRequest.get(key));
		}

		entities = searchKeys.isEmpty() ? moveReservationRepository.findAll()
				: moveReservationRepository.findAll(MoveRsvSpecs.searchWith(searchKeys));

		return toDTOList(entities);
	}

	/**
	 * @author      : hagyuho
	 * @date        : 2021. 05. 21
	 * @method      : saveRsv
	 * @description : 예약처리 
	 *                1. 예약 전 체크
	 *                2. 예약처리
	 */	
	@Transactional
	public String saveReservation(MoveReservationRequestDTO moveReservationRequestDTO) throws Exception {

		boolean rsvCheck = reservationCheck(moveReservationRequestDTO);
		if (rsvCheck) {
			MoveReservationEntity entity = new MoveReservationEntity().builder().dong(moveReservationRequestDTO.getDong()).ho(moveReservationRequestDTO.getHo())
					.hpNumber(moveReservationRequestDTO.getHpNumber()).moveDttm(moveReservationRequestDTO.getMoveDttm())
					.reservationDttm(LocalDateTime.now()).reservationYn("Y").build();

			moveReservationRepository.save(entity);
			return "예약처리가 완료되었습니다";
		}
		return "예약처리에 실패하였습니다";
	}

	/**
	 * @author      : hagyuho
	 * @date        : 2021. 05. 21
	 * @method      : cancelRsv
	 * @description : 예약 취소 
	 */	
	@Transactional
	public String cancelReservation(MoveReservationRequestDTO moveReservationRequestDTO) throws Exception {

		MoveReservationResponseDTO rsvDTO = toDTO(moveReservationRepository.findByHpNumberAndReservationYn(moveReservationRequestDTO.getHpNumber(), "Y"));

		if (rsvDTO.getReservationId() != 0) {
			MoveReservationEntity entity = new MoveReservationEntity().builder().reservationId(rsvDTO.getReservationId()).dong(rsvDTO.getDong())
					.ho(rsvDTO.getHo()).reservationDttm(rsvDTO.getReservationDttm()).moveDttm(rsvDTO.getMoveDttm()).hpNumber(rsvDTO.getHpNumber()).reservationYn("N").build();

			moveReservationRepository.save(entity);
			return "예약취소가 완료되었습니다";
		}

		return "예약취소에 실패하였습니다";
	}

	/**
	 * @author      : hagyuho
	 * @date        : 2021. 05. 21
	 * @method      : rsvCheck
	 * @description : 예약 전 체크(내부) 
	 *                1. 이사날짜 60일 이내 or 오늘 이전
	 *                2. 동일 동 && 동일 날짜 && 예약여부 Y
	 *                3. 동일연락처 && 예약여부 Y
	 */	
	public boolean reservationCheck(MoveReservationRequestDTO moveReservationRequestDTO) throws Exception {
		boolean reservationCheck = true;

		System.out.println("Service:" + moveReservationRequestDTO.getMoveDttm());

		// 1. 이사날짜 60일 이내 or 오늘 이전
		if (moveReservationRequestDTO.getMoveDttm().minusDays(60).compareTo(LocalDateTime.now()) > 0) {
			reservationCheck = false;
			throw new Exception("오늘로부터 60일 이내까지만 예약가능합니다.");
		} else if (moveReservationRequestDTO.getMoveDttm().compareTo(LocalDateTime.now()) < 0) {
			reservationCheck = false;
			throw new Exception("오늘 이후 일자부터 예약가능합니다.");
		}

		// 2. 동일 동 && 동일 날짜 && 예약여부 Y
		List<MoveReservationResponseDTO> checkDTOList = toDTOList(
				moveReservationRepository.findAllByReservationYnAndMoveDttmGreaterThanEqual("Y", LocalDateTime.now()));
		List<MoveReservationResponseDTO> checkDTOListForDong = checkDTOList.stream()
				.filter(p -> p.getDong().equals(moveReservationRequestDTO.getDong()))
				.filter(p -> p.getMoveDttm().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
						.equals(moveReservationRequestDTO.getMoveDttm().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))))
				.collect(Collectors.toList());

		if (checkDTOListForDong.size() > 0) {
			reservationCheck = false;
			throw new Exception("이사 희망 일자에 이미 예약 처리 되어있습니다.");
		}

		// 3. 전화번호 체크
		List<MoveReservationResponseDTO> checkDTOListForHpNumber = checkDTOList.stream()
				.filter(p -> p.getHpNumber().equals(moveReservationRequestDTO.getHpNumber())).collect(Collectors.toList());
		if (checkDTOListForHpNumber.size() > 0) {
			reservationCheck = false;
			throw new Exception("동일 휴대폰 번호로 1회만 예약 가능합니다.");
		}

		return reservationCheck;
	}

	
	
	
	/**
	 * @author      : hagyuho
	 * @date        : 2021. 05. 21
	 * @method      : toDTO
	 * @description : entity to dto
	 */	
	public MoveReservationResponseDTO toDTO(MoveReservationEntity moveRsvEntity) {
		MoveReservationResponseDTO dto = new MoveReservationResponseDTO();
		dto.setDong(moveRsvEntity.getDong());
		dto.setHo(moveRsvEntity.getHo());
		dto.setHpNumber(moveRsvEntity.getHpNumber());
		dto.setMoveDttm(moveRsvEntity.getMoveDttm());
		dto.setReservationId(moveRsvEntity.getReservationId());
		dto.setReservationDttm(moveRsvEntity.getReservationDttm());
		dto.setReservationYn(moveRsvEntity.getReservationYn());

		return dto;
	}

	/**
	 * @author      : hagyuho
	 * @date        : 2021. 05. 21
	 * @method      : toDTOList
	 * @description : entities to DTOList
	 */	
	public List<MoveReservationResponseDTO> toDTOList(List<MoveReservationEntity> moveRsvEntityList) {

		List<MoveReservationResponseDTO> dtoList = new ArrayList<MoveReservationResponseDTO>();
		if (moveRsvEntityList.size() < 1) {
			return dtoList;
		}
		for (MoveReservationEntity entity : moveRsvEntityList) {
			dtoList.add(toDTO(entity));
		}
		return dtoList;
	}
}
