package client.views.tablestuff;

import java.util.ArrayList;

import javax.swing.table.*;



@SuppressWarnings("serial")
public class InputTableModel extends AbstractTableModel {

	private ArrayList<InputScheme> inputSchemes;

	public InputTableModel(ArrayList<InputScheme> inputSchemes) {
		this.inputSchemes = inputSchemes;
	}

	@Override
	public int getColumnCount() {
		return inputSchemes.get(0).getColumnNum();
	}

	@Override
	public String getColumnName(int column) {
		String result = null;
		if (column >0 && column < getColumnCount()) {
			result = inputSchemes.get(0).getFields().get(column-1).getTitle();
		} 
		else if(column == 0){
			result = "Record Number";
		}
		else {
			throw new IndexOutOfBoundsException();
		}
		return result;
	}

	@Override
	public int getRowCount() {
		return inputSchemes.size();
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		if(column == 0 ){
			return false;
		}
		return true;
	}

	@Override
	public Object getValueAt(int row, int column) {
		Object result = null;
		if (row >= 0 && row < getRowCount() && column >= 0
				&& column < getColumnCount()) {
			InputScheme is = inputSchemes.get(row);
			result = is.getValues().get(column);
		} 
		else {
			throw new IndexOutOfBoundsException();
		}
		return result;
	}

	@Override
	public void setValueAt(Object value, int row, int column) {
		if (row >= 0 && row < getRowCount() && column >= 0
				&& column < getColumnCount()) {
			InputScheme is = inputSchemes.get(row);
			String c = null;
			if (column > 0) {
				c = (String)value;
				if (c == null) {
					return;
				}
			}
			is.setValues(c,column);
			fireTableCellUpdated(row, column);
		} 
		else {
			throw new IndexOutOfBoundsException();
		}		
	}

}