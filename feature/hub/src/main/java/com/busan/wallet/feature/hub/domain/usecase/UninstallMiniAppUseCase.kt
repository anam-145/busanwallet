package com.busan.wallet.feature.hub.domain.usecase

import com.busan.wallet.core.common.result.MiniAppResult
import com.busan.wallet.feature.miniapp.common.data.common.MiniAppFileManager
import com.busan.wallet.feature.miniapp.common.data.common.MiniAppScanner
import javax.inject.Inject

/**
 * 미니앱을 제거하는 UseCase
 */
class UninstallMiniAppUseCase @Inject constructor(
    private val fileManager: MiniAppFileManager,
    private val scanner: MiniAppScanner
) {
    suspend operator fun invoke(appId: String): MiniAppResult<Unit> {
        return try {
            // 1. 파일 삭제
            val result = fileManager.uninstallMiniApp(appId)
            
            // 삭제 실패 시 early return
            if (result is MiniAppResult.Error) {
                return result
            }
            
            // 2. 캐시 초기화하여 UI 업데이트
            scanner.clearCache()
            
            MiniAppResult.Success(Unit)
        } catch (e: Exception) {
            MiniAppResult.Error.UnknownError(e.message ?: "Uninstall failed")
        }
    }
}