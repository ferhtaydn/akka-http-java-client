package com.ferhtaydn.akkahttpjavaclient.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class GeoPosition {

    @JsonProperty("latitude")
    private Double latitude;
    @JsonProperty("longitude")
    private Double longitude;

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
}