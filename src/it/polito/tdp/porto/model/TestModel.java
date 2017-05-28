package it.polito.tdp.porto.model;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();
		Author a1=model.getAuthorById(1859);
		Author a2=model.getAuthorById(2041);
		Author a3=model.getAuthorById(85);

		System.out.println(a2); 
		System.out.println(model.getCo_Authors(a2));
	
	}

}
