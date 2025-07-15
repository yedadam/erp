<h1 align="center">
  🚀 구독형 ERP 시스템
</h1>

<p align="center">
  <img src="https://readme-typing-svg.demolab.com?font=Fira+Code&pause=1000&center=true&vCenter=true&width=435&lines=ERP+Subscription+System+Project;Spring+Boot+%2B+Thymeleaf+%2B+MySQL" alt="Typing SVG" />
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Java-17-007396?style=for-the-badge&logo=openjdk&logoColor=white"/>
  <img src="https://img.shields.io/badge/SpringBoot-3.2-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"/>
  <img src="https://img.shields.io/badge/Thymeleaf-3.1-005F0F?style=for-the-badge&logo=thymeleaf&logoColor=white"/>
  <img src="https://img.shields.io/badge/MySQL-8.0-4479A1?style=for-the-badge&logo=mysql&logoColor=white"/>
</p>

---

## 📦 프로젝트 개요

**ERP 구독형 시스템**은 기업의 업무를 효율적으로 관리하기 위한 웹 애플리케이션입니다.  
영업, 재고, 회계, 인사 등 주요 기능을 모듈화하여 통합적으로 처리할 수 있도록 설계되었습니다.

---

## 🛠️ 사용 기술

| 구분 | 기술 |
|------|------|
| 백엔드 | Java 17, Spring Boot 3.2, Spring Data JPA |
| 프론트엔드 | Thymeleaf, HTML5, CSS3, JavaScript |
| 데이터베이스 | MySQL |
| 개발환경 | IntelliJ / VS Code / Git / GitHub |

---

## 📁 주요 기능

- ✅ **영업 관리**: 견적 → 수주 → 매출까지의 흐름 자동화  
- ✅ **재고 관리**: 입고 / 출고 / 실사 / 재고 차트 제공  
- ✅ **회계 처리**: 매출 전표, 지급 전표, 일자별 통계  
- ✅ **인사 관리**: 사원 등록, 휴가 신청, 급여 계산  
- ✅ **권한 관리**: 사용자/관리자 권한 분리  

---

## 🎞️ 시연 영상 및 이미지

![erp-demo](https://your-gif-url-here.gif)

> 위 이미지는 CoreUI 기반의 관리자 화면 예시입니다.  
> 📌 추가로 Vue, ag-Grid 기반 시연 화면이 필요하시면 여기에 삽입 가능합니다.

---

## ⛏️ 개발 중 이슈 및 개선 포인트

- 🔧 복잡한 생산 흐름의 처리 (예: 다단계 BOM, 실시간 공정 추적)
- 🔐 보안 처리 (JWT or Spring Security 연동)
- 📱 반응형 UI (모바일 최적화)
- 📦 멀티 테넌시 구독 구조에 대한 아키텍처 설계

---

## ✍️ 개발자 소개

| 이름 | 역할 | GitHub |
|------|------|--------|
| 신현욱 | 전체 기획, 백엔드, 프론트 일부 | [github.com/hyunwook](https://github.com/yourusername) |
| 정유환 | 개발 환경, 백엔드, 프론트 일부 | [github.com/JungYouhwan](https://github.com/JungYouhwan) |
| 이병찬 | 배포 담당, 백엔드, 프론트 일부 | [github.com/LeeByungChan](https://github.com/poes0147). |
---

## ⭐ 프로젝트 실행 방법

```bash
git clone https://github.com/yourusername/erp-subscription.git
cd erp-subscription
./gradlew bootRun
