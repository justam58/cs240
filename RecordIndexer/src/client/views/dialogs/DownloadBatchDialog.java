package client.views.dialogs;

import java.awt.*;
import java.awt.Image;
import java.awt.event.*;
import java.io.IOException;
import java.net.*;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import shared.communication.*;
import shared.model.*;

import client.communication.ServerCommunicator;
import client.views.BatchState;


@SuppressWarnings("serial")
public class DownloadBatchDialog extends JDialog implements ActionListener {
		
	    private String VIEW = "ViewewSample";
	    private String DOWNLOAD = "Download";
	    private String CANCEL = "cancel";
		private JComboBox<String> projectList;
		private String username;
		private String password;
		private Context context;
	    private ServerCommunicator sc = ServerCommunicator.getInstance();
	    
		public interface Context {
			public void downloaded(DownloadBatch_Output output);
		}

	    public DownloadBatchDialog(String uname, String pword, Context c) {
	    	//setup
	    	super(null,"Download Batch", Dialog.ModalityType.APPLICATION_MODAL);
	    	setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
	    	setResizable(false);
	    	context = c;
	    	username = uname;
	        password = pword;
	        //create panels and add
	        generateList();
	        JComponent buttonPane = createButtonPanel();
	        JPanel inputPane = createInputPanel();
	        add(inputPane);
	        add(buttonPane);
	    	// set the dialog in the middle
			GraphicsConfiguration gc = getGraphicsConfiguration();  
	    	Rectangle bounds = gc.getBounds();  
	    	Dimension size = getPreferredSize();
	    	setLocation((int) ((bounds.width / 2) - (size.getWidth() / 2)), 
	    			(int) ((bounds.height / 2) - (size.getHeight() / 2)));
	    	pack();
	    }
	    
		private String[] addElement(String[] org, String added) {
			String[] result = Arrays.copyOf(org, org.length +1);
		    result[org.length] = added;
		    return result;
		}

	    private JComponent createButtonPanel() {
	        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER));
	        JButton downloadButton = new JButton("Download");
	        JButton cancelButton = new JButton("cancel");
	        downloadButton.setActionCommand(DOWNLOAD);
	        cancelButton.setActionCommand(CANCEL);
	        downloadButton.addActionListener(this);
	        cancelButton.addActionListener(this);
	        p.add(downloadButton);
	        p.add(cancelButton);
	        return p;
	    }
	    
		private JPanel createInputPanel(){
			JPanel inputPane = new JPanel(new FlowLayout(FlowLayout.CENTER));
	        JLabel projectLabel = new JLabel("Project ");
	        projectLabel.setLabelFor(projectList);
	        JButton viewButton = new JButton("View Sample");
	        viewButton.setActionCommand(VIEW);
	        viewButton.addActionListener(this);
	        inputPane.add(projectLabel);
	        inputPane.add(projectList);
	        inputPane.add(viewButton);
	        return inputPane;
		}
		
		private void generateList(){
	        GetProjects_Input input= new GetProjects_Input(username,password);
	        GetProjects_Output output = sc.getProjects(input);
	        ArrayList<Project> projects = output.getProjects();
	        
	        String[] projectStrings = {};
	        for(int i = 0; i < projects.size(); i++){
	        	projectStrings = addElement(projectStrings,projects.get(i).getTitle());
	        }
	    	projectList = new JComboBox<String>(projectStrings);
		}
	    
		@Override
	    public void actionPerformed(ActionEvent e) {
	        String cmd = e.getActionCommand();
	        int projectID = projectList.getSelectedIndex()+1;
	        if (VIEW.equals(cmd)) { 
	            GetSampleImage_Input input = new GetSampleImage_Input(username, password, projectID);
	            GetSampleImage_Output output = sc.getSampleImage(input);
	            if (output != null) {
	        		String prefix = ServerCommunicator.getInstance().getURLPrefix();
	        		Image image = null;
					try {
						URL url = new URL(prefix + "//" + output.getImage().getFile());
						image = ImageIO.read(url);
					} catch (MalformedURLException e1) {
						System.out.println("connect fail!");
						e1.printStackTrace();
					} catch (IOException e1) {
						System.out.println("image io fail!");
						e1.printStackTrace();
					}
	            	ImageIcon imageIcon = new ImageIcon(image);
	                JOptionPane.showMessageDialog(this,"",
	                    String.format("Sample image from %s",projectList.getSelectedObjects()),
	                    JOptionPane.PLAIN_MESSAGE, imageIcon);
	        		
	            } 
	            else {
	                JOptionPane.showMessageDialog(this,
	                    "Didn't get the output from server or invalid input",
	                    "Failed",
	                    JOptionPane.ERROR_MESSAGE);
	            }
	        } 
	        else if(DOWNLOAD.equals(cmd)){
	        	DownloadBatch_Input input = new DownloadBatch_Input(username, password,projectID);
	        	DownloadBatch_Output output = sc.downloadBatch(input);
	        	if (output != null) {
	        		BatchState.getInstance().generate(output.getProject().getRecordsPerImage(), 
	        										output.getFields().size());
					if (context != null) {
						context.downloaded(output);
					}
					dispose();
        		
	            } else {
	                JOptionPane.showMessageDialog(this,
	                    "Didn't get the output from server or invalid input",
	                    "Failed",
	                    JOptionPane.ERROR_MESSAGE);
	            }
	        }
	        else { 
	            dispose();
	        }
	    }

	}