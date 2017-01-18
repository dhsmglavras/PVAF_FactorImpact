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
import com.pvaf.impactFactor.io.ReadImpactFactor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author douglas
 */
public class AppImpactFactor {
    
    protected static ArrayList<Publication> addPublicationIne = new ArrayList<>();
    protected static ArrayList<Publication> addPublicationExi = new ArrayList<>();
    
    protected static LinkedHashSet<String> issnsIne = new LinkedHashSet<>(); // isnns não existentes no bd    
    protected static LinkedHashSet<String> issnsExi = new LinkedHashSet<>(); // isnns existentes no bd
    
    public static ArrayList<Publication> lerImpactFactor(String path){
        ReadImpactFactor read = new ReadImpactFactor(path);
        
        try {
            read.readImpactFactor();
        } catch (IOException ex) {  
            System.err.println(ex.getMessage());
        }
        return read.getPublications();
    }
    
    public static void splitPublications(List<Publication> publications){
        
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
    
    public static void main(String[] args) {
        
        ArrayList<Publication> publications =  AppImpactFactor.lerImpactFactor("ISIout.xml");
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
    }    
}

