package com.busan.wallet.feature.miniapp.webapp.domain.usecase

import com.busan.wallet.feature.miniapp.webapp.domain.repository.WebAppServiceRepository
import javax.inject.Inject

/**
 * WebApp Service에서 활성화된 블록체인 ID를 가져오는 UseCase
 */
class GetActiveBlockchainIdFromWebAppServiceUseCase @Inject constructor(
    private val repository: WebAppServiceRepository
) {
    suspend operator fun invoke(): String? {
        return repository.getActiveBlockchainId()
    }
}