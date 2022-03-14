
import java.util.List;

public class OrderBLL {

	private String mesaj;
	private ClientDAO clientD;
	private ProductDAO productD;
	private OrderDAO orderD;
	private int nrOrder;
	
	public OrderBLL(){
		mesaj = "Nu mai este in stoc";
		clientD = new ClientDAO();
		productD = new ProductDAO();
		orderD = new OrderDAO();
		nrOrder = 1;
	}
	
	public String getMesaj(){
		return mesaj;
	}
	
	public String createOrder(int idClient,int idProd,int cantitate){
		Product product;
		Client client;
		product = productD.findById(idProd);

		if (product.getCantitate() >= cantitate){
			client = clientD.findById(idClient);
			List<Orders> ordL = orderD.findAll();
			nrOrder = ordL.get(ordL.size()-1).getId();
			nrOrder++;
			orderD.insert(new Orders(nrOrder,client.getNume(),product.getNume(),cantitate));
			productD.update(new Product(product.getId(),product.getNume(),product.getPret(),product.getCantitate()-cantitate));
			
			
			return null;
		}
		else return(mesaj);
		
	}
	
	public List<Orders> selectAll(){
		return orderD.findAll();
	}
	
	public Orders selectById(int id){
		return orderD.findById(id);
	}
	
	public void deleteById(int id){
		orderD.deleteRow(id);
	}
	
	public void updateOrder(Orders o){
		orderD.update(o);
	}
}
