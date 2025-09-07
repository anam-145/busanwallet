package com.busan.wallet.feature.miniapp.common.domain.usecase

import com.busan.wallet.core.common.result.MiniAppResult

/**
 * 현재 활성화된 블록체인 ID를 가져오는 UseCase의 인터페이스
 * 
 * 실제 구현은 각 모듈(blockchain, webapp)에서 제공합니다.
 */
interface GetActiveBlockchainIdUseCase {
    suspend operator fun invoke(): MiniAppResult<String>
}