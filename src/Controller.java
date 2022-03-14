
import java.util.ArrayList;
import java.util.List;



public class Controller {

	private ClientBLL clientBll;
	private ProductBLL productBll;
	private OrderBLL ordersBll;
	
	public Controller(){
		clientBll = new ClientBLL();
		productBll = new ProductBLL();
		ordersBll = new OrderBLL();
	}
	
	public List<Client> getClientiAll(){
		return clientBll.selectAll();
	}
	
	public List<Product> getProductAll(){
		return productBll.selectAll();	
	}
	
	public List<Orders> getOrdersAll(){
		return ordersBll.selectAll();
	}
	
	public List<Client> getClientId(int id){
		List<Client> listaCl = new ArrayList<Client>(10);
		listaCl.add(clientBll.selectById(id));
		return listaCl;
	}
	
	public List<Product> getProductId(int id){
		List<Product> listaPr = new ArrayList<Product>(10);
		listaPr.add(productBll.selectById(id));
		return listaPr;
	}
	
	public List<Orders> getOrderId(int id){
		List<Orders> listaOr = new ArrayList<Orders>(10);
		listaOr.add(ordersBll.selectById(id));
		return listaOr;
	}
	
	public void deleteClientId(int id){
		clientBll.deleteClient(id);
		}
	
	public void deleteProductId(int id){
		productBll.deleteProd(id);
	}
	
	public void deleteOrderId(int id){
		ordersBll.deleteById(id);
	}
	
	public void insertClient(Client c){
		clientBll.insertClient(c);
	}
	
	public void insertProdus(Product p){
		productBll.insertProd(p);
	}
	
	public String insertOrder(int idClient,int idProdus,int cantitate){
		return ordersBll.createOrder(idClient, idProdus, cantitate);
	}
	
	public void updateClient(Client c){
		clientBll.updateClient(c);
	}
	
	public void updateProduct(Product p){
		productBll.updateProd(p);
	}
	
	public void updateOrders(Orders o){
		ordersBll.updateOrder(o);
	}
}
