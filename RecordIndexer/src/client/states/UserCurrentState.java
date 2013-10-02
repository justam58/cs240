package client.states;

import java.awt.Dimension;
import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

import client.views.Cell;

import shared.communication.DownloadBatch_Output;

@SuppressWarnings("serial")
public class UserCurrentState implements Serializable{
	
	private String[][] values;
	private Boolean[][] status;
	private Cell selectedCell;
	private ArrayList<Cell> errors;
	private DownloadBatch_Output batch;
	private Point windowLocation;
	private Dimension windowSize;
	private int horLocation;
	private int verLocation;
	private double scale;
	private int originX;
	private int originY;
	private boolean highlight;
	private boolean invert;
	
    private static UserCurrentState instance = new UserCurrentState();
    
    public static UserCurrentState getInstance() {
        return instance;
    }
    
	public UserCurrentState(){
		
	}

	public String[][] getValues() {
		return values;
	}

	public void setValues(String[][] values) {
		this.values = values;
	}

	public Cell getSelectedCell() {
		return selectedCell;
	}

	public void setSelectedCell(Cell selectedCell) {
		this.selectedCell = selectedCell;
	}

	public Point getWindowLocation() {
		return windowLocation;
	}

	public void setWindowLocation(Point windowLocation) {
		this.windowLocation = windowLocation;
	}

	public Dimension getWindowSize() {
		return windowSize;
	}

	public void setWindowSize(Dimension windowSize) {
		this.windowSize = windowSize;
	}

	public int getHorLocation() {
		return horLocation;
	}

	public void setHorLocation(int horLocation) {
		this.horLocation = horLocation;
	}

	public int getVerLocation() {
		return verLocation;
	}

	public void setVerLocation(int verLocation) {
		this.verLocation = verLocation;
	}

	public double getScale() {
		return scale;
	}

	public void setScale(double scale) {
		this.scale = scale;
	}

	public int getOriginX() {
		return originX;
	}

	public void setOriginX(int originX) {
		this.originX = originX;
	}

	public int getOriginY() {
		return originY;
	}

	public void setOriginY(int originY) {
		this.originY = originY;
	}

	public boolean isHighlight() {
		return highlight;
	}

	public void setHighlight(boolean highlight) {
		this.highlight = highlight;
	}

	public boolean isInvert() {
		return invert;
	}

	public void setInvert(boolean invert) {
		this.invert = invert;
	}

	public DownloadBatch_Output getBatch() {
		return batch;
	}

	public void setBatch(DownloadBatch_Output batch) {
		this.batch = batch;
	}

	public ArrayList<Cell> getErrors() {
		return errors;
	}

	public void setErrors(ArrayList<Cell> errors) {
		this.errors = errors;
	}

	public Boolean[][] getStatus() {
		return status;
	}

	public void setStatus(Boolean[][] status) {
		this.status = status;
	}

}
