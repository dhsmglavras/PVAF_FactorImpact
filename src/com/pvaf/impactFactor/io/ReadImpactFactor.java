/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pvaf.impactFactor.io;

import com.pvaf.impactFactor.entidades.Publication;
import com.pvaf.impactFactor.exceptions.IssnException;
import com.pvaf.impactFactor.util.Files;
import com.pvaf.impactFactor.util.Strings;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author douglas
 */
public class ReadImpactFactor {
    
    private String name;
    private String year;
    private final String path;
    private final ArrayList<Publication> publications;
        
    public ReadImpactFactor(String path) {
        this.name = "";
        this.year = "";
        this.path = path;
        this.publications = new ArrayList<>();
    }

    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }
    
    /**
     * @return the publications
     */
    public ArrayList<Publication> getPublications() {
        return publications;
    }
    
    /**
     * @return the year
     */
    public String getYear() {
        return year;
    }
    
    public static boolean validateIssn(String issn){
        if(issn.matches("([0-9]\\d{3}-[0-9]\\d{2}X)") || issn.matches("([0-9]\\d{3}-[0-9]\\d{3})") ||
                issn.matches("([0-9]\\d{3}-[0-9]\\d{2}x)")){       
            return true;
        }            
        return false;
    }
    
    public void readImpactFactor( ) throws FileNotFoundException, IOException {
        
        String cod = Files.getContentFile(this.path);
        String[] sp = cod.split("\n");
        this.name = sp[1].replaceFirst("   <from>", "").replaceFirst("</from>", "");
        this.year = sp[2].replaceFirst("   <year>", "").replaceFirst("</year>", "");
        
        //System.out.println(this.year);
        for (int i = 3; i < sp.length - 1; ++i) {
            if (sp[i].equals("   <pub-venue>")) {
                Publication pub = new Publication();
                int anti = i;
                ++i;
                
                pub.setYear(Long.parseLong(year));
                
                while (!sp[i].equals("   </pub-venue>")) {

                    if (anti == i ) {
                        throw new IllegalArgumentException("\nO arquivo " + this.path + " \nnão está no formato do parse PUblicationVenue\nErro linha: " + (i + 1) + "\nvalor: " + sp[i]);
                    }
                    anti = i;
                    
                    if (sp[i].startsWith("      <id>")) {
                        i++;
                        continue;
                    }
                    
                    if (sp[i].startsWith("      <idClass>")) {
                        i++;
                        continue;
                    }
                                        
                    if (sp[i].startsWith("      <issn>")) {
                        pub.addIssn(Strings.getPattern(sp[i], ">[^<]+<").replaceAll("[<>\n]", ""));
                        i++;
                        continue;
                    }
                    
                    if (sp[i].startsWith("      <title>")) {
                        pub.addPubVenueTitle(Strings.getPattern(sp[i], ">[^<]+<").replaceAll("[<>\n]", ""));
                        i++;
                        continue;
                    }
                
                    if (sp[i].startsWith("      <title-abrev>")) {
                        pub.addTitleAbrev(Strings.getPattern(sp[i], ">[^<]+<").replaceAll("[<>\n]", ""));
                        i++;
                        continue;
                    }
                    
                                    
                    if (sp[i].startsWith("      <publisher>")) {
                        pub.addPublisher(Strings.getPattern(sp[i], ">[^<]+<").replaceAll("[<>\n]", ""));
                        i++;
                        continue;
                    }
                    
                    if (sp[i].startsWith("      <pub-type>")) {
                        pub.setPubType(Strings.getPattern(sp[i], ">[^<]+<").replaceAll("[<>\n]", ""));
                        i++;
                        continue;
                    }
                    
                    if (sp[i].startsWith("      <language>")) {
                        pub.setLanguage(Strings.getPattern(sp[i], ">[^<]+<").replaceAll("[<>\n]", ""));
                        i++;
                        continue;
                    }
                    
                    if (sp[i].startsWith("      <subject>")) {
                        String str = Strings.getPattern(sp[i], ">[^<]+<").replaceAll("[<>\n]", "").replace("   ",";");
                        //System.out.println(str);
                        pub.addSubject(str);
                        i++;
                        continue;
                    }
                    
                    if (sp[i].startsWith("      <impact-factor>")) {
                        pub.setImpactFactor(Double.parseDouble(Strings.getPattern(sp[i], ">[^<]+<").replaceAll("[<>\n]", "")));
                        i++;
                        continue;
                    }
                
                    if (sp[i].startsWith("      <impact-factor-5-years>")) {
                        pub.setImpactFactor5Years(Double.parseDouble(Strings.getPattern(sp[i], ">[^<]+<").replaceAll("[<>\n]", "")));
                        i++;
                        continue;
                    }
                }
                
                boolean adicione = false;
                for(String issn: pub.getIssn()){
                    if(validateIssn(issn)){
                        adicione = true;
                    }else{
                        
                        try {
                            throw new IssnException(pub,this.name,this.year);
                        } catch (IssnException e) {
                            if (!e.fileExist(this.name)) {
                                System.err.println(e.getMessage());
                            }
                            e.log();
                        }
                        
                        adicione = false;
                    }
                }
                
                if(adicione){
                    this.publications.add(pub);
                }
            }
        }
    }
}
