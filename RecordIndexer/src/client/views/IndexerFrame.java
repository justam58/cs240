package client.views;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import client.communication.ServerCommunicator;
import client.states.UserCurrentState;
import client.views.dialogs.DownloadBatchDialog;
import client.views.dialogs.LoginDialog;
import shared.communication.*;

@SuppressWarnings("serial")
public class IndexerFrame extends JFrame{
	
	private String username;
	private String password;
    private JMenuItem downloadMenuItem;
    private JMenuItem logoutMenuItem;
    private JMenuItem exitMenuItem;
    private InputPanel inputPanel;
    private HelpPanel helpPanel;
    private ToorPanel toorPanel;
    private ImageComponent imageComponent;
    private JSplitPane horizontalSplitPane;
    private JSplitPane verticalSplitPane;
    private int imageID;
    private BatchState bs;
    private ServerCommunicator sc;
	
    public IndexerFrame(String uname, String pword, UserCurrentState ucs) {
    	// setup
        super("Indexer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 600));
    	setSize(new Dimension(800, 600));
        username = uname;
        password = pword;
        imageID = -1;
        bs = BatchState.getInstance();
        sc = ServerCommunicator.getInstance();
        // place the frame in the middle
    	GraphicsConfiguration gc = getGraphicsConfiguration();  
    	Rectangle bounds = gc.getBounds();  
    	Dimension size = getPreferredSize();
    	setLocation((int) ((bounds.width / 2) - (size.getWidth() / 2)), 
    			(int) ((bounds.height / 2) - (size.getHeight() / 2)));  
    	// create
        createComponents();
        createMenu();
        addWindowListener(windowAdapter);
        // retrieve user state
        if(ucs != null){
        	setUserState(ucs);
        }
        pack();
    }
    private void createMenu(){
    	JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu menu = new JMenu("File");
        menu.setMnemonic('f');
        menuBar.add(menu);
        downloadMenuItem = new JMenuItem("Download Batch", KeyEvent.VK_D);
        downloadMenuItem.addActionListener(actionListener);
        menu.add(downloadMenuItem);
        logoutMenuItem = new JMenuItem("Logout", KeyEvent.VK_L);
        logoutMenuItem.addActionListener(actionListener);
        menu.add(logoutMenuItem);
        exitMenuItem = new JMenuItem("Exit", KeyEvent.VK_X);
        exitMenuItem.addActionListener(actionListener);
        menu.add(exitMenuItem); 
    }
    
    private void createComponents() {        
        imageComponent = new ImageComponent();
        inputPanel = new InputPanel();
        helpPanel = new HelpPanel();
        toorPanel = new ToorPanel(toorContext);
        // split
        horizontalSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, inputPanel, helpPanel);
        verticalSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, imageComponent, horizontalSplitPane);
        horizontalSplitPane.setResizeWeight(0.5);
        verticalSplitPane.setResizeWeight(0.8);
        // add
        JPanel rootPanel = new JPanel(new BorderLayout());
        rootPanel.add(verticalSplitPane, BorderLayout.CENTER);
        rootPanel.add(toorPanel, BorderLayout.NORTH);

        this.add(rootPanel);
    }
    
    private void saveUserState(){
		UserCurrentState ucs = new UserCurrentState();
		ucs.setValues(BatchState.getInstance().getValues());
		ucs.setSelectedCell(BatchState.getInstance().getSelectedCell());
		ucs.setScale(imageComponent.getScale());
		ucs.setHighlight(imageComponent.isHighlight());
		ucs.setInvert(imageComponent.isInvert());
		ucs.setOriginX(imageComponent.getW_originX());
		ucs.setOriginY(imageComponent.getW_originY());
		ucs.setBatch(imageComponent.getOutput());
		ucs.setWindowLocation(this.getLocationOnScreen());
		ucs.setWindowSize(getSize());
		ucs.setHorLocation(horizontalSplitPane.getDividerLocation());
		ucs.setVerLocation(verticalSplitPane.getDividerLocation());
		ucs.setStatus(BatchState.getInstance().getAllStatus());
		setUserFile(ucs);
    }
    
    private void setUserFile(UserCurrentState ucs){
    	XStream xStream = new XStream(new DomDriver());
		try {
			OutputStream outFile = new BufferedOutputStream
					(new FileOutputStream(username + ".xml"));
			xStream.toXML(ucs, outFile);
			outFile.close();
		} catch (FileNotFoundException e) {
			System.out.println("file not found!");
		} catch (IOException e) {
			System.out.println("file close fail!");
		}
    }
    
    private void setUserState(UserCurrentState ucs){
    	if(ucs.getBatch() != null){
    		int row = ucs.getBatch().getProject().getRecordsPerImage();
    		int col = ucs.getBatch().getFields().size();
    		BatchState.getInstance().generate(row, col);
    		BatchState.getInstance().setValues(ucs.getValues());
	    	BatchState.getInstance().setAllStatus(ucs.getStatus());
			inputPanel.download(ucs.getBatch());
			helpPanel.download(ucs.getBatch());
			imageComponent.download(ucs.getBatch());
	    	imageComponent.setScale(ucs.getScale());
	    	imageComponent.setHighlight(ucs.isHighlight());
	    	imageComponent.setInvert(ucs.isInvert());
	    	imageComponent.setW_originX(ucs.getOriginX());
	    	imageComponent.setW_originY(ucs.getOriginY());
	    	BatchState.getInstance().setSelectedCell(ucs.getSelectedCell());
			toorPanel.enabledButtons(true);
			downloadMenuItem.setEnabled(false);	 
			imageID = ucs.getBatch().getImage().getImageID();
    	}
    	else{
			toorPanel.enabledButtons(false);
			downloadMenuItem.setEnabled(true);
    	}
    	this.setLocation(ucs.getWindowLocation());
    	this.setPreferredSize(ucs.getWindowSize());
    	horizontalSplitPane.setDividerLocation(ucs.getHorLocation());
    	verticalSplitPane.setDividerLocation(ucs.getVerLocation());
    }

    private WindowAdapter windowAdapter = new WindowAdapter() {
  
        public void windowClosing(WindowEvent e) {
        	saveUserState();
            System.exit(0);
        }
    };
    
    private ActionListener actionListener = new ActionListener() {
    	
	    @SuppressWarnings("deprecation")
		public void actionPerformed(ActionEvent e) {
	    	
	        if (e.getSource() == downloadMenuItem) {
	        	DownloadBatchDialog db = new DownloadBatchDialog
	        						(username, password, downloadContext);
	        	db.show();
	        }
	        else if (e.getSource() == logoutMenuItem) {
	        	saveUserState();
	        	LoginDialog login = new LoginDialog();
	        	dispose();
				login.setVisible(true);
	        }
	        else if (e.getSource() == exitMenuItem) {
	        	saveUserState();
	            System.exit(0);
	        }
	    }
    };
    
	private DownloadBatchDialog.Context downloadContext = new DownloadBatchDialog.Context() {
		@Override
		public void downloaded(DownloadBatch_Output output) {
			inputPanel.download(output);
			helpPanel.download(output);
			imageComponent.download(output);
			downloadMenuItem.setEnabled(false);
			toorPanel.enabledButtons(true);
			imageID = output.getImage().getImageID();
		}
	};
	
	private ToorPanel.Context toorContext = new ToorPanel.Context() {

		@Override
		public void bigger() {
			imageComponent.zoomIn();
		}

		@Override
		public void smaller() {
			imageComponent.zoomOut();
		}

		@Override
		public void invert() {
			imageComponent.invert();
		}

		@Override
		public void toggle() {
			imageComponent.toggle();
		}

		@Override
		public void save() {
			saveUserState();
		}

		@Override
		public void submit() {
			String[][] info = bs.getValues();
			ArrayList<String> values = new ArrayList<String>();
	        for (int i = 0; i < info.length; i++){
	            for (int j = 0; j < info[0].length; j++){
	            	if(info[i][j] == null){
	            		info[i][j] = "";
	            	}
	            	values.add(info[i][j]);
	            }
	        }
	        SubmitBatch_Input input = new SubmitBatch_Input(imageID, values, username, password);
	        SubmitBatch_Output output = sc.submitBatch(input);
	        if(!output.isValid()){
                System.out.println("submit fail!");
	        }
	        else{
				inputPanel.submit();
				helpPanel.submit();
				imageComponent.submit();
				toorPanel.enabledButtons(false);
				downloadMenuItem.setEnabled(true);
	        }
		}
	};
       
}
