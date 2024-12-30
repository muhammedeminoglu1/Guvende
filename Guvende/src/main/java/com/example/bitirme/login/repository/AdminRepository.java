package com.example.bitirme.login.repository;

import com.example.bitirme.login.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByEmailAndSifre(String email, String sifre);

    Optional<Object> findByEmail(String email);
}
