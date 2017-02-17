/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pvaf.impactFactor.dao;

import com.pvaf.impactFactor.service.DBLocator;
import com.pvaf.impactFactor.entidades.Title;
import com.pvaf.impactFactor.exceptions.ErrorException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author marte
 */
public class TitleDAO {
    
    private final static Logger log = Logger.getLogger(TitleDAO.class);
    
    public static List<Title> getTitles(int idPubVenue) throws ErrorException{
        List<Title> listT = new ArrayList<>();
        int i=1;
        
        try(Connection conn = DBLocator.getConnection(); 
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM title WHERE id_pub_venue = ?")){
            ps.setInt(i++,idPubVenue);
            
            try (ResultSet rs = ps.executeQuery()) {
                Title title;
                while(rs.next()){
                    title = new Title(rs.getInt("id_pub_venue"),rs.getString("title"));
                    listT.add(title);
                }
            }
            
	}catch(SQLException e){
            log.error("Ocorreu uma exceção de SQL.", e.fillInStackTrace());
            throw new ErrorException("Ocorreu um Erro Interno");
	}
	return listT;
    }    
    
    public static Integer checkIdPubVenue(Integer idPubVenue, String journalTitle) throws ErrorException{
        
        int idPubVenueAux = 0;
        try(Connection conn = DBLocator.getConnection()){ 
            
            PreparedStatement ps = conn.prepareStatement("SELECT id_pub_venue FROM title WHERE id_pub_venue = ? AND title = ?");
            
            int i = 1;
            ps.setInt(i++, idPubVenue);
            ps.setString(i++, journalTitle);
            
            ResultSet title = ps.executeQuery();
            if (title.first()) {
                idPubVenueAux = title.getInt("id_pub_venue");
            }
            title.close();
            ps.close();
            
        }catch(SQLException e){
            log.error("Ocorreu uma exceção de SQL.", e.fillInStackTrace());
            throw new ErrorException("Ocorreu um Erro Interno");
	}
        
        return idPubVenueAux;
    }
}
