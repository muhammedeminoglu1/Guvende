package com.example.bitirme.kullacnicikonumbilgisi.service;

import com.example.bitirme.kullacnicikonumbilgisi.dto.KonumBilgileriResponseDTO;
import com.example.bitirme.kullacnicikonumbilgisi.dto.KonumBilgisiRequestDTO;
import com.example.bitirme.kullacnicikonumbilgisi.dto.ReverseGeocodingResponseDTO;
import com.example.bitirme.kullacnicikonumbilgisi.entity.KonumBilgisiEntity;
import com.example.bitirme.kullacnicikonumbilgisi.repository.KonumBilgisiRepository;
import com.example.bitirme.kullacnicikonumbilgisi.service.webclient.KonumBilgisiServiceImpl;
import com.example.bitirme.kullanicicrud.entity.Kullanicilar;
import com.example.bitirme.kullanicicrud.repository.KullaniciRepository;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class KonumBilgisiService {

    private final KonumBilgisiRepository konumBilgisiRepository;
    private final KullaniciRepository kullaniciRepository;

    @Autowired
    public KonumBilgisiService(KonumBilgisiRepository konumBilgisiRepository, KullaniciRepository kullaniciRepository) {
        this.konumBilgisiRepository = konumBilgisiRepository;
        this.kullaniciRepository = kullaniciRepository;
    }

    public void saveKonumBilgisi(KonumBilgisiRequestDTO konumBilgisiRequestDTO) {
        // Kullanıcıyı email veya kullanıcı id ile bulma
        Kullanicilar kullanici = kullaniciRepository.findByEmail(konumBilgisiRequestDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

        // Konum kaydını oluşturup kullanıcı ile eşleştirme
        KonumBilgisiEntity konumBilgisi = new KonumBilgisiEntity();
        konumBilgisi.setEnlem(konumBilgisiRequestDTO.getEnlem());
        konumBilgisi.setBoylam(konumBilgisiRequestDTO.getBoylam());
        konumBilgisi.setKullanici(kullanici);

        konumBilgisiRepository.save(konumBilgisi);
    }

    public Mono<ReverseGeocodingResponseDTO> getReverseGeocoding(KonumBilgisiRequestDTO konumBilgisiRequestDTO) {
        return KonumBilgisiServiceImpl.getAddressByCoordinates(konumBilgisiRequestDTO.getEnlem(), konumBilgisiRequestDTO.getBoylam());
    }

    public KonumBilgileriResponseDTO getAllKonumBilgisi(String email) {
        // Kullanıcıyı email ile bul
        Kullanicilar kullanici = kullaniciRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Kullanıcı bulunamadı: " + email));

        // Kullanıcı ID ile konum bilgilerini getir
        List<KonumBilgisiEntity> konumBilgileri = konumBilgisiRepository.findByKullaniciId(kullanici.getKullaniciId());

        // DTO'ya dönüştür
        List<KonumBilgisiRequestDTO> konumBilgisiDTOs = konumBilgileri.stream().map(konum -> {
            KonumBilgisiRequestDTO dto = new KonumBilgisiRequestDTO();
            dto.setEnlem(konum.getEnlem());
            dto.setBoylam(konum.getBoylam());
            dto.setEmail(email);
            return dto;
        }).collect(Collectors.toList());

        // Response DTO oluştur
        KonumBilgileriResponseDTO responseDTO = new KonumBilgileriResponseDTO();
        responseDTO.setKullaniciId(kullanici.getKullaniciId());
        responseDTO.setKonumBilgileri(konumBilgisiDTOs);

        return responseDTO;
    }



    public List<KonumBilgisiRequestDTO> getKonumBilgileriByKullaniciId(Long kullaniciId) {
        List<KonumBilgisiEntity> konumBilgileriEntities = konumBilgisiRepository.findByKullaniciId(kullaniciId);
        List<KonumBilgisiRequestDTO> responseDTOList = new ArrayList<>();

        for (KonumBilgisiEntity konum : konumBilgileriEntities) {
            KonumBilgisiRequestDTO dto = new KonumBilgisiRequestDTO();

            dto.setEnlem(konum.getEnlem());
            dto.setBoylam(konum.getBoylam());

            if (konum.getKullanici() != null) {
                dto.setEmail(konum.getKullanici().getEmail());
            }

            responseDTOList.add(dto);
        }

        return responseDTOList;
    }

}
