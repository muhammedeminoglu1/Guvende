package com.example.bitirme.kullanicicrud.repository;

import com.example.bitirme.kullanicicrud.entity.Kullanicilar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface KullaniciRepository extends JpaRepository<Kullanicilar, String> {
    Optional<Kullanicilar> findByEmail(String email);

    @Procedure(name = "kullaniciekle")
    void kullaniciEkle(
            String adi, String soyadi, String adres, boolean kullaniciMuvcutMu,
            String email, String kisiselTelefon, LocalDate kayitTarihi, LocalDate cikisTarihi,
            String kullaniciSifre, String veliAdi, String veliSoyadi, String veliTelefon, String veliNotu
    );

    Optional<Kullanicilar> findByEmailAndKullaniciSifre(String email, String kullaniciSifre);


    // Kullanıcı güncelleme için özel sorgu
    @Modifying
    @Query("UPDATE Kullanicilar k SET k.adi = :adi, k.soyadi = :soyadi, k.adres = :adres, " +
            "k.kullaniciMuvcutMu = :kullaniciMuvcutMu, k.email = :email, k.kisiselTelefon = :kisiselTelefon, " +
            "k.kayitTarihi = :kayitTarihi, k.cikisTarihi = :cikisTarihi, k.kullaniciSifre = :kullaniciSifre " +
            "WHERE k.id = :kullaniciId")
    int updateKullanici(@Param("adi") String adi,
                        @Param("soyadi") String soyadi,
                        @Param("adres") String adres,
                        @Param("kullaniciMuvcutMu") boolean kullaniciMuvcutMu,
                        @Param("email") String email,
                        @Param("kisiselTelefon") String kisiselTelefon,
                        @Param("kayitTarihi") LocalDate kayitTarihi,
                        @Param("cikisTarihi") LocalDate cikisTarihi,
                        @Param("kullaniciSifre") String kullaniciSifre,
                        @Param("kullaniciId") Long kullaniciId);

}