package com.example.bitirme.kullanicicrud.service;

import com.example.bitirme.kullanicicrud.dto.GirisCikisIzinBilgileriDTO;
import com.example.bitirme.kullanicicrud.dto.KullaniciRequestDTO;
import com.example.bitirme.kullanicicrud.dto.VeliBigileriKayitDTO;
import com.example.bitirme.kullanicicrud.entity.GirisCikisIzinBilgileri;
import com.example.bitirme.kullanicicrud.entity.Kullanicilar;
import com.example.bitirme.kullanicicrud.entity.VeliBilgileri;
import com.example.bitirme.kullanicicrud.repository.KullaniciRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class KullaniciServiceImpl implements KullaniciService {

    private final KullaniciRepository kullaniciRepository;

    @Override
    public Optional<KullaniciRequestDTO> saveKullanici(KullaniciRequestDTO kullaniciEklemeDTO) {

        Kullanicilar kullanicilar = new Kullanicilar();

        kullanicilar.setAdi(kullaniciEklemeDTO.getAdi());
        kullanicilar.setSoyadi(kullaniciEklemeDTO.getSoyadi());
        kullanicilar.setAdres(kullaniciEklemeDTO.getAdres());
        kullanicilar.setKullaniciMuvcutMu(kullaniciEklemeDTO.isKullaniciMuvcutMu());
        kullanicilar.setEmail(kullaniciEklemeDTO.getEmail());
        kullanicilar.setKisiselTelefon(kullaniciEklemeDTO.getKisiselTelefon());
        kullanicilar.setKayitTarihi(kullaniciEklemeDTO.getKayitTarihi());
        kullanicilar.setCikisTarihi(kullaniciEklemeDTO.getCikisTarihi());
        kullanicilar.setKullaniciSifre(kullaniciEklemeDTO.getKullaniciSifre());

        /*Set<GirisCikisIzinBilgileri> girisCikisBilgileriList = new HashSet<>();
        for (GirisCikisIzinBilgileriDTO girisCikisIzinBilgileriDTO : kullaniciEklemeDTO.getGirisCikisIzinBilgileriDTO()) {
            GirisCikisIzinBilgileri girisCikisIzinBilgileri = new GirisCikisIzinBilgileri();
            girisCikisIzinBilgileri.setIzinHakkiVarMi(girisCikisIzinBilgileriDTO.isIzinHakkiVarMi());
            girisCikisIzinBilgileri.setIzinBaslangicTarihi(girisCikisIzinBilgileriDTO.getIzinBaslangicTarihi());
            girisCikisIzinBilgileri.setIzinBitisTarihi(girisCikisIzinBilgileriDTO.getIzinBitisTarihi());
            girisCikisIzinBilgileri.setKullanici(kullanicilar);

            girisCikisBilgileriList.add(girisCikisIzinBilgileri);
        }*/

        VeliBigileriKayitDTO veliBigileriDTO = kullaniciEklemeDTO.getVeliBilgileriDTO();
        VeliBilgileri veliBilgileri = new VeliBilgileri();
        veliBilgileri.setVeliAdi(veliBigileriDTO.getVeliAdi());
        veliBilgileri.setVeliSoyadi(veliBigileriDTO.getVeliSoyadi());
        veliBilgileri.setVeliTelefon(veliBigileriDTO.getVeliTelefon());
        veliBilgileri.setVeliNotu(veliBigileriDTO.getVeliNotu());
        veliBilgileri.setKullanicilar(kullanicilar);

        //kullanicilar.setGirisCikisBilgileri(girisCikisBilgileriList);
        kullanicilar.setVeliBilgileri(veliBilgileri);

        kullaniciRepository.save(kullanicilar);

        return Optional.of(kullaniciEklemeDTO); // Kaydedilen kullanıcı DTO olarak geri döndürülüyor
    }

    public void kullaniciEkle(KullaniciRequestDTO dto) {
        // Veli bilgilerini DTO içinden çekerek manuel olarak prosedüre aktar
        String veliAdi = dto.getVeliBilgileriDTO() != null ? dto.getVeliBilgileriDTO().getVeliAdi() : null;
        String veliSoyadi = dto.getVeliBilgileriDTO() != null ? dto.getVeliBilgileriDTO().getVeliSoyadi() : null;
        String veliTelefon = dto.getVeliBilgileriDTO() != null ? dto.getVeliBilgileriDTO().getVeliTelefon() : null;
        String veliNotu = dto.getVeliBilgileriDTO() != null ? dto.getVeliBilgileriDTO().getVeliNotu() : null;

        // Kullanıcı bilgilerini prosedüre aktar
        kullaniciRepository.kullaniciEkle(
                dto.getAdi(),
                dto.getSoyadi(),
                dto.getAdres(),
                dto.isKullaniciMuvcutMu(),
                dto.getEmail(),
                dto.getKisiselTelefon(),
                dto.getKayitTarihi(),
                dto.getCikisTarihi(),
                dto.getKullaniciSifre(),
                veliAdi,
                veliSoyadi,
                veliTelefon,
                veliNotu
        );
    }

    @Transactional
    @Override
    public Optional<KullaniciRequestDTO> kullaniciGuncelle(KullaniciRequestDTO kullaniciRequestDTO, Long id) {
        int updatedRows = kullaniciRepository.updateKullanici(
                kullaniciRequestDTO.getAdi(),
                kullaniciRequestDTO.getSoyadi(),
                kullaniciRequestDTO.getAdres(),
                kullaniciRequestDTO.isKullaniciMuvcutMu(),
                kullaniciRequestDTO.getEmail(),
                kullaniciRequestDTO.getKisiselTelefon(),
                kullaniciRequestDTO.getKayitTarihi(),
                kullaniciRequestDTO.getCikisTarihi(),
                kullaniciRequestDTO.getKullaniciSifre(),
                id
        );

        if (updatedRows > 0) {
            return Optional.of(kullaniciRequestDTO);
        } else {
            throw new IllegalArgumentException("Kullanıcı güncelleme başarısız!");
        }
    }

    @Override
    public ResponseEntity<String> deleteKullaniciById(String id){
        kullaniciRepository.deleteById(id);
        return ResponseEntity.ok("");
    }


    @Override
    public List<KullaniciRequestDTO> getAllKullanici() {
        List<Kullanicilar> kullaniciList = kullaniciRepository.findAll();

        return kullaniciList.stream().map(kullanici -> {
            KullaniciRequestDTO dto = new KullaniciRequestDTO();
            dto.setId(kullanici.getKullaniciId());
            dto.setAdi(kullanici.getAdi());
            dto.setSoyadi(kullanici.getSoyadi());
            dto.setAdres(kullanici.getAdres());
            dto.setKullaniciMuvcutMu(kullanici.isKullaniciMuvcutMu());
            dto.setEmail(kullanici.getEmail());
            dto.setKisiselTelefon(kullanici.getKisiselTelefon());
            dto.setKayitTarihi(kullanici.getKayitTarihi());
            dto.setCikisTarihi(kullanici.getCikisTarihi());

            // GirisCikisIzinBilgileri verilerini DTO'ya ekle
            List<GirisCikisIzinBilgileriDTO> girisCikisIzinBilgileriDTOList = kullanici.getGirisCikisBilgileri().stream().map(izin -> {
                GirisCikisIzinBilgileriDTO izinDTO = new GirisCikisIzinBilgileriDTO();
                izinDTO.setIzinHakkiVarMi(izin.isIzinHakkiVarMi());
                izinDTO.setIzinBaslangicTarihi(izin.getIzinBaslangicTarihi());
                izinDTO.setIzinBitisTarihi(izin.getIzinBitisTarihi());
                return izinDTO;
            }).collect(Collectors.toList());
            dto.setGirisCikisIzinBilgileriDTO(girisCikisIzinBilgileriDTOList);

            // VeliBilgileri verisini DTO'ya ekle
            if (kullanici.getVeliBilgileri() != null) {
                VeliBilgileri veli = kullanici.getVeliBilgileri();
                VeliBigileriKayitDTO veliBilgileriDTO = new VeliBigileriKayitDTO();
                veliBilgileriDTO.setVeliAdi(veli.getVeliAdi());
                veliBilgileriDTO.setVeliSoyadi(veli.getVeliSoyadi());
                veliBilgileriDTO.setVeliTelefon(veli.getVeliTelefon());
                veliBilgileriDTO.setVeliNotu(veli.getVeliNotu());
                dto.setVeliBilgileriDTO(veliBilgileriDTO);
            }

            return dto;
        }).collect(Collectors.toList());
    }


    @Override
    public Optional<Kullanicilar> getKullaniciById(@RequestParam Long id) {
        return kullaniciRepository.findById(String.valueOf(id));
    }

    @Override
    public List<GirisCikisIzinBilgileriDTO> getIzinBilgileri(Long kullaniciId) {
        Optional<Kullanicilar> kullaniciOpt = kullaniciRepository.findById(String.valueOf(kullaniciId));

        if (kullaniciOpt.isPresent()) {
            Set<GirisCikisIzinBilgileri> izinBilgileri = kullaniciOpt.get().getGirisCikisBilgileri();

            // Entity -> DTO dönüşümü
            return izinBilgileri.stream().map(izin -> new GirisCikisIzinBilgileriDTO(
                    izin.isIzinHakkiVarMi(),       // Boolean değer
                    izin.getIzinBaslangicTarihi(), // Başlangıç tarihi
                    izin.getIzinBitisTarihi()      // Bitiş tarihi
            )).collect(Collectors.toList());
        } else {
            return Collections.emptyList(); // Kullanıcı bulunamazsa boş bir liste döndür
        }
    }


    @Override
    public Optional<VeliBigileriKayitDTO> getVeliBilgileriById(Long id) {
        Optional<Kullanicilar> kullaniciOpt = kullaniciRepository.findById(String.valueOf(id));

        if (kullaniciOpt.isPresent() && kullaniciOpt.get().getVeliBilgileri() != null) {
            VeliBilgileri veliBilgileri = kullaniciOpt.get().getVeliBilgileri();
            VeliBigileriKayitDTO dto = new VeliBigileriKayitDTO(
                    veliBilgileri.getVeliAdi(),
                    veliBilgileri.getVeliSoyadi(),
                    veliBilgileri.getVeliTelefon(),
                    veliBilgileri.getVeliNotu()
            );
            return Optional.of(dto);
        } else {
            return Optional.empty();
        }
    }

    public Optional<Kullanicilar> getKullaniciProfil(String email, String kullaniciSifre) {
        return kullaniciRepository.findByEmailAndKullaniciSifre(email, kullaniciSifre);
    }
    @Override
    public Optional<Kullanicilar> getKullaniciByEmail(String email) {
        return kullaniciRepository.findByEmail(email);
    }
}
