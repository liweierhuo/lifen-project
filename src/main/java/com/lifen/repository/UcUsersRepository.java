package com.lifen.repository;

import com.lifen.dataobject.UcUsers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UcUsersRepository extends JpaRepository<UcUsers, Long> {

    List<UcUsers> findAllByUserType(String userType);

    List<UcUsers> findAllByUserAccountAndUserType(String userAccount,String userType);

    UcUsers findAllByUserAccount(String userAccount);

    @Query(nativeQuery = true, value = "select * from uc_users ORDER BY ?#{#pageable}",
            countQuery = "select count(*) from uc_users")
    Page<UcUsers> findAllByPageable(Pageable pageable);

    Page<UcUsers> findAll(Specification<UcUsers> spec, Pageable pageable);

}
