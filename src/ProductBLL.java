

import java.util.List;


public class ProductBLL {
	private ProductDAO prodD;
	
	public ProductBLL(){
		prodD = new ProductDAO();
	}
	
	public List<Product> selectAll(){
		return prodD.findAll();
	}
	
	public Product selectById(int id){
		return prodD.findById(id);
	}
	
	public void insertProd(Product p){
		prodD.insert(p);
	}
	
	public void updateProd(Product p){
		prodD.update(p);
	}
	
	public void deleteProd(int id){
		prodD.deleteRow(id);
	}
}
