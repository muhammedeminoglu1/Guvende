package com.example.bitirme.kullanicicrud.service;

import com.example.bitirme.kullanicicrud.dto.GirisCikisIzinBilgileriDTO;
import com.example.bitirme.kullanicicrud.dto.KullaniciRequestDTO;
import com.example.bitirme.kullanicicrud.dto.VeliBigileriKayitDTO;
import com.example.bitirme.kullanicicrud.entity.Kullanicilar;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface KullaniciService {
    Optional<KullaniciRequestDTO> saveKullanici(KullaniciRequestDTO kullaniciDTO);
    void kullaniciEkle(KullaniciRequestDTO dto);
    Optional<Kullanicilar> getKullaniciById(Long id);

    @Transactional
    Optional<KullaniciRequestDTO> kullaniciGuncelle(KullaniciRequestDTO kullaniciRequestDTO, Long id);

    ResponseEntity<String> deleteKullaniciById(String id);
    List<KullaniciRequestDTO> getAllKullanici();

    Optional<VeliBigileriKayitDTO> getVeliBilgileriById(Long id);
    List<GirisCikisIzinBilgileriDTO> getIzinBilgileri(Long kullaniciId);
    Optional<Kullanicilar> getKullaniciProfil(String email, String sifre);
    Optional<Kullanicilar> getKullaniciByEmail(String email);
}
