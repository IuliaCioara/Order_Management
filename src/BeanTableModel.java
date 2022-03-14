

import java.lang.reflect.*;
import java.util.*;


public class BeanTableModel<T> extends RowTableModel<T>
{

	private static Map<Class, Class> primitives = new HashMap<Class, Class>(10);

	static
	{
		primitives.put(Boolean.TYPE, Boolean.class);
		primitives.put(Byte.TYPE, Byte.class);
		primitives.put(Character.TYPE, Character.class);
		primitives.put(Double.TYPE, Double.class);
		primitives.put(Float.TYPE, Float.class);
		primitives.put(Integer.TYPE, Integer.class);
		primitives.put(Long.TYPE, Long.class);
		primitives.put(Short.TYPE, Short.class);
	}

	private Class beanClass;
	private Class ancestorClass;

	private List<ColumnInformation> columns = new ArrayList<ColumnInformation>();

	
	public BeanTableModel(Class beanClass)
	{
		this(beanClass, beanClass, new ArrayList<T>());
	}

	public BeanTableModel(Class beanClass, Class ancestorClass)
	{
		this(beanClass, ancestorClass, new ArrayList<T>());
	}

	
	public BeanTableModel(Class beanClass, List<T> modelData)
	{
		this(beanClass, beanClass, modelData);
	}

	
	public BeanTableModel(Class beanClass, Class ancestorClass, List<T> modelData)
	{
		super( beanClass );
		this.beanClass = beanClass;
		this.ancestorClass = ancestorClass;

	

		createColumnInformation();

	

		List<String> columnNames = new ArrayList<String>();

		for (ColumnInformation info: columns)
		{
			columnNames.add( info.getName() );
		}

		

		super.setDataAndColumnNames(modelData, columnNames);
		resetModelDefaults();
	}

	
	@SuppressWarnings("unchecked")
	private void createColumnInformation()
	{
		Method[] theMethods = beanClass.getMethods();


		for (int i = 0; i < theMethods.length; i++)
		{
			Method theMethod = theMethods[i];

			if (theMethod.getParameterTypes().length == 0
			&&  ancestorClass.isAssignableFrom(theMethod.getDeclaringClass()) )
			{
				String methodName = theMethod.getName();

				if (theMethod.getName().startsWith("get"))
					buildColumnInformation(theMethod, methodName.substring(3));

				if (theMethod.getName().startsWith("is"))
					buildColumnInformation(theMethod, methodName.substring(2));
			}
		}
	}

	
	@SuppressWarnings("unchecked")
	private void buildColumnInformation(Method theMethod, String theMethodName)
	{
	
		Class returnType = getReturnType( theMethod );

		if (returnType == null) return;

		

		String headerName = formatColumnName( theMethodName );

		Method setMethod = null;

		try
		{
			String setMethodName = "set" + theMethodName;
			setMethod = beanClass.getMethod(setMethodName, theMethod.getReturnType());
		}
		catch(NoSuchMethodException e) {}


		ColumnInformation ci = new ColumnInformation(headerName, returnType, theMethod, setMethod);
    	columns.add( ci );
	}

	
	private Class getReturnType(Method theMethod)
	{
		Class returnType = theMethod.getReturnType();

		if (returnType.isInterface()
		||  returnType.isArray())
			return null;


		if (returnType.isPrimitive())
			returnType = primitives.get(returnType);

		return returnType;
	}

	
	private void resetModelDefaults()
	{
		columnNames.clear();

		for (int i = 0; i < columns.size(); i++)
		{
			ColumnInformation info = columns.get(i);
			columnNames.add( info.getName() );
			super.setColumnClass(i, info.getReturnType());
			super.setColumnEditable(i, info.getSetter() == null ? false : true);
		}
	}

	
	@Override
	public Object getValueAt(int row, int column)
	{
		ColumnInformation ci = (ColumnInformation)columns.get( column );

		Object value = null;

		try
		{
			value = ci.getGetter().invoke(getRow(row));
		}
		catch(IllegalAccessException e) {}
		catch(InvocationTargetException e) {}

		return value;
	}

	
	@Override
	public void setValueAt(Object value, int row, int column)
	{
		ColumnInformation ci = (ColumnInformation)columns.get( column );

		try
		{
			Method setMethod = ci.getSetter();

			if (setMethod != null)
			{
				setMethod.invoke(getRow(row), value);
				fireTableCellUpdated(row, column);
			}
		}
		catch(IllegalAccessException e) {}
		catch(InvocationTargetException e) {}
	}


	@Override
	public void setColumnClass(int column, Class columnClass)
	{
	}

	
	@Override
	public void setColumnEditable(int column, boolean isEditable)
	{
		ColumnInformation ci = (ColumnInformation)columns.get( column );

		if (isEditable && ci.getSetter() == null) return;

		super.setColumnEditable(column, isEditable);
	}

	
	public void setColumnName(int column, String name)
	{
		ColumnInformation ci = (ColumnInformation)columns.get( column );
		ci.setName( name );
		resetModelDefaults();
	}

	
	public void sortColumnNames()
	{
		Collections.sort(columns);
		resetModelDefaults();
	}

	
	private class ColumnInformation implements Comparable<ColumnInformation>
	{
		private String name;
		private Class returnType;
		private Method getter;
		private Method setter;

		public ColumnInformation(String name, Class returnType, Method getter, Method setter)
		{
			this.name = name;
			this.returnType = returnType;
			this.getter = getter;
			this.setter = setter;
		}

		
		public Class getReturnType()
		{
			return returnType;
		}

		
		public Method getGetter()
		{
			return getter;
		}

		
		public String getName()
		{
			return name;
		}

		
		public Method getSetter()
		{
			return setter;
		}

		
		public void setName(String name)
		{
			this.name = name;
		}

    	
    	public int compareTo(ColumnInformation o)
    	{
    		return getName().compareTo(o.getName());
    	}
	}
}
