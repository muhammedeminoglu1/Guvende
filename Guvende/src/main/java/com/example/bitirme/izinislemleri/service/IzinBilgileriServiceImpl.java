package com.example.bitirme.izinislemleri.service;

import com.example.bitirme.izinislemleri.dto.KullaniciIzinBilgileriDTO;
import com.example.bitirme.izinislemleri.repository.IzinBilgileriRepository;
import com.example.bitirme.kullanicicrud.entity.GirisCikisIzinBilgileri;
import com.example.bitirme.kullanicicrud.entity.Kullanicilar;
import com.example.bitirme.kullanicicrud.repository.KullaniciRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IzinBilgileriServiceImpl {

    private final IzinBilgileriRepository izinBilgileriRepository;
    private final KullaniciRepository kullaniciRepository;

    // Kullanıcıya izin bilgisi ekleme
    @Transactional
    public String addIzinBilgisi(Long kullaniciId, LocalDate baslangicTarihi, LocalDate bitisTarihi) {
        try {
            izinBilgileriRepository.ekleIzinBilgisi(kullaniciId, baslangicTarihi, bitisTarihi, true);
            return "İzin bilgisi başarıyla eklendi.";
        } catch (Exception e) {
            System.err.println("Hata oluştu: " + e.getMessage());
            throw new RuntimeException("İzin bilgisi eklenemedi!");
        }
    }


    // Kullanıcının izin bilgilerini listeleme
    @Transactional
    public List<KullaniciIzinBilgileriDTO> getKullaniciIzinBilgileri(Long kullaniciId){
        List<GirisCikisIzinBilgileri> izinler = izinBilgileriRepository.findByKullaniciId(kullaniciId);

        // DTO dönüşümü
        return izinler.stream()
                .map(izin -> new KullaniciIzinBilgileriDTO(
                        izin.getKullanici().getAdi(),
                        izin.getKullanici().getSoyadi(),
                        izin.getIzinBaslangicTarihi(),
                        izin.getIzinBitisTarihi()
                ))
                .collect(Collectors.toList());
    }

    // tum izin bilgilerini getir
    @Transactional
    public List<KullaniciIzinBilgileriDTO> getTumKullaniciIzinBilgileri(){
        List<GirisCikisIzinBilgileri> izinBilgileris = izinBilgileriRepository.findAll();

        return izinBilgileris.stream()
                .map(tumIzinBilgileris -> new KullaniciIzinBilgileriDTO(
                        tumIzinBilgileris.getKullanici().getAdi(),
                        tumIzinBilgileris.getKullanici().getSoyadi(),
                        tumIzinBilgileris.getIzinBaslangicTarihi(),
                        tumIzinBilgileris.getIzinBitisTarihi()
                ))
                .collect(Collectors.toList());

    }

    public void deleteIzinBilgisi(Long izinId) {
        izinBilgileriRepository.deleteById(izinId);
    }

}