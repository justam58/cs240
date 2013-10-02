package client.views;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;

import client.views.dialogs.SuggestionDialog;



import shared.communication.DownloadBatch_Output;
import shared.model.Field;

@SuppressWarnings("serial")
public class InputFormPanel extends JSplitPane {
	
	private JList<Integer> list ;
	private ArrayList<JTextField> textFields;
	private BatchState bs;
	private PopupMenu popup;
	private Cell error;
	
	public InputFormPanel(){
		super();
		bs = BatchState.getInstance();
	}
	
	public void download(DownloadBatch_Output output){
		textFields = new ArrayList<JTextField>();
		int recordsNum = output.getProject().getRecordsPerImage();
		ArrayList<Field> fields = output.getFields();
		generateFormPanel(recordsNum,fields);
		bs.addListener(batchStateListener);
		popup = new PopupMenu(suggestContext,fields);
		setupInfo();
	}
	
	public void setupInfo(){
		int row = bs.getSelectedCell().record;
		int col = bs.getSelectedCell().field;
		list.setSelectedIndex(row);
		textFields.get(col).requestFocus();
		for(int i=0; i < textFields.size(); i++){
			textFields.get(i).setText(bs.getValue(new Cell(row,i)));
			if(bs.getStatus(new Cell(0,i))){
				textFields.get(i).setBackground(Color.RED);
			}
			else{
				textFields.get(i).setBackground(Color.WHITE);
			}
		}
	}
	
	private void generateFormPanel(int recordsNum, ArrayList<Field> fields){
		Integer[] numbers = {};
        for(int i = 1; i < recordsNum + 1; i++){
        	numbers = addElement(numbers, i);
        } 
        list = new JList<Integer>(numbers);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setVisibleRowCount(-1);
        list.setSelectedIndex(0);
        list.getSelectionModel().addListSelectionListener(listSelectionListener);
        JScrollPane scrollListPane= new JScrollPane(list);
        JPanel fieldsPanel = generateFieldsPanel(fields);
        JScrollPane scrollFieldsPane= new JScrollPane(fieldsPanel);
        setLeftComponent(scrollListPane);
        setRightComponent(scrollFieldsPane);
	}
	
	private Integer[] addElement(Integer[] org, Integer added) {
		Integer[] result = Arrays.copyOf(org, org.length +1);
	    result[org.length] = added;
	    return result;
	}
	
	private JPanel generateFieldsPanel(ArrayList<Field> fields) {
    	JPanel panel = new JPanel(new GridLayout(fields.size(),1));
    	for(int i = 0; i < fields.size(); i++){
    		JPanel field = generateField(fields.get(i).getTitle());
    		panel.add(field);
    	}
        return panel;
    }
    
    private JPanel generateField(String field){
    	JTextField textField = new JTextField(10);
        JLabel label= new JLabel(field + ": ");
        label.setLabelFor(textField);
        textField.addMouseListener(mouseAdapter);
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(label);
        panel.add(textField);
        textField.addFocusListener(textFieldFocusListener);
        textFields.add(textField);
        return panel;
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
				for(int i=0; i< textFields.size(); i++){
					if(e.getSource() == textFields.get(i)){
						int row = list.getSelectedIndex();
						cell = new Cell(row,i);
					}
				}
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
			if(list.getSelectedIndex() == cell.record){
				textFields.get(cell.field).setText(newValue);
			}
		}

		@Override
		public void selectedCellChanged(Cell cell) {
			list.setSelectedIndex(cell.record);
			textFields.get(cell.field).requestFocus();
		}

		@Override
		public void statusChanged(Cell cell, boolean status) {
			if(list.getSelectedIndex() == cell.record){
				if(status){
					textFields.get(cell.field).setBackground(Color.RED);
				}
				else{
					textFields.get(cell.field).setBackground(Color.WHITE);
				}
			}
		}
    };
    
    private FocusListener textFieldFocusListener = new FocusListener() {
		@Override
		public void focusGained(FocusEvent f) {
			int row = list.getSelectedIndex();
			for(int i=0; i < textFields.size(); i++){
				if(f.getComponent() == textFields.get(i)){
					bs.setSelectedCell(new Cell(row,i));
				}
			}
		}

		@Override
		public void focusLost(FocusEvent f) {
			int row = list.getSelectedIndex();
			for(int i=0; i < textFields.size(); i++){
				if(f.getComponent() == textFields.get(i)){
					String value = textFields.get(i).getText();
					bs.setValue(new Cell(row,i),value);			
				}
			}
		}
    };
    
    private ListSelectionListener listSelectionListener = new ListSelectionListener() {
		@Override
		public void valueChanged(ListSelectionEvent e) {
			int row = list.getSelectedIndex();
			Cell cell = bs.getSelectedCell();
			cell.record = row;
			bs.setSelectedCell(cell);
			textFields.get(cell.field).requestFocus();
			for(int i=0; i < textFields.size(); i++){
				textFields.get(i).setText(bs.getValue(new Cell(row,i)));
				if(bs.getStatus(new Cell(row,i))){
					textFields.get(i).setBackground(Color.RED);
				}
				else{
					textFields.get(i).setBackground(Color.WHITE);
				}
			}
		}
    };
}
