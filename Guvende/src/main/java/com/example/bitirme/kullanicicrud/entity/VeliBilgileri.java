package com.example.bitirme.kullanicicrud.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "VeliBilgileri")
public class VeliBilgileri {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long veliBilgileriId;

    private String veliAdi;
    private String VeliSoyadi;
    private String veliTelefon;
    private String veliNotu;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "kullaniciId")
    @JsonBackReference
    private Kullanicilar kullanicilar;
}
