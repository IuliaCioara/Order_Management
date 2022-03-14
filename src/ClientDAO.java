
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;



public class ClientDAO extends AbstractDAO<Client>{

	public ClientDAO(){
		
	}
	
	public List<Client> findByNume(String nume){
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = createSelectQuery("nume");
		try{
			connection = ConnectionUnit.getConnection();
			statement = connection.prepareStatement(query);
			statement.setString(1, nume);
			resultSet = statement.executeQuery();

			return createObjects(resultSet);
			
		}catch(SQLException e){
			System.out.println("Problema in ClientDAO");
		}finally{
			ConnectionUnit.close(resultSet);
			ConnectionUnit.close(statement);
			ConnectionUnit.close(connection);
		}
		return null;
	}
}
