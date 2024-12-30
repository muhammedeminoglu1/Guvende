package com.example.bitirme.kullacnicikonumbilgisi.repository;

import com.example.bitirme.kullacnicikonumbilgisi.entity.KonumBilgisiEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface KonumBilgisiRepository extends JpaRepository<KonumBilgisiEntity, String> {

    // Kullanıcı ID’sine göre konum bilgilerini bulmak için özel sorgu
    @Query("SELECT k FROM KonumBilgisiEntity k WHERE k.kullanici.kullaniciId = :kullaniciId")
    List<KonumBilgisiEntity> findByKullaniciId(@Param("kullaniciId") Long kullaniciId);

    
}
