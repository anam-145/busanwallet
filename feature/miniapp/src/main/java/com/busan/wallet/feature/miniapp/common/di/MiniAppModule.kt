package com.busan.wallet.feature.miniapp.common.di

import com.busan.wallet.feature.miniapp.common.data.repository.MiniAppRepositoryImpl
import com.busan.wallet.feature.miniapp.webapp.data.repository.WebAppServiceRepositoryImpl
import com.busan.wallet.feature.miniapp.blockchain.data.repository.BlockchainServiceRepositoryImpl
import com.busan.wallet.feature.miniapp.common.domain.repository.MiniAppRepository
import com.busan.wallet.feature.miniapp.webapp.domain.repository.WebAppServiceRepository
import com.busan.wallet.feature.miniapp.blockchain.domain.repository.BlockchainServiceRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * MiniApp 기능의 의존성 주입 모듈
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class MiniAppModule {
    
    @Binds
    @Singleton
    abstract fun bindMiniAppRepository(
        miniAppRepositoryImpl: MiniAppRepositoryImpl
    ): MiniAppRepository
    
    /**
     * WebAppServiceRepository 바인딩
     */
    @Binds
    @Singleton
    abstract fun bindWebAppServiceRepository(
        repositoryImpl: WebAppServiceRepositoryImpl
    ): WebAppServiceRepository
    
    /**
     * BlockchainServiceRepository 바인딩
     */
    @Binds
    @Singleton
    abstract fun bindBlockchainServiceRepository(
        repositoryImpl: BlockchainServiceRepositoryImpl
    ): BlockchainServiceRepository
}