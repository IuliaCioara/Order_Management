


public class Orders {
	private int id;
	private String client;
	private String produs;
	private int cantitate;
	
	public Orders(){
		
	}
	
	public Orders(int id,String client,String produs,int cantitate){
		super();
		this.id = id;
		this.client = client;
		this.produs = produs;
		this.cantitate = cantitate;
	}
	
	public int getId(){
		return id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public String getClient(){
		return client;
	}
	
	public void setClient(String client){
		this.client = client;
	}
	
	public void setProdus(String produs){
		this.produs = produs;
	}
	
	public String getProdus(){
		return produs;
	}
	
	public int getCantitate(){
		return cantitate;
	}
	
	public void setCantitate(int cantitate){
		this.cantitate = cantitate;
	}
	
@Override
	
	public String toString(){
		return ("Order [id=" + id + ", numeClient=" + client + ", numeProdus=" + produs + ", cantitate=" + cantitate + "]" );
	}
}