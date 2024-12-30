package com.example.bitirme.kullanicicrud.controller;

import com.example.bitirme.kullanicicrud.dto.KullaniciRequestDTO;
import com.example.bitirme.kullanicicrud.entity.Kullanicilar;
import com.example.bitirme.kullanicicrud.service.KullaniciService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping(path = "/api/kullanici-bilgi")
public class KullanicilarBilgilerndirmeController {

    private KullaniciService kullaniciService;

    public KullanicilarBilgilerndirmeController(KullaniciService kullaniciService) {
        this.kullaniciService = kullaniciService;
    }

    /*@PostMapping(path = "/kullanici-ekle")
    public ResponseEntity<KullaniciRequestDTO> kullaniciEkle(@RequestBody KullaniciRequestDTO kullaniciEklemeDTO) {
        kullaniciService.saveKullanici(kullaniciEklemeDTO);
        return new ResponseEntity<>(kullaniciEklemeDTO, HttpStatus.OK);
    }*/
    @PostMapping("/kullanici-ekle")
    public ResponseEntity<String> kullaniciEkleProcedure(@RequestBody KullaniciRequestDTO kullaniciRequestDTO) {
        try {
            kullaniciService.saveKullanici(kullaniciRequestDTO);
            return ResponseEntity.ok("Kullanıcı başarıyla eklendi.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Kullanıcı eklenirken bir hata oluştu: " + e.getMessage());
        }
    }

    @PostMapping("/guncelle")
    public ResponseEntity<KullaniciRequestDTO> kullaniciGuncelleme(
            @RequestBody KullaniciRequestDTO kullaniciGuncellemeDTO,
            @RequestParam Long id) {

        Optional<KullaniciRequestDTO> guncellenenKullanici = kullaniciService.kullaniciGuncelle(kullaniciGuncellemeDTO, id);

        if (guncellenenKullanici.isPresent()) {
            return new ResponseEntity<>(guncellenenKullanici.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/tüm-kullanici-bilgilerini-getirir")
    public <List> Object tumKullaniciGetirir() {
        return new ResponseEntity<>(kullaniciService.getAllKullanici(), HttpStatus.OK);
    }

    @GetMapping(path = "/id-ile-kullanıcı-getir")
    public Object idileKullaniciGetir(@RequestParam Long id) {
        return new ResponseEntity<>(kullaniciService.getKullaniciById(id), HttpStatus.OK);
    }

    @GetMapping(path = "id-ile-kullanıcı-sil")
    public ResponseEntity<Object> idileKullaniciSil(@RequestParam Long id) {
        kullaniciService.deleteKullaniciById(String.valueOf(id));
        return new ResponseEntity<Object>(HttpStatus.OK);
    }

    @GetMapping("/bilgi")
    public ResponseEntity<Kullanicilar> getProfilBilgisi(@RequestParam String email, @RequestParam String kullaniciSifre) {

        return kullaniciService.getKullaniciProfil(email, kullaniciSifre)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(404).build());
    }
}
