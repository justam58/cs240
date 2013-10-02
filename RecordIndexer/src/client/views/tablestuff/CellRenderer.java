package client.views.tablestuff;

import java.awt.*;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

import client.views.BatchState;
import client.views.Cell;


@SuppressWarnings("serial")
public class CellRenderer extends DefaultTableCellRenderer   {
	
	public CellRenderer() {
		setOpaque(true);
	}

	public Component getTableCellRendererComponent(JTable table,
			Object value, boolean isSelected, boolean hasFocus, int row,
			int column) {
		
		JLabel l = (JLabel) super.getTableCellRendererComponent
					(table, value, isSelected, hasFocus, row, column);
		if(column > 0){
			if(BatchState.getInstance().getStatus(new Cell(row,column-1))){
				l.setBackground(Color.RED);
			}
			else{
				l.setBackground(Color.WHITE);
			}
		}
		return l;
	}
}