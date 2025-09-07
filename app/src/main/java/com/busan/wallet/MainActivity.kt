package com.busan.wallet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.busan.wallet.core.ui.theme.BusanWalletTheme
import com.busan.wallet.core.ui.components.Header
import com.busan.wallet.navigation.BusanBottomNavigation
import com.busan.wallet.navigation.BusanNavHost
import com.busan.wallet.navigation.BusanNavRoute
import com.busan.wallet.ui.language.LanguageViewModel
import com.busan.wallet.core.ui.language.LocalStrings
import com.busan.wallet.core.ui.language.getStringsForLanguage
import androidx.compose.runtime.CompositionLocalProvider
import androidx.hilt.navigation.compose.hiltViewModel
import com.busan.wallet.feature.main.ui.MainViewModel
import com.busan.wallet.feature.main.ui.MainContract
import com.busan.wallet.core.security.domain.usecase.VerifyAppPasswordUseCase
import com.busan.wallet.feature.auth.domain.PasswordManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScaffoldDefaults
import com.busan.wallet.core.ui.components.ResponsiveContentWrapper

// Hilt가 의존성을 주입하는 시작점
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    
    // Main 화면의 ViewModel (스플래시 상태도 관리)
    private val mainViewModel: MainViewModel by viewModels()
    
    @Inject
    lateinit var verifyAppPasswordUseCase: VerifyAppPasswordUseCase
    
    @Inject
    lateinit var passwordManager: PasswordManager
    
    private val _authState = MutableStateFlow<AuthState>(AuthState.Loading)
    val authState: StateFlow<AuthState> = _authState
    
    override fun onCreate(savedInstanceState: Bundle?) {
        // Splash Screen 설치 (super.onCreate 전에 호출!)
        val splashScreen = installSplashScreen()
        
        super.onCreate(savedInstanceState)
        
        // Splash Screen 유지 조건: MiniApp 초기화 중일 때
        /**
         *   1. 앱 시작
         *     - MainActivity가 시작되면서 mainViewModel 생성
         *     - mainViewModel의 isInitializing은 초기값이 true
         *   2. 스플래시 화면 유지
         *   splashScreen.setKeepOnScreenCondition {
         *       mainViewModel.isInitializing.value  // true인 동안 스플래시 유지
         *   }
         *   3. 백그라운드 작업
         *     - setContent { BusanWalletApp() }로 UI는 이미 준비됨
         *     - 하지만 스플래시 오버레이가 화면을 가리고 있음
         *     - MainViewModel이 초기화 작업 진행
         *   4. 초기화 완료
         *   // MainViewModel에서
         *   _isInitializing.value = false  // 이 순간 스플래시 종료
         *   5. 스플래시 종료
         *     - isInitializing이 false가 되면 스플래시 화면 사라짐
         *     - 이미 준비된 MainScreen이 보임
         * */
        splashScreen.setKeepOnScreenCondition {
            mainViewModel.isInitializing.value
        }
        
        // Splash Screen 종료 애니메이션
        splashScreen.setOnExitAnimationListener { splashScreenViewProvider ->
            // 페이드 아웃 애니메이션
            val splashScreenView = splashScreenViewProvider.view
            splashScreenView.animate()
                .alpha(0f)
                .setDuration(300L)
                .withEndAction {
                    splashScreenViewProvider.remove()
                }
                .start()
        }
        
        enableEdgeToEdge()
        
        // 인증 상태 확인
        lifecycleScope.launch {
            _authState.value = AuthState.Loading
            
            // 앱 시작 시 항상 비밀번호 초기화 (재인증 필요)
            passwordManager.clearPassword()
            
            val hasPassword = verifyAppPasswordUseCase.hasPassword()
            
            _authState.value = when {
                !hasPassword -> AuthState.NoPassword
                else -> AuthState.NotAuthenticated
            }
        }
        
        setContent {
            val authStateValue by authState.collectAsStateWithLifecycle()
            BusanWalletApp(
                activity = this,
                authState = authStateValue
            )
        }
    }
    
    sealed class AuthState {
        object Loading : AuthState()
        object NoPassword : AuthState()
        object NotAuthenticated : AuthState()
    }
}

/**
 * Busan Wallet 메인 앱 컴포저블
 * 
 * Navigation Controller를 생성하고 Bottom Navigation과
 * NavHost를 포함한 Scaffold를 구성.
 */
@Composable
fun BusanWalletApp(
    activity: ComponentActivity,
    authState: MainActivity.AuthState = MainActivity.AuthState.Loading
) {
    
    // 언어 ViewModel
    val languageViewModel: LanguageViewModel = hiltViewModel()
    val language by languageViewModel.language.collectAsStateWithLifecycle()
    val strings = getStringsForLanguage(language)

    /**
     * CompositionLocal로 언어와 문자열 제공
     * CompositionLocal은 Compose의 "암시적 데이터 전달" 메커니즘.
     * 모든 하위 컴포저블에서 매개변수로 전달하지 않고도 데이터에 접근할 수 있게 해줌
     */
    CompositionLocalProvider(
        LocalStrings provides strings
    ) {
        BusanWalletTheme {
            // Navigation Controller 생성
            val navController = rememberNavController()
            
            // 현재 경로 추적
            // Navigation Compose에서 현재 화면의 상태를 관찰하는 함수
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route // "main", "hub" 등 문자열
            val currentNavRoute = BusanNavRoute.fromRoute(currentRoute) // 문자열 → 객체 변환
            
            // MainViewModel에서 블록체인 정보 가져오기
            val mainViewModel: MainViewModel = hiltViewModel()
            val mainUiState by mainViewModel.uiState.collectAsStateWithLifecycle()
            val activeBlockchain = mainUiState.blockchainApps.find { 
                it.appId == mainUiState.activeBlockchainId 
            }
            
            // 시작 화면 결정
            val startDestination = when (authState) {
                MainActivity.AuthState.Loading -> BusanNavRoute.Main.route
                MainActivity.AuthState.NoPassword -> BusanNavRoute.SetupPassword.route
                MainActivity.AuthState.NotAuthenticated -> BusanNavRoute.Login.route
            }
            
            // 인증 상태가 변경되면 자동으로 네비게이션
            LaunchedEffect(authState) {
                when (authState) {
                    MainActivity.AuthState.NoPassword -> {
                        navController.navigate(BusanNavRoute.SetupPassword.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                    MainActivity.AuthState.NotAuthenticated -> {
                        navController.navigate(BusanNavRoute.Login.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                    MainActivity.AuthState.Loading -> {
                        // 로딩 중에는 아무것도 하지 않음
                    }
                }
            }
            
            // 인증 화면에서는 바텀 네비게이션 숨기기
            val showBottomBar = currentRoute !in listOf(
                BusanNavRoute.Login.route,
                BusanNavRoute.SetupPassword.route
            )
            
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                contentWindowInsets = ScaffoldDefaults.contentWindowInsets,  // 기본값 사용 (시스템 바 안전 영역)
                topBar = {
                    // 상단 헤더 (인증 화면에서는 숨김)
                    if (showBottomBar) {
                        Header(
                            // LocalStrings로 언어별 타이틀 제공
                            title = when (currentNavRoute) {
                                BusanNavRoute.Main -> strings.headerTitleMain
                                BusanNavRoute.Hub -> strings.headerTitleHub
                                BusanNavRoute.Browser -> strings.headerTitleBrowser
                                // BusanNavRoute.Identity -> strings.headerTitleIdentity  // DID 기능 임시 비활성화
                                BusanNavRoute.Settings -> strings.headerTitleSettings
                                else -> strings.headerTitle
                            },
                            showBlockchainStatus = true,  // 모든 화면에서 블록체인 상태 표시
                            activeBlockchainName = activeBlockchain?.name
                            // onBlockchainClick 제거 - 클릭해도 이동하지 않음
                        )
                    }
                },
                bottomBar = {
                    // Bottom Navigation Bar (인증 화면에서는 숨김)
                    if (showBottomBar) {
                        BusanBottomNavigation(navController = navController)
                    }
                }
            ) { innerPadding ->
                // 컨텐츠 레이아웃 래퍼
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(innerPadding)
                        .consumeWindowInsets(innerPadding)  // 중첩된 컴포저블에서 insets 읽기 위해 유지
                ) {
                    // 반응형 콘텐츠 래퍼 - 태블릿에서 폰 크기로 제한
                    ResponsiveContentWrapper {
                        // Navigation Host - 모든 화면들을 관리
                        BusanNavHost(
                            navController = navController,
                            mainViewModel = mainViewModel,
                            modifier = Modifier.fillMaxSize(),
                            startDestination = startDestination
                        )
                    }
                }
            }
        }
    }
}