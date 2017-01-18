/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pvaf.impactFactor.entidades;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.LinkedHashSet;

/**
 *
 * @author douglas
 */
public class Publication {
    
    private LinkedHashSet<String> issn;
    private LinkedHashSet<String> title;
    private LinkedHashSet<String> titleAbrev;
    private LinkedHashSet<String> publisher;
    private LinkedHashSet<String> subject;
    private Double impactFactor;
    private Double impactFactor5Years;
    private String language;
    private String pubType;
    private final ArrayList<Publication> publications;
    private int id;
    private long year;
    
    public Publication(){
        this.issn =  new LinkedHashSet<>();
        this.title = new LinkedHashSet<>();
        this.titleAbrev = new LinkedHashSet<>();
        this.publisher =  new LinkedHashSet<>();
        this.subject =  new LinkedHashSet<>();
        this.publications =  new ArrayList<>();
        this.impactFactor = null;
        this.impactFactor5Years = null;
        this.language =  null;
        this.pubType =  null;
        this.id = 0;
        this.year = 0;
    }

    /**
     * @return the issn
     */
    public LinkedHashSet<String> getIssn() {
        return issn;
    }

    /**
     * @param issn the issn to set
     */
    public void setIssn(LinkedHashSet<String> issn) {
        this.issn = issn;
    }

    /**
     * @return the title
     */
    public LinkedHashSet<String> getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(LinkedHashSet<String> title) {
        this.title = title;
    }

    /**
     * @return the titleAbrev
     */
    public LinkedHashSet<String> getTitleAbrev() {
        return titleAbrev;
    }

    /**
     * @param titleAbrev the titleAbrev to set
     */
    public void setTitleAbrev(LinkedHashSet<String> titleAbrev) {
        this.titleAbrev = titleAbrev;
    }

    /**
     * @return the publisher
     */
    public LinkedHashSet<String> getPublisher() {
        return publisher;
    }

    /**
     * @param publisher the publisher to set
     */
    public void setPublisher(LinkedHashSet<String> publisher) {
        this.publisher = publisher;
    }

    /**
     * @return the subject
     */
    public LinkedHashSet<String> getSubject() {
        return subject;
    }

    /**
     * @param subject the subject to set
     */
    public void setSubject(LinkedHashSet<String> subject) {
        this.subject = subject;
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
        boolean workshop = false;
        boolean magazine = false;
        for (String s : title) {
            if (s.toLowerCase().indexOf("workshop") != -1) {
                workshop = true;
                break;
            }
        }
        for (String s : title) {
            if (s.toLowerCase().indexOf("magazine") != -1) {
                magazine = true;
                break;
            }
        }
        if (workshop) {
            this.pubType = "W";
        } else {
            if (magazine) {
                this.pubType = "M";
            } else {
                this.pubType = pubType;
            }
        }
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
    
    public void addIssn(String issn) {
        this.issn.add(issn);
    }
    
    public void addPubVenueTitle(String s) {
        for (String s1 : title) {
            if (s1.replaceAll("\\p{Punct}", " ").replaceAll(" +", " ").equalsIgnoreCase(s.replaceAll("\\p{Punct}", " ").replaceAll(" +", " "))) {
                return;
            }
        }
        for (String s1 : titleAbrev) {
            if (s1.replaceAll("\\p{Punct}", " ").replaceAll(" +", " ").equalsIgnoreCase(s.replaceAll("\\p{Punct}", " ").replaceAll(" +", " "))) {
                return;
            }
        }
        this.title.add(s);
    }
    
    public void addTitleAbrev(String s) {
        for (String s1 : titleAbrev) {
            if (s1.replaceAll("\\p{Punct}", " ").replaceAll(" +", " ").equalsIgnoreCase(s.replaceAll("\\p{Punct}", " ").replaceAll(" +", " "))) {
                return;
            }
        }
        for (String s1 : title) {
            if (s1.replaceAll("\\p{Punct}", " ").replaceAll(" +", " ").equalsIgnoreCase(s.replaceAll("\\p{Punct}", " ").replaceAll(" +", " "))) {
                return;
            }
        }
        this.titleAbrev.add(s);
    }
    
    public void addPublisher(String[] str) {
        for (String s : str) {
            this.addPublisher(s);
        }
    }

    public void addPublisher(String s) {
        for (String s1 : publisher) {
            if (s1.replaceAll("\\p{Punct}", " ").replaceAll(" +", " ").equalsIgnoreCase(s.replaceAll("\\p{Punct}", " ").replaceAll(" +", " "))) {
                return;
            }
        }
        this.publisher.add(s);
    }
    
    public void addSubject(String[] str) {
        for (String s : str) {
            this.addSubject(s);
        }
    }

    public void addSubject(String s) {
        for (String s1 : subject) {
            if (s1.replaceAll("\\p{Punct}", " ").replaceAll(" +", " ").equalsIgnoreCase(s.replaceAll("\\p{Punct}", " ").replaceAll(" +", " "))) {
                return;
            }
        }
        this.subject.add(s);
    }
    
    @Override
    public String toString() {
        String content = "Publication{";
        
        if (subject != null && !subject.isEmpty()) {
            content += " \nsubject=" + subject;
        }
        
        if (publisher != null && !publisher.isEmpty()) {
            content += " \npublisher=" + publisher;
        }
        
        if (titleAbrev != null && !titleAbrev.isEmpty()) {
            content += " \ntitleAbrev=" + titleAbrev;
        }
        
        if (title != null && !title.isEmpty()) {
            content += " \ntitle=" + title;
        }
        
        if (impactFactor != null /*&& !impactFactor.isEmpty()*/) {
            content += " \nimpactFactor=" + impactFactor;
        }
        
        if (impactFactor5Years != null /*&& !impactFactor5Years.isEmpty()*/) {
            content += " \nimpactFactor5Years=" + impactFactor5Years;
        }
        
        if (issn != null && !issn.isEmpty()) {
            content += " \nissn=" + issn;
        }
        
        if (language != null && !language.isEmpty()) {
            content += " \nlanguage=" + language;
        }
        
        if (getPubType() != null && !pubType.isEmpty()) {
            content += " \npubType=" + getPubType();
        }
        
        content += "}";
        return content;
    }

    public void addPublication(Publication publication) {
        this.publications.add(publication);
    }

    public void setId(int contador) {
        this.id =contador;
    }
    
    public boolean append(FileWriter fw) {
        String content = "";
        int cont = 0;
        boolean salva = false;
        content += "   <pub-venue>\n";
        content += "      <id>" + id + "</id>\n";
        content += "      <idClass>" + 0 + "</idClass>\n";
        
        if (issn != null) {
            ++cont;
            Iterator<String> i = issn.iterator();
            String str;
            while (i.hasNext()) {
                str = i.next();
                if (str.length() != 0) {
                    content += "      <issn>" + str.replaceAll(" +", " ").replaceAll("^ +", "").replaceAll(" +$", "") + "</issn>\n";
                }
            }
        }
        
        if (title != null) {
            ++cont;
            Iterator<String> i = title.iterator();
            String str;
            while (i.hasNext()) {
                str = i.next();
                if (str.length() != 0) {
                    salva = true;
                    content += "      <title>" + str.replaceAll(" +", " ").replaceAll("^ +", "").replaceAll(" +$", "") + "</title>\n";
                }
            }
        }
        
        if (titleAbrev != null) {
            ++cont;
            Iterator<String> i = titleAbrev.iterator();
            String str;
            while (i.hasNext()) {
                str = i.next();
                if (str.length() != 0) {
                    content += "      <title-abrev>" + str.replaceAll(" +", " ").replaceAll("^ +", "").replaceAll(" +$", "") + "</title-abrev>\n";
                }
            }
        }
                
        if (publisher != null) {
            ++cont;
            Iterator<String> i = publisher.iterator();
            String str;
            while (i.hasNext()) {
                str = i.next();
                if (str.length() != 0) {
                    content += "      <publisher>" + str.replaceAll(" +", " ").replaceAll("^ +", "").replaceAll(" +$", "") + "</publisher>\n";
                }
            }
        }
        
        if ((pubType != null) && (pubType.length() != 0)) {
            ++cont;
            content += "      <pub-type>" + pubType + "</pub-type>\n";
        }
        
        if ((language != null) && (language.length() != 0)) {
            ++cont;
            content += "      <language>" + language + "</language>\n";
        }
        
        if (subject != null) {
            ++cont;
            Iterator<String> i = subject.iterator();
            String str;
            while (i.hasNext()) {
                str = i.next();
                if (str.length() != 0) {
                    content += "      <subject>" + str.replaceAll(" +", " ").replaceAll("^ +", "").replaceAll(" +$", "") + "</subject>\n";
                }
            }
        }
        
        if ((impactFactor != null) /*&& (impactFactor.length() != 0)*/) {
            ++cont;
            content += "      <impact-factor>" + impactFactor + "</impact-factor>\n";
        }
        
        if ((impactFactor5Years != null) /*&& (impactFactor5Years.length() != 0)*/) {
            ++cont;
            content += "      <impact-factor-5-years>" + impactFactor5Years + "</impact-factor-5-years>\n";
        }
        
        
        content += "   </pub-venue>\n";
        try {
            // adiciona somente se houver mais de tres atributos para o pubvenue
            if (salva) {
                fw.write(content);
                return true;
            } else {
                return false;
            }
        } catch (IOException ex) {
            System.out.println("Erro:\nClass Publication\nMetodo append.");
            Logger.getLogger(Publication.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
