package com.busan.wallet.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.busan.wallet.feature.main.ui.MainScreen
import com.busan.wallet.feature.main.ui.MainViewModel
import com.busan.wallet.feature.hub.ui.HubScreen
import com.busan.wallet.feature.browser.ui.BrowserScreen
import com.busan.wallet.feature.settings.navigation.SettingsNavHost
import com.busan.wallet.feature.miniapp.webapp.WebAppActivity
import com.busan.wallet.feature.miniapp.blockchain.BlockchainActivity
import com.busan.wallet.feature.auth.ui.login.LoginScreen
import com.busan.wallet.feature.auth.ui.setup.SetupPasswordScreen
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.busan.wallet.ui.language.LanguageViewModel
import com.busan.wallet.core.ui.navigation.animatedComposable

/**
 * ANAM Wallet의 메인 Navigation Host
 * 
 * 모든 화면의 네비게이션을 관리하며, 각 경로에 대한
 * Composable 화면을 연결합니다.
 * 
 * @param navController 네비게이션 컨트롤러
 * @param mainViewModel 공유되는 MainViewModel
 * @param startDestination 시작 화면 (기본값: Main)
 */
@Composable
fun BusanNavHost(
    navController: NavHostController,
    mainViewModel: MainViewModel,
    modifier: Modifier = Modifier,
    startDestination: String = BusanNavRoute.Main.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        // ========== Bottom Navigation 화면들 ==========
        
        // 메인 화면
        animatedComposable(route = BusanNavRoute.Main.route) {
            val context = LocalContext.current
            // 언어 정보만 가져오기 (스킨 제거)
            val languageViewModel: LanguageViewModel = hiltViewModel()
            val currentLanguage by languageViewModel.language.collectAsStateWithLifecycle()
            
            MainScreen(
                viewModel = mainViewModel,  // 공유 ViewModel 전달
                onNavigateToMiniApp = { appId ->
                    // MiniApp Activity 실행 (메인 프로세스) - 스킨 제거
                    val intent = WebAppActivity.createIntent(context, appId, currentLanguage)
                    context.startActivity(intent)
                },
                onLaunchBlockchain = { blockchainId ->
                    // 블록체인 Activity 실행 (블록체인 프로세스) - 스킨 제거
                    val intent = BlockchainActivity.createIntent(context, blockchainId, currentLanguage)
                    context.startActivity(intent)
                },
                onNavigateToHub = {
                    // Hub 탭으로 이동
                    navController.navigate(BusanNavRoute.Hub.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
        
        // 허브 화면
        animatedComposable(route = BusanNavRoute.Hub.route) {
            HubScreen()
        }
        
        // 브라우저 화면
        animatedComposable(route = BusanNavRoute.Browser.route) {
            BrowserScreen()
        }
        
        // 설정 화면 (Nested Navigation)
        animatedComposable(route = BusanNavRoute.Settings.route) {
            SettingsNavHost()
        }
        
        // ========== 상세 화면들 ==========
        
        // 미니앱 상세 화면
        animatedComposable(
            route = BusanNavRoute.MiniAppDetail.route,
            arguments = listOf(
                navArgument("appId") { 
                    defaultValue = ""
                }
            )
        ) { backStackEntry ->
            val appId = backStackEntry.arguments?.getString("appId") ?: ""
            // TODO: MiniAppDetailScreen(appId)
        }
        
        // 모듈 상세 화면
        animatedComposable(
            route = BusanNavRoute.ModuleDetail.route,
            arguments = listOf(
                navArgument("moduleId") { 
                    defaultValue = ""
                }
            )
        ) { backStackEntry ->
            val moduleId = backStackEntry.arguments?.getString("moduleId") ?: ""
            // TODO: ModuleDetailScreen(moduleId)
        }
        
        // Identity 상세 화면들은 IdentityNavHost에서 처리
        // 기존 route들은 호환성을 위해 남겨둠 수 있음
        
        // ========== 인증 화면들 ==========
        
        // 로그인 화면
        animatedComposable(route = BusanNavRoute.Login.route) {
            LoginScreen(
                onNavigateToMain = {
                    navController.navigate(BusanNavRoute.Main.route) {
                        popUpTo(BusanNavRoute.Login.route) { inclusive = true }
                    }
                }
            )
        }
        
        // 비밀번호 설정 화면
        animatedComposable(route = BusanNavRoute.SetupPassword.route) {
            SetupPasswordScreen(
                onNavigateToMain = {
                    navController.navigate(BusanNavRoute.Main.route) {
                        popUpTo(BusanNavRoute.SetupPassword.route) { inclusive = true }
                    }
                }
            )
        }
    }
}