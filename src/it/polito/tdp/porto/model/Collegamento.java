package it.polito.tdp.porto.model;

public class Collegamento {
	private int eprintid;
	private int authorid;
	
	
	public Collegamento(int eprintid, int authorid) {

		this.eprintid = eprintid;
		this.authorid = authorid;
	}


	public int getEprintid() {
		return eprintid;
	}


	public void setEprintid(int eprintid) {
		this.eprintid = eprintid;
	}


	public int getAuthorid() {
		return authorid;
	}


	public void setAuthorid(int authorid) {
		this.authorid = authorid;
	}


	@Override
	public String toString() {
		return eprintid + " " + authorid;
	}
	
	
	

}
