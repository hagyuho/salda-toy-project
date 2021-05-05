package com.salda.saldahomework.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.salda.saldahomework.entity.MoveRsvEntity;

public class MoveRsvSpecs {

	public enum SearchKey {
		// enum 적용 스터디하기
		DONG("dong"), HPNUMBER("hpNumber"), MOVEDTTM("moveDttm");

		private final String value;

		SearchKey(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

	}

	public static Specification<MoveRsvEntity> searchWith(Map<SearchKey, String> searchKeyword) {
		return (Specification<MoveRsvEntity>) ((root, query, builder) -> {
			List<Predicate> predicate = getPredicateWithKeyword(searchKeyword, root, builder);
			return builder.and(predicate.toArray(new Predicate[0]));
		});
	}

	private static List<Predicate> getPredicateWithKeyword(Map<SearchKey, String> searchKeyword,
			Root<MoveRsvEntity> root, CriteriaBuilder builder) {
		List<Predicate> predicate = new ArrayList<>();
		for (SearchKey key : searchKeyword.keySet()) {
			switch (key) {
			case DONG:
			case HPNUMBER:
				predicate.add(builder.equal(root.get(key.value), searchKeyword.get(key)));
				break;
			case MOVEDTTM:
				predicate.add(builder.equal(root.get(key.value), LocalDate.parse(searchKeyword.get(key))));
				break;
			}
		}
		return predicate;
	}

	public static Specification<MoveRsvEntity> withDong(String dong) {
		// Specification의 toPredicate() 람다식으로 구현 // 기본식도 확인하기
		return (Specification<MoveRsvEntity>) ((root, query, builder) -> builder.equal(root.get("dong"), dong));
	}
}
