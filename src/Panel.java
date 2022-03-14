

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;



public class Panel {
	
	public JFrame frame;
	public JButton clientiAll,produseAll,ordersAll,clientiSel,produseSel,ordersSel,clientiDel,produseDel,ordersDel,clientiIns,produseIns,ordersIns,clientiUp,produseUp,ordersUp;
	public JPanel butoane,clientiPanel,produsePanel,ordersPanel,mainPanel;
	public JTable table;
	Controller cont;
	public Panel(){
		
		 cont = new Controller();
		butoane = new JPanel();
		clientiAll = new JButton("Toti clientii");
		produseAll = new JButton("Toate produsele");
		ordersAll = new JButton("Toate comenzile");
		clientiSel = new JButton("Selectie clienti");
		produseSel = new JButton("Selectie produse");
		ordersSel = new JButton("Selectie comenzi");
		clientiDel = new JButton("Stergere clienti");
		produseDel = new JButton("Stergere produse");
		ordersDel = new JButton("Stergere comenzi");
		clientiIns = new JButton("Inserare clienti");
		produseIns = new JButton("Inserare produse");
		ordersIns = new JButton("Inserare comenzi");
		clientiUp = new JButton("Update clienti");
		produseUp = new JButton("Update produse");
		ordersUp = new JButton("Update comenzi");
		clientiPanel = new JPanel();
		mainPanel = new JPanel();
		ordersPanel = new JPanel();
		produsePanel = new JPanel();
		
		butoane.add(clientiAll);
		butoane.add(produseAll);
		butoane.add(ordersAll);
		butoane.add(clientiSel);
		butoane.add(produseSel);
		butoane.add(ordersSel);
		butoane.add(clientiDel);
		butoane.add(produseDel);
		butoane.add(ordersDel);
		butoane.add(clientiIns);
		butoane.add(produseIns);
		butoane.add(ordersIns);
		butoane.add(clientiUp);
		butoane.add(produseUp);
		butoane.add(ordersUp);
	
		frame = new JFrame("Simulator gestiune clienti");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1200,800);
		frame.setVisible(true);
		frame.setLayout(new GridLayout(4,4));
		frame.add(butoane);
		frame.add(mainPanel);
		inceput();
	}
	
	public void inceput(){
		
			
			clientiAll.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					
					
					List<Client> lista = cont.getClientiAll();
					table = new JTable(new BeanTableModel<Client>(Client.class,lista));
					mainPanel.removeAll();
					JScrollPane scroll = new JScrollPane();
					scroll.getViewport().add(table);
					mainPanel.add(scroll);
					mainPanel.updateUI();
					
				}
			});
			
			produseAll.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					List<Product> prodL = cont.getProductAll();
					table = new JTable(new BeanTableModel<Product>(Product.class,prodL));
					mainPanel.removeAll();
					mainPanel.add(new JScrollPane(table));
					mainPanel.updateUI();
				}
			});
			
			ordersAll.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					List<Orders> orderL = cont.getOrdersAll();
					table = new JTable(new BeanTableModel<Orders>(Orders.class,orderL));
					mainPanel.removeAll();
					mainPanel.add(new JScrollPane(table));
					mainPanel.updateUI();
				}
			});
			
			clientiSel.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					JTextField ptId = new JTextField(3);
					JButton ok = new JButton("ok");
					mainPanel.removeAll();
					mainPanel.add(ptId);
					mainPanel.add(ok);
					mainPanel.updateUI();
					
					ok.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent t){
							int id = Integer.parseInt(ptId.getText());
							List<Client> clientL = cont.getClientId(id);
							mainPanel.removeAll();
							table = new JTable(new BeanTableModel<Client>(Client.class,clientL));
							mainPanel.add(new JScrollPane(table));
							mainPanel.updateUI();
						}
					});
				}
			});
			
			produseSel.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					JTextField ptId = new JTextField(3);
					JButton ok = new JButton("ok");
					mainPanel.removeAll();
					mainPanel.add(ptId);
					mainPanel.add(ok);
					mainPanel.updateUI();
					
					ok.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent t){
							int id = Integer.parseInt(ptId.getText());
							List<Product> prodL = cont.getProductId(id);
							mainPanel.removeAll();
							table = new JTable(new BeanTableModel<Product>(Product.class,prodL));
							mainPanel.add(new JScrollPane(table));
							mainPanel.updateUI();
						}
					});
				}
			});
			
			ordersSel.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					JTextField ptId = new JTextField(3);
					JButton ok = new JButton("ok");
					mainPanel.removeAll();
					mainPanel.add(ptId);
					mainPanel.add(ok);
					mainPanel.updateUI();
					
					ok.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent t){
							int id = Integer.parseInt(ptId.getText());
							List<Orders> ordL = cont.getOrderId(id);
							mainPanel.removeAll();
							table = new JTable(new BeanTableModel<Orders>(Orders.class,ordL));
							mainPanel.add(new JScrollPane(table));
							mainPanel.updateUI();
						}
					});
				}
			});
		
			clientiDel.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					JTextField ptId = new JTextField(3);
					JButton ok = new JButton("ok");
					mainPanel.removeAll();
					mainPanel.add(ptId);
					mainPanel.add(ok);
					mainPanel.updateUI();
					
					ok.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent t){
							int id = Integer.parseInt(ptId.getText());
							mainPanel.removeAll();
							cont.deleteClientId(id);
							mainPanel.updateUI();
						}
					});
				}
			});
			
			
			produseDel.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					JTextField ptId = new JTextField(3);
					JButton ok = new JButton("ok");
					mainPanel.removeAll();
					mainPanel.add(ptId);
					mainPanel.add(ok);
					mainPanel.updateUI();
					
					ok.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent t){
							int id = Integer.parseInt(ptId.getText());
							mainPanel.removeAll();
							cont.deleteProductId(id);
							mainPanel.updateUI();
						}
					});
				}
			});
			
			clientiIns.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					JTextField ptId = new JTextField(3);
					JTextField ptNume = new JTextField(20);
					JTextField ptEmail = new JTextField(20);
					JTextField ptVarsta = new JTextField(3);
					JTextField ptAdresa = new JTextField(20);
					JLabel lId = new JLabel("ID");
					JLabel lNume = new JLabel("Nume");
					JLabel lAdresa = new JLabel("Adresa");
					JLabel lEmail = new JLabel("Email");
					JLabel lVarsta = new JLabel("Varsta");
					JButton ok = new JButton("ok");
					mainPanel.removeAll();
					mainPanel.add(lId);
					mainPanel.add(ptId);
					mainPanel.add(lNume);
					mainPanel.add(ptNume);
					mainPanel.add(lAdresa);
					mainPanel.add(ptAdresa);
					mainPanel.add(lEmail);
					mainPanel.add(ptEmail);
					mainPanel.add(lVarsta);
					mainPanel.add(ptVarsta);
					mainPanel.add(ok);
					mainPanel.updateUI();
					
					ok.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent t){
							int id = Integer.parseInt(ptId.getText());
							String nume = ptNume.getText();
							String adresa = ptAdresa.getText();
							String mail = ptEmail.getText();
							int varsta = Integer.parseInt(ptVarsta.getText());
							Client c = new Client(id,nume,adresa,mail,varsta);
							cont.insertClient(c);
							mainPanel.removeAll();
							mainPanel.updateUI();
						}
					});
				}
			});	
			
			produseIns.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					JTextField ptId = new JTextField(3);
					JTextField ptNume = new JTextField(20);
					JTextField ptPret = new JTextField(10);
					JTextField ptCantitate = new JTextField(3);
					JLabel lId = new JLabel("ID");
					JLabel lNume = new JLabel("Nume");
					JLabel lPret = new JLabel("Pret");
					JLabel lCantiate = new JLabel("Cantiate");
					JButton ok = new JButton("ok");
					mainPanel.removeAll();
					mainPanel.add(lId);
					mainPanel.add(ptId);
					mainPanel.add(lNume);
					mainPanel.add(ptNume);
					mainPanel.add(lPret);
					mainPanel.add(ptPret);
					mainPanel.add(lCantiate);
					mainPanel.add(ptCantitate);
					mainPanel.add(ok);
					mainPanel.updateUI();
					
					ok.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent t){
							int id = Integer.parseInt(ptId.getText());
							String nume = ptNume.getText();
							int pret = Integer.parseInt(ptPret.getText());
							int cantitate = Integer.parseInt(ptCantitate.getText());
							
							Product p = new Product(id,nume,pret,cantitate);
							cont.insertProdus(p);
							mainPanel.removeAll();
							mainPanel.updateUI();
						}
					});
				}
			});	
			
			ordersIns.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					JTextField ptClient = new JTextField(3);
					JTextField ptProdus = new JTextField(3);
					JTextField ptCantitate = new JTextField(3);
			
					JLabel lClient = new JLabel("Id Client");
					JLabel lProdus = new JLabel("Id Produs");
					JLabel lCantitate = new JLabel("Cantitate");
					JButton ok = new JButton("ok");
					mainPanel.removeAll();
					mainPanel.add(lClient);
					mainPanel.add(ptClient);
					mainPanel.add(lProdus);
					mainPanel.add(ptProdus);
					mainPanel.add(lCantitate);
					mainPanel.add(ptCantitate);
					mainPanel.add(ok);
					mainPanel.updateUI();
					
					ok.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent t){
							
							int client = Integer.parseInt(ptClient.getText());
							int produs = Integer.parseInt(ptProdus.getText());
							int cantitate = Integer.parseInt(ptCantitate.getText());
							String rez = cont.insertOrder(client, produs, cantitate);
							if(rez != null){
								JLabel msj = new JLabel(rez);
								mainPanel.removeAll();
								mainPanel.add(msj);
								mainPanel.updateUI();
							}
							else{
								mainPanel.removeAll();
								mainPanel.updateUI();
								try{
									PrintWriter writer = new PrintWriter("Order.txt","UTF-8");
									List<Orders> ordL = cont.getOrdersAll();
									for(Orders o : ordL){
										writer.println("Nr Order:"+ o.getId());
										writer.println("Clientul" + o.getClient());
										writer.println("Produsul" + o.getProdus());
										writer.println("Cantitate:" + o.getCantitate());
										writer.close();
									}
								}catch (IOException e){
										System.out.println("Problema la scrierea in fisier");
									}
						
								}
							}
							
						
					});
				}
			});	
	
			clientiUp.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					JTextField ptId = new JTextField(3);
					JTextField ptNume = new JTextField(20);
					JTextField ptEmail = new JTextField(20);
					JTextField ptVarsta = new JTextField(3);
					JTextField ptAdresa = new JTextField(20);
					JLabel lId = new JLabel("ID");
					JLabel lNume = new JLabel("Nume");
					JLabel lAdresa = new JLabel("Adresa");
					JLabel lEmail = new JLabel("Email");
					JLabel lVarsta = new JLabel("Varsta");
					JButton ok = new JButton("ok");
					mainPanel.removeAll();
					mainPanel.add(lId);
					mainPanel.add(ptId);
					mainPanel.add(lNume);
					mainPanel.add(ptNume);
					mainPanel.add(lAdresa);
					mainPanel.add(ptAdresa);
					mainPanel.add(lEmail);
					mainPanel.add(ptEmail);
					mainPanel.add(lVarsta);
					mainPanel.add(ptVarsta);
					mainPanel.add(ok);
					mainPanel.updateUI();
					
					ok.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent t){
							int id = Integer.parseInt(ptId.getText());
							String nume = ptNume.getText();
							String adresa = ptAdresa.getText();
							String mail = ptEmail.getText();
							int varsta = Integer.parseInt(ptVarsta.getText());
							Client c = new Client(id,nume,adresa,mail,varsta);
							cont.updateClient(c);
							mainPanel.removeAll();
							mainPanel.updateUI();
						}
					});
				}
			});	
			
			produseUp.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					JTextField ptId = new JTextField(3);
					JTextField ptNume = new JTextField(20);
					JTextField ptPret = new JTextField(10);
					JTextField ptCantitate = new JTextField(3);
					JLabel lId = new JLabel("ID");
					JLabel lNume = new JLabel("Nume");
					JLabel lPret = new JLabel("Pret");
					JLabel lCantiate = new JLabel("Cantiate");
					JButton ok = new JButton("ok");
					mainPanel.removeAll();
					mainPanel.add(lId);
					mainPanel.add(ptId);
					mainPanel.add(lNume);
					mainPanel.add(ptNume);
					mainPanel.add(lPret);
					mainPanel.add(ptPret);
					mainPanel.add(lCantiate);
					mainPanel.add(ptCantitate);
					mainPanel.add(ok);
					mainPanel.updateUI();
					
					ok.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent t){
							int id = Integer.parseInt(ptId.getText());
							String nume = ptNume.getText();
							int pret = Integer.parseInt(ptPret.getText());
							int cantitate = Integer.parseInt(ptCantitate.getText());
							
							Product p = new Product(id,nume,pret,cantitate);
							cont.updateProduct(p);
							mainPanel.removeAll();
							mainPanel.updateUI();
						}
					});
				}
			});	
			
			ordersUp.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					JTextField ptClient = new JTextField(20);
					JTextField ptProdus = new JTextField(10);
					JTextField ptCantitate = new JTextField(3);
					JTextField ptId = new JTextField(3);
					JLabel lClient = new JLabel("Client");
					JLabel lProdus = new JLabel("Produs");
					JLabel lId = new JLabel("Id");
					JLabel lCantitate = new JLabel("Cantiate");
					JButton ok = new JButton("ok");
					mainPanel.removeAll();
					mainPanel.add(lId);
					mainPanel.add(ptId);
					mainPanel.add(lClient);
					mainPanel.add(ptClient);
					mainPanel.add(lProdus);
					mainPanel.add(ptProdus);
					mainPanel.add(lCantitate);
					mainPanel.add(ptCantitate);
					mainPanel.add(ok);
					mainPanel.updateUI();
					
					ok.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent t){
							int id = Integer.parseInt(ptId.getText());
							String client = ptClient.getText();
							String produs = ptProdus.getText();
							int cantitate = Integer.parseInt(ptCantitate.getText());
							cont.updateOrders(new Orders(id,client,produs,cantitate));
							mainPanel.removeAll();
							mainPanel.updateUI();
						}
					});
				}
			});	
	

			ordersDel.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					JTextField ptId = new JTextField(3);
					JButton ok = new JButton("ok");
					mainPanel.removeAll();
					mainPanel.add(ptId);
					mainPanel.add(ok);
					mainPanel.updateUI();
					
					ok.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent t){
							int id = Integer.parseInt(ptId.getText());
							mainPanel.removeAll();
							cont.deleteOrderId(id);
							mainPanel.updateUI();
						}
					});
				}
			});
	
	}
		
}
