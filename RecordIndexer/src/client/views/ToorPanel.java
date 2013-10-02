package client.views;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ToorPanel extends JPanel implements ActionListener {
	
	private JButton zoomInButton;
	private JButton zoomOutButton;
	private JButton invertImageButton;
	private JButton toggleHighlightsButton;
	private JButton saveButton;
	private JButton submitButton;
	private Context context;
	
	public interface Context {
		public void bigger();
		public void smaller();
		public void invert();
		public void toggle();
		public void save();
		public void submit();
	}

	public ToorPanel(Context c){
		
    	context = c;
    	createButtons();
		
		setLayout(new FlowLayout(FlowLayout.LEADING));
		
		add(zoomInButton);
		add(zoomOutButton);
		add(invertImageButton);
		add(toggleHighlightsButton);
		add(saveButton);
		add(submitButton);
		
	}
	
	public void enabledButtons(boolean status){
		zoomInButton.setEnabled(status);
		zoomOutButton.setEnabled(status);
		invertImageButton.setEnabled(status);
		toggleHighlightsButton.setEnabled(status);
		saveButton.setEnabled(status);
		submitButton.setEnabled(status);
	}
	
	private void createButtons(){
		zoomInButton = new JButton("Zoom In");
		zoomInButton.setActionCommand("in");
		zoomInButton.addActionListener(this);
		zoomOutButton = new JButton("Zoom Out");
		zoomOutButton.setActionCommand("out");
		zoomOutButton.addActionListener(this);
		invertImageButton = new JButton("Invert Image");
		invertImageButton.setActionCommand("invert");
		invertImageButton.addActionListener(this);
		toggleHighlightsButton = new JButton("Toggle Highlights");
		toggleHighlightsButton.setActionCommand("toggle");
		toggleHighlightsButton.addActionListener(this);
		saveButton = new JButton("Save");
		saveButton.setActionCommand("save");
		saveButton.addActionListener(this);
		submitButton = new JButton("Submit");
		submitButton.setActionCommand("submit");
		submitButton.addActionListener(this);
		
		enabledButtons(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	    if ("in".equals(e.getActionCommand())) {
			if (context != null) {
				context.bigger();
			}
	    } 
	    else if ("out".equals(e.getActionCommand())) {
			if (context != null) {
				context.smaller();
			}
	    } 
	    else if ("invert".equals(e.getActionCommand())) {
			if (context != null) {
				context.invert();
			}
	    } 
	    else if ("toggle".equals(e.getActionCommand())) {
			if (context != null) {
				context.toggle();
			}
	    } 
	    else if ("save".equals(e.getActionCommand())) {
			if (context != null) {
				context.save();
			}
	    } 
	    else if ("submit".equals(e.getActionCommand())) {
			if (context != null) {
				context.submit();
			}
	    } 
	}

}
