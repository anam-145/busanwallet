package com.busan.wallet.feature.settings.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.busan.wallet.core.common.model.Language
import com.busan.wallet.feature.settings.domain.repository.LanguageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton
import java.util.Locale

/**
 * LanguageRepository 구현체
 * 
 * DataStore를 사용하여 언어 설정을 영구 저장합니다.
 */
@Singleton
class LanguageRepositoryImpl @Inject constructor(
    // SharedPreferences의 현대적 대체제
    // 비동기적으로 key-value 데이터 저장
    private val dataStore: DataStore<Preferences>
) : LanguageRepository {
    
    companion object {
        // "language"라는 이름의 String 타입 키 생성
        private val LANGUAGE_KEY = stringPreferencesKey("language")
    }
    
    override val language: Flow<Language> = dataStore.data.map { preferences ->
        // 저장된 언어 코드 읽기
        val savedLanguageCode = preferences[LANGUAGE_KEY]
        
        when (savedLanguageCode) {
            null -> {
                // 저장된 값이 없으면 시스템 언어 사용
                getSystemLanguage()
            }
            else -> {
                // 저장된 언어 코드로 Language enum 찾기
                Language.entries.find { it.code == savedLanguageCode } ?: getSystemLanguage()
            }
        }
    }
    
    override suspend fun setLanguage(language: Language) {
        dataStore.edit { preferences ->
            preferences[LANGUAGE_KEY] = language.code
        }
    }
    
    /**
     * 시스템 언어를 지원하는 언어로 변환
     */
    private fun getSystemLanguage(): Language {
        return when (Locale.getDefault().language) {
            "ko" -> Language.KOREAN
            "en" -> Language.ENGLISH
            else -> Language.KOREAN  // 지원하지 않는 언어는 한국어로 기본 설정
        }
    }
}