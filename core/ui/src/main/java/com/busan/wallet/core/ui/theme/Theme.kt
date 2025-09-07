package com.busan.wallet.core.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

/**
 * Busan Wallet 테마 정의
 * 
 * Material Design 3 기반의 라이트 테마 시스템입니다.
 */


// ========== 부산 라이트 모드 컬러 스킴 ==========
/**
 * 부산 월렛 색상 스킴
 * 부산 해양 도시 테마 - 진한 네이비에서 밝은 하늘색 그라데이션
 */
private val BusanLightColorScheme = lightColorScheme(
    // 주요 색상
    primary = Color(0xFF1D3679),              // 진한 네이비 (깊은 바다)
    secondary = Color(0xFF4686BC),            // 중간 블루
    tertiary = Color(0xFF6CD0FF),             // 밝은 하늘색
    
    // 배경 및 표면
    background = Color(0xFFFCFCFC),           // 거의 흰색 (약간의 회색빛)
    surface = Color(0xFFFFFFFF),              // 순수 흰색
    surfaceVariant = Color(0xFFECF6FF),       // 매우 연한 하늘색
    
    // 컨테이너 색상
    primaryContainer = Color(0xFFCCE5FF),     // 밝은 하늘색
    onPrimaryContainer = Color(0xFF0E1729),   // 진한 네이비
    
    // 콘텐츠 색상 (on으로 시작하는 색상들)
    onPrimary = Color(0xFFFFFFFF),            // 흰색
    onSecondary = Color(0xFFFFFFFF),          // 흰색
    onTertiary = Color(0xFFFFFFFF),           // 흰색
    onBackground = Color(0xFF1C1B1F),         // 진한 회색
    onSurface = Color(0xFF1C1B1F),            // 진한 회색
    onSurfaceVariant = Color(0xFF3E4A5C),     // 중간 회색
    
    // 기타
    outline = Color(0xFF99C9E8),              // 연한 파란색 아웃라인
    error = Color(0xFFEF4444),                // 에러 상태
    onError = Color(0xFFFFFFFF)               // 에러 색상 위의 콘텐츠
)

// ========== 메인 테마 컴포저블 ==========
/**
 * Busan Wallet의 메인 테마 컴포저블
 * 
 * @param content 테마가 적용될 컨텐츠
 */
@Composable
fun BusanWalletTheme(
    content: @Composable () -> Unit
) {
    // 부산 월렛 색상 스킴 사용
    val colorScheme = BusanLightColorScheme
    
    // 상태바 색상 설정 (프리뷰 모드가 아닐 때만)
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val context = view.context
            // Activity 컨텍스트인 경우에만 상태바 설정
            if (context is Activity) {
                val window = context.window
                // 상태바 색상을 배경색과 동일하게 설정
                window.statusBarColor = colorScheme.background.toArgb()
                // 상태바 아이콘 색상 설정 (라이트 모드이므로 어두운 아이콘)
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
            }
        }
    }

    // 테마 적용
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}