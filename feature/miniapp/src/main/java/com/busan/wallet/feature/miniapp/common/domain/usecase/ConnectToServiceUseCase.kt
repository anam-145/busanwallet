package com.busan.wallet.feature.miniapp.common.domain.usecase

import com.busan.wallet.core.common.result.MiniAppResult
import com.busan.wallet.feature.miniapp.webapp.domain.repository.WebAppServiceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * 서비스 연결을 관리하는 UseCase
 * 
 * MiniApp 서비스와의 연결을 설정하고 상태를 관찰합니다.
 */
class ConnectToServiceUseCase @Inject constructor(
    private val repository: WebAppServiceRepository
) {
    /**
     * 서비스에 연결합니다.
     * 
     * @param appId 앱 ID
     * @return 연결 결과
     */
    suspend fun connect(appId: String): MiniAppResult<Boolean> {
        return repository.connectToService(appId)
    }
    
    /**
     * 서비스 연결을 해제합니다.
     */
    suspend fun disconnect() {
        repository.disconnectFromService()
    }
    
    /**
     * 서비스 연결 상태를 관찰합니다.
     * 
     * @return 연결 상태 Flow
     */
    fun observeConnectionState(): Flow<Boolean> {
        return repository.observeServiceConnection()
    }
}