package it.polito.tdp.porto.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.porto.db.PortoDAO;

public class Model {
	
	private PortoDAO dao=new PortoDAO();
	
	private SimpleGraph<Author,DefaultEdge> graph= new SimpleGraph<Author, DefaultEdge>(DefaultEdge.class);
	private List<Author> autori;
	private List<Paper> articoli;
	private List<Collegamento> creator;
	
	
	public Model(){
		autori=new ArrayList<Author>();
		articoli=new ArrayList<Paper>();
		creator=new ArrayList<Collegamento>();
	}
	
	/** 
	 * FUNZIONA
	 */
	public void createGraph(){ 
		
		this.riempiListe();
		
		//aggiungo vertici
		Graphs.addAllVertices(graph, autori);
		//aggiungo archi
		for(Paper ptemp:articoli){
			for(int i=1; i< this.getPaperAuthors(ptemp.getEprintid()).size(); i++){
					graph.addEdge(this.getPaperAuthors(ptemp.getEprintid()).get(i-1), 
								this.getPaperAuthors(ptemp.getEprintid()).get(i));
				}
			}		
	}

	
/** :-D
 * Dati due autori, li confronta per vedere se sono coautori
 * @param a1
 * @param a2
 * @return true, se sono coautori; false altrimenti
 */
	public boolean areTheyCoAuthors(Author a1, Author a2){
		
		this.clearList();
		this.riempiListe();

		for(Collegamento ctemp1: this.creator){
			for(Collegamento ctemp2: this.creator){
				if(ctemp1.getAuthorid()!=ctemp2.getAuthorid() 
						&& ctemp1.getEprintid()==ctemp2.getEprintid()){
					if((a1.getId()==ctemp1.getAuthorid() && a2.getId()==ctemp2.getAuthorid())
							||(a2.getId()==ctemp1.getAuthorid() && a1.getId()==ctemp2.getAuthorid()))
						return true;
					}				
				}
			}
		return false;
		
	}

/**
 * :-D
 * @param codice
 * @return
 */
	public Paper getArticoloDatoCodice(int codice){
		Paper p=new Paper();
		this.riempiListe();
		for(Paper ptemp:articoli){
			if(ptemp.getEprintid()==codice){
				p=articoli.get(articoli.indexOf(ptemp));				
			}
		}
		return p;
	}
	
	/** :))
	 * Ritorna la lista completa di Authors
	 * @return autori
	 */
	
	public List<Author> getAllAuthors(){
		if(autori.isEmpty()){
			autori.addAll(dao.loadAutori());
		}
		return autori;
	}
	
	public Author getAuthorById(int id){
		this.clearList();
		this.riempiListe();
		
		for(Author atemp: autori){
			if(atemp.getId()==id){
				return atemp;
			}
		}
		return null;
	}
	
	
	public List<Author> getPaperAuthors(int eprintid){
		this.riempiListe();
		dao.setAutoriArticolo(this.getArticoloDatoCodice(eprintid), autori);
		return this.getArticoloDatoCodice(eprintid).getAutoriArticolo();
		
	}
	
/** :-))
 * 
 * @param a
 * dato un autore, ritorna icoautori con cui ha scritto articoli
 * sfruttando le proprietà del grafo
 * @return lista coautori
 */
	public List<Author> getCo_Authors(Author a){
		this.createGraph();
		return new ArrayList<Author>(Graphs.neighborListOf(graph, autori.get(autori.indexOf(a))));
		
	}
	
	
	public boolean isVertex(Author a){
		this.createGraph();
		
		for(Author atemp: this.getGraph().vertexSet()){
			if(atemp.getId()==a.getId())
				return true;
		}
		
		
		return false;
			
	}

	
	public SimpleGraph<Author, DefaultEdge> getGraph() {
		return graph;
	}

	public void setGraph(SimpleGraph<Author, DefaultEdge> graph) {
		this.graph = graph;
	}

	/**												METODI PRIVATI
	 * 
	 */
	private void riempiListe(){
		if(autori.isEmpty()){
			autori.addAll(dao.loadAutori());
		}
		if(articoli.isEmpty()){
			articoli.addAll(dao.loadPaper());
		}
		if(creator.isEmpty()){
			creator.addAll(dao.loadCreator());
		}
	}

	
	private void clearList(){
		this.creator.clear();
		this.autori.clear();
		this.articoli.clear();
	}
}
