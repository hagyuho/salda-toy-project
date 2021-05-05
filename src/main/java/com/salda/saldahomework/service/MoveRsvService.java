package com.salda.saldahomework.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.aop.ThrowsAdvice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.salda.saldahomework.dto.MoveRsvReqDTO;
import com.salda.saldahomework.dto.MoveRsvResDTO;
import com.salda.saldahomework.entity.MoveRsvEntity;
import com.salda.saldahomework.repository.MoveRsvRepository;
import com.salda.saldahomework.repository.MoveRsvSpecs;
import com.salda.saldahomework.repository.MoveRsvSpecs.SearchKey;

import lombok.RequiredArgsConstructor;
import net.bytebuddy.implementation.bytecode.Throw;

@RequiredArgsConstructor
@Service
public class MoveRsvService {

	private final MoveRsvRepository moveRsvRepository;

	// R 전체조회
	@Transactional(readOnly = true)
	public List<MoveRsvResDTO> findAll() throws Exception {
		List<MoveRsvEntity> entities = moveRsvRepository.findAll();
		return toDTOList(entities);
	}

	// R 조회조건 별 조회
	// 조회조건 존재하지 않을 시 전체조회
	// 조회조건 존재 할 시 해당 조건으로 조회
	@Transactional(readOnly = true)
	public List<MoveRsvResDTO> getRsvList(Map<String, Object> searchRequest) throws Exception {
		List<MoveRsvEntity> entities = new ArrayList<MoveRsvEntity>();

		Map<SearchKey, Object> searchKeys = new HashMap<>();
		for (String key : searchRequest.keySet()) {
			searchKeys.put(SearchKey.valueOf(key.toUpperCase()), searchRequest.get(key));
		}

		entities = searchKeys.isEmpty() ? moveRsvRepository.findAll()
				: moveRsvRepository.findAll(MoveRsvSpecs.searchWith(searchKeys));

		return toDTOList(entities);
	}

	// C 예약처리
	// 1. 예약 전 체크
	// 2. 예약 처리
	@Transactional
	public String saveRsv(MoveRsvReqDTO moveRsvReqDTO) throws Exception {

		boolean rsvCheck = rsvCheck(moveRsvReqDTO);

		if (rsvCheck) {
			MoveRsvEntity entity = new MoveRsvEntity().builder().dong(moveRsvReqDTO.getDong()).ho(moveRsvReqDTO.getHo())
					.hpNumber(moveRsvReqDTO.getHo()).moveDttm(moveRsvReqDTO.getMoveDttm())
					.rsvDttm(LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE)).rsvYn("Y").build();

			moveRsvRepository.save(entity);
		}
		return null;
	}

	// R 예약 전 체크(내부)
	// 1. 이사날짜 60일 이내
	// 2. 동일 동 && 동일 날짜 && 예약여부 Y
	// 3. 동일연락처 && 예약여부 Y
	public boolean rsvCheck(MoveRsvReqDTO moveRsvReqDTO) throws Exception {
		boolean rsvCheck = true;

		// 1. 이사날짜 60일 이내
		DateTimeFormatter formatter = DateTimeFormatter.BASIC_ISO_DATE;
		LocalDate moveDttm = LocalDate.parse(moveRsvReqDTO.getMoveDttm(), formatter);
		if (moveDttm.compareTo(LocalDate.now()) > 60) {
			rsvCheck = false;
			throw new Exception("오늘로부터 60일 이내까지만 예약가능합니다.");
		}

		// 2. 동일 동 && 동일 날짜 && 예약여부 Y
		String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		
		List<MoveRsvResDTO> checkDTOList = toDTOList(
				moveRsvRepository.findAllByRsvYnAndMoveDttmGreaterThanEqual("Y", today));
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
		Date parsedDate = dateFormat.parse(moveRsvReqDTO.getMoveDttm());
		Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
		
		List<MoveRsvResDTO> checkDTOListForDong = checkDTOList.stream()
				.filter(p -> p.getDong().equals(moveRsvReqDTO.getDong()))
				.filter(p -> p.getMoveDttm().equals(timestamp))
				.collect(Collectors.toList());
		

		if (checkDTOListForDong.size() > 0) {
			rsvCheck = false;
			throw new Exception("이사 희망 일자에 이미 예약 처리 되어있습니다.");
		}

		// 3. 전화번호 체크
		List<MoveRsvResDTO> checkDTOListForHpNumber = checkDTOList.stream()
				.filter(p -> p.getHpNumber().equals(moveRsvReqDTO.getHpNumber())).collect(Collectors.toList());
		if (checkDTOListForHpNumber.size() > 0) {
			rsvCheck = false;
			throw new Exception("동일 휴대폰 번호로 1회만 예약 가능합니다.");
		}

		return rsvCheck;
	}

	// to DTO
	public MoveRsvResDTO toDTO(MoveRsvEntity moveRsvEntity) {
		MoveRsvResDTO dto = new MoveRsvResDTO();
		dto.setDong(moveRsvEntity.getDong());
		dto.setHo(moveRsvEntity.getHo());
		dto.setHpNumber(moveRsvEntity.getHpNumber());
		dto.setMoveDttm(moveRsvEntity.getMoveDttm());
		dto.setRsvId(moveRsvEntity.getRsvId());
		dto.setRsvDttm(moveRsvEntity.getRsvDttm());
		dto.setRsvYn(moveRsvEntity.getRsvYn());

		return dto;
	}

	// to DTOLIST
	public List<MoveRsvResDTO> toDTOList(List<MoveRsvEntity> moveRsvEntityList) {

		List<MoveRsvResDTO> dtoList = new ArrayList<MoveRsvResDTO>();
		if (moveRsvEntityList.size() < 1) {
			return null;
		}
		for (MoveRsvEntity entity : moveRsvEntityList) {
			dtoList.add(toDTO(entity));
		}
		return dtoList;
	}
}
