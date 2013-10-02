package client.views.dialogs;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Set;

import javax.swing.*;
import javax.swing.event.*;

@SuppressWarnings("serial")
public class SuggestionDialog extends JDialog  implements ActionListener {
	
	private String CANCEL = "Cancel";
    private String USE = "Use";

    private JList<String> list;
    private JButton useButton;
    
	public interface Context {
		public void useWord(String word);
	}

	private Context context;
    
    public SuggestionDialog(Set<String> suggestions, Context c) {
    	// setup
    	super(null,"Suggestions", Dialog.ModalityType.APPLICATION_MODAL);
    	setResizable(false);
    	setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
    	context = c;
        // create
        JComponent buttonPane = createButtonPanel();
        JScrollPane scrollListPane = creatList(suggestions);
        // add
        add(scrollListPane);
        add(buttonPane);
        // place it in the middle
    	GraphicsConfiguration gc = getGraphicsConfiguration();  
    	Rectangle bounds = gc.getBounds();  
    	Dimension size = getPreferredSize();
    	setLocation((int) ((bounds.width / 2) - (size.getWidth() / 2)), 
    			(int) ((bounds.height / 2) - (size.getHeight() / 2)));  
		pack();
    }
    
    private JScrollPane creatList(Set<String> suggestions){
		String[] words = suggestions.toArray(new String[0]);
        list = new JList<String>(words);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        list.getSelectionModel().addListSelectionListener(listSelectionListener);
        JScrollPane scrollListPane = new JScrollPane(list);
        return scrollListPane;
    }
    
    private JComponent createButtonPanel() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton cancelButton = new JButton("Cancel");
        useButton = new JButton("Use Suggestion");
        cancelButton.setActionCommand(CANCEL);
        useButton.setActionCommand(USE);
        cancelButton.addActionListener(this);
        useButton.addActionListener(this);
        useButton.setEnabled(false);
        p.add(cancelButton);
        p.add(useButton);
        return p;
    }

	@Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (USE.equals(cmd)) { 
        	String word = list.getSelectedValue();
        	if(context != null){
        		context.useWord(word);
        	}
        	dispose();
        } 
        else { 
        	dispose();
        }
    }
	
    private ListSelectionListener listSelectionListener = new ListSelectionListener() {
		@Override
		public void valueChanged(ListSelectionEvent e) {
			useButton.setEnabled(true);
		}
    };
}
