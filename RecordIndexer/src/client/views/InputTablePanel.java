package client.views;

import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import shared.communication.DownloadBatch_Output;
import shared.model.Field;
import client.spell.Checker;
import client.views.dialogs.SuggestionDialog;
import client.views.tablestuff.CellRenderer;
import client.views.tablestuff.InputScheme;
import client.views.tablestuff.InputTableModel;

@SuppressWarnings("serial")
public class InputTablePanel extends JScrollPane {
	
	private JTable table;
	private BatchState bs;
	private Checker checker;
	private PopupMenu popup;
	private ArrayList<Field> fields;
	private boolean doneSetup;
	private Cell error;
	
	public InputTablePanel(){
		super();
		table = new JTable();
		bs = BatchState.getInstance();
		checker = new Checker();
	 }

	public void download(DownloadBatch_Output output){
		int recordsNum = output.getProject().getRecordsPerImage();
		fields = output.getFields();
		ArrayList<InputScheme> inputSchemes = generateSchemes(recordsNum,fields);
		generateTablePanel(new InputTableModel(inputSchemes));
		popup = new PopupMenu(suggestContext,fields);
		bs.addListener(batchStateListener);
		doneSetup = false;
		setupInfo();
		doneSetup = true;
		getViewport().add(table);
		table.setFillsViewportHeight(true);
	}
	
	public void setupInfo(){
		int row = bs.getSelectedCell().record;
		int col = bs.getSelectedCell().field;
		table.changeSelection(row, col+1, false, false);
		for(int i=0; i < table.getRowCount(); i++){
			for(int j=1; j < table.getColumnCount(); j++){
				table.setValueAt(bs.getValue(new Cell(i,j-1)), i, j);
			}
		}
	}
	
	private ArrayList<InputScheme> generateSchemes(int records, ArrayList<Field> fields) {
		ArrayList<InputScheme> result = new ArrayList<InputScheme>();
		for (int i = 1; i <= records; ++i) {
			InputScheme scheme = new InputScheme(String.valueOf(i),fields);
			result.add(scheme);
		}
		return result;
	}
    
	private void generateTablePanel(InputTableModel tableModel){
		table = new JTable(tableModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setCellSelectionEnabled(true);
		table.getTableHeader().setReorderingAllowed(false);
		table.changeSelection(0, 1, false, false);
		table.requestFocus();
		table.getSelectionModel().addListSelectionListener(tableSelectionListener);
        table.getColumnModel().getSelectionModel().addListSelectionListener(tableSelectionListener);
		table.getModel().addTableModelListener(tableModelListener);
		table.addMouseListener(mouseAdapter);
		TableColumnModel columnModel = table.getColumnModel();	
		for (int i = 0; i < tableModel.getColumnCount(); ++i) {
			TableColumn column = columnModel.getColumn(i);
			column.setWidth(50);
			column.setCellRenderer(new CellRenderer());
		}
	}
	
	private SuggestionDialog.Context suggestContext = new SuggestionDialog.Context() {
		@Override
		public void useWord(String word) {
			bs.setValue(error, word);
			bs.setStatus(error, false);
		}
	};
	
    private MouseAdapter mouseAdapter = new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e){
			if(SwingUtilities.isRightMouseButton(e)){
				Cell cell = null; 
				int row = table.rowAtPoint(e.getPoint());
				int col = table.columnAtPoint(e.getPoint());
				cell = new Cell(row,col-1);
				if(cell != null && bs.getStatus(cell)){
					error = cell;
					popup.setup(cell,bs.getValue(cell));
					popup.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		}
    };
    
    private BatchStateListener batchStateListener = new BatchStateListener() {
    	
		@Override
		public void valueChanged(Cell cell, String newValue) {
			if(!table.getValueAt(cell.record, cell.field + 1).equals(newValue)){
				table.setValueAt(newValue, cell.record, cell.field + 1);
			}
		}

		@Override
		public void selectedCellChanged(Cell cell) {
			table.changeSelection(cell.record, cell.field + 1, false, false);
		}

		@Override
		public void statusChanged(Cell cell, boolean status) {
		}
    };
    
    private ListSelectionListener tableSelectionListener = new ListSelectionListener() {
		@Override
		public void valueChanged(ListSelectionEvent e) {
			int col = table.getSelectedColumn() - 1;
			if(col < 0){
				col = 0;
			}
			int row = table.getSelectedRow();
			Cell cell = new Cell(row,col);
			bs.setSelectedCell(cell);
		}
    };
    
    private TableModelListener tableModelListener = new TableModelListener() {
    	
		@Override
		public void tableChanged(TableModelEvent t) {
			int row = t.getFirstRow();
			int col = t.getColumn();
			String value = (String) table.getValueAt(row, col);
			Cell cell = new Cell(row,col-1);
			bs.setValue(cell,value);
			
			if(doneSetup){
				if(fields.get(col-1).getKnownData() != null){
					File file = new File(fields.get(col-1).getKnownData());
					try {
						checker.useDictionary(file);
					} catch (IOException e) {
						System.out.println("dictionary file not found!");
					}
					
					checker.suggestSimilarWord(value.toUpperCase());
					Set<String> suggestions = checker.getSuggestions();
	
					if(suggestions.contains(value.toUpperCase()) || value.equals("")){
						if(bs.getStatus(cell)){
							bs.setStatus(cell, false);
						}
					}
					else{
						if(!bs.getStatus(cell)){
							bs.setStatus(cell, true);
						}
					}					
				}
			}
		}
    };	
}
