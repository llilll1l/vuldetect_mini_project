# vuldetect_mini_project
kisia s/w camp 과정 프로젝트 - cloud(azure) 취약점 진단 미니 프로젝트

## 프로젝트 목적
-  Azure 클라우드 환경에서 발생할 수 있는 보안 취약점을 식별하고 보안가이드를 제시함으로써 클라우드의 보안을 향상시키는 것.
## 개발 환경
1. IDE
    - intelliJ
1. language
    - Java
    - JavaScript
    - HTML
1. dependencies
    - SpringBoot, JPA
    - Lombok
    - thyme leaf
    - Mysql
1. CVS
    - Git

## 구동 환경
- local 환경
## 기능 / 웹 페이지 구성 
- azure cis benchmark 컴플라이언스 기준으로 항목 기능 구현과 리소스 단위 취약점 진단.
    1. 프로젝트 관리 페이지
        - 프로젝트 생성, 수정, 삭제, 진단 기능
    1. 대시보드 페이지
        - 프로젝트 진단에 따른 프로젝트명과 회차에 따른 결과 표시
        - 진단 결과 구성 
            1. 컴플라이언스 명
                - 프로젝트 생성시 사용할 컴플라이언스 명
            1. 진단 점수 (양호도)
                - $$(\frac{양호한 진단항목의 심각도 합}{총 진단 항목의 심각도 합}) \times 100$$
            1. 진단항목수
                - 19항목
        - 차트 구성
            1. 항목별 심각도에 따른 '양호', '취약' 개수
            1. 프로젝트 회차별 취약점 발생 현황
            1. 분류별 취약점 발생 현황
            1. 진단 항목 분류별 개수   
        - 테이블 구성
            1. 분류(항목별 카테고리)
            1. 항목 코드
            1. 심각도
                - High : Lv.3
                - Midium : Lv.2
                - Low : Lv.1
            1. 점검 항목명
            1. 진단 결과
                - 항목별 각 엔티티 중 하나라도 '취약'이라면 최종 진단결과 '취약'
            1. 진단 엔티티, 양호 엔티티, 취약 엔티티
                - 진단한 총 엔티티 개수와 그 중 양호한 엔티티 개수, 취약한 엔티티 개수 표기
            1. 상세
                - 엔티티 별 상세 정보(진단 결과, 엔티티 섹별자) 표기

## 진단 범위(분류)
### 보안 솔루션 (Security Solutions)
-  ms defender 와 같은 보안 관리 도구를 사용하여 보안 상태를 평가하고 취약점을 식별합니다.
- 보안 정책 및 규정 준수 여부를 검토하고 강화합니다.
- ms Defender는 가상 머신, 컨테이너, 네트워크, 애플리케이션 등 다양한 환경에서 보안 이슈를 모니터링하고 감시합니다.
### 접근 제어 (Access Control)
- 사용자 및 그룹의 권한 관리를 분석하고 강화합니다.
- Azure Blob Storage, table service 등의 저장소 서비스를 검토하여 데이터 암호화 및 접근 제어를 확인합니다.
### 로깅 (Logging)
- Azure Monitor와 Azure Security Center를 활용하여 시스템 이벤트 및 보안 로그를 수집하고 분석합니다.
- 이상 징후를 탐지하고 보안 이슈에 대한 경고 및 알림을 설정합니다.
### 모니터링 (Monitoring)
- Azure Monitor 및 Azure Application Insights를 사용하여 시스템 및 애플리케이션 성능을 모니터링하고 병목 현상을 식별합니다.
- 이상 징후를 탐지하고 시스템 성능에 영향을 미치는 요인을 해결합니다.

### 진단 항목
|분류|항목코드|심각도|점검 항목명|
|:---:|:---:|:---:|:---:|
|Security Solutions|AZR_024|High / Lv.3|앱 서비스용 ms defender on 설정 확인|
|Security Solutions|AZR_026|High / Lv.3|머신 sql 서버용 ms defender on 설정 확인|
|Security Solutions|AZR_029|Medium / Lv.2|컨테이너 레지스트리용 ms defender가 활성화 되어있는 경우|
|Security Solutions|AZR_031|High / Lv.3|엔드포인트용 클라우드 ms defender 설정 확인|
|Security Solutions|AZR_033|Medium / Lv.2|자동 브로비저닝 on 설정 확인|
|Access Control|AZR_041|High / Lv.3|스토리지 계정 내 기본 네트워크 엑세스 제한 설정|
|Access Control|AZR_042|High / Lv.3|스토리지 계정 내 서비스 액세스 제한 설정|
|ETC|AZR_043|High / Lv.3|Blob 서비스의 삭제(soft delete)기능 활성화 확인|
|Logging|AZR_044|Medium / Lv.2|Blob 서비스의 읽기/쓰기/삭제 스토리지 로깅 설정|
|Logging|AZR_045|Medium / Lv.2|테이블 서비스의 읽기/쓰기/삭제 스토리지 로깅 설정|
|Access Control|AZR_047|High / Lv.3|스토리지 액세스 키 재생성 주기 점검|
|Monitoring|AZR_075|Medium / Lv.2|정책 할당(삭제) 이벤트 작업 로그 경고 설정|
|Monitoring|AZR_077|Medium / Lv.2|네트워크 보안 그룹(삭제) 이벤트 작업 로그 경고 설정|
|Monitoring|AZR_079|Medium / Lv.2|네트워크 보안 그룹 룰(삭제) 이벤트 작업 로그 경고 설정|
|Monitoring|AZR_081|High / Lv.3|보안솔루션(삭제) 이벤트 작업 로그 경고 설정|
|ETC|AZR_105|High / Lv.3|Azure 앱 서비스 내 앱서비스 인증 사용 여부 점검|
|ETC|AZR_106|Medium / Lv.2|Azure 앱 서비스 내 웹/앱 HTTPS 리다이렉트 사용 확인|
|ETC|AZR_107|Medium / Lv.2|최신 버전 TLS 암호화 사용 여부 확인|
|ETC|AZR_108|High / Lv.3|웹/앱 클라이언트 인증서(수신측) 상태 확인|

