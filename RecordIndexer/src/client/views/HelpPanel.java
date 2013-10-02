package client.views;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.*;
import client.communication.ServerCommunicator;
import shared.communication.DownloadBatch_Output;
import shared.model.Field;

@SuppressWarnings("serial")
public class HelpPanel extends JTabbedPane {
		
	private JEditorPane help = new JEditorPane();
	private ImageComponent image = new ImageComponent();
	private BatchState bs = BatchState.getInstance();
	private ArrayList<Field> fields;
	
	public HelpPanel(){
		help.setEditable(false);
		help.setContentType("text/html");
		image.setVisible(false);
		JScrollPane scrollHelp = new JScrollPane(help);
		addTab("Field Help", scrollHelp);
		addTab("Image Navigation", image);
        setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
	}
	
	private void updateHelp(int field){
		try {
			String prefix = ServerCommunicator.getInstance().getURLPrefix();
			URL url = new URL(prefix + "//" + fields.get(field).getHelpHTML());
			help.setPage(url);
		} catch (IOException e) {
			System.out.println("set page fail!");
			e.printStackTrace();
		}
	}
	
	public void submit() {
		help.setText("");
	}
	
	public void download(DownloadBatch_Output output){
		fields = output.getFields();
		updateHelp(0);
		bs.addListener(batchStateListener);
	}
	
    private BatchStateListener batchStateListener = new BatchStateListener() {
    	
		@Override
		public void valueChanged(Cell cell, String newValue) {
		}

		@Override
		public void selectedCellChanged(Cell newSelectedCell) {
			updateHelp(newSelectedCell.field);
			
		}

		@Override
		public void statusChanged(Cell cell, boolean status) {
		}
    };
}
