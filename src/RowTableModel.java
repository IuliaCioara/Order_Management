

import java.lang.reflect.*;
import java.util.*;
import javax.swing.table.*;

/**
 *  A TableModel that better supports the processing of rows of data. 
 
 */
abstract class RowTableModel<T> extends AbstractTableModel
{
	protected List<T> modelData;
	protected List<String> columnNames;
	protected Class[] columnClasses;
	protected Boolean[] isColumnEditable;
	private Class rowClass = Object.class;
	private boolean isModelEditable = true;

	/**
	 *  Constructs a <code>RowTableModel</code> with the row class.
	 */
	protected RowTableModel(Class rowClass)
	{
		setRowClass( rowClass );
	}

	/**
	 *  Constructs a <code>RowTableModel</code> with column names.
	 */
	protected RowTableModel(List<String> columnNames)
	{
		this(new ArrayList<T>(), columnNames);
	}

	/**
	 *  Constructs a <code>RowTableModel</code> with initial data and
	 */
	protected RowTableModel(List<T> modelData, List<String> columnNames)
	{
		setDataAndColumnNames(modelData, columnNames);
	}

	/**
	 *  Full Constructor for creating a <code>RowTableModel</code>.
	 */
	protected RowTableModel(List<T> modelData, List<String> columnNames, Class rowClass)
	{
		setDataAndColumnNames(modelData, columnNames);
		setRowClass( rowClass );
	}

	/**
	 *  Reset the data and column names of the model.

	 */
	protected void setDataAndColumnNames(List<T> modelData, List<String> columnNames)
	{
		this.modelData = modelData;
		this.columnNames = columnNames;
		columnClasses = new Class[getColumnCount()];
		isColumnEditable = new Boolean[getColumnCount()];
		fireTableStructureChanged();
	}

	/**
	 *  The class of the Row being stored in the TableModel
	 */
	protected void setRowClass(Class rowClass)
	{
		this.rowClass = rowClass;
	}
//
//  Implement the TableModel interface
//
	/**
	 *  Returns the Class of the queried <code>column</code>.
	*/
	public Class getColumnClass(int column)
	{
		Class columnClass = null;

		//  Get the class, if set, for the specified column

		if (column < columnClasses.length)
			columnClass = columnClasses[column];

		//  Get the default class

		if (columnClass == null)
			columnClass = super.getColumnClass(column);

		return columnClass;
	}

	/**
	 * Returns the number of columns in this table model.
	 */
	public int getColumnCount()
	{
		return columnNames.size();
	}

	/**
	 * Returns the column name.
	 */
	public String getColumnName(int column)
	{
		Object columnName = null;

		if (column < columnNames.size())
		{
			columnName = columnNames.get( column );
		}

		return (columnName == null) ? super.getColumnName( column ) : columnName.toString();
	}

	/**
	 * Returns the number of rows in this table model.
	 */
	public int getRowCount()
	{
		return modelData.size();
	}

	/**
	 * Returns true regardless of parameter values.
	 */
	public boolean isCellEditable(int row, int column)
	{
		Boolean isEditable = null;

		//  Check is column editability has been set

		if (column < isColumnEditable.length)
			isEditable = isColumnEditable[column];

		return (isEditable == null) ? isModelEditable : isEditable.booleanValue();
	}
//
//  Implement custom methods
//
	/**
	 *  Adds a row of data to the end of the model.
	 */
	public void addRow(T rowData)
	{
		insertRow(getRowCount(), rowData);
	}

	/**
	 * Returns the Object of the requested <code>row</code>.
	 */
	public T getRow(int row)
	{
		return modelData.get( row );
	}

	/**
	 * Returns an array of Objects for the requested <code>rows</code>.
	 */
	@SuppressWarnings("unchecked")
	public T[] getRowsAsArray(int... rows)
	{
		List<T> rowData = getRowsAsList(rows);
		T[] array = (T[])Array.newInstance(rowClass, rowData.size());
		return (T[]) rowData.toArray( array );
	}

	/**
	 * Returns a List of Objects for the requested <code>rows</code>.
	 */
	public List<T> getRowsAsList(int... rows)
	{
		ArrayList<T> rowData = new ArrayList<T>(rows.length);

		for (int i = 0; i < rows.length; i++)
		{
			rowData.add( getRow(rows[i]) );
		}

		return rowData;
	}

	/**
	 *  Insert a row of data at the <code>row</code> location in the model.
	 */
	public void insertRow(int row, T rowData)
	{
		modelData.add(row, rowData);
		fireTableRowsInserted(row, row);
	}

	/**
	 *  Insert multiple rows of data at the <code>row</code> location in the model.
	 */
	public void insertRows(int row, List<T> rowList)
	{
		modelData.addAll(row, rowList);
		fireTableRowsInserted(row, row + rowList.size() - 1);
	}

	/**
	 *  Insert multiple rows of data at the <code>row</code> location in the model.
	 */
	public void insertRows(int row, T[] rowArray)
	{
		List<T> rowList = new ArrayList<T>(rowArray.length);

		for (int i = 0; i < rowArray.length; i++)
		{
			rowList.add( rowArray[i] );
		}

		insertRows(row, rowList);
	}

	/**
	 *  Moves one or more rows from the inlcusive range <code>start</code> to
	 *  <code>end</code> to the <code>to</code> position in the model.
	 */
	public void moveRow(int start, int end, int to)
	{
		if (start < 0)
		{
			String message = "Start index must be positive: " + start;
			throw new IllegalArgumentException( message );
		}

		if (end > getRowCount() - 1)
		{
			String message = "End index must be less than total rows: " + end;
			throw new IllegalArgumentException( message );
		}

		if (start > end)
		{
			String message = "Start index cannot be greater than end index";
			throw new IllegalArgumentException( message );
		}

		int rowsMoved = end - start + 1;

		if (to < 0
		||  to > getRowCount() - rowsMoved)
		{
			String message = "New destination row (" + to + ") is invalid";
			throw new IllegalArgumentException( message );
		}

		//  Save references to the rows that are about to be moved

		ArrayList<T> temp = new ArrayList<T>(rowsMoved);

		for (int i = start; i < end + 1; i++)
		{
			temp.add(modelData.get(i));
		}

		//  Remove the rows from the current location and add them back
		//  at the specified new location

		modelData.subList(start, end + 1).clear();
		modelData.addAll(to, temp);

		//  Determine the rows that need to be repainted to reflect the move

		int first;
		int last;

		if (to < start)
		{
			first = to;
			last = end;
		}
		else
		{
			first = start;
			last = to + end - start;
		}

		fireTableRowsUpdated(first, last);
	}

	/**
	 *  Remove the specified rows from the model. 

	 */
	public void removeRowRange(int start, int end)
	{
		modelData.subList(start, end + 1).clear();
		fireTableRowsDeleted(start, end);
	}

	/**
	 *  Remove the specified rows from the model. The row indexes in the
	 *  array must be in increasing order.
	
	 */
	public void removeRows(int... rows)
	{
		for (int i = rows.length - 1; i >= 0; i--)
		{
			int row = rows[i];
			modelData.remove(row);
			fireTableRowsDeleted(row, row);
		}
	}

	/**
	 *  Replace a row of data at the <code>row</code> location in the model.

	 */
	public void replaceRow(int row, T rowData)
	{
		modelData.set(row, rowData);
		fireTableRowsUpdated(row, row);
	}

	/**
	 * Sets the Class for the specified column.

	 */
	public void setColumnClass(int column, Class columnClass)
	{
		columnClasses[column] = columnClass;
		fireTableRowsUpdated(0, getRowCount() - 1);
	}

	/**
	 * Sets the editability for the specified column.
	
	 */
	public void setColumnEditable(int column, boolean isEditable)
	{
		isColumnEditable[column] = isEditable ? Boolean.TRUE : Boolean.FALSE;
	}

	/**
	 *  Set the ability to edit cell data for the entire model
	 */
	public void setModelEditable(boolean isModelEditable)
	{
		this.isModelEditable = isModelEditable;
	}

	/*
	 *  Convert an unformatted column name to a formatted column name.
	 */
	public static String formatColumnName(String columnName)
	{
		if (columnName.length() < 3) return columnName;

		StringBuffer buffer = new StringBuffer( columnName );
		boolean isPreviousLowerCase = false;

		for (int i = 1; i < buffer.length(); i++)
		{
			boolean isCurrentUpperCase = Character.isUpperCase( buffer.charAt(i) );

			if (isCurrentUpperCase && isPreviousLowerCase)
			{
				buffer.insert(i, " ");
				i++;
			}

			isPreviousLowerCase = ! isCurrentUpperCase;
		}

		return buffer.toString().replaceAll("_", " ");
	}
}

