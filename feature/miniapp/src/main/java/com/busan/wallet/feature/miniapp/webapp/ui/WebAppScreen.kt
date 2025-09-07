package com.busan.wallet.feature.miniapp.webapp.ui

import android.util.Log

import android.webkit.WebView
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.busan.wallet.core.ui.components.Header
import com.busan.wallet.core.ui.language.LocalStrings
import com.busan.wallet.core.ui.language.getStringsForLanguage
import com.busan.wallet.core.common.model.Language
import com.busan.wallet.feature.miniapp.common.data.common.MiniAppFileManager
import androidx.compose.runtime.CompositionLocalProvider
import com.busan.wallet.feature.miniapp.webapp.ui.components.WebAppWebView
import com.busan.wallet.feature.miniapp.common.ui.components.ErrorContent
import com.busan.wallet.feature.miniapp.common.ui.components.ServiceConnectionCard
import com.busan.wallet.feature.miniapp.common.ui.components.ResponsiveWebViewContainer
// VP/Credential 기능 제거됨
import com.busan.wallet.feature.miniapp.webapp.ui.components.TransactionApprovalBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WebAppScreen(
    appId: String,
    viewModel: WebAppViewModel,
    fileManager: MiniAppFileManager,
    language: Language = Language.KOREAN
) {
    // 언어에 맞는 문자열 가져오기
    val strings = getStringsForLanguage(language)
    
    // CompositionLocal로 문자열 제공
    CompositionLocalProvider(
        LocalStrings provides strings
    ) {
        WebAppScreenContent(
            appId = appId,
            viewModel = viewModel,
            fileManager = fileManager
        )
    }
}

@Composable
private fun WebAppScreenContent(
    appId: String,
    viewModel: WebAppViewModel,
    fileManager: MiniAppFileManager
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var webView by remember { mutableStateOf<WebView?>(null) }
    val strings = LocalStrings.current
    
    // 초기화
    LaunchedEffect(key1 = appId) {
        viewModel.initialize(appId)
    }

    // URL 로드 처리
    LaunchedEffect(key1 = uiState.webUrl) {
        uiState.webUrl?.let { url ->
            webView?.loadUrl(url)
        }
    }
    
    // Effect 처리
    LaunchedEffect(key1 = viewModel) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is WebAppContract.Effect.SendTransactionResponse -> {
                    // WebView에 JavaScript 실행
                    val script = """
                        (function() {
                            const event = new CustomEvent('transactionResponse', {
                                detail: ${effect.responseJson}
                            });
                            window.dispatchEvent(event);
                        })();
                    """.trimIndent()
                    
                    webView?.evaluateJavascript(script, null)
                }
                // VP 관련 Effect 제거됨
                is WebAppContract.Effect.SendTransactionError -> {
                    // 트랜잭션 에러를 JavaScript로 전달
                    val script = """
                        (function() {
                            const event = new CustomEvent('transactionError', {
                                detail: { error: '${effect.error}' }
                            });
                            window.dispatchEvent(event);
                        })();
                    """.trimIndent()
                    
                    webView?.evaluateJavascript(script, null)
                }
                else -> {
                    // Activity에서 처리
                }
            }
        }
    }
    
    Scaffold(
        topBar = {
            Header(
                title = strings.headerTitle,
                showBackButton = false,  // 뒤로가기 버튼 제거
                onTitleClick = {  // 타이틀 클릭 시 뒤로가기
                    viewModel.handleIntent(WebAppContract.Intent.NavigateBack)
                },
                showBlockchainStatus = !uiState.activeBlockchainName.isNullOrEmpty(),
                activeBlockchainName = uiState.activeBlockchainName
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator()
                }
                uiState.error != null -> {
                    ErrorContent(
                        error = uiState.error ?: "오류가 발생했습니다",
                        onRetry = {
                            viewModel.handleIntent(WebAppContract.Intent.DismissError)
                            viewModel.initialize(appId)
                        }
                    )
                }
                uiState.manifest != null -> {
                    uiState.manifest?.let { manifest ->
                        ResponsiveWebViewContainer {
                            WebAppWebView(
                                appId = appId,
                                manifest = manifest,
                                fileManager = fileManager,
                                onTransactionRequest = { transactionData ->
                                    viewModel.handleIntent(
                                        WebAppContract.Intent.RequestTransaction(transactionData)
                                    )
                                },
                                onVPRequest = { vpRequest ->
                                    // VP 요청 처리 제거됨
                                    Log.w("WebAppScreen", "VP request received but feature is disabled")
                                },
                                onWebViewCreated = { 
                                    webView = it
                                    viewModel.onWebViewReady()
                                }
                            )
                        }
                    }
                }
            }
            
            // 서비스 연결 상태 표시 (10초 타임아웃 후에만 표시)
            if (!uiState.isServiceConnected && uiState.connectionTimeout) {
                ServiceConnectionCard(
                    onRetry = {
                        viewModel.handleIntent(WebAppContract.Intent.RetryServiceConnection)
                    }
                )
            }
        }
    }
    
    // VP 바텀시트 제거됨
    
    // 트랜잭션 승인 바텀시트
    if (uiState.showTransactionApproval) {
        uiState.pendingTransactionJson?.let { transactionJson ->
            TransactionApprovalBottomSheet(
                blockchainName = uiState.activeBlockchainName ?: "Blockchain",
                transactionJson = transactionJson,
                onApprove = {
                    viewModel.handleIntent(WebAppContract.Intent.ApproveTransaction)
                },
                onReject = {
                    viewModel.handleIntent(WebAppContract.Intent.RejectTransaction)
                },
                onDismiss = {
                    viewModel.handleIntent(WebAppContract.Intent.DismissTransactionApproval)
                }
            )
        }
    }
}