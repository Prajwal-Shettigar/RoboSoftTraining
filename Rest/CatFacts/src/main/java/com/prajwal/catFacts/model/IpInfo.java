package com.prajwal.catFacts.model;
/*

      "ip": "161.185.160.93",
              "city": "New York City",
              "region": "New York",
              "country": "US",
              "loc": "40.7143,-74.0060",
              "org": "AS22252 The City of New York",
              "postal": "10004",
              "timezone": "America/New_York",
              "readme": "https://ipinfo.io/missingauth"
 */
public class IpInfo {

    private String ip;
    private String city;
    private String region;

    private String loc;

    private String org;
    private long postal;
    private String timezone;
    private String readme;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public long getPostal() {
        return postal;
    }

    public void setPostal(long postal) {
        this.postal = postal;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getReadme() {
        return readme;
    }

    public void setReadme(String readme) {
        this.readme = readme;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }
}
