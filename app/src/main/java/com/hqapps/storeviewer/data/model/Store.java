package com.hqapps.storeviewer.data.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.util.Objects;

@Root(name = "store", strict = false)
public class Store {

    @Element(name = "posId")
    public Long posId;

    @Element(name = "name")
    public String name;

    @Element(name = "address")
    public String address;

    @Element(name = "latitude")
    public Float latitude;

    @Element(name = "longitude")
    public Float longitude;

    @Element(name = "postalCode")
    public String postalCode;

    @Element(name = "city")
    public String city;

    @Element(name = "distributorId")
    public Long distributorId;

    @Element(name = "country")
    public String country;

    @Element(name = "posTypeLogo", required = false)
    public String posTypeLogo;

    @Element(name = "productLogo")
    public String productLogo;

    @Element(name = "specialText", required = false)
    public String specialText;

    @Element(name = "recommended")
    public Boolean recommended;

    @Element(name = "directload")
    public Boolean directload;

    @Element(name = "mdirectload")
    public Boolean mdirectload;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Store store = (Store) o;
        return Objects.equals(posId, store.posId) && Objects.equals(name, store.name) && Objects.equals(address, store.address) && Objects.equals(latitude, store.latitude) && Objects.equals(longitude, store.longitude) && Objects.equals(postalCode, store.postalCode) && Objects.equals(city, store.city) && Objects.equals(distributorId, store.distributorId) && Objects.equals(country, store.country) && Objects.equals(posTypeLogo, store.posTypeLogo) && Objects.equals(productLogo, store.productLogo) && Objects.equals(specialText, store.specialText) && Objects.equals(recommended, store.recommended) && Objects.equals(directload, store.directload) && Objects.equals(mdirectload, store.mdirectload);
    }

    @Override
    public int hashCode() {
        return Objects.hash(posId, name, address, latitude, longitude, postalCode, city, distributorId, country, posTypeLogo, productLogo, specialText, recommended, directload, mdirectload);
    }
}