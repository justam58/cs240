package client.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import shared.model.Field;

import client.spell.*;

import client.views.dialogs.SuggestionDialog;
import client.views.dialogs.SuggestionDialog.Context;

@SuppressWarnings("serial")
public class PopupMenu extends JPopupMenu{
	
	private Context suggestContext;
	private ArrayList<Field> fields; 
	private Cell error;
	private Checker checker;
	private String word;
	
	public PopupMenu(Context suggestContext, ArrayList<Field> fields){
		this.suggestContext = suggestContext;
		this.fields = fields;
		this.error = null;
		this.word = null;
		checker = new Checker();
        JMenuItem sugestionItem = new JMenuItem("See Suggestion");
        sugestionItem.addActionListener(actionListener);
        add(sugestionItem);
	}
	
	public void setup(Cell error, String word){
		this.error = error;
		this.word = word;
	}

	private ActionListener actionListener = new ActionListener() {

		@SuppressWarnings("deprecation")
		@Override
		public void actionPerformed(ActionEvent e) {
			File file = new File(fields.get(error.field).getKnownData());
			try {
				checker.useDictionary(file);
			} catch (IOException i) {
				System.out.println("dictionary file not found!");
			}
			checker.suggestSimilarWord(word);
			Set<String> suggestions = checker.getSuggestions();
			SuggestionDialog s = new SuggestionDialog(suggestions, suggestContext);
			s.show();
		}
    };

}
