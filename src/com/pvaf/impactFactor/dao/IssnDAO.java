/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pvaf.impactFactor.dao;

import com.pvaf.impactFactor.service.DBLocator;
import com.pvaf.impactFactor.entidades.Issn;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author marte
 */
public class IssnDAO {
    public static HashSet<String> issnDB() {
        HashSet<String> issns = new HashSet<>();
        Connection conn = null;
        
        try{
            conn = DBLocator.getConnection();         
            Statement statement = conn.createStatement();
            
            ResultSet publicationvenue = statement.executeQuery("select * from publicationvenue");
            ResultSet issn;
            int idPubVenue;
            
            publicationvenue.first();
            
            do{
                // In the past the id starts at 0, but now at 1. So the less 1 in the calculation is for compatibility.
                idPubVenue = publicationvenue.getInt("id_pub_venue");
                //publication.setId(idPubVenue - 1);               
                        
                String pub_type = publicationvenue.getString("pub_type");
                
                if (pub_type.equals("J") || pub_type.equals("M")){
                    try{
                        statement = conn.createStatement();
                        issn = statement.executeQuery("select * from issn where id_pub_venue = " + idPubVenue + " order by issn");
                        
                        while(issn.next()){
                            issns.add(issn.getString("issn"));
                        }
                        
                        statement.close();
                    } catch(SQLException sePublication){
                        System.out.println( "Excessão ao fechar a conexão. Causa: " + sePublication.getMessage());
                    }
                }
                
            }while(publicationvenue.next());
        }catch(SQLException e){
            System.out.println( "Ocorreu uma excessão de SQL. Causa: " + e.getMessage() );
	}finally{
            if(conn !=null){
		try{
                    conn.close();
		}catch(SQLException e){
                    System.out.println( "Excessão ao fechar a conexão. Causa: " + e.getMessage() );
		}
            }
	}
        return issns;
    }
    
    public static List<Issn> getAllIssn() {
        List<Issn> issns = new ArrayList<>();
        Connection conn = null;
        
        try{
            conn = DBLocator.getConnection();         
            Statement statement = conn.createStatement();
            
            ResultSet publicationvenue = statement.executeQuery("select * from publicationvenue");
            ResultSet issnRs;
            int idPubVenue;
            
            while(publicationvenue.next()){
                idPubVenue = publicationvenue.getInt("id_pub_venue");
                
                String pub_type = publicationvenue.getString("pub_type");
                
                if (pub_type.equals("J")|| pub_type.equals("M")){
                    try{
                        statement = conn.createStatement();
                        issnRs = statement.executeQuery("select * from issn where id_pub_venue = " + idPubVenue + " order by issn");
                        
                        Issn issn;
                        while(issnRs.next()){
                            issn = new Issn(issnRs.getInt("idPubvebue"),issnRs.getString("issn"),issnRs.getString("print/online"));
                            issns.add(issn);
                        }
                    } catch(SQLException sePublication){
                        System.out.println( "Excessão ao fechar a conexão. Causa: " + sePublication.getMessage() );
                        sePublication.printStackTrace();
                    }
                }
                
            }
        }catch(SQLException e){
            System.out.println( "Ocorreu uma excessão de SQL. Causa: " + e.getMessage() );            
	}finally{
            if(conn !=null){
		try{
                    conn.close();
		}catch(SQLException e){
                    System.out.println( "Excessão ao fechar a conexão. Causa: " + e.getMessage() );
                    e.printStackTrace();
		}
            }
	}
        return issns;
    }
    
    public static boolean getIssn(String issn){        
        int i=1;
        
        try(Connection conn = DBLocator.getConnection(); 
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM issn WHERE issn = ?")){
            ps.setString(i++,issn);
            
            try (ResultSet rs = ps.executeQuery()) {
                
                if(rs.first()){
                    return true;
                }  
            }
	}catch(SQLException e){
            System.err.println("Ocorreu uma excessão de SQL. Causa: " + e.getMessage() );
            
	}
        return false;
    }
}
