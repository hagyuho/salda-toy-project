package com.salda.saldahomework.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.salda.saldahomework.dto.MoveRsvResDTO;
import com.salda.saldahomework.entity.MoveRsvEntity;
import com.salda.saldahomework.repository.MoveRsvRepository;
import com.salda.saldahomework.repository.MoveRsvSpecs;
import com.salda.saldahomework.repository.MoveRsvSpecs.SearchKey;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MoveRsvService {

	private final MoveRsvRepository moveRsvRepository;

	// 전체조회
	@Transactional(readOnly = true)
	public List<MoveRsvResDTO> findAll() {
		List<MoveRsvEntity> entities = moveRsvRepository.findAll();
		return toDTOList(entities);
	}

	// 조건별조회
	@Transactional(readOnly = true)
	public List<MoveRsvResDTO> getRsvList(String dong, String hpNumber, LocalDate moveDttm) {
		List<MoveRsvEntity> entities = new ArrayList<MoveRsvEntity>();
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMDD");

		Map<SearchKey, String> searchKeys = new HashMap<>();
		searchKeys.put(SearchKey.DONG, dong);
		searchKeys.put(SearchKey.HPNUMBER, hpNumber);
		searchKeys.put(SearchKey.MOVEDTTM, moveDttm.format(dateTimeFormatter));

		entities = searchKeys.isEmpty() ? moveRsvRepository.findAll()
				: moveRsvRepository.findAll(MoveRsvSpecs.searchWith(searchKeys));

		return toDTOList(entities);
	}

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
