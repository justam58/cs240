package client.views.tablestuff;

import java.util.ArrayList;

import shared.model.Field;

public class InputScheme {
	
	private ArrayList<String> values;
	private ArrayList<Field> fields;
	private String number;
	private int columnNum;
	
	public InputScheme(String newNumber ,ArrayList<Field> newFields) {
		number = newNumber;
		fields = newFields;
		values = new ArrayList<String>();
		values.add(newNumber);
		for(int i = 0; i < fields.size(); i++){
			values.add("");
		}
		columnNum = fields.size() + 1;
	}

	public ArrayList<String> getValues() {
		return values;
	}

	public void setValues(String newValue, int index) {
		values.set(index, newValue);
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public int getColumnNum() {
		return columnNum;
	}

	public void setColumnNum(int columnNum) {
		this.columnNum = columnNum;
	}

	public ArrayList<Field> getFields() {
		return fields;
	}

	public void setFields(ArrayList<Field> fields) {
		this.fields = fields;
	}
}