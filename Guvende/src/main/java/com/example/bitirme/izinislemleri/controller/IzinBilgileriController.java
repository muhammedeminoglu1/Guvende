package com.example.bitirme.izinislemleri.controller;

import com.example.bitirme.izinislemleri.dto.KullaniciIzinBilgileriDTO;
import com.example.bitirme.izinislemleri.service.IzinBilgileriServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/izin")
@RequiredArgsConstructor
public class IzinBilgileriController {

    private final IzinBilgileriServiceImpl izinBilgileriService;

    // Kullanıcıya izin bilgisi ekleme
    @PostMapping("/ekle")
    public ResponseEntity<String> addIzinBilgisi(
            @RequestParam Long kullaniciId,
            @RequestParam LocalDate baslangicTarihi,
            @RequestParam LocalDate bitisTarihi) {
        String response = izinBilgileriService.addIzinBilgisi(kullaniciId, baslangicTarihi, bitisTarihi);
        return ResponseEntity.ok(response);
    }

    // Kullanıcının izin bilgilerini listeleme
    @GetMapping("/listele")
    public ResponseEntity<List<KullaniciIzinBilgileriDTO>> getKullaniciIzinBilgileri(@RequestParam Long kullaniciId) {
        List<KullaniciIzinBilgileriDTO> izinBilgileri = izinBilgileriService.getKullaniciIzinBilgileri(kullaniciId);
        return ResponseEntity.ok(izinBilgileri);
    }
    @GetMapping("/tum-kullanici-izin-listele")
    public ResponseEntity<List<KullaniciIzinBilgileriDTO>> getTumKullaniciIzinBilgileri(){
        List<KullaniciIzinBilgileriDTO> izinBilgileriDTOList = izinBilgileriService.getTumKullaniciIzinBilgileri();
        return ResponseEntity.ok(izinBilgileriDTOList);
    }



    // İzin silme
    @DeleteMapping("/sil/{izinId}")
    public ResponseEntity<String> deleteIzinBilgisi(@PathVariable Long izinId) {
        izinBilgileriService.deleteIzinBilgisi(izinId);
        return ResponseEntity.ok("İzin bilgisi başarıyla silindi.");
    }
}
