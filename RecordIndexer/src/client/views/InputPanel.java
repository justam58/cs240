package client.views;

import javax.swing.*;



import shared.communication.DownloadBatch_Output;


@SuppressWarnings("serial")
public class InputPanel extends JTabbedPane{
	
	private InputTablePanel tablePanel;
	private InputFormPanel formPanel;
	
	public InputPanel(){
		tablePanel = new InputTablePanel();
		formPanel = new InputFormPanel();
        this.addTab("Table Entry", tablePanel);
        this.addTab("Form Entry", formPanel);
        this.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
	}
	
	public void submit() {
		this.setComponentAt(0,new JScrollPane());
		this.setComponentAt(1,new JSplitPane());
	}
	
	public void download(DownloadBatch_Output output){
		tablePanel.download(output);
		formPanel.download(output);
		this.setComponentAt(0,tablePanel);
		this.setComponentAt(1,formPanel);
	}
	
	public void setupInfo(){
		tablePanel.setupInfo();
		formPanel.setupInfo();
	}
	
}


