/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pvaf.impactFactor.dao;

import com.pvaf.impactFactor.entidades.TitleAbrev;
import com.pvaf.impactFactor.exceptions.ErrorException;
import com.pvaf.impactFactor.service.DBLocator;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author douglas
 */
public class TitleAbrevDAO {
    
    private final static Logger log = Logger.getLogger(TitleAbrevDAO.class);  
    
    public static List<TitleAbrev> getTitlesAbrev(int idPubVenue) throws ErrorException{
        List<TitleAbrev> listT = new ArrayList<>();
        int i=1;
        
        try(Connection conn = DBLocator.getConnection(); 
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM title_abrev WHERE id_pub_venue = ?")){
            ps.setInt(i++,idPubVenue);
            
            ResultSet rs = ps.executeQuery();
            TitleAbrev titleAbrev;
            while(rs.next()){
                titleAbrev = new TitleAbrev(rs.getInt("id_pub_venue"),rs.getString("title_abrev"));
                listT.add(titleAbrev);
            }
            rs.close();
            
	}catch(SQLException e){
            log.error("Ocorreu uma exceção de SQL.", e.fillInStackTrace());
            throw new ErrorException("Ocorreu um Erro Interno");
	}
	return listT;
    }
    
    public static Integer checkIdPubVenue(Integer idPubVenue, String journalTitleAbrev) throws ErrorException{
        
        int idPubVenueAux = 0;
        try(Connection conn = DBLocator.getConnection()){ 
            
            PreparedStatement ps = conn.prepareStatement("SELECT id_pub_venue FROM title_abrev WHERE id_pub_venue = ? AND title_abrev = ?");
            
            int i = 1;
            ps.setInt(i++, idPubVenue);
            ps.setString(i++, journalTitleAbrev);
            
            ResultSet titleAbrev = ps.executeQuery();
            if (titleAbrev.first()) {
                idPubVenueAux = titleAbrev.getInt("id_pub_venue");
            }
            titleAbrev.close();
            ps.close();
            
        } catch (SQLException e) {
            log.error("Ocorreu uma exceção de SQL.", e.fillInStackTrace());
            throw new ErrorException("Ocorreu um Erro Interno");
        }
        
        return idPubVenueAux;
    }
}
