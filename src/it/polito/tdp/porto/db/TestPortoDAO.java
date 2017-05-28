package it.polito.tdp.porto.db;

import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.porto.model.Author;
import it.polito.tdp.porto.model.Paper;

public class TestPortoDAO {
	
	public static void main(String args[]) {
		PortoDAO pd = new PortoDAO();
//		System.out.println(pd.getAutore(85));
//		System.out.println(pd.getArticolo(2293546));
//		System.out.println(pd.getArticolo(1941144));
		List<Paper> temp=new ArrayList<Paper>();
		List<Author> temp1=new ArrayList<Author>();
		temp.addAll(pd.loadPaper());
		temp1.addAll(pd.loadAutori());
		Author a =pd.getAutore(85); 
		Paper p=pd.getArticolo(2460918);
		pd.setAutoriArticolo(p, temp1);
		pd.setArticoliAutore(a, temp);
		//pd.setArticoliAutore(a, temp);
		System.out.println(p.getAutoriArticolo());
		
		

	}

}
