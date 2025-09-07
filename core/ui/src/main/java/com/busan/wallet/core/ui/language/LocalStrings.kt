package com.busan.wallet.core.ui.language

import androidx.compose.runtime.compositionLocalOf
import com.busan.wallet.core.common.model.Language

/**
 * 언어에 따른 문자열 리소스를 제공하는 Provider
 * 암시적 데이터 전달 메커니즘
 * Props drilling 없이 컴포넌트 트리 전체에 데이터를 전달할 수 있음
 */
val LocalStrings = compositionLocalOf { Strings() }

/**
 * 언어별 문자열 리소스를 담는 data class
 * 
 * strings.xml 대신 사용하여 실시간 언어 변경을 지원
 */
data class Strings(
    // Header
    val headerTitle: String = "Busan Wallet",
    val headerTitleMain: String = "Busan Wallet",
    val headerTitleHub: String = "허브",
    val headerTitleBrowser: String = "웹뷰",
    val headerTitleSettings: String = "설정",
    
    // Bottom Navigation
    val navMain: String = "메인",
    val navDid: String = "신분증",
    val navHub: String = "허브",
    val navBrowser: String = "브라우저",
    val navSettings: String = "설정",
    
    // Settings Screen
    val settingsLanguageSection: String = "언어",
    val settingsSupportSection: String = "지원",
    val settingsAboutSection: String = "정보",
    
    val settingsHelp: String = "도움말",
    val settingsHelpDescription: String = "앱 사용 가이드",
    val settingsFaq: String = "자주 묻는 질문",
    val settingsFaqDescription: String = "FAQ 확인하기",
    
    val settingsAppInfo: String = "앱 소개",
    val settingsAppInfoDescription: String = "Busan Wallet에 대해 알아보기",
    val settingsLicense: String = "라이선스",
    val settingsLicenseDescription: String = "오픈소스 라이선스 정보",
    
    val settingsLanguageKorean: String = "한국어",
    val settingsLanguageEnglish: String = "English",
    
    // Browser Screen
    val browserSearchPlaceholder: String = "검색어 또는 주소 입력",
    val cancel: String = "취소",
    val browserSearchDuckDuckGo: String = "DuckDuckGo에서 검색",
    val browserNoBookmarks: String = "북마크가 없습니다",
    val browserNoBookmarksDescription: String = "웹사이트 방문 시 북마크를 추가하세요",
    val browserBookmarkAdded: String = "북마크가 추가되었습니다",
    val browserBookmarkRemoved: String = "북마크가 삭제되었습니다",
    val browserDeleteBookmarkTitle: String = "북마크 삭제",
    val browserDeleteBookmarkMessage: String = "%s을(를) 북마크에서 삭제하시겠습니까?",
    val browserPageLoadError: String = "페이지를 불러올 수 없습니다",
    val browserCancel: String = "취소",
    val browserDelete: String = "삭제",
    
    // Main Screen - Busan Specific
    val syncingApps: String = "앱을 준비 중입니다...",
    val busanServices: String = "부산 서비스",
    val mainAddMoreServices: String = "더 많은 서비스 보기",
    val blockchainSwitch: String = "블록체인 전환",
    val installBlockchainHint: String = "블록체인을 설치해주세요",
    val activated: String = "활성화됨",
    val changeCurrentBlockchain: String = "현재 블록체인을 변경합니다",
    val noBlockchain: String = "No Blockchain",
    val viewDetail: String = "상세보기",
    val noServicesInstalled: String = "설치된 서비스가 없습니다",
    val addServicesHint: String = "아래 버튼을 눌러 서비스를 추가하세요",
    
    // Common
    val back: String = "뒤로가기",
    val dismiss: String = "닫기",
    
    // Auth - Login Screen
    val loginTitle: String = "비밀번호를 입력하여 지갑에 접근하세요",
    val loginPasswordLabel: String = "비밀번호",
    val loginPasswordPlaceholder: String = "최소 8자 이상",
    val loginPasswordHide: String = "비밀번호 숨기기",
    val loginPasswordShow: String = "비밀번호 보기",
    val loginUnlockButton: String = "잠금 해제",
    val loginForgotPassword: String = "비밀번호를 잊으셨나요? 앱을 재설치해야 합니다.",
    
    // Auth - Setup Password Screen
    val setupPasswordTitle: String = "비밀번호 설정",
    val setupPasswordDescription: String = "지갑을 보호할 비밀번호를 설정하세요.\n이 비밀번호는 앱 접근 시 필요합니다.",
    val setupPasswordLabel: String = "비밀번호",
    val setupPasswordPlaceholder: String = "최소 8자 이상 입력",
    val setupPasswordConfirmLabel: String = "비밀번호 확인",
    val setupPasswordConfirmPlaceholder: String = "비밀번호를 다시 입력하세요",
    val setupPasswordMatch: String = "비밀번호 일치",
    val setupPasswordButton: String = "비밀번호 설정",
    val setupPasswordWarningTitle: String = "주의사항",
    val setupPasswordWarningContent: String = "• 비밀번호를 잊으면 지갑에 접근할 수 없습니다\n• 앱을 재설치하면 모든 데이터가 삭제됩니다",
    val setupPasswordStrengthWeak: String = "약함",
    val setupPasswordStrengthMedium: String = "보통",
    val setupPasswordStrengthStrong: String = "강함",
    
    // Auth - Error Messages
    val authErrorPasswordTooShort: String = "비밀번호는 최소 8자 이상이어야 합니다",
    val authErrorPasswordMismatch: String = "비밀번호가 일치하지 않습니다",
    val authErrorLoginFailed: String = "로그인 중 오류가 발생했습니다",
    val authErrorPasswordSetupFailed: String = "비밀번호 설정 중 오류가 발생했습니다",
    
    // Service Connection
    val serviceDisconnected: String = "서비스 연결 끊김",
    val retry: String = "재연결",
    val tryAgain: String = "다시 시도",
    
    // Help Screen
    val helpTitle: String = "도움말",
    val helpPasswordTitle: String = "앱 비밀번호 설정",
    val helpPasswordContent: String = "Busan Wallet을 처음 실행하면 앱 보호를 위한 비밀번호 설정이 필요합니다. 최소 8자 이상의 비밀번호를 설정하세요. 이 비밀번호는 앱 접근 시 매번 필요하며, 분실 시 앱을 재설치해야 하므로 안전하게 보관하세요.",
    val helpMiniAppTitle: String = "미니앱 설치 및 관리",
    val helpMiniAppContent: String = "하단 네비게이션의 '허브' 탭에서 다양한 블록체인 지갑과 웹 서비스를 설치할 수 있습니다. 설치하고 싶은 미니앱을 선택하여 '설치' 버튼을 누르면 메인 화면에 추가됩니다. 설치된 미니앱은 메인 화면에서 바로 실행할 수 있습니다.",
    val helpBlockchainTitle: String = "블록체인 지갑 사용",
    val helpBlockchainContent: String = "메인 화면에서 원하는 블록체인 지갑(Bitcoin, Ethereum 등)을 선택하면 해당 지갑으로 이동합니다. 각 지갑에서는 잔액 확인, 송금, 수신 주소 생성 등의 기능을 사용할 수 있습니다. 활성 블록체인은 상단 헤더에 표시되며, 탭하여 빠르게 전환할 수 있습니다.",
    val helpBrowserTitle: String = "웹 브라우저",
    val helpBrowserContent: String = "하단 네비게이션의 '브라우저' 탭을 통해 웹사이트와 DApp에 접근할 수 있습니다. 북마크 기능을 지원하며, 자주 방문하는 사이트를 저장하여 빠르게 접속할 수 있습니다.",
    val helpLanguageTitle: String = "언어 변경",
    val helpLanguageContent: String = "설정' 탭에서 앱의 언어(한국어/English)를 변경할 수 있습니다.",
    
    // FAQ Screen
    val faqTitle: String = "자주 묻는 질문",
    val faqQuestion1: String = "미니앱이란 무엇인가요?",
    val faqAnswer1: String = "미니앱은 Busan Wallet 내에서 독립적으로 실행되는 작은 애플리케이션입니다. 블록체인 지갑(Bitcoin, Ethereum 등)과 웹 서비스(부산일보, 비온미디어 등)를 미니앱 형태로 설치하여 사용할 수 있습니다. 각 미니앱은 별도의 프로세스에서 실행되어 보안성과 안정성이 보장됩니다.",
    val faqQuestion2: String = "새로운 블록체인 지갑을 추가하려면 어떻게 하나요?",
    val faqAnswer2: String = "하단 네비게이션의 '허브' 탭에서 원하는 블록체인 미니앱을 찾아 '설치' 버튼을 누르면 됩니다. 설치된 미니앱은 메인 화면에 나타나며, 탭하여 바로 사용할 수 있습니다. 필요 없는 미니앱은 언제든지 제거할 수 있습니다.",
    val faqQuestion3: String = "앱 비밀번호를 잊어버렸어요. 어떻게 해야 하나요?",
    val faqAnswer3: String = "보안상의 이유로 앱 비밀번호는 복구할 수 없습니다. 앱을 삭제하고 재설치해야 하며, 이 경우 모든 데이터가 초기화됩니다. 따라서 비밀번호는 안전한 곳에 별도로 보관하시기 바랍니다.",
    val faqQuestion4: String = "여러 개의 블록체인을 동시에 사용할 수 있나요?",
    val faqAnswer4: String = "네, 가능합니다. Busan Wallet은 모듈형 구조로 설계되어 여러 블록체인 미니앱을 동시에 설치하고 관리할 수 있습니다. 상단 헤더에서 현재 활성화된 블록체인을 확인하고, 탭하여 빠르게 전환할 수 있습니다.",
    val faqQuestion5: String = "미니앱의 업데이트는 어떻게 하나요?",
    val faqAnswer5: String = "허브에서 설치된 미니앱의 업데이트를 확인할 수 있습니다. 새 버전이 있으면 업데이트 버튼이 표시되며, 탭하여 최신 버전으로 업데이트할 수 있습니다.",
    val faqQuestion6: String = "오프라인에서도 사용할 수 있나요?",
    val faqAnswer6: String = "설치된 미니앱은 오프라인에서도 기본 기능을 사용할 수 있습니다. 단, 잔액 조회나 트랜잭션 전송 같은 네트워크가 필요한 기능은 인터넷 연결이 필요합니다.",
    val faqQuestion7: String = "브라우저의 북마크는 어디에 저장되나요?",
    val faqAnswer7: String = "북마크는 앱 내부에 안전하게 저장됩니다. 브라우저 하단의 북마크 아이콘을 탭하여 추가/제거할 수 있으며, 북마크 목록에서 저장된 사이트에 빠르게 접속할 수 있습니다.",
    
    // App Info Screen
    val appInfoTitle: String = "앱 정보",
    val appInfoVersion: String = "버전",
    val appInfoDescription: String = "Busan Wallet은 부산시민을 위한 통합 디지털 지갑 서비스입니다. 단순한 기능이 아닌 근본 철학으로서, 사용자가 원하는 블록체인을 추가하고 불필요한 것은 제거하여 자신에게 완벽하게 맞는 지갑을 만들 수 있습니다. 더 이상 여러 지갑을 관리하거나 업데이트를 기다릴 필요가 없습니다.\n\n개방성을 위해 설계된 Busan Wallet은 누구나 친숙한 웹 표준(HTML, CSS, JavaScript)으로 모듈을 만들고 배포할 수 있어 생태계의 빠른 성장을 촉진합니다.\n\n이러한 개방성은 완벽한 보안으로 뒷받침됩니다. 특허 받은 멀티 프로세스 격리 기술로 모든 모듈이 독립된 샌드박스 프로세스와 전용 메모리에서 실행되며, 메인 프로세스가 모든 통신을 중재합니다. 악의적인 모듈도 다른 모듈의 개인키나 데이터에 접근할 수 없습니다.\n\n블록체인을 넘어 Busan Wallet은 애플리케이션 모듈과 분산 신원(DID)을 지원합니다. 통합 API가 모든 것을 연결하여 어떤 서비스든 블록체인과 상호작용할 수 있습니다. 인증과 결제가 하나의 안전한 인터페이스를 통해 이루어집니다.\n\nBusan Wallet은 Web3를 실용적으로 만들어 블록체인 기술과 실제 서비스를 연결합니다.",
    val appInfoDeveloper: String = "개발자",
    val appInfoDeveloperName: String = "Busan Digital Agency",
    val appInfoWebsite: String = "웹사이트",
    val appInfoWebsiteUrl: String = "https://busanwallet.kr",
    val appInfoContact: String = "문의",
    val appInfoContactEmail: String = "support@busanwallet.kr",
    
    // License Screen
    val licenseTitle: String = "오픈소스 라이선스",
    val licenseCopyright: String = "Copyright © 2024 Busan Metropolitan City. All rights reserved.",
    val licenseIntro: String = "이 애플리케이션은 다음의 오픈소스 라이브러리를 사용합니다:",
    val licenseApache: String = "Apache License 2.0",
    val licenseMit: String = "MIT License",
    val licenseViewLicense: String = "라이선스 보기"
)

/**
 * 영어 문자열 리소스
 */
val EnglishStrings = Strings(
    // Header
    headerTitle = "Busan Wallet",
    headerTitleMain = "Busan Wallet",
    headerTitleHub = "Hub",
    headerTitleBrowser = "WebView",
    headerTitleSettings = "Settings",
    
    // Bottom Navigation
    navMain = "Main",
    navDid = "ID",
    navHub = "Hub",
    navBrowser = "Browser",
    navSettings = "Settings",
    
    // Settings Screen
    settingsLanguageSection = "Language",
    settingsSupportSection = "Support",
    settingsAboutSection = "About",
    
    settingsHelp = "Help",
    settingsHelpDescription = "App usage guide",
    settingsFaq = "FAQ",
    settingsFaqDescription = "Check frequently asked questions",
    
    settingsAppInfo = "App Info",
    settingsAppInfoDescription = "Learn about Busan Wallet",
    settingsLicense = "License",
    settingsLicenseDescription = "Open source license information",
    
    settingsLanguageKorean = "한국어",
    settingsLanguageEnglish = "English",
    
    // Browser Screen
    browserSearchPlaceholder = "Search or enter address",
    cancel = "Cancel",
    browserSearchDuckDuckGo = "Search on DuckDuckGo",
    browserNoBookmarks = "No bookmarks",
    browserNoBookmarksDescription = "Add bookmarks when visiting websites",
    browserBookmarkAdded = "Bookmark added",
    browserBookmarkRemoved = "Bookmark removed",
    browserDeleteBookmarkTitle = "Delete Bookmark",
    browserDeleteBookmarkMessage = "Delete %s from bookmarks?",
    browserPageLoadError = "Unable to load page",
    browserCancel = "Cancel",
    browserDelete = "Delete",
    
    // Main Screen - Busan Specific
    syncingApps = "Preparing apps...",
    busanServices = "Busan Services",
    mainAddMoreServices = "View More Services",
    blockchainSwitch = "Switch Blockchain",
    installBlockchainHint = "Please install a blockchain",
    activated = "Activated",
    changeCurrentBlockchain = "Change current blockchain",
    noBlockchain = "No Blockchain",
    viewDetail = "View Detail",
    noServicesInstalled = "No services installed",
    addServicesHint = "Press the button below to add services",
    
    // Common
    back = "Back",
    dismiss = "Dismiss",
    
    // Auth - Login Screen
    loginTitle = "Enter password to access wallet",
    loginPasswordLabel = "Password",
    loginPasswordPlaceholder = "At least 8 characters",
    loginPasswordHide = "Hide password",
    loginPasswordShow = "Show password",
    loginUnlockButton = "Unlock",
    loginForgotPassword = "Forgot password? You need to reinstall the app.",
    
    // Auth - Setup Password Screen
    setupPasswordTitle = "Set Password",
    setupPasswordDescription = "Set a password to protect your wallet.\nThis password is required every time you access the app.",
    setupPasswordLabel = "Password",
    setupPasswordPlaceholder = "Enter at least 8 characters",
    setupPasswordConfirmLabel = "Confirm Password",
    setupPasswordConfirmPlaceholder = "Re-enter password",
    setupPasswordMatch = "Passwords match",
    setupPasswordButton = "Set Password",
    setupPasswordWarningTitle = "Important",
    setupPasswordWarningContent = "• If you forget your password, you cannot access your wallet\n• Reinstalling the app will delete all data",
    setupPasswordStrengthWeak = "Weak",
    setupPasswordStrengthMedium = "Medium",
    setupPasswordStrengthStrong = "Strong",
    
    // Auth - Error Messages
    authErrorPasswordTooShort = "Password must be at least 8 characters",
    authErrorPasswordMismatch = "Passwords do not match",
    authErrorLoginFailed = "Login failed",
    authErrorPasswordSetupFailed = "Failed to set password",
    
    // Service Connection
    serviceDisconnected = "Service disconnected",
    retry = "Reconnect",
    tryAgain = "Try Again",
    
    // Help Screen
    helpTitle = "Help",
    helpPasswordTitle = "App Password Setup",
    helpPasswordContent = "When you first launch Busan Wallet, you need to set a password to protect the app. Set a password of at least 8 characters. This password is required every time you access the app, and if lost, you need to reinstall the app, so please keep it safe.",
    helpMiniAppTitle = "Installing and Managing Mini Apps",
    helpMiniAppContent = "You can install various blockchain wallets and web services from the 'Hub' tab at the bottom navigation. Select the mini app you want to install and press the 'Install' button to add it to the main screen. Installed mini apps can be launched directly from the main screen.",
    helpBlockchainTitle = "Using Blockchain Wallets",
    helpBlockchainContent = "Select the desired blockchain wallet (Bitcoin, Ethereum, etc.) from the main screen to navigate to that wallet. In each wallet, you can check balance, send funds, generate receiving addresses, and more. The active blockchain is displayed in the header, and you can tap to quickly switch.",
    helpBrowserTitle = "Web Browser",
    helpBrowserContent = "Access websites and DApps through the 'Browser' tab at the bottom navigation. Bookmark feature is supported, allowing you to save frequently visited sites for quick access.",
    helpLanguageTitle = "Changing Language",
    helpLanguageContent = "You can change the app language (Korean/English) in the 'Settings' tab.",
    
    // FAQ Screen
    faqTitle = "FAQ",
    faqQuestion1 = "What is a mini app?",
    faqAnswer1 = "A mini app is a small application that runs independently within Busan Wallet. You can install and use blockchain wallets (Bitcoin, Ethereum, etc.) and web services (Busan Ilbo, Bion Media, etc.) as mini apps. Each mini app runs in a separate process, ensuring security and stability.",
    faqQuestion2 = "How do I add a new blockchain wallet?",
    faqAnswer2 = "Find the desired blockchain mini app in the 'Hub' tab at the bottom navigation and press the 'Install' button. Installed mini apps appear on the main screen and can be used immediately by tapping. Unnecessary mini apps can be removed at any time.",
    faqQuestion3 = "I forgot my app password. What should I do?",
    faqAnswer3 = "For security reasons, the app password cannot be recovered. You need to delete and reinstall the app, which will reset all data. Therefore, please keep your password in a safe place.",
    faqQuestion4 = "Can I use multiple blockchains simultaneously?",
    faqAnswer4 = "Yes, you can. Busan Wallet is designed with a modular structure, allowing you to install and manage multiple blockchain mini apps simultaneously. You can check the currently active blockchain in the header and tap to quickly switch.",
    faqQuestion5 = "How do I update mini apps?",
    faqAnswer5 = "You can check for updates of installed mini apps in the Hub. If a new version is available, an update button will appear, which you can tap to update to the latest version.",
    faqQuestion6 = "Can I use it offline?",
    faqAnswer6 = "Installed mini apps can use basic functions offline. However, functions that require network such as balance inquiry or transaction sending need internet connection.",
    faqQuestion7 = "Where are browser bookmarks saved?",
    faqAnswer7 = "Bookmarks are safely stored within the app. You can add/remove by tapping the bookmark icon at the bottom of the browser, and quickly access saved sites from the bookmark list.",
    
    // App Info Screen
    appInfoTitle = "App Info",
    appInfoVersion = "Version",
    appInfoDescription = "Busan Wallet is an integrated digital wallet service for Busan citizens. As a fundamental philosophy rather than just a feature, users can add desired blockchains and remove unnecessary ones to create a wallet perfectly suited to them. No more managing multiple wallets or waiting for updates.\n\nDesigned for openness, Busan Wallet allows anyone to create and deploy modules using familiar web standards (HTML, CSS, JavaScript), promoting rapid ecosystem growth.\n\nThis openness is backed by perfect security. With patented multi-process isolation technology, all modules run in independent sandbox processes and dedicated memory, with the main process mediating all communication. Even malicious modules cannot access private keys or data of other modules.\n\nBeyond blockchain, Busan Wallet supports application modules and decentralized identity (DID). A unified API connects everything, allowing any service to interact with blockchain. Authentication and payment occur through a single secure interface.\n\nBusan Wallet makes Web3 practical by connecting blockchain technology with real services.",
    appInfoDeveloper = "Developer",
    appInfoDeveloperName = "Busan Digital Agency",
    appInfoWebsite = "Website",
    appInfoWebsiteUrl = "https://busanwallet.kr",
    appInfoContact = "Contact",
    appInfoContactEmail = "support@busanwallet.kr",
    
    // License Screen
    licenseTitle = "Open Source Licenses",
    licenseCopyright = "Copyright © 2024 Busan Metropolitan City. All rights reserved.",
    licenseIntro = "This application uses the following open source libraries:",
    licenseApache = "Apache License 2.0",
    licenseMit = "MIT License",
    licenseViewLicense = "View License"
)

/**
 * 언어에 따른 문자열 제공 함수
 */
fun getStringsForLanguage(language: Language): Strings {
    return when (language) {
        Language.KOREAN -> Strings()  // 기본값이 이미 한국어
        Language.ENGLISH -> EnglishStrings
    }
}