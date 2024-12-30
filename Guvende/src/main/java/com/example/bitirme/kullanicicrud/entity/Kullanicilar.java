package com.example.bitirme.kullanicicrud.entity;

import com.example.bitirme.kullacnicikonumbilgisi.entity.KonumBilgisiEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Lazy;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "kullanicilar")
public class Kullanicilar {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "kullanicilar_kullanici_id_seq")
    @SequenceGenerator(name = "kullanicilar_kullanici_id_seq", sequenceName = "kullanicilar_kullanici_id_seq", allocationSize = 1)
    private Long kullaniciId;

    private String adi;
    private String soyadi;
    private String adres;
    private boolean kullaniciMuvcutMu;
    private String email;
    private String kisiselTelefon;
    private LocalDate kayitTarihi;
    private LocalDate cikisTarihi;
    private String kullaniciSifre;

    @JsonManagedReference
    @OneToMany(mappedBy = "kullanici", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<GirisCikisIzinBilgileri> girisCikisBilgileri;

    @OneToOne( cascade = CascadeType.ALL)
    @JoinColumn(name = "kullaniciId")
    @JsonManagedReference
    private VeliBilgileri veliBilgileri;

    @Lazy
    @OneToMany(mappedBy = "kullanici", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<KonumBilgisiEntity> konumBilgisiEntities;


    public Kullanicilar() {

    }
}
