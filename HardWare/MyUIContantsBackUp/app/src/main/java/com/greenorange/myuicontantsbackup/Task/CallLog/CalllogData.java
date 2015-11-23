package com.greenorange.myuicontantsbackup.Task.CallLog;

import android.text.TextUtils;

public class CalllogData {

    private String _id    = "";
    private String number = "";
    private String date   = "";
    private String duration = "";
    private String type   = "";
    private String countryiso = "";
    private String voicemail_uri = "";
    private String geocoded_location = "";
    private String name   = "";
    private String numbertype = "";           // 9
    private String numberlabel = "";          // 10
    private String lookup_uri  = "";           // 11
    private String matched_number = "";      // 12
    private String normalized_number = "";    // 13
    private String photo_id = "";       // 14
    private String formatted_number = "";     // 15
    private String is_read = "";                     // 16
    private String presentation = "";
    private String subscription_component_name = "";
    private String subscription_id = "";
    private String features = "";
    private String data_usage = "";
    private String transcription = "";

    @Override
    public boolean equals(Object o) {
        if(o instanceof CalllogData){
            return TextUtils.equals(number, ((CalllogData) o).number);
        }
        return super.equals(o);
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCountryiso() {
        return countryiso;
    }

    public void setCountryiso(String countryiso) {
        this.countryiso = countryiso;
    }

    public String getVoicemail_uri() {
        return voicemail_uri;
    }

    public void setVoicemail_uri(String voicemail_uri) {
        this.voicemail_uri = voicemail_uri;
    }

    public String getGeocoded_location() {
        return geocoded_location;
    }

    public void setGeocoded_location(String geocoded_location) {
        this.geocoded_location = geocoded_location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumbertype() {
        return numbertype;
    }

    public void setNumbertype(String numbertype) {
        this.numbertype = numbertype;
    }

    public String getNumberlabel() {
        return numberlabel;
    }

    public void setNumberlabel(String numberlabel) {
        this.numberlabel = numberlabel;
    }

    public String getLookup_uri() {
        return lookup_uri;
    }

    public void setLookup_uri(String lookup_uri) {
        this.lookup_uri = lookup_uri;
    }

    public String getMatched_number() {
        return matched_number;
    }

    public void setMatched_number(String matched_number) {
        this.matched_number = matched_number;
    }

    public String getNormalized_number() {
        return normalized_number;
    }

    public void setNormalized_number(String normalized_number) {
        this.normalized_number = normalized_number;
    }

    public String getPhoto_id() {
        return photo_id;
    }

    public void setPhoto_id(String photo_id) {
        this.photo_id = photo_id;
    }

    public String getFormatted_number() {
        return formatted_number;
    }

    public void setFormatted_number(String formatted_number) {
        this.formatted_number = formatted_number;
    }

    public String getIs_read() {
        return is_read;
    }

    public void setIs_read(String is_read) {
        this.is_read = is_read;
    }

    public String getPresentation() {
        return presentation;
    }

    public void setPresentation(String presentation) {
        this.presentation = presentation;
    }

    public String getSubscription_component_name() {
        return subscription_component_name;
    }

    public void setSubscription_component_name(String subscription_component_name) {
        this.subscription_component_name = subscription_component_name;
    }

    public String getSubscription_id() {
        return subscription_id;
    }

    public void setSubscription_id(String subscription_id) {
        this.subscription_id = subscription_id;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public String getData_usage() {
        return data_usage;
    }

    public void setData_usage(String data_usage) {
        this.data_usage = data_usage;
    }

    public String getTranscription() {
        return transcription;
    }

    public void setTranscription(String transcription) {
        this.transcription = transcription;
    }
}