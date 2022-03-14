

public class Client {
	private int id;
	private String nume;
	private String adresa;
	private String email;
	private int varsta;
	
	
	public Client(){
		
	}
	
	public Client(int id,String nume,String adresa,String email,int varsta){
		super();
		this.id = id;
		this.nume = nume;
		this.adresa = adresa;
		this.email = email;
		this.varsta = varsta;
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
		this.nume = nume;
	}
	
	public String getAdresa(){
		return adresa;
	}
	
	public void setAdresa(String adresa){
		this.adresa = adresa;
	}
	
	public int getVarsta(){
		return varsta;
	}
	
	public void setVarsta(int varsta){
		this.varsta = varsta;
	}
	
	public String getEmail(){
		return email;
	}
	
	public void setEmail(String email){
		this.email = email;
	}

	@Override
	public String toString(){
		return ("Client [idClient=" + id + ", nume=" + nume + ", adresa=" + adresa + ", email=" + email + ", varsta=" + varsta + "]");
	}
	
}
