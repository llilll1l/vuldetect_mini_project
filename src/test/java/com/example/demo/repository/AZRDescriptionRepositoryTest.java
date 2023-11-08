package com.example.demo.repository;

import com.example.demo.entity.AZRDescription;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class AZRDescriptionRepositoryTest {

    @Autowired
    AZRDescriptionRepository azrDescriptionRepository;
    @Test
    @Rollback(value = false)
    public void createAZRDescription() {
        AZRDescription AZR_024 = new AZRDescription();
        AZRDescription AZR_026 = new AZRDescription();
        AZRDescription AZR_029 = new AZRDescription();
        AZRDescription AZR_031 = new AZRDescription();
        AZRDescription AZR_033 = new AZRDescription();
        AZRDescription AZR_041 = new AZRDescription();
        AZRDescription AZR_042 = new AZRDescription();
        AZRDescription AZR_043 = new AZRDescription();
        AZRDescription AZR_044 = new AZRDescription();
        AZRDescription AZR_045 = new AZRDescription();
        AZRDescription AZR_047 = new AZRDescription();
        AZRDescription AZR_075 = new AZRDescription();
        AZRDescription AZR_077 = new AZRDescription();
        AZRDescription AZR_079 = new AZRDescription();
        AZRDescription AZR_081 = new AZRDescription();
        AZRDescription AZR_105 = new AZRDescription();
        AZRDescription AZR_106 = new AZRDescription();
        AZRDescription AZR_107 = new AZRDescription();
        AZRDescription AZR_108 = new AZRDescription();

        AZR_024.createAZRDescription("AZR_024", 3, "H", " \"pricingTier\" : \"Standard\" 면 양호", "Security Solutions", "앱 서비스용 MS Defender On 설정 확인" );
        AZR_026.createAZRDescription("AZR_026", 3, "H"," \"pricingTier\" : \"Standard\" 면 양호", "Security Solutions", "머신 SQL 서버용 MS Defender On 설정 확인");
        AZR_029.createAZRDescription("AZR_029", 2, "M" ," \"pricingTier\" : \"Standard\" 면 양호", "Security Solutions", "컨테이너 레지스트리용 MS Defender가 활성화되어 있는 경우");
        AZR_031.createAZRDescription("AZR_031", 3, "H"," \"enabled\" : \"true\" 면 양호", "Security Solutions", "엔드포인트용 클라우드 MS Defender 설정 확인");
        AZR_033.createAZRDescription("AZR_033", 2, "M"," \"autoProvision\" : \"On\" 면 양호", "Security Solutions", "자동 프로비저닝 On 설정 확인" );
        AZR_041.createAZRDescription("AZR_041", 3, "H"," \"defaultAction\" : \"Deny\" 면 양호", "Access Control", "스토리지 계정 내 기본 네트워크 액세스 제한 설정") ;
        AZR_042.createAZRDescription("AZR_042", 3, "H"," \"bypass\" : \"AzureService\" 면 양호", "Access Control", "스토리지 계정 내 서비스 액세스 제한 설정");
        AZR_043.createAZRDescription("AZR_043", 3, "H"," \"enabled\" : \"true\" 면 양호", "ETC", "스토리지 내 일시 삭제(Soft Delete) 기능 활성화 확인");
        AZR_044.createAZRDescription("AZR_044", 2, "M"," \"delete\" : \"true\" , \"read\" : \"true\" , \"write\" : \"true\" 면 양호", "Logging", "Blob 서비스의 읽기/쓰기/삭제 스토리지 로깅 설정");
        AZR_045.createAZRDescription("AZR_045", 2, "M"," \"delete\" : \"true\" , \"read\" : \"true\" , \"write\" : \"true\" 면 양호", "Logging", "테이블 서비스의 읽기/쓰기/삭제  스토리지 로깅 설정");
        AZR_047.createAZRDescription("AZR_047", 3, "H"," \"keyPolicy\" : 90 이하, \"null\" 아니면 양호", "Access Control", "스토리지 액세스 키 재생성 주기 점검");
        AZR_075.createAZRDescription("AZR_075", 2, "M"," \"enabled\" : \"true\" 면 양호", "Monitoring", "정책 할당 (삭제) 이벤트 작업로그 경고 설정");
        AZR_077.createAZRDescription("AZR_077", 2, "M"," \"enabled\" : \"true\" 면 양호", "Monitoring", "네트워크 보안그룹(삭제) 이벤트 작업로그 경고 설정");
        AZR_079.createAZRDescription("AZR_079", 2, "M"," \"enabled\" : \"true\" 면 양호", "Monitoring", "네트워크 보안그룹 룰(삭제) 이벤트 작업로그 경고 설정");
        AZR_081.createAZRDescription("AZR_081", 3, "H"," \"enabled\" : \"true\" 면 양호", "Monitoring", "보안솔루션 (삭제) 이벤트 작업로그 경고 설정");
        AZR_105.createAZRDescription("AZR_105", 3, "H"," \"enabled\" : \"true\" 면 양호", "ETC", "Azure 앱 서비스 내 앱 서비스 인증 사용 여부 점검");
        AZR_106.createAZRDescription("AZR_106", 2, "M"," \"httpsOnly\" : \"true\" 면 양호", "ETC", "Azure 앱 서비스 내 웹/앱 HTTPS 리다이렉트 사용 확인");
        AZR_107.createAZRDescription("AZR_107", 2, "M"," \"minTlsVersion\" : \"1.2\" 면 양호", "ETC", "최신 버전 TLS 암호화 사용 여부 확인");
        AZR_108.createAZRDescription("AZR_108", 3, "H"," \"clientCertEnabled\" : \"true\" 면 양호", "ETC", "웹/앱 클라이언트 인증서(수신측) 상태 확인");

        azrDescriptionRepository.save(AZR_024);
        azrDescriptionRepository.save(AZR_026);
        azrDescriptionRepository.save(AZR_029);
        azrDescriptionRepository.save(AZR_031);
        azrDescriptionRepository.save(AZR_033);
        azrDescriptionRepository.save(AZR_041);
        azrDescriptionRepository.save(AZR_042);
        azrDescriptionRepository.save(AZR_043);
        azrDescriptionRepository.save(AZR_044);
        azrDescriptionRepository.save(AZR_045);
        azrDescriptionRepository.save(AZR_047);
        azrDescriptionRepository.save(AZR_075);
        azrDescriptionRepository.save(AZR_077);
        azrDescriptionRepository.save(AZR_079);
        azrDescriptionRepository.save(AZR_081);
        azrDescriptionRepository.save(AZR_105);
        azrDescriptionRepository.save(AZR_106);
        azrDescriptionRepository.save(AZR_107);
        azrDescriptionRepository.save(AZR_108);
    }


}