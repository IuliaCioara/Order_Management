

public class Product {
	private int id;
	private String nume;
	private int pret;
	private int cantitate;
	
	public Product(){
	
	}
	
	public Product(int id, String nume, int pret, int cantitate){
		
		super();
		this.id = id;
		this.nume = new String(nume);
		this.pret = pret;
		this.cantitate = cantitate;
	}
	
	public int getId(){
		return id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public String getNume(){
		return nume;
	}
	
	public void setNume(String nume){
		this.nume = new String(nume);
	}
	
	public int getPret(){
		return pret;
	}
	
	public void setPret(int pret){
		this.pret = pret;
	}
	
	public int getCantitate(){
		return cantitate;
	}
	
	public void setCantitate(int cantitate){
		this.cantitate = cantitate;
	}
	
	@Override
	
	public String toString(){
		return ("Produs [idProd=" + id + ", nume=" + nume + ", pret=" + pret + ", cantitate=" + cantitate + "]" );
	}
}
