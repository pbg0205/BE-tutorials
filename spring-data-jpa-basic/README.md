# Spring-Data-Jpa-Basic

# N + 1 쿼리 이슈

## N + 1 문제

---

### 1. N + 1 문제?? N + 1 이 발생하는 이유??

[JPA N+1 문제 해결 방법 및 실무 적용 팁  - 삽질중인 개발자](https://programmer93.tistory.com/83)

> N + 1 문제가 발생하는 원인은 **영속성 컨텍스트**이다. 쿼리를 요청할 때 연관 관계의 엔티티가 영속성 컨텍스트에 존재하지 않을 경우, 영속성 컨텍스트에 엔티티를 추가하기 위해 추가 조회 쿼리를 요청하는데 이를 `N + 1 문제`라 한다.
>

### 2. N + 1 이 발생하는 상황

1. **Eager Loading 을 통해 객체 조회시**
2. **Lazy Loading** option + 연관 관계의 필드, 데이터를 접근하는 경우

### 3. N + 1 해결 방법

1. Fetch Join

    ```java
    package com.cooper.springdatajpabasic.employee.repository;
    
    import com.cooper.springdatajpabasic.employee.domain.Employee;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.data.jpa.repository.Query;
    
    import java.util.List;
    
    public interface EmployeesRepository extends JpaRepository<Employee, Long> {
    
    		// [2] 연관관계 조인 : N + 1 발생 (X)
        @Query("select employee " +
                "from Employee employee " +
                "inner join Department department on employee.department.id = department.id " +
                "where department.id in :departmentIds")
        List<Employee> findEmployeesByDepartmentIdNoFetch(List<Long> departmentIds);
    
    		// [2] 연관관계 조인 + fetch : N + 1 발생 (X)
        @Query("select employee " +
                "from Employee employee " +
                "inner join fetch employee.department " +
                "where employee.department.id in :departmentIds")
        List<Employee> findEmployeesByDepartmentIdsWithFetch(List<Long> departmentIds);
    
    		// [3] 일반 join + fetch : N + 1 발생 (O)
        @Query("select employee " +
                "from Employee employee " +
                "inner join Department department on employee.department.id = department.id " +
                "where department.id in :departmentIds")
        List<Employee> findEmployeesByDepartmentIdsNormalJoin(List<Long> departmentIds);
    
    }
    ```

2. `@EntityGraph`
    1. @EntityGraph 라는 어노테이션을 사용해서 fetch 조인을 하는 것인데 그냥 이런 게 있구나만 알아두고 사용하지 말자. 사용하는 순간 조금만 관계가 복잡해져도 헬게이트가 열린다.
        - source : [JPA N+1 문제 해결 방법 및 실무 적용 팁 - 삽질중인 개발자](https://programmer93.tistory.com/83)
3. `@BatchSize`
    - 정확히 N+1 문제를 방지하는 방법이 아닌 N+1 쿼리를 최소화하는 방법이다. 예를 들어 100번 일어날 N+1 문제를 1번만 더 조회하는 방식으로 성능을 최적화할 수 있다.
    - [프록시 객체 조회 시, @BatchSize 역할](https://www.notion.so/BatchSize-19e7a4fc060742d58ed13bedec351ba6?pvs=21)

## 프록시 객체 조회 시, @BatchSize 역할

---

### 1. 기능

1. 여러 프록시 객체를 조회할 때, **여러 개의 SELECT 쿼리를 하나의 IN 쿼리로 생성해주는 어노테이션**
2. `@BatchSize` 의 size 를 값을 설정하면 size 만큼의 in 절의 파라미터로 추가된다.
    - size = 5 : in(1, 2, 3, 4, 5) 까지 in절로 처리
    - size = 8 : in(1, 2, 3, 4, 5, 6, 7, 8) 까지 in절로 처리

### 2. 테스트

1. sample code

    ```java
    // ...
    import org.hibernate.annotations.BatchSize;
    // ...
    
    @Entity
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @EqualsAndHashCode(of = {"id", "name"})
    @ToString(exclude = "employees")
    public class Department {
    
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
    
        private String name;
    
        @BatchSize(size = 1000) // 이 부분!
        @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
        private List<Employee> employees;
    
        public Department(String name) {
            this.name = name;
        }
    
    }
    
    ```

    ```java
    @DataJpaTest
    @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
    class DepartmentRepositoryTest {
    
        @Autowired
        private DepartmentRepository departmentRepository;
    
        @Test
        @DisplayName("부서의 직원을 호출하면 N + 1 문제가 발생한다")
        void findEmployeeInDepartment() {
            List<Department> departments = departmentRepository.findAll();
    
    				departments.get(0).getEmployees().size();
            departments.get(1).getEmployees().size();
            departments.get(2).getEmployees().size();
            departments.get(3).getEmployees().size();
            departments.get(4).getEmployees().size();
            departments.get(5).getEmployees().size();
            departments.get(6).getEmployees().size();
            departments.get(7).getEmployees().size();
        }
        
    }
    ```

2. 쿼리 비교
    1. @BatchSize 가 없을 경우

        ```java
        Hibernate: 
            select
                department0_.id as id1_0_,
                department0_.name as name2_0_ 
            from
                department department0_
        Hibernate: // employee 조회 쿼리 1
            select
                employees0_.department_id as departme3_1_0_,
                employees0_.id as id1_1_0_,
                employees0_.id as id1_1_1_,
                employees0_.department_id as departme3_1_1_,
                employees0_.name as name2_1_1_ 
            from
                employee employees0_ 
            where
                employees0_.department_id=?
        Hibernate: // employee 조회 쿼리 2
            select
                employees0_.department_id as departme3_1_0_,
                employees0_.id as id1_1_0_,
                employees0_.id as id1_1_1_,
                employees0_.department_id as departme3_1_1_,
                employees0_.name as name2_1_1_ 
            from
                employee employees0_ 
            where
                employees0_.department_id=?
        Hibernate: // employee 조회 쿼리 3
            select
                employees0_.department_id as departme3_1_0_,
                employees0_.id as id1_1_0_,
                employees0_.id as id1_1_1_,
                employees0_.department_id as departme3_1_1_,
                employees0_.name as name2_1_1_ 
            from
                employee employees0_ 
            where
                employees0_.department_id=?
        Hibernate: // employee 조회 쿼리 4
            select
                employees0_.department_id as departme3_1_0_,
                employees0_.id as id1_1_0_,
                employees0_.id as id1_1_1_,
                employees0_.department_id as departme3_1_1_,
                employees0_.name as name2_1_1_ 
            from
                employee employees0_ 
            where
                employees0_.department_id=?
        Hibernate: // employee 조회 쿼리 5
            select
                employees0_.department_id as departme3_1_0_,
                employees0_.id as id1_1_0_,
                employees0_.id as id1_1_1_,
                employees0_.department_id as departme3_1_1_,
                employees0_.name as name2_1_1_ 
            from
                employee employees0_ 
            where
                employees0_.department_id=?
        Hibernate: // employee 조회 쿼리 6
            select
                employees0_.department_id as departme3_1_0_,
                employees0_.id as id1_1_0_,
                employees0_.id as id1_1_1_,
                employees0_.department_id as departme3_1_1_,
                employees0_.name as name2_1_1_ 
            from
                employee employees0_ 
            where
                employees0_.department_id=?
        Hibernate: // employee 조회 쿼리 7
            select
                employees0_.department_id as departme3_1_0_,
                employees0_.id as id1_1_0_,
                employees0_.id as id1_1_1_,
                employees0_.department_id as departme3_1_1_,
                employees0_.name as name2_1_1_ 
            from
                employee employees0_ 
            where
                employees0_.department_id=?
        Hibernate: // employee 조회 쿼리 8
            select
                employees0_.department_id as departme3_1_0_,
                employees0_.id as id1_1_0_,
                employees0_.id as id1_1_1_,
                employees0_.department_id as departme3_1_1_,
                employees0_.name as name2_1_1_ 
            from
                employee employees0_ 
            where
                employees0_.department_id=?
        ```

    2. @BatchSize 가 있을 경우

        ```java
        Hibernate: 
            select
                department0_.id as id1_0_,
                department0_.name as name2_0_ 
            from
                department department0_
        Hibernate: 
            select
                employees0_.department_id as departme3_1_1_,
                employees0_.id as id1_1_1_,
                employees0_.id as id1_1_0_,
                employees0_.department_id as departme3_1_0_,
                employees0_.name as name2_1_0_ 
            from
                employee employees0_ 
            where
                employees0_.department_id in (
                    ?, ?, ?, ?, ?, ?, ?, ?
                )
        ```
