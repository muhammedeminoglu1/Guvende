package com.example.bitirme.kullacnicikonumbilgisi.controller;

import com.example.bitirme.kullacnicikonumbilgisi.dto.KonumBilgileriResponseDTO;
import com.example.bitirme.kullacnicikonumbilgisi.dto.KonumBilgisiRequestDTO;
import com.example.bitirme.kullacnicikonumbilgisi.dto.ReverseGeocodingResponseDTO;
import com.example.bitirme.kullacnicikonumbilgisi.service.KonumBilgisiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/konum-bilgi")
public class KonumBilgisiController {

    private final KonumBilgisiService konumBilgisiService;

    @Autowired
    public KonumBilgisiController(KonumBilgisiService konumBilgisiService) {
        this.konumBilgisiService = konumBilgisiService;
    }

    // Dış servisten gelen POST isteğini alıp veritabanına kaydetme
    @PostMapping("/gonder")
    public ResponseEntity<KonumBilgisiRequestDTO> saveKonumBilgisi(@RequestBody KonumBilgisiRequestDTO konumBilgisiResponseDTO) {
        konumBilgisiService.saveKonumBilgisi(konumBilgisiResponseDTO);
        return new ResponseEntity<>(konumBilgisiResponseDTO, HttpStatus.OK);
    }
    @GetMapping("/reverse-geocoding")
    public ResponseEntity<ReverseGeocodingResponseDTO> getReverseGeocoding(
            @RequestParam String lat,
            @RequestParam String lon,
            @RequestParam String email,
            @RequestParam(required = false) String format // Eğer gerekli değilse "required=false"
    ) {
        KonumBilgisiRequestDTO requestDTO = new KonumBilgisiRequestDTO();
        requestDTO.setEnlem(lat);
        requestDTO.setBoylam(lon);
        requestDTO.setEmail(email);

        return konumBilgisiService.getReverseGeocoding(requestDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build())
                .block();
    }



    @GetMapping("/getir")
    public ResponseEntity<KonumBilgileriResponseDTO> getKonumBilgisi(@RequestParam String email) {
        KonumBilgileriResponseDTO responseDTO = konumBilgisiService.getAllKonumBilgisi(email);
        return ResponseEntity.ok(responseDTO);
    }

}
