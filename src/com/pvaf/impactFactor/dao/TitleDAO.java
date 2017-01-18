/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pvaf.impactFactor.dao;

import com.pvaf.impactFactor.service.DBLocator;
import com.pvaf.impactFactor.entidades.Title;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author marte
 */
public class TitleDAO {
    
    public static List<Title> getTitles(int idPubVenue){
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
            System.err.println("Ocorreu uma exceção de SQL. Causa: " + e.getMessage());
	}
	return listT;
    }    
    
    public static Integer checkIdPubVenue(Integer idPubVenue, String journalTitle){
        
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
            System.err.println("Ocorreu uma exceção de SQL. Causa: " + e.getMessage());
	}
        
        return idPubVenueAux;
    }
}
