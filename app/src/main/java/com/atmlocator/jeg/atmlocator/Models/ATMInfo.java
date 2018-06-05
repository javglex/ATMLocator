package com.atmlocator.jeg.atmlocator.Models;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by javgon on 4/19/2017.
 * ATM info model
 */

public class ATMInfo {

    String state, locType, label, address, city, zip, name, lat, lng, bank, access, distance;

    String services, languages,hours;

    public ATMInfo(){

    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLocType() {
        return locType;
    }

    public void setLocType(String locType) {
        this.locType = locType;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getServices() {

        return services;
    }

    public void setServices(JSONArray services) {
        String list="";
        if (services!=null)
            for (int i =0; i<services.length(); i++) {
                list += services.optString(i, "services");
                list += "\n";
            }

        this.services =list;
    }

    public String getLanguages() {

        return languages;
    }

    public void setLanguages(JSONArray languages) {

        String list = "";
        if (languages!=null)
            for (int i =0; i<languages.length(); i++) {
                list += languages.optString(i, "languages");
                list += "\n";
            }
        this.languages = list;
    }

    public void setHours(JSONArray hours) {

        String list = "";
        if (hours!=null)
            for (int i =0; i<hours.length(); i++) {
                list += hours.optString(i, "lobbyHrs");
                list += "\n";
            }
        this.hours = list;
    }
    public String getHours() {
        return hours;
    }

}
