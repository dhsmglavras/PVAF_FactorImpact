/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pvaf.impactFactor.entidades;

import java.util.HashSet;

/**
 *
 * @author douglas
 */
public class Journal {
    
    private String issn;
    //private HashSet<String> invalidIssns = new HashSet<>();
    private HashSet<String> titles = new HashSet<>();
    private HashSet<String> titlesAbrev = new HashSet<>();
    private HashSet<String> publishers = new HashSet<>();
    private HashSet<String> subjects = new HashSet<>();
    private Double impactFactor;
    private Double impactFactor5Years;
    private String language;
    private String pubType;
    private long year;
    
    public Journal(){
        
    }
    
    public Journal(String issn){
        this.issn = issn;        
    }
    
    /**
     * @return the issn
     */
    public String getIssn() {
        return issn;
    }

    /**
     * @return the titles
     */
    public HashSet<String> getTitles() {
        return titles;
    }

    /**
     * @param titles the titles to set
     */
    public void setTitles(HashSet<String> titles) {
        this.titles = titles;
    }

    /**
     * @return the titlesAbrev
     */
    public HashSet<String> getTitlesAbrev() {
        return titlesAbrev;
    }

    /**
     * @param titlesAbrev the titlesAbrev to set
     */
    public void setTitlesAbrev(HashSet<String> titlesAbrev) {
        this.titlesAbrev = titlesAbrev;
    }

    /**
     * @return the publishers
     */
    public HashSet<String> getPublishers() {
        return publishers;
    }

    /**
     * @param publishers the publishers to set
     */
    public void setPublishers(HashSet<String> publishers) {
        this.publishers = publishers;
    }

    /**
     * @return the subjects
     */
    public HashSet<String> getSubjects() {
        return subjects;
    }

    /**
     * @param subjects the subjects to set
     */
    public void setSubjects(HashSet<String> subjects) {
        this.subjects = subjects;
    }

    /**
     * @return the impactFactor
     */
    public Double getImpactFactor() {
        return impactFactor;
    }

    /**
     * @param impactFactor the impactFactor to set
     */
    public void setImpactFactor(Double impactFactor) {
        this.impactFactor = impactFactor;
    }

    /**
     * @return the impactFactor5Years
     */
    public Double getImpactFactor5Years() {
        return impactFactor5Years;
    }

    /**
     * @param impactFactor5Years the impactFactor5Years to set
     */
    public void setImpactFactor5Years(Double impactFactor5Years) {
        this.impactFactor5Years = impactFactor5Years;
    }

    /**
     * @return the language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * @param language the language to set
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * @return the pubType
     */
    public String getPubType() {
        return pubType;
    }

    /**
     * @param pubType the pubType to set
     */
    public void setPubType(String pubType) {
        this.pubType = pubType;
    }
    
    /**
     * @return the year
     */
    public long getYear() {
        return year;
    }

    /**
     * @param year the year to set
     */
    public void setYear(long year) {
        this.year = year;
    }
    
    /**
     * @return the invalidIssns
     */
    /*public HashSet<String> getInvalidIssns() {
        return invalidIssns;
    }*/

    /**
     * @param invalidIssns the invalidIssns to set
     */
    /*public void setInvalidIssns(HashSet<String> invalidIssns) {
        this.invalidIssns = invalidIssns;
    }*/
    
    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        
        str.append(this.issn);
        str.append(" ");
        str.append(this.pubType);
        str.append(" ");
        str.append(this.year);
        str.append("\n");
        
        /*str.append("====================================\n");        
        if(!(this.invalidIssns == null)){
            for(String s: this.getInvalidIssns()){
                str.append(s);
                str.append("\n");
            }
        }*/
        
        str.append("====================================\n");
        if(!(this.titles == null)){
            for(String s: this.titles){
                str.append(s);
                str.append("\n");
            }
        }
        
        str.append("====================================\n");
        if(!(this.titlesAbrev == null)){
            for(String s: this.titlesAbrev){
                str.append(s);
                str.append("\n");
            }
        }
        
        str.append("====================================\n");
        if(!(this.publishers == null)){
            for(String s: this.publishers){
                str.append(s);
                str.append("\n");
            }
        }        
        
        str.append("====================================\n");
        if(!(this.subjects == null)){
            for(String s: this.subjects){
                str.append(s);
                str.append("\n");
            }
        }
        
        str.append("====================================\n");
        str.append(this.impactFactor);
        str.append("\n");
        str.append(this.impactFactor5Years);
        str.append("\n");
        str.append(this.language);
        str.append("\n\n");
        
        return str.toString();
    }    
}
