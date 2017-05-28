package it.polito.tdp.porto.model;

import java.util.ArrayList;
import java.util.List;

public class Author implements Comparable<Author> {

	private int id;
	private String lastname;
	private String firstname;
	private List<Paper> articoli;
		
	public Author(int id, String lastname, String firstname) {
		this.id = id;
		this.lastname = lastname;
		this.firstname = firstname;
		articoli=new ArrayList<Paper>();
		
	}

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getLastname() {
		return lastname;
	}


	public void setLastname(String lastname) {
		this.lastname = lastname;
	}


	public String getFirstname() {
		return firstname;
	}


	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	


	public List<Paper> getArticoli() {
		return articoli;
	}



	public void setArticoli(List<Paper> articoli) {
		this.articoli = articoli;
	}



	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		 if(!(o instanceof Author))
			 return false;
		 if(this.getId()==((Author) o).id)
			 return true;
		return false;
	}

	@Override
	public String toString() {
		return lastname+" " + firstname;
	}

	@Override
	public int compareTo(Author o) {
		if(this.lastname.compareTo(o.getLastname())!=0){
			return this.lastname.compareTo(o.getLastname());
		}
		
		return this.firstname.compareTo(o.firstname);
	}
}
