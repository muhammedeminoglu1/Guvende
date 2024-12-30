package com.example.bitirme.kullacnicikonumbilgisi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Address {

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        if (suburb != null) {
            result.append("mahalle='").append(suburb).append("', ");
        }
        if (city != null) {
            result.append("şehir='").append(city).append("', ");
        }
        if (province != null) {
            result.append("şehir='").append(province).append("', ");
        }
        if (iso31662Lvl4 != null) {
            result.append("iso31662Lvl4='").append(iso31662Lvl4).append("', ");
        }
        if (region != null) {
            result.append("bölge='").append(region).append("', ");
        }
        if (postcode != null) {
            result.append("posta kodu='").append(postcode).append("', ");
        }
        if (country != null) {
            result.append("ülke='").append(country).append("', ");
        }
        if (countryCode != null) {
            result.append("ülke kodu='").append(countryCode).append("', ");
        }

        // Son virgülü kaldır
        if (result.length() > 0) {
            result.setLength(result.length() - 2);
        }

        return result.toString();
    }


    private String suburb;
    private String city;
    private String province;
    private String iso31662Lvl4;
    private String region;
    private String postcode;
    private String country;
    private String countryCode;
}
