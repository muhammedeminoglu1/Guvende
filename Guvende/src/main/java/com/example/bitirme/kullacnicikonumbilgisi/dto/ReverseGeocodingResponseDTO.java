package com.example.bitirme.kullacnicikonumbilgisi.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReverseGeocodingResponseDTO {
    private long placeId;
    private String licence;
    private String osmType;
    private long osmId;
    private String lat;
    private String lon;
    private String classification;
    private String type;
    private int placeRank;
    private double importance;
    private String addressType;
    private String name;
    private String displayName;
    private Address address;
    private List<String> boundingBox;

    @Override
    public String toString() {
        return "ReverseGeocodingResponseDTO{" +
                "placeId=" + placeId +
                ", licence='" + licence + '\'' +
                ", osmType='" + osmType + '\'' +
                ", osmId=" + osmId +
                ", lat='" + lat + '\'' +
                ", lon='" + lon + '\'' +
                ", classification='" + classification + '\'' +
                ", type='" + type + '\'' +
                ", placeRank=" + placeRank +
                ", importance=" + importance +
                ", addressType='" + addressType + '\'' +
                ", name='" + name + '\'' +
                ", displayName='" + displayName + '\'' +
                ", address=" + address +
                ", boundingBox=" + boundingBox +
                '}';
    }
}
