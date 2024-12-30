package com.example.bitirme.kullacnicikonumbilgisi.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class KonumBilgileriResponseDTO {
    private Long kullaniciId;
    private List<KonumBilgisiRequestDTO> konumBilgileri;
}
