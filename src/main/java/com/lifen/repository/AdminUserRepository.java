package com.lifen.repository;

import com.lifen.dataobject.AdminUsers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminUserRepository extends JpaRepository<AdminUsers, Long> {

    List<AdminUsers> findByAdminAccount(String adminAccount);

}
