package com.busan.wallet.feature.miniapp.common.domain.usecase

import com.busan.wallet.core.common.result.MiniAppResult
import com.busan.wallet.feature.miniapp.common.domain.model.TransactionRequest
import com.busan.wallet.feature.miniapp.common.domain.model.TransactionResponse
import com.busan.wallet.feature.miniapp.webapp.domain.repository.WebAppServiceRepository
import javax.inject.Inject

/**
 * 트랜잭션 요청을 처리하는 UseCase
 * 
 * 미니앱의 트랜잭션 요청을 블록체인 서비스로 전달하고 응답을 받습니다.
 */
class RequestTransactionUseCase @Inject constructor(
    private val repository: WebAppServiceRepository
) {
    /**
     * 트랜잭션을 요청합니다.
     * 
     * @param request 트랜잭션 요청 정보
     * @return 트랜잭션 응답 JSON 문자열
     */
    suspend operator fun invoke(request: TransactionRequest): MiniAppResult<String> {
        return repository.requestTransaction(request)
    }
}