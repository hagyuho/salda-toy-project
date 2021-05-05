package com.salda.saldahomework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.salda.saldahomework.entity.MoveRsvEntity;

public interface MoveRsvRepository extends JpaRepository<MoveRsvEntity, Integer>, JpaSpecificationExecutor<MoveRsvEntity>{

}
