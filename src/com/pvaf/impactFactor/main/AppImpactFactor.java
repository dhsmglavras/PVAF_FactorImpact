/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pvaf.impactFactor.main;

import com.pvaf.impactFactor.dao.IssnDAO;
import com.pvaf.impactFactor.dao.JournalDAO;
import com.pvaf.impactFactor.entidades.Journal;
import com.pvaf.impactFactor.entidades.Publication;
import com.pvaf.impactFactor.exceptions.ErrorException;
import com.pvaf.impactFactor.exceptions.IssnException;
import com.pvaf.impactFactor.io.ReadImpactFactor;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author douglas
 */
public class AppImpactFactor {
    
    static {
        PropertyConfigurator.configure("properties/log4j.properties");
    }
    
    protected static ArrayList<Publication> addPublicationIne = new ArrayList<>();
    protected static ArrayList<Publication> addPublicationExi = new ArrayList<>();
    
    protected static LinkedHashSet<String> issnsIne = new LinkedHashSet<>(); // isnns não existentes no bd    
    protected static LinkedHashSet<String> issnsExi = new LinkedHashSet<>(); // isnns existentes no bd
    
    public static ArrayList<Publication> lerImpactFactor(String path) throws ErrorException{
        ReadImpactFactor read = new ReadImpactFactor(path);
        read.readImpactFactor();
        return read.getPublications();
    }
    
    public static void splitPublications(List<Publication> publications) throws ErrorException{
        
        for(Publication p: publications){
            boolean existe = false;
            
                        
            for(String issn: p.getIssn()){
                existe = IssnDAO.getIssn(issn);
                if(existe){
                    break;
                }
            
            }
            
            if((p.getImpactFactor()!=null)){
                if(!existe){
                    addPublicationIne.add(p);
                    issnsIne.addAll(p.getIssn());
                }else{
                    addPublicationExi.add(p);
                    issnsExi.addAll(p.getIssn());
                }
            }
        }
    }
    
    public static List<Journal> createListJournal(Set<String> issns){ //retornar
        List<Journal> listJournal = new ArrayList<>();
        
        for(String issn: issns){
            Journal j = new Journal(issn);
            listJournal.add(j);
        }
        
        System.out.println(issns.size());
        return listJournal;
    }
    
    public static void addAttributesImpactFactor(List<Journal> listJournal, List<Publication> listPublication){
        
        for (int i = 0; i < listJournal.size(); i++) {
            Journal journal = listJournal.get(i);
            HashSet<String> titles;
            HashSet<String> titlesAbrev;
            HashSet<String> publishers;
            HashSet<String> subjects;

            for (Publication pub : listPublication) {
                
                if(pub.getIssn().contains(journal.getIssn())){
                    titles = journal.getTitles();
                    titles.addAll(pub.getTitle());
                    journal.setTitles(titles);
                    
                    titlesAbrev =  journal.getTitlesAbrev();
                    titlesAbrev.addAll(pub.getTitleAbrev());
                    journal.setTitlesAbrev(titlesAbrev);
                    
                    publishers = journal.getPublishers();
                    publishers.addAll(pub.getPublisher());
                    journal.setPublishers(publishers);
                    
                    subjects =  journal.getSubjects();
                    subjects.addAll(pub.getSubject());
                    journal.setSubjects(subjects);
                    
                    if(journal.getImpactFactor()!=null){
                        if((journal.getImpactFactor()<pub.getImpactFactor())){
                            journal.setImpactFactor(pub.getImpactFactor());
                        }
                    }else{
                        journal.setImpactFactor(pub.getImpactFactor());
                    }
                    
                    journal.setImpactFactor(pub.getImpactFactor());
                    
                    journal.setImpactFactor5Years(pub.getImpactFactor5Years());
                    
                    journal.setLanguage(pub.getLanguage());
                    
                    journal.setPubType(pub.getPubType());
                    
                    if(journal.getPubType().equals("J")){
                        journal.setPubType("J");
                    }else{
                        journal.setPubType(pub.getPubType());
                    }
                    
                    journal.setYear(pub.getYear());
                }
            }
        }
    }
    
    public static void deleteFile(String path){
        File file = new File(path);
        file.delete();
    }
    
    public static boolean fileExists(String path){
        File file = new File(path);
        return file.exists();
    }
    
    public static void main(String[] args) {
        
        try {
            System.out.println("Opções");
            System.out.println("1 journal Impact Factor");
            System.out.println("2 registro de erros");
            
            Scanner ler = new Scanner(System.in);
            int op = ler.nextInt();
            
            switch (op) {
                
                case 1:
                    
                    AppImpactFactor.deleteFile("registroErro.xml");
                    
                    //ArrayList<Publication> publications =  AppImpactFactor.lerImpactFactor("ISIout.xml");
                    ArrayList<Publication> publications =  AppImpactFactor.lerImpactFactor("ISI1out.xml");
                    
                    AppImpactFactor.splitPublications(publications);
                    
                    publications.clear();
                    
                    List<Journal> listJournalExi = AppImpactFactor.createListJournal(issnsExi);
                    
                    AppImpactFactor.addAttributesImpactFactor(listJournalExi, addPublicationExi);
                    
                    issnsExi.clear();
                    addPublicationExi.clear();
                    
                    List<Journal> listJournalIne = AppImpactFactor.createListJournal(issnsIne);
                    
                    AppImpactFactor.addAttributesImpactFactor(listJournalIne, addPublicationIne);
                    
                    issnsIne.clear();
                    addPublicationIne.clear();
                    
                    for(Journal j: listJournalExi){
                        JournalDAO.update(j);
                    }
                    
                    listJournalExi.clear();
                    
                    for(Journal j: listJournalIne){
                        JournalDAO.insert(j);
                    }
                    
                    listJournalIne.clear();
                    
                    break;
                    
                case 2:
                    
                    publications = AppImpactFactor.lerImpactFactor("registroErro.xml");
                    
                    AppImpactFactor.splitPublications(publications);
                    
                    publications.clear();
                    
                    listJournalExi = AppImpactFactor.createListJournal(issnsExi);
                    
                    AppImpactFactor.addAttributesImpactFactor(listJournalExi, addPublicationExi);
                    
                    issnsExi.clear();
                    addPublicationExi.clear();
                    
                    listJournalIne = AppImpactFactor.createListJournal(issnsIne);
                    
                    AppImpactFactor.addAttributesImpactFactor(listJournalIne, addPublicationIne);
                    
                    issnsIne.clear();
                    addPublicationIne.clear();
                    
                    for (Journal j : listJournalExi) {
                        JournalDAO.update(j);
                    }
                    
                    listJournalExi.clear();
                    
                    for (Journal j : listJournalIne) {
                        JournalDAO.insert(j);
                    }
                    
                    listJournalIne.clear();
                    
                    break;
                    
                default:;
            }
            
            if (AppImpactFactor.fileExists("registroErro.xml")) {
                try {
                    throw new IssnException();
                } catch (IssnException e) {
                    System.err.println(e.getMessage());
                }
            }
            
        } catch (ErrorException ex) {
            System.err.println(ex.getMessage());
        }
                
    }    
}

