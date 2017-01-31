/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pvaf.impactFactor.exceptions;

import com.pvaf.impactFactor.entidades.Publication;
import com.pvaf.impactFactor.util.Files;
import com.pvaf.impactFactor.util.Strings;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author marte
 */
public class IssnException extends Exception{
    
    private static int  autoIncremento = 0;
    private final int numero;
    private final String year;
    private String name;
    private int contador = 0;
    private Publication pub;
    private File file;
    private FileWriter out;
    private PrintWriter writer;
    private ArrayList<Publication> publications;
    
    public IssnException(Publication pub, String nome,String year){
        
        this.numero = autoIncremento++;
        this.pub = pub;
        this.name = nome;
        this.year = year;
        this.pub = pub;
        this.publications = new ArrayList<>();
        this.pub.setId(numero);
    }
    
    public void log(){
        try {
            File file = new File("registroErro.xml");
            if (file.exists()) {
                load("registroErro.xml");
            }
            this.publications.add(pub);
            save("registroErro.xml");
        } catch (IOException ex) {
            Logger.getLogger(IssnException.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void save(String fileName) {
        try {
            FileWriter fw = new FileWriter(fileName, false);

            fw.write("<publication-venues>\n");
            fw.write("   <from>" + this.name + "</from>\n");
            fw.write("   <year>" + this.year + "</year>\n");

            Iterator<Publication> i = publications.iterator();
            
            contador = 0;
            while (i.hasNext()) {
                
                this.pub = i.next();
                this.pub.setId(contador);
                if (pub.append(fw)) {
                    ++contador;
                }

            }
            fw.write("</publication-venues>");
            fw.close();
        } catch (IOException e) {
            System.out.println("Erro no método SaveFile da classe PublicationVenue.");
        }
    }
    
    public void load(String arqName) throws FileNotFoundException, IOException {
        
        String cod = Files.getContentFile(arqName);
        String[] sp = cod.split("\n");
        this.name = sp[1].replaceFirst("   <from>", "").replaceFirst("</from>", "");
        this.contador = 0;
        for (int i = 2; i < sp.length - 1; ++i) {
            if (sp[i].equals("   <pub-venue>")) {
                Publication pub = new Publication();
                int anti = i;
                ++i;

                while (!sp[i].equals("   </pub-venue>")) {
                    //System.out.println(sp[i]);
                    if (anti == i) {
                        throw new IllegalArgumentException("\nO arquivo " + arqName + " \nnão está no formato do parse PUblicationVenue\nErro linha: " + (i + 1) + "\nvalor: " + sp[i]);
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
                        String str = Strings.getPattern(sp[i], ">[^<]+<").replaceAll("[<>\n]", "").replace("   ", ";");
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
                this.publications.add(pub);
            }
        }
    }
    
    public ArrayList<Publication> getPublications() {
        return publications;
    }
    
    public boolean fileExist(){
        File file = new File("registroErro.xml");
        if(this.numero==0){
            return false;
        }
        return true;        
    }
        
    @Override
    public String getMessage(){
	return "ISSN inválido, verifique no arquivo: registroErro.xml";
    }
}
