package com.busan.wallet.feature.miniapp.webapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.Lifecycle
// 스킨 시스템 제거
import com.busan.wallet.core.common.model.Language
import com.busan.wallet.core.ui.theme.BusanWalletTheme
import com.busan.wallet.feature.miniapp.common.data.common.MiniAppFileManager
import com.busan.wallet.feature.miniapp.webapp.ui.WebAppContract
import com.busan.wallet.feature.miniapp.webapp.ui.WebAppScreen
import com.busan.wallet.feature.miniapp.webapp.ui.WebAppViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 일반 미니앱을 표시하는 Activity
 * 
 * :app 프로세스에서 실행되며, WebAppService를 통해
 * 블록체인 서비스와 통신합니다.
 */
@AndroidEntryPoint
class WebAppActivity : ComponentActivity() {
    
    companion object {
        private const val TAG = "WebAppActivity"
        const val EXTRA_APP_ID = "app_id"
        const val EXTRA_LANGUAGE = "language"
        
        fun createIntent(
            context: Context, 
            appId: String,
            language: Language = Language.KOREAN
        ): Intent {
            return Intent(context, WebAppActivity::class.java).apply {
                putExtra(EXTRA_APP_ID, appId)
                putExtra(EXTRA_LANGUAGE, language.name)
            }
        }
    }
    
    @Inject
    lateinit var fileManager: MiniAppFileManager
    
    private val viewModel: WebAppViewModel by viewModels()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val appId = intent.getStringExtra(EXTRA_APP_ID) ?: ""
        val languageName = intent.getStringExtra(EXTRA_LANGUAGE) ?: Language.KOREAN.name
        val intentLanguage = try {
            Language.valueOf(languageName)
        } catch (e: IllegalArgumentException) {
            Log.e(TAG, "Invalid language name from intent: $languageName, using default")
            Language.KOREAN
        }
        
        // Effect 처리
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.effect
                    .onEach { effect ->
                        when (effect) {
                            is WebAppContract.Effect.NavigateBack -> {
                                finish()
                            }
                            is WebAppContract.Effect.ShowError -> {
                                runOnUiThread {
                                    Toast.makeText(this@WebAppActivity, effect.message, Toast.LENGTH_LONG).show()
                                    Log.d(TAG, "Showing toast: ${effect.message}")
                                }
                            }
                            is WebAppContract.Effect.SendTransactionResponse -> {
                                // WebAppScreen의 WebView에서 처리됨
                            }
                            is WebAppContract.Effect.SendTransactionError -> {
                                // WebAppScreen의 WebView에서 처리됨
                            }
                            // VP 관련 Effect 제거됨
                        }
                    }
                    .launchIn(this)
            }
        }
        
        setContent {
            // 스킨 제거 - 부산만 사용
            BusanWalletTheme() {
                WebAppScreen(
                    appId = appId,
                    viewModel = viewModel,
                    fileManager = fileManager,
                    language = intentLanguage  // 언어 정보만 전달
                )
            }
        }
    }
}