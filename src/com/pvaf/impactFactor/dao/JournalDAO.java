/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pvaf.impactFactor.dao;

import com.pvaf.impactFactor.entidades.Journal;
import com.pvaf.impactFactor.service.DBLocator;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author douglas
 */
public class JournalDAO {
    
    private static int cont =1;
    
    public static void insert(Journal journal){
        Connection conn = null;
        try{
            int i=1;
            conn = DBLocator.getConnection();
            
            int[] idPubVenues;
            idPubVenues = new int[journal.getPublishers().size()];
            
            // Inserir
            int j=0;
            PreparedStatement ps;
            for(String publisher: journal.getPublishers()){
                ps = conn.prepareStatement("INSERT INTO publicationvenue (pub_type,publisher) VALUES (?,?)");
                ps.setString(i++,String.valueOf(journal.getPubType()));
                ps.setString(i++,publisher);
                ps.executeUpdate();
                ps.close();
                
                //Selecionar
                ps = conn.prepareStatement("SELECT id_pub_venue FROM publicationvenue ORDER BY id_pub_venue");
                try (ResultSet publicationVenue = ps.executeQuery()) {
                    if(publicationVenue.last()){
                        idPubVenues[j] = publicationVenue.getInt("id_pub_venue");
                        j++;
                    }
                }            
                ps.close();
            }
            
            // Inserir issn
            for(int k=0; k<idPubVenues.length; k++){
                i=1;
                ps = conn.prepareStatement("INSERT INTO issn (id_pub_venue,issn,`print/online`) VALUES (?,?,?)");
                ps.setInt(i++,idPubVenues[k]);
                ps.setString(i++, journal.getIssn());
                ps.setNull(i++, java.sql.Types.INTEGER);
                ps.executeUpdate();
                ps.close();
            }
            
            // Inserir impact_factor
            for(int k=0; k<idPubVenues.length; k++){
                
                i=1;
                ps = conn.prepareStatement("INSERT INTO impact_factor (id_pub_venue,`year`,`if_last_5_years`,`if`) VALUES (?,?,?,?)");
                ps.setInt(i++,idPubVenues[k]);
                ps.setBigDecimal(i++, BigDecimal.valueOf(journal.getYear()));
                
                if(journal.getImpactFactor5Years()!=null){
                    ps.setBigDecimal(i++, BigDecimal.valueOf(journal.getImpactFactor5Years()));
                }else{
                    ps.setNull(i++, java.sql.Types.INTEGER);
                }
                
                ps.setBigDecimal(i++, BigDecimal.valueOf(journal.getImpactFactor()));
                
                ps.executeUpdate();
                ps.close();
            }
            
            // Inserir title_abrev
            for(int k=0; k<idPubVenues.length; k++){
                
                for(String t: journal.getTitlesAbrev()){
                    i=1;
                    ps = conn.prepareStatement("INSERT INTO title_abrev (id_pub_venue,title_abrev) VALUES (?,?)");
                    ps.setInt(i++,idPubVenues[k]);
                    byte array[] = t.getBytes("UTF-8");
                    t = new String(array, "UTF-8");
                    ps.setString(i++, t);
                    ps.executeUpdate();
                    ps.close();
                }
            }
            
            // Inserir subject
            for(int k=0; k<idPubVenues.length; k++){
                for(String subjects: journal.getSubjects()){
                    i=1;
                    ps = conn.prepareStatement("INSERT INTO subject (id_pub_venue,subject) VALUES (?,?)");
                    ps.setInt(i++,idPubVenues[k]);
                    ps.setString(i++, subjects);
                    ps.executeUpdate();
                    ps.close();
                }
            }
            
            // Inserir language
            for(int k=0; k<idPubVenues.length; k++){
                i=1;
                ps = conn.prepareStatement("INSERT INTO language (id_pub_venue,language) VALUES (?,?)");
                ps.setInt(i++,idPubVenues[k]);
                ps.setString(i++, journal.getLanguage());
                ps.executeUpdate();
                ps.close();
            }
            
            // Inserir title
            for(int k=0; k<idPubVenues.length; k++){
                
                for(String t: journal.getTitles()){
                    i=1;
                    ps = conn.prepareStatement("INSERT INTO title (id_pub_venue,title) VALUES (?,?)");
                    ps.setInt(i++,idPubVenues[k]);
                    byte array[] = t.getBytes("UTF-8");
                    t = new String(array, "UTF-8");
                    ps.setString(i++, t);
                    ps.executeUpdate();
                    ps.close();
                }
            }
            
            conn.commit();
        }catch(SQLException e){
            System.err.println( "Ocorreu uma exceção de SQL. Causa: " + e.getMessage() );
            if(conn !=null){
		try{
                    conn.rollback();
		}catch(SQLException e1){
                    System.err.println( "Exceção ao realizar rollback. Causa: " + e1.getMessage() );
		}
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(JournalDAO.class.getName()).log(Level.SEVERE, null, ex);
            ex.getMessage();
        }finally{
            if(conn !=null){
		try{
                    conn.close();
		}catch(SQLException e){
                    System.err.println( "Exceção ao fechar a conexão. Causa: " + e.getMessage() );
                    //e.printStackTrace();
		}
            }
	}
    }
    
    public static void update(Journal journal){
        Connection conn = null;
        
        try{
            conn = DBLocator.getConnection();
            int i=1;
            
            PreparedStatement ps = conn.prepareStatement("SELECT id_pub_venue FROM issn WHERE issn = ?");
            ps.setString(i++,journal.getIssn());
            
            int idPubVenue = 0;
            
            ResultSet issn = ps.executeQuery();
            if(issn.first()){
                idPubVenue = issn.getInt("id_pub_venue");
            }
            issn.close();
            ps.close();
            
            // Impact Factor
            i=1;
            ps = conn.prepareStatement("SELECT id_pub_venue FROM impact_factor WHERE id_pub_venue = ? AND `year` = ?");
            ps.setInt(i++, idPubVenue);
            ps.setBigDecimal(i++, BigDecimal.valueOf(journal.getYear()));
                        
            ResultSet impactFactor = ps.executeQuery();
            int idPubVenueAux = 0;
            if(impactFactor.first()){
                idPubVenueAux = impactFactor.getInt("id_pub_venue");
            }
            impactFactor.close();
            ps.close();
            
            if(idPubVenue != idPubVenueAux){
                
                i = 1;
                ps = conn.prepareStatement("INSERT INTO impact_factor (id_pub_venue, `year`, `if_last_5_years`, `if`) VALUES (?,?,?,?)");
                ps.setInt(i++, idPubVenue);
                ps.setBigDecimal(i++, BigDecimal.valueOf(journal.getYear()));
                if (journal.getImpactFactor5Years() != null) {
                    ps.setBigDecimal(i++, BigDecimal.valueOf(journal.getImpactFactor5Years()));
                } else {
                    ps.setNull(i++, java.sql.Types.INTEGER);
                }
                ps.setBigDecimal(i++, BigDecimal.valueOf(journal.getImpactFactor()));
                ps.executeUpdate();
                ps.close();
            }
            
            // Title Abrev
            for (String journalTitleAbrev : journal.getTitlesAbrev()) {
                byte array[] = journalTitleAbrev.getBytes("UTF-8");
                journalTitleAbrev = new String(array, "UTF-8");
                idPubVenueAux = 0;
                idPubVenueAux = TitleAbrevDAO.checkIdPubVenue(idPubVenue, journalTitleAbrev);

                if (idPubVenue != idPubVenueAux) {
                    i=1;
                    ps = conn.prepareStatement("INSERT INTO title_abrev (id_pub_venue, title_abrev) VALUES (?,?)");
                    ps.setInt(i++,idPubVenue);                    
                    ps.setString(i++, journalTitleAbrev);
                    ps.executeUpdate();
                    ps.close();
                }
            }
            
            // Title
            for (String journalTitle : journal.getTitles()) {
                byte array[] = journalTitle.getBytes("UTF-8");
                journalTitle = new String(array, "UTF-8");
                
                idPubVenueAux = 0;
                idPubVenueAux = TitleDAO.checkIdPubVenue(idPubVenue, journalTitle);
                
                if (idPubVenue != idPubVenueAux) {
                    
                    i=1;
                    ps = conn.prepareStatement("INSERT INTO title (id_pub_venue, title) VALUES (?,?)");
                    ps.setInt(i++,idPubVenue);                    
                    ps.setString(i++, journalTitle);
                    ps.executeUpdate();
                    ps.close();
                    conn.commit();
                }
            }
            
            conn.commit();
        }catch(SQLException e){
            System.err.println( "Ocorreu uma exceção de SQL. Causa: " + e.getMessage());            
            if(conn !=null){
		try{
                    conn.rollback();
		}catch(SQLException e1){
                    System.err.println( "Exceção ao realizar rollback. Causa: " + e1.getMessage() );
		}
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(JournalDAO.class.getName()).log(Level.SEVERE, null, ex);
            ex.getMessage();
        }finally{
            if(conn !=null){
		try{
                    conn.close();
		}catch(SQLException e){
                    System.err.println( "Exceção ao fechar a conexão. Causa: " + e.getMessage() );
                    //e.printStackTrace();
		}
            }
	}
    }
}
