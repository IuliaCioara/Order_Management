


import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;



public class AbstractDAO<T> {
	protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

	private final Class<T> type;

	@SuppressWarnings("unchecked")
	public AbstractDAO() {
		this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

	}

	public String createSelectQuery(String field) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append(" * ");
		sb.append(" FROM ");
		sb.append(type.getSimpleName());
		sb.append(" WHERE " + field + " =?");
	
		return sb.toString();
	}
	
	public String createSelectAll() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append(" * ");
		sb.append(" FROM ");
		sb.append(type.getSimpleName());
		return sb.toString();
	}
	
	public String createInsertQuery(Object object){
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO ");
		sb.append(type.getSimpleName());
		sb.append("(");
		for (Field field : object.getClass().getDeclaredFields()){
			field.setAccessible(true);
			sb.append(field.getName() + ",");
		}
		sb.delete(sb.length()-1,sb.length());
		sb.append(")");
		sb.append(" VALUES (");
		for (Field field : object.getClass().getDeclaredFields()){
			field.setAccessible(true);
		try{
			Object value;
			value = field.get(object);
			if(field.getType() == String.class){
				sb.append("'" + value + "'" + ',');
			}else{
			sb.append(value + ",");
			}
		}catch (IllegalArgumentException e){
			e.printStackTrace();
		}catch (IllegalAccessException e){
			e.printStackTrace();
		}
	}
		sb.delete(sb.length()-1, sb.length());
		sb.append(")");
		return sb.toString();
	}
	
	public String createUpdateQuery(Object object){
		Object valId = null;
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE ");
		sb.append(type.getSimpleName());
		sb.append(" SET ");
		for (Field field : object.getClass().getDeclaredFields()){
			field.setAccessible(true);
			try{
			if (field.getName() != "id"){
				sb.append(field.getName() + "=");
				Object value;
				value = field.get(object);
				if (field.getType() == String.class){
					sb.append("'" + value + "'" + ",");
				}
				else {
					sb.append(value + ",");
				}
				
			}
			else {
				valId = field.get(object);
			}
			}catch(IllegalArgumentException e){
				e.printStackTrace();
			}catch(IllegalAccessException e){
				e.printStackTrace();
			}
			
		}
		sb.delete(sb.length()-1,sb.length());
		sb.append(" WHERE id=" + valId);
		
		return sb.toString();
	}
	
	
	public List<T> findAll() {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = createSelectAll();
		try {
			connection = ConnectionUnit.getConnection();
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			return createObjects(resultSet);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
		} finally {
			ConnectionUnit.close(resultSet);
			ConnectionUnit.close(statement);
			ConnectionUnit.close(connection);
		}
		return null;
		
	}

	public T findById(int id) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = createSelectQuery("id");
		try {
			connection = ConnectionUnit.getConnection();
			statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();

			return createObjects(resultSet).get(0);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
		} finally {
			ConnectionUnit.close(resultSet);
			ConnectionUnit.close(statement);
			ConnectionUnit.close(connection);
		}
		return null;
	}

	public List<T> createObjects(ResultSet resultSet) {
		List<T> list = new ArrayList<T>();

		try {
			while (resultSet.next()) {
				T instance = type.newInstance();
				for (Field field : type.getDeclaredFields()) {
					Object value = resultSet.getObject(field.getName());
					PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(),type);
					Method method = propertyDescriptor.getWriteMethod();
					method.invoke(instance, value);
				
				}
			
				list.add(instance);
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		return list;
	}

	public void insert(Object object) {
		Connection connection = null;
		PreparedStatement statement = null;
		String query = createInsertQuery(object);
		try {
			connection = ConnectionUnit.getConnection();
			statement = connection.prepareStatement(query);
			statement.executeUpdate(query);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
		} finally {
			ConnectionUnit.close(statement);
			ConnectionUnit.close(connection);
		}
		}

	public void update(Object object) {
		Connection connection = null;
		PreparedStatement statement = null;
		String query = createUpdateQuery(object);
		try{
			connection = ConnectionUnit.getConnection();
			statement = connection.prepareStatement(query);
			statement.executeUpdate(query);
		}catch (SQLException e){
			LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
		} finally {
			ConnectionUnit.close(statement);
			ConnectionUnit.close(connection);
		}
	}
	
	public String createDeleteQuery(int id) {
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE ");
		sb.append("FROM ");
		sb.append(type.getSimpleName());
		sb.append(" WHERE id=" + id);
		return sb.toString();
	}
	
	
	public void deleteRow(int id){
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = createDeleteQuery(id);
		try {
			connection = ConnectionUnit.getConnection();
			statement = connection.prepareStatement(query);
			 statement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:deleteClient " + e.getMessage());
		} finally {
			ConnectionUnit.close(resultSet);
			ConnectionUnit.close(statement);
			ConnectionUnit.close(connection);
		}
		
	}


}

