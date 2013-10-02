package client.views;

import java.util.ArrayList;
import java.util.List;


interface BatchStateListener {
	
	public void valueChanged(Cell cell, String newValue);
	
	public void selectedCellChanged(Cell newSelectedCell);
	
	public void statusChanged(Cell cell, boolean status);
}

public class BatchState {
	
	private String[][] values;
	private Boolean[][] allStatus;
	private Cell selectedCell;
	private List<BatchStateListener> listeners;
	
    private static BatchState instance = new BatchState();

    public static BatchState getInstance() {
        return instance;
    }
  
    public BatchState() {
		selectedCell = new Cell(0,0);
		listeners = new ArrayList<BatchStateListener>();
	}
	
	public void generate(int records, int fields) {
		values = new String[records][fields];
		allStatus = new Boolean[records][fields];
        for (int i = 0; i < allStatus.length; i++){
            for (int j = 0; j < allStatus[0].length; j++){
            	allStatus[i][j] = false;
            }
        }
        for (int i = 0; i < values.length; i++){
            for (int j = 0; j < values[0].length; j++){
            	values[i][j] = "";
            }
        }
	}
		
	public void addListener(BatchStateListener l) {
		listeners.add(l);
	}
	
	public void setValue(Cell cell, String value) {
		String temp = values[cell.record][cell.field];
		if(temp == null){
			temp = "";
		}
		values[cell.record][cell.field] = value;
		if(!temp.equals(value)){
			for (BatchStateListener l : listeners) {
				l.valueChanged(cell, value);
			}
		}
	}
	
	public String getValue(Cell cell) {
		if(values[cell.record][cell.field] == null){
			values[cell.record][cell.field] = "";
		}
		return values[cell.record][cell.field];
	}
	
	public void setSelectedCell(Cell selCell) {
		Cell temp = selectedCell;
		selectedCell = selCell;
		if(!temp.equals(selectedCell)){
			for (BatchStateListener l : listeners) {
				l.selectedCellChanged(selCell);
			}
		}
	}
	
	public Cell getSelectedCell() {
		return selectedCell;
	}

	public String[][] getValues() {
		return values;
	}

	public void setValues(String[][] values) {
		this.values = values;
	}

	public Boolean[][] getAllStatus() {
		return allStatus;
	}

	public void setAllStatus(Boolean[][] allStatus) {
		this.allStatus = allStatus;
	}
	
	public void setStatus(Cell cell, boolean status) {
		allStatus[cell.record][cell.field] = status;
		for (BatchStateListener l : listeners) {
			l.statusChanged(cell, status);
		}
	}
	
	public boolean getStatus(Cell cell) {
		return allStatus[cell.record][cell.field];
	}
	
}
