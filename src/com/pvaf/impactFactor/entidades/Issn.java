/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pvaf.impactFactor.entidades;

/**
 *
 * @author marte
 */
public class Issn {
    
    private int idPubVenue;
    private String issn;
    private String version;
    
    public Issn(String issn){
        this.issn = issn;
    }
    
    public Issn(int idPubVenue, String issn, String version){
        this.idPubVenue = idPubVenue;
        this.issn = issn;
        this.version = version;
    }

    /**
     * @return the idPubVenue
     */
    public int getIdPubVenue() {
        return idPubVenue;
    }

    /**
     * @param idPubVenue the idPubVenue to set
     */
    public void setIdPubVenue(int idPubVenue) {
        this.idPubVenue = idPubVenue;
    }

    /**
     * @return the issn
     */
    public String getIssn() {
        return issn;
    }

    /**
     * @param issn the issn to set
     */
    public void setIssn(String issn) {
        this.issn = issn;
    }

    /**
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(String version) {
        this.version = version;
    }
    
    public String toString(){
        return this.idPubVenue+";"+this.issn+";"+this.version;
    }
}
