package com.example.mymoviememoir.dbentity;

import java.sql.Struct;

public class Cinema {
    private Long cinemaId;
    private String cinemaName;
    private String cinemaPostcode;
    public Cinema(){

    }
    public Cinema(Long cinemaId){
        this.cinemaId = cinemaId;
    }
    public  Cinema(Long cinemaId, String cinemaName,String cinemaPostcode){
        this.cinemaId = cinemaId;
        this.cinemaName = cinemaName;
        this.cinemaPostcode = cinemaPostcode;
    }
    public Long getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(Long cinemaId) {
        this.cinemaId = cinemaId;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }

    public String getCinemaPostcode() {
        return cinemaPostcode;
    }

    public void setCinemaPostcode(String cinemaPostcode) {
        this.cinemaPostcode = cinemaPostcode;
    }




}
