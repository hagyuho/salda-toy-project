package com.salda.saldahomework.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.salda.saldahomework.dto.MoveRsvResDTO;
import com.salda.saldahomework.entity.MoveRsvEntity;
import com.salda.saldahomework.repository.MoveRsvRepository;

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
