package com.starbucks.starvive.common.utils;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 랜덤 닉네임을 생성하는 유틸리티 클래스.
 * 미리 정의된 형용사/수식어와 명사 목록을 조합하여 닉네임을 생성합니다.
 */
public class NicknameGenerator {

    // 형용사/수식어 목록 (원하는 단어들로 자유롭게 추가/수정하세요)
    private static final List<String> ADJECTIVES = List.of(
        "씩씩한", "빛나는", "용감한", "슬픈", "배고픈", "귀여운",
        "행복한", "조용한", "바람의", "칙칙한", "달콤한", "매콤한",
        "신나는", "우울한", "잠자는", "현명한", "작은", "거대한",
        "푸른", "영리한", "수줍은", "미소짓는", "노래하는"
    );

    // 명사 목록 (원하는 단어들로 자유롭게 추가/수정하세요)
    private static final List<String> NOUNS = List.of(
        "사자", "호랑이", "강아지", "고양이", "오리", "바나나",
        "초코", "별", "달", "구름", "나무", "바다", "사막",
        "전사", "마법사", "요정", "오시아", "감자", "커피콩",
        "탐험가", "예술가", "여행자", "수호자", "그림자"
    );

    public static String generateRandomNickname() {
        // 각 목록에서 랜덤 인덱스 선택
        int adjIndex = ThreadLocalRandom.current().nextInt(ADJECTIVES.size());
        int nounIndex = ThreadLocalRandom.current().nextInt(NOUNS.size());

        // 인덱스에 해당하는 단어 가져오기
        String adjective = ADJECTIVES.get(adjIndex);
        String noun = NOUNS.get(nounIndex);

        // 두 단어 조합 (띄어쓰기 없이 붙임)
        return adjective + noun;
    }

    // 비공개 생성자 (유틸리티 클래스는 인스턴스화 방지)
    private NicknameGenerator() {
        throw new IllegalStateException("Utility class");
    }
} 