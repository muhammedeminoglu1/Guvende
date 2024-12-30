package com.example.bitirme.kullanicicrud.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "GirisCikisIzinBilgileri")
public class GirisCikisIzinBilgileri{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long girisCikisId;

    private Boolean izinHakkiVarMi;
    private LocalDate izinBaslangicTarihi;
    private LocalDate izinBitisTarihi;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kullaniciId")
    @JsonBackReference
    private Kullanicilar kullanici;

    public boolean isIzinHakkiVarMi() {
        return izinHakkiVarMi;
    }

    public Kullanicilar getKullanici() {
        return kullanici;
    }

}
