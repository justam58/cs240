package client.views.dialogs;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Arrays;
import javax.swing.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import client.communication.ServerCommunicator;
import client.states.UserCurrentState;
import client.views.IndexerFrame;
import shared.communication.*;
import shared.model.*;


@SuppressWarnings("serial")
public class LoginDialog extends JDialog implements ActionListener {
	
    private String LOGIN = "Login";
    private String EXIT = "Exit";

    private JPasswordField passwordField;
    private JTextField usernameField;
    private ServerCommunicator sc;
    
    public LoginDialog() {
    	//set up
    	super(null,"Login to Indexer", Dialog.ModalityType.APPLICATION_MODAL);
    	setResizable(false);
    	setTitle("Login to Indexer");
    	setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
    	sc = ServerCommunicator.getInstance();
        // create
        JPanel usernamePane = createUsernamePanel();
        JComponent buttonPane = createButtonPanel();
        JPanel passwordPane = createPasswordPanel();
        //add
        add(usernamePane);
        add(passwordPane);
        add(buttonPane);
    	//place it in the middle
    	GraphicsConfiguration gc = getGraphicsConfiguration();  
    	Rectangle bounds = gc.getBounds();  
    	Dimension size = getPreferredSize();
    	setLocation((int) ((bounds.width / 2) - (size.getWidth() / 2)), 
    			(int) ((bounds.height / 2) - (size.getHeight() / 2)));  
		pack();
    }
    
    private JPanel createUsernamePanel(){
        JPanel usernamePane = new JPanel(new FlowLayout(FlowLayout.CENTER));
        usernameField = new JTextField(25);
        JLabel usernameLabel = new JLabel("Username: ");
        usernameLabel.setLabelFor(usernameField);
        usernamePane.add(usernameLabel);
        usernamePane.add(usernameField);
    	return usernamePane;
    }
    
    private JPanel createPasswordPanel(){
    	JPanel passwordPane = new JPanel(new FlowLayout(FlowLayout.CENTER));
        passwordField = new JPasswordField(25);
        passwordField.setActionCommand(LOGIN);
        passwordField.addActionListener(this);
        JLabel passwordLabel = new JLabel("Password: ");
        passwordLabel.setLabelFor(passwordField);
        passwordPane.add(passwordLabel);
        passwordPane.add(passwordField);
        return passwordPane;
    }
    
    private JComponent createButtonPanel() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton loginButton = new JButton("Login");
        JButton exitButton = new JButton("Exit");
        loginButton.setActionCommand(LOGIN);
        exitButton.setActionCommand(EXIT);
        loginButton.addActionListener(this);
        exitButton.addActionListener(this);
        p.add(loginButton);
        p.add(exitButton);
        return p;
    }
    
    private UserCurrentState getUserFile(String username){
        boolean foundFile = true;
        UserCurrentState ucs = null;
		XStream xStream = new XStream(new DomDriver());		
		try {
			InputStream inFile = new BufferedInputStream(new FileInputStream(username+".xml"));
			ucs = (UserCurrentState)xStream.fromXML(inFile);
			inFile.close();
		} catch (FileNotFoundException e1) {
			System.out.println("file not found");
			foundFile = false;
		} catch (IOException e2) {
			System.out.println("close file fail!");
			e2.printStackTrace();
		}
		if(foundFile){
			return ucs;
		}
		return null;
    }

	@Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if (LOGIN.equals(cmd)) { 
        	String usernameInput = usernameField.getText();
            String passwordInput = String.valueOf(passwordField.getPassword());
            ValidateUser_Input input = new ValidateUser_Input(usernameInput, passwordInput);
            ValidateUser_Output output = sc.validateUser(input);
            if (output != null &&  output.isValid()) {
            	User user = output.getUser();
            	
                JOptionPane.showMessageDialog(this,
                    String.format("Welcome, %s %s.\nYou have indexes %d records.", 
                    user.getFirstName(), user.getLastName(), user.getIndexedRecords()),
                    "Welcome to Indexer",JOptionPane.PLAIN_MESSAGE);
                
                UserCurrentState ucs = getUserFile(usernameInput);
        		IndexerFrame indexer = new IndexerFrame(usernameInput, passwordInput, ucs);
        		indexer.setVisible(true);
				dispose();      		
            } else {
                JOptionPane.showMessageDialog(this,
                    "Invalid username and/or password",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE);
            }

            //Zero out the possible password, for security.
            Arrays.fill(passwordField.getPassword(), '0');

            passwordField.selectAll();
            resetFocus();
        } 
        else { //Exit
            System.exit(0);
        }
    }

    protected void resetFocus() {
        passwordField.requestFocusInWindow();
    }

}
