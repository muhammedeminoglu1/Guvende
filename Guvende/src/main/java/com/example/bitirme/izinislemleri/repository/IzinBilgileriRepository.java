package com.example.bitirme.izinislemleri.repository;

import com.example.bitirme.kullanicicrud.entity.GirisCikisIzinBilgileri;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface IzinBilgileriRepository extends JpaRepository<GirisCikisIzinBilgileri, Long> {

    @Query("SELECT i FROM GirisCikisIzinBilgileri i WHERE i.kullanici.kullaniciId = :kullaniciId")
    List<GirisCikisIzinBilgileri> findByKullaniciId(@Param("kullaniciId") Long kullaniciId);

    @Query("SELECT i FROM GirisCikisIzinBilgileri i WHERE :today BETWEEN i.izinBaslangicTarihi AND i.izinBitisTarihi")
    List<GirisCikisIzinBilgileri> findIzinByToday(@Param("today") LocalDate today);

    @Procedure(procedureName = "ekle_izin_bilgisi")
    void ekleIzinBilgisi(
            @Param("kullanici_id") Long kullaniciId,
            @Param("baslangic_tarihi") LocalDate baslangicTarihi,
            @Param("bitis_tarihi") LocalDate bitisTarihi,
            @Param("izin_hakki_var_mi") Boolean izinHakkiVarMi
    );
}