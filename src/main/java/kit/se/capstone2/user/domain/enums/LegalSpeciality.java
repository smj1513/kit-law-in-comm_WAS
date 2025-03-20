package kit.se.capstone2.user.domain.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
public enum LegalSpeciality {
	// 성범죄 분야
	SEXUAL_CRIMES("성범죄"),

	// 재산범죄 분야
	PROPERTY_CRIMES_EMBEZZLEMENT("횡령/배임"),
	PROPERTY_CRIMES_FRAUD("사기/공갈"),
	PROPERTY_CRIMES_THEFT("절도"),
	PROPERTY_CRIMES_DAMAGE("재물손괴"),

	// 교통사고 분야
	TRAFFIC_ACCIDENT_HIT_RUN("교통사고/도주"),
	TRAFFIC_ACCIDENT_DUI("음주/무면허"),

	// 형사절차 분야
	CRIMINAL_PROCEDURE_LAWSUIT("고소/소송절차"),
	CRIMINAL_PROCEDURE_INVESTIGATION("수사/체포/구속"),

	// 폭행/협박 분야
	ASSAULT_GENERAL("폭행/협박/상해 일반"),
	ASSAULT_DEFAMATION("명예훼손/모욕"),

	// 기타 형사범죄
	OTHER_CRIMES_DRUGS("마약/도박"),
	OTHER_CRIMES_JUVENILE("소년범죄/학교폭력"),

	// 부동산 분야
	REAL_ESTATE_GENERAL("부동산 일반"),
	REAL_ESTATE_REDEVELOPMENT("재개발/재건축"),
	REAL_ESTATE_LEASE("임대차"),

	// 금전/계약 분야
	FINANCIAL_COMPENSATION("손해배상"),
	FINANCIAL_LOAN("대여금/채권추심"),
	FINANCIAL_CONTRACT("계약일반/매매"),

	// 민사절차 분야
	CIVIL_PROCEDURE_LITIGATION("소송/집행절차"),
	CIVIL_PROCEDURE_SEIZURE("가압류/가처분"),

	// 가족법 분야
	FAMILY_DIVORCE("이혼"),
	FAMILY_INHERITANCE("상속"),
	FAMILY_GENERAL("가사 일반"),

	// 기업법 분야
	CORPORATE_LAW("기업법무"),
	CORPORATE_LABOR("노동/인사"),

	// 특수분야
	MEDICAL_TAX("의료/세금/행정"),
	IT_IP_FINANCE("IT/지식재산/금융");

	private final String description;

	LegalSpeciality(String description) {
		this.description = description;
	}
	// 한국어 이름으로 enums 조회
	public static Optional<LegalSpeciality> fromDescription(String description) {
		return Arrays.stream(values())
				.filter(field -> field.description.equals(description))
				.findFirst();
	}
}
