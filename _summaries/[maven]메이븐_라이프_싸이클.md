## maven life cycle

- `Default(Build)` : 일반적인 빌드 프로세스를 위한 모델이다.
- `Clean` : 빌드 시 생성되었던 파일들을 삭제하는 단계
- `Validate` : 프로젝트가 올바른지 확인하고 필요한 모든 정보를 사용할 수 있는지 확인하는 단계
- `Compile` : 프로젝트의 소스코드를 컴파일 하는 단계
- `Test` : 유닛(단위) 테스트를 수행 하는 단계(테스트 실패시 빌드 실패로 처리, 스킵 가능)
- `Package` : 실제 컴파일된 소스 코드와 리소스들을 jar, war 등등의 파일 등의 배포를 위한 패키지로 만드는 단계
- `Verify` : 통합 테스트 결과에 대한 검사를 실행하여 품질 기준을 충족하는지 확인하는 단계
- `Install` : 패키지를 로컬 저장소에 설치하는 단계
- `Site` : 프로젝트 문서와 사이트 작성, 생성하는 단계
- `Deploy` : 만들어진 package를 원격 저장소에 release 하는 단계

- reference : https://goddaehee.tistory.com/199
