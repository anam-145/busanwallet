package com.busan.wallet.feature.miniapp.webapp.domain.repository

import com.busan.wallet.core.common.result.MiniAppResult
import com.busan.wallet.feature.miniapp.common.domain.model.TransactionRequest
import kotlinx.coroutines.flow.Flow

/**
 * WebApp 서비스와 통신하기 위한 Repository 인터페이스
 */
interface WebAppServiceRepository {
    /**
     * 서비스 연결 상태를 관찰합니다.
     */
    fun observeServiceConnection(): Flow<Boolean>
    
    /**
     * 서비스에 연결합니다.
     */
    suspend fun connectToService(appId: String): MiniAppResult<Boolean>
    
    /**
     * 서비스 연결을 해제합니다.
     */
    suspend fun disconnectFromService()
    
    /**
     * 활성화된 블록체인 ID를 가져옵니다.
     */
    suspend fun getActiveBlockchainId(): String?
    
    /**
     * 트랜잭션을 요청합니다.
     */
    suspend fun requestTransaction(request: TransactionRequest): MiniAppResult<String>
}