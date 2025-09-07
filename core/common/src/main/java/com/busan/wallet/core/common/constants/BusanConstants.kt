package com.busan.wallet.core.common.constants

/**
 * 메인 화면 섹션 순서
 */
enum class SectionOrder {
    BLOCKCHAIN_FIRST,  // 블록체인이 위, 앱이 아래
    APPS_FIRST        // 앱이 위, 블록체인이 아래 (부산 월렛 기본)
}

/**
 * 부산 월렛 전용 상수
 * 
 * 스킨 시스템이 제거되고 부산 월렛 전용 앱이 되면서
 * 부산에서 사용하는 앱 ID들을 관리
 */
object BusanConstants {
    
    /**
     * 부산 월렛에서 사용하는 미니앱 ID 목록
     * 
     * 기존 부산 스킨에서 사용하던 4개 앱
     */
    val BUSAN_APP_IDS = setOf(
        "com.anam.6nqxb5qfm5lptbc9", // 비온미디어
        "com.anam.vh7lpswl75iqdarh", // 부산일보
        "com.anam.rehrxj11f38gn09k", // 비트코인
        "com.anam.osba5s0oy5582dc0"  // 이더리움
    )
    
    /**
     * 섹션 순서 - 부산은 앱이 먼저
     */
    val SECTION_ORDER = SectionOrder.APPS_FIRST
}