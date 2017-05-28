package it.polito.tdp.porto.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.porto.model.Author;
import it.polito.tdp.porto.model.Collegamento;
import it.polito.tdp.porto.model.Paper;

public class PortoDAO {

	/*
	 * Dato l'id ottengo l'autore.
	 */
	public Author getAutore(int id) {

		final String sql = "SELECT * FROM author where id=?";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, id);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {

				Author autore = new Author(rs.getInt("id"), rs.getString("lastname"), rs.getString("firstname"));
				return autore;
			}

			return null;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}

	
	/*
	 * Dato l'id ottengo l'articolo.
	 */
	public Paper getArticolo(int eprintid) {

		final String sql = "SELECT * FROM paper where eprintid=?";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, eprintid);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				Paper paper = new Paper(rs.getInt("eprintid"), rs.getString("title"), rs.getString("issn"),
						rs.getString("publication"), rs.getString("type"), rs.getString("types"));
				return paper;
			}

			return null;

		} catch (SQLException e) {
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}
	
	
	public List<Author> loadAutori(){
		List<Author> autori= new LinkedList<Author>();
		final String sql="SELECT DISTINCT author.`firstname`, author.`lastname`, author.`id` "
				+ "FROM author, creator, paper "
				+ "WHERE author.`id`=creator.`authorid`  "
				+ "AND creator.`eprintid`=paper.`eprintid`"
				+ "AND paper.`type`='article'"
						+ "order by lastname";

		try {
			Connection conn= DBConnect.getConnection();
			PreparedStatement st=conn.prepareStatement(sql);
			
			ResultSet rs=st.executeQuery();
			while(rs.next()){
				Author a=new Author(rs.getInt("id"), 
									rs.getString("lastname"), 
									rs.getString("firstname"));
				autori.add(a);	
			}
		
			conn.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return autori;
	}
	
	public List<Paper> loadPaper(){
		List<Paper> articoli= new ArrayList<Paper>();
		
		final String sql="SELECT *  "
				+ "FROM paper, papertype "
				+ "WHERE paper.`types`=papertype.`types` "
				+ "AND paper.`type`='article'";
		
		
		try {
			Connection conn= DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs=st.executeQuery();
			
			while (rs.next()){
				Paper p= new Paper(rs.getInt("eprintid"), 
						rs.getString("title"));
				articoli.add(p);
			}
			conn.close();
			rs.close();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return articoli;
	}
	
	
	public List<Collegamento> loadCreator(){
		List<Collegamento> relazione=new ArrayList<Collegamento>();
		
		final String sql="SELECT * FROM creator";
		
		Connection conn= DBConnect.getConnection();
		PreparedStatement st;
		try {
			st = conn.prepareStatement(sql);
			ResultSet rs=st.executeQuery();
			
			while(rs.next()){
				Collegamento c=new Collegamento(rs.getInt("eprintid"),rs.getInt("authorid"));
				relazione.add(c);
			}
			rs.close();
			conn.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return relazione;
	}
	/**NON FUNZIONA
	 * Passato autore e la lista completa di articoli
	 * aggiunge all'autore gli articoli scritti da lui
	 * @param a
	 * @param temp
	 */
	
	public void setArticoliAutore(Author a, List<Paper> temp){
		List<Paper> articoli= new ArrayList<Paper>();
		
		final String sql="SELECT paper.`eprintid`, paper.`title`"
				+ " FROM paper, creator  "
				+ " WHERE paper.`eprintid`=creator.`eprintid` "
				+ " AND paper.`type`='article' "
				+ " AND creator.`authorid`=? "; 
		
		Connection conn = DBConnect.getConnection();
		
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, a.getId());
			ResultSet rs = st.executeQuery();
			
			while(rs.next()){
				
				Paper p = temp.get(temp.indexOf(new Paper(rs.getInt("eprintid"), rs.getString("title"))));
				articoli.add(p);	
			}
			conn.close();
			rs.close();
			//a.setArticoli(articoli);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setAutoriArticolo(Paper p, List<Author> autori){
		List<Author> temp= new ArrayList<Author>();
		
		final String sql="SELECT author.`id`, author.`lastname`, author.`firstname`  "
				+ " FROM creator, author "
				+ " WHERE creator.`authorid`=author.`id` "
				+ " AND creator.`eprintid`=?";
		Connection conn=DBConnect.getConnection();
		try {
			PreparedStatement st=conn.prepareStatement(sql);
			st.setInt(1, p.getEprintid());
			ResultSet rs=st.executeQuery();
			
			while(rs.next()){
				Author a=autori.get(autori.indexOf(new Author(rs.getInt("id"),rs.getString("lastname"),rs.getString("firstname"))));
				temp.add(a);
			}
			p.setAutoriArticolo(temp);
			conn.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	

	
	
	
	
	
}