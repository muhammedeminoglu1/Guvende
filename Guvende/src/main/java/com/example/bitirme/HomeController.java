package com.example.bitirme;

import com.example.bitirme.izinislemleri.dto.KullaniciIzinBilgileriDTO;
import com.example.bitirme.izinislemleri.service.IzinBilgileriServiceImpl;
import com.example.bitirme.kullacnicikonumbilgisi.dto.KonumBilgisiRequestDTO;
import com.example.bitirme.kullacnicikonumbilgisi.dto.ReverseGeocodingResponseDTO;
import com.example.bitirme.kullacnicikonumbilgisi.service.KonumBilgisiService;
import com.example.bitirme.kullanicicrud.dto.GirisCikisIzinBilgileriDTO;
import com.example.bitirme.kullanicicrud.dto.KullaniciRequestDTO;
import com.example.bitirme.kullanicicrud.dto.VeliBigileriKayitDTO;
import com.example.bitirme.kullanicicrud.entity.Kullanicilar;
import com.example.bitirme.kullanicicrud.service.KullaniciService;
import com.example.bitirme.login.dto.LoginRequestDto;
import com.example.bitirme.login.service.LoginService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

    private final KullaniciService kullaniciService;
    private final IzinBilgileriServiceImpl izinBilgileriService;
    private final KonumBilgisiService konumBilgisiService;
    private final LoginService loginService;

    public HomeController(KullaniciService kullaniciService, IzinBilgileriServiceImpl izinBilgileriService, KonumBilgisiService konumBilgisiService, LoginService loginService) {
        this.kullaniciService = kullaniciService;
        this.izinBilgileriService = izinBilgileriService;
        this.konumBilgisiService = konumBilgisiService;
        this.loginService = loginService;
    }

    @GetMapping("/kullanicilar")
    public String getKullanicilar(Model model) {
        List<KullaniciRequestDTO> kullanicilar = kullaniciService.getAllKullanici();
        List<KullaniciIzinBilgileriDTO> izinliKullanicilar = izinBilgileriService.getTumKullaniciIzinBilgileri();

        model.addAttribute("kullanicilar", kullanicilar);
        model.addAttribute("izinliKullanicilar", izinliKullanicilar);

        return "kullanici-listesi";  // kullanici-listesi.html sayfasına yönlendirme yapılır
    }
    @GetMapping("/kullancisil/{id}")
    public String getKullanici(@PathVariable Long id, Model model) {
        kullaniciService.deleteKullaniciById(String.valueOf(id));
        return "redirect:/kullanicilar";
    }

    @GetMapping("/kullanici/ekle")
    public String yeniKullaniciForm(Model model) {
        model.addAttribute("kullanici", new KullaniciRequestDTO());
        return "kullanici-ekle";  // kullanıcı ekleme formunu döndüren GET endpoint
    }
    @PostMapping("/kullanici/ekle")
    public String kullaniciEkle(@Valid @ModelAttribute KullaniciRequestDTO kullaniciRequestDTO,
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            // Hatalı form durumunda tekrar form sayfasına dön
            return "kullanici-ekle";
        }
        // Veriyi servis katmanına göndererek kaydediyoruz
        kullaniciService.saveKullanici(kullaniciRequestDTO);

        // Hatalı değilse işleme devam et
        return "redirect:/kullanicilar";  // yönlendirme yapılır
    }


    @GetMapping("/guncelle")
    public String kullaniciGuncelleSayfasi(Model model) {
        model.addAttribute("kullaniciRequestDTO", new KullaniciRequestDTO());

        return "kullanici-guncelle";  // Thymeleaf sayfa adı
    }

    @PostMapping("/guncelle/arama")
    public String kullaniciGetir(@RequestParam("email") String email, Model model) {
        Optional<Kullanicilar> kullanici = kullaniciService.getKullaniciByEmail(email);
        if (kullanici.isPresent()) {
            Kullanicilar mevcutKullanici = kullanici.get();

            KullaniciRequestDTO kullaniciRequestDTO = new KullaniciRequestDTO();
            kullaniciRequestDTO.setId(mevcutKullanici.getKullaniciId());
            kullaniciRequestDTO.setAdi(mevcutKullanici.getAdi());
            kullaniciRequestDTO.setSoyadi(mevcutKullanici.getSoyadi());
            kullaniciRequestDTO.setEmail(mevcutKullanici.getEmail());
            kullaniciRequestDTO.setAdres(mevcutKullanici.getAdres());
            kullaniciRequestDTO.setKisiselTelefon(mevcutKullanici.getKisiselTelefon());

            // Veli bilgileri
            VeliBigileriKayitDTO veliDTO = new VeliBigileriKayitDTO();
            veliDTO.setVeliAdi(mevcutKullanici.getVeliBilgileri().getVeliAdi());
            veliDTO.setVeliSoyadi(mevcutKullanici.getVeliBilgileri().getVeliSoyadi());
            veliDTO.setVeliTelefon(mevcutKullanici.getVeliBilgileri() .getVeliTelefon());

            kullaniciRequestDTO.setVeliBilgileriDTO(veliDTO);

            model.addAttribute("kullaniciRequestDTO", kullaniciRequestDTO);
            return "kullanici-guncelle";
        }

        model.addAttribute("hata", "Kullanıcı bulunamadı.");
        model.addAttribute("kullaniciRequestDTO", new KullaniciRequestDTO());
        return "kullanici-guncelle";
    }

    @PostMapping("/guncelle")
    public String kullaniciGuncelle(@Valid @ModelAttribute KullaniciRequestDTO kullaniciRequestDTO, Model model) {
        Optional<Kullanicilar> mevcutKullanici = kullaniciService.getKullaniciByEmail(kullaniciRequestDTO.getEmail());

        if (mevcutKullanici.isPresent()) {
            kullaniciService.kullaniciGuncelle(kullaniciRequestDTO, mevcutKullanici.get().getKullaniciId());
            return "redirect:/kullanicilar";
        } else {
            model.addAttribute("hata", "Kullanıcı bulunamadı.");
            model.addAttribute("kullaniciRequestDTO", new KullaniciRequestDTO());
            return "kullanici-guncelle";
        }
    }










    @GetMapping("/izin/{id}")
    public String getIzinBilgileri(@PathVariable Long id, Model model) {
        List<GirisCikisIzinBilgileriDTO> izinBilgileri = kullaniciService.getIzinBilgileri(id);

        model.addAttribute("izinBilgileri", izinBilgileri);
        model.addAttribute("kullaniciId", id);

        return "izinBilgileri";
    }

    @PostMapping("/ekle")
    public String addIzinBilgisi(
            @RequestParam Long kullaniciId,
            @RequestParam LocalDate baslangicTarihi,
            @RequestParam LocalDate bitisTarihi) {

        if (kullaniciId == null || baslangicTarihi == null || bitisTarihi == null) {
            throw new IllegalArgumentException("Tüm alanları doldurun!");
        }

        izinBilgileriService.addIzinBilgisi(kullaniciId, baslangicTarihi, bitisTarihi);
        return "redirect:/kullanicilar";
    }



    @GetMapping("/veli-bilgileri/{id}")
    public String getVeliBilgileri(@PathVariable Long id, Model model) {
        Optional<VeliBigileriKayitDTO> veliBilgileriOpt = kullaniciService.getVeliBilgileriById(id);

        if (veliBilgileriOpt.isPresent()) {
            model.addAttribute("veliBilgileri", veliBilgileriOpt.get());
        } else {
            model.addAttribute("errorMessage", "Veli bilgileri bulunamadı.");
        }

        return "veliBilgileri";
    }



    @GetMapping("/konum/{kullaniciId}")
    public String getKonum(@PathVariable Long kullaniciId, Model model) {
        // Kullanıcının konum bilgilerini al
        List<KonumBilgisiRequestDTO> konumBilgileri = konumBilgisiService.getKonumBilgileriByKullaniciId(kullaniciId);

        List<ReverseGeocodingResponseDTO> reverseGeocodingResults = konumBilgileri.stream()
                .map(konum -> {
                    ReverseGeocodingResponseDTO result = konumBilgisiService.getReverseGeocoding(konum).block();
                    konum.setAddress(result.getAddress().toString());
                    return result != null ? result : new ReverseGeocodingResponseDTO();
                })
                .toList();


        // Model'e ekle
        model.addAttribute("konumBilgileri", konumBilgileri);
       // model.addAttribute("reverseGeocodingResults", reverseGeocodingResults);

        return "konum-bilgisi";
    }



    @PostMapping("/admin-giris")
    public String adminLogin(@ModelAttribute LoginRequestDto loginRequest, Model model) {
        // Admin giriş kontrolü
        boolean basarili = loginService.adminGirisYap(loginRequest).isBasarili();

        if (basarili) {
            // Başarılı giriş: kullanici-listesi.html sayfasına yönlendirilir
            return "redirect:/kullanicilar";
        }

        // Başarısız giriş: tekrar giriş ekranına dönülür ve hata mesajı eklenir
        model.addAttribute("error", "Geçersiz email veya şifre!");
        return "redirect:/admin-login"; // login.html sayfasına dön
    }

    @GetMapping("/admin-login")
    public String showAdminLoginPage(Model model) {
        model.addAttribute("loginRequest", new LoginRequestDto());
        return "login"; // login.html sayfasını döner
    }



}
