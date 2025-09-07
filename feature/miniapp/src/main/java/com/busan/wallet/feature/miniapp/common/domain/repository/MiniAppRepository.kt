package com.busan.wallet.feature.miniapp.common.domain.repository

import com.busan.wallet.core.common.model.MiniApp
import com.busan.wallet.core.common.model.MiniAppManifest
import com.busan.wallet.core.common.result.MiniAppResult
import kotlinx.coroutines.flow.SharedFlow

interface MiniAppRepository {
    suspend fun initializeMiniApps(): MiniAppResult<Unit>
    suspend fun getInstalledMiniApps(): MiniAppResult<Map<String, MiniApp>>
    suspend fun loadMiniAppManifest(appId: String): MiniAppResult<MiniAppManifest>
    suspend fun isMiniAppsInitialized(): Boolean
    suspend fun clearCache()
    fun observeAppChanges(): SharedFlow<Unit>
}