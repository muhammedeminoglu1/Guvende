package com.example.bitirme.kullacnicikonumbilgisi.service.webclient;

import com.example.bitirme.kullacnicikonumbilgisi.dto.ReverseGeocodingResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class KonumBilgisiServiceImpl {

    private static WebClient.Builder webClientBuilder;


    public void NominatimClient(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public KonumBilgisiServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public static Mono<ReverseGeocodingResponseDTO> getAddressByCoordinates(String lat, String lon) {
        WebClient webClient = WebClient.create("https:  //nominatim.openstreetmap.org");
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/reverse")
                        .queryParam("lat", lat)
                        .queryParam("lon", lon)
                        .queryParam("format", "json")
                        .build())
                .retrieve()
                .bodyToMono(ReverseGeocodingResponseDTO.class);
    }

}
