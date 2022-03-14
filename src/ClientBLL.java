
import java.util.List;


public class ClientBLL {
	public ClientDAO clientD;
	
	public ClientBLL(){
		clientD = new ClientDAO();
	}
	
	public List<Client> selectAll(){
		return clientD.findAll();
	}
	
	public Client selectById(int id){
		return clientD.findById(id);
	}
	
	public void insertClient(Client c){
		clientD.insert(c);
	}
	
	public void updateClient(Client c){
		clientD.update(c);
	}
	
	public void deleteClient(int id){
		clientD.deleteRow(id);
	}
	
}


