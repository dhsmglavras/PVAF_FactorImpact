/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pvaf.impactFactor.entidades;

/**
 *
 * @author douglas
 */
public class TitleAbrev {
    
    private int idPubVenue;
    private String titleAbrev;
    
    public TitleAbrev(String titleAbrev){
        this.titleAbrev = titleAbrev;
    }
    
    public TitleAbrev(int idPubVenue, String titleAbrev){
        this.idPubVenue = idPubVenue;
        this.titleAbrev = titleAbrev;
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
     * @return the titleAbrev
     */
    public String getTitleAbrev() {
        return titleAbrev;
    }

    /**
     * @param title the titleAbrev to set
     */
    public void setTitleAbrev(String title) {
        this.titleAbrev = title;
    }
    
    @Override
    public String toString(){
        return this.idPubVenue+";"+this.titleAbrev;
    }
    
}
