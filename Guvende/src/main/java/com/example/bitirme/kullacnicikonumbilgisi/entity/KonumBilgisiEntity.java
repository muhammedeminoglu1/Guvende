package com.example.bitirme.kullacnicikonumbilgisi.entity;

import com.example.bitirme.kullanicicrud.entity.Kullanicilar;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "konum_bilgisi")
public class KonumBilgisiEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String enlem;
    private String boylam;

    @ManyToOne
    @JoinColumn(name = "kullanici_id")
    @JsonBackReference
    private Kullanicilar kullanici;
}
