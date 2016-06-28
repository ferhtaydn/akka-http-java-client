package com.ferhtaydn.akkahttpjavaclient.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Map;

/**
 *
 {
    "_id": 407183,
    "key": null,
    "name": "Istanbul",
    "fullName": "Istanbul, Turkey",
    "iata_airport_code": null,
    "type": "location",
    "country": "Turkey",
    "geo_position": {
        "latitude": 41.01384,
        "longitude": 28.94966
    },
    "locationId": 39417,
    "inEurope": false,
    "countryId": 217,
    "countryCode": "TR",
    "coreCountry": false,
    "distance": null,
    "names": {
        "it": "Istanbul",
        "es": "Estambul",
        "tr": "İstanbul",
        "pl": "Stambuł",
        "pt": "Istambul",
        "fr": "Istanbul",
        "ru": "Стамбул",
        "is": "Istanbúl",
        "de": "Istanbul",
        "en": "Istanbul",
        "ca": "Istanbul",
        "nl": "Istanbul"
    },
    "alternativeNames": {}
 }
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(value = { "_id", "name", "type", "latitude", "longitude" })
public final class City {

    @JsonProperty("_id")
    private Long id;
    @JsonProperty("key")
    private String key;
    @JsonProperty("name")
    private String name;
    @JsonProperty("fullName")
    private String fullName;
    @JsonProperty("iata_airport_code")
    private String iataAirPortCode;
    @JsonProperty("type")
    private String type;
    @JsonProperty("country")
    private String country;
    @JsonProperty("geo_position")
    private GeoPosition geoPosition;
    @JsonProperty("locationId")
    private Long locationId;
    @JsonProperty("inEurope")
    private Boolean inEurope;
    @JsonProperty("countryId")
    private Long countryId;
    @JsonProperty("countryCode")
    private String countryCode;
    @JsonProperty("coreCountry")
    private Boolean coreCountry;
    @JsonProperty("distance")
    private String distance;
    @JsonProperty("names")
    private Map<String, String> names;
    @JsonProperty("alternativeNames")
    private Map<String, String> alternativeNames;

    public Double getLatitude() {
        return getGeoPosition().getLatitude();
    }

    public Double getLongitude() {
        return getGeoPosition().getLongitude();
    }

    @Override
    public String toString() {
        return String.format("%d,%s,%s,%f,%f", id, name, type, getLatitude(), getLongitude());
    }

    public Long getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return fullName;
    }

    public String getIataAirPortCode() {
        return iataAirPortCode;
    }

    public String getType() {
        return type;
    }

    public String getCountry() {
        return country;
    }

    public GeoPosition getGeoPosition() {
        return geoPosition;
    }

    public Long getLocationId() {
        return locationId;
    }

    public Boolean isInEurope() {
        return inEurope;
    }

    public Long getCountryId() {
        return countryId;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public Boolean isCoreCountry() {
        return coreCountry;
    }

    public String getDistance() {
        return distance;
    }

    public Map<String, String> getNames() {
        return names;
    }

    public Map<String, String> getAlternativeNames() {
        return alternativeNames;
    }
}