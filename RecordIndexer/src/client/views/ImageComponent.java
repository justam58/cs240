package client.views;

import java.awt.*;
import java.awt.Image;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import client.communication.ServerCommunicator;
import shared.communication.DownloadBatch_Output;
import shared.model.*;


@SuppressWarnings("serial")
public class ImageComponent extends JComponent{
	
	private double scale;
	private int w_originX;
	private int w_originY;;
	private int d_dragStartX;
	private int d_dragStartY;
	private int w_dragStartOriginX;
	private int w_dragStartOriginY;
	private int highlightRow;
	private int highlightColumn;
	private boolean dragging;
	private boolean highlight;
	private boolean invert;
	private Project project;
	private Image image;
	private ArrayList<Field> fields;	
	private DrawingImage imageShape;
	private DrawingRect highlightShape;

;
	private DownloadBatch_Output output;
	private BatchState bs = BatchState.getInstance();
	
	public ImageComponent(){
		initDrag();
		imageShape = null;
		highlightShape = null;
		setBackground(Color.gray);
        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
        addMouseWheelListener(mouseAdapter);
	}
	
	public void invert() {
		if(invert){
			invert = false;
		}
		else{
			invert = true;
		}
        int w = image.getWidth(null);
        int h = image.getHeight(null);
        BufferedImage invertImage =
                new BufferedImage(w, h, ((BufferedImage) image).getType());
        for (int y=0; y < h; ++y){
            for (int x=0; x < w; ++x){
            	int oldRGB = ((BufferedImage) image).getRGB(x, y);
            	Color oldColor = new Color(oldRGB,false);
            	int r = 255 - oldColor.getRed();
            	int g = 255 - oldColor.getGreen();
            	int b = 255 - oldColor.getBlue();
            	Color newColor = new Color(r,g,b);
            	int newRGB = newColor.getRGB();
            	invertImage.setRGB(x, y, newRGB);
            }
        }
        image = invertImage;
        imageShape = new DrawingImage(image, 
        		new Rectangle2D.Double(0, 0, image.getWidth(null), image.getHeight(null)));
		this.repaint();
	}
	
	public void toggle() {
		if(highlight){
			highlight = false;
		}
		else{
			highlight = true;
		}
		this.repaint();
	}
	
	public void submit() {
		imageShape = null;
		highlightShape = null;
		output = null;
		this.repaint();
	}
	
	public void download(DownloadBatch_Output output){
		project = output.getProject();
		fields = output.getFields();
		this.setOutput(output);
		
		highlightRow = 1;
		highlightColumn = 1;
		scale = 1.0;
		highlight = true;
		invert = false;
		w_originX = getWidth()/2;
		w_originY = getHeight()/2;
		
		try {
			String prefix = ServerCommunicator.getInstance().getURLPrefix();
			URL url = new URL(prefix + "//" + output.getImage().getFile());
			image = ImageIO.read(url);
		} catch (MalformedURLException e1) {
			System.out.println("url fail!");
		} catch (IOException e1) {
			System.out.println("image io fail!");
		}
		
		imageShape = new DrawingImage(image, 
				new Rectangle2D.Double(0, 0, image.getWidth(null), image.getHeight(null)));
		highlightShape = new DrawingRect(new Rectangle2D.Double(fields.get(0).getxCoord(), 
				project.getFirstYCoord(), fields.get(0).getWidth(), 
				project.getRecordHeight()), new Color(210, 180, 140, 192));
		bs.addListener(batchStateListener);
		this.updateHighlight();
		this.repaint();
	}
	
	public void zoomIn() {
		scale += 0.03;
		setScale(scale);
	}
	
	public void zoomOut() {
		scale -= 0.03;
		setScale(scale);
	}
	
	public void setScale(double newScale) {
		if( newScale < 0 ){
			scale = 0;
		}
		else{
			scale = newScale;
		}
		this.repaint();
	}
	
	public double getScale() {
		return scale;
	}

	public int getW_originX() {
		return w_originX;
	}

	public void setW_originX(int w_originX) {
		this.w_originX = w_originX;
	}

	public int getW_originY() {
		return w_originY;
	}

	public void setW_originY(int w_originY) {
		this.w_originY = w_originY;
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
	
	private void drawBackground(Graphics2D g2) {
		g2.setColor(getBackground());
		g2.fillRect(0,  0, getWidth(), getHeight());
	}
	
	private void updateHighlight(){	
		highlightShape = new DrawingRect
				(new Rectangle2D.Double(fields.get(highlightColumn-1).getxCoord(), 
				project.getFirstYCoord() + (project.getRecordHeight() * (highlightRow - 1)), 
				fields.get(highlightColumn - 1).getWidth(), 
				project.getRecordHeight()), new Color(210, 180, 140, 192));
		this.repaint();
	}
	
	private void initDrag() {
		dragging = false;
		d_dragStartX = 0;
		d_dragStartY = 0;
		w_dragStartOriginX = 0;
		w_dragStartOriginY = 0;
	}
	
	private int worldToDeviceX(int w_X) {
		double d_X = w_X;
		d_X -= w_originX;
		d_X *= scale;
		d_X += getWidth()/2;
		return (int)d_X;
	}
	
	private int worldToDeviceY(int w_Y) {
		double d_Y = w_Y;
		d_Y -= w_originY;
		d_Y *= scale;
		d_Y += getHeight()/2;
		return (int)d_Y;
	}
	
	private int deviceToWorldX(int d_X) {
		double w_X = d_X;
		w_X -= getWidth()/2;
		w_X /= scale;
		w_X += w_originX;		
		return (int)w_X;
	}
	
	private int deviceToWorldY(int d_Y) {
		double w_Y = d_Y;
		w_Y -= getHeight()/2;
		w_Y /= scale;
		w_Y += w_originY;
		return (int)w_Y;
	}
	
	private MouseAdapter mouseAdapter = new MouseAdapter() {
		
		@Override
		public void mouseClicked(MouseEvent e){
			int d_X = e.getX();
			int d_Y = e.getY();
			int w_X = deviceToWorldX(d_X);
			int w_Y = deviceToWorldY(d_Y);
			
			if(image != null && 
				w_Y > project.getFirstYCoord() && 
				w_X > fields.get(0).getxCoord() &&
				w_Y	< (project.getFirstYCoord() + (project.getRecordHeight()*project.getRecordsPerImage())) &&
				w_X < (fields.get(fields.size()-1).getxCoord() + fields.get(fields.size()-1).getWidth())){
				
				highlightRow = ((w_Y-project.getFirstYCoord())/project.getRecordHeight() + 1);
				highlightColumn = 0;
				for(int i = 0; i < fields.size(); i++){
					if(w_X > fields.get(i).getxCoord()){
						highlightColumn++;
					}
					else{
						break;
					}
				}
				updateHighlight();
				bs.setSelectedCell(new Cell(highlightRow-1, highlightColumn-1));
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			dragging = true;
			d_dragStartX = e.getX();
			d_dragStartY = e.getY();		
			w_dragStartOriginX = w_originX;
			w_dragStartOriginY = w_originY;
		}

		@Override
		public void mouseDragged(MouseEvent e) {	
			if (dragging) {
				int d_deltaX = (e.getX() - d_dragStartX);
				int d_deltaY = (e.getY() - d_dragStartY);
				
				int w_deltaX = (int)(d_deltaX / scale);
				int w_deltaY = (int)(d_deltaY / scale);
				
				w_originX = w_dragStartOriginX - w_deltaX;
				w_originY = w_dragStartOriginY - w_deltaY;
				
				repaint();
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			initDrag();
		}

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			if(e.getWheelRotation() < 0){
				scale += 0.03;
			}
			if(e.getWheelRotation() > 0){
				scale -= 0.03;
			}
			setScale(scale);
			return;
		}	
	};
	
    private BatchStateListener batchStateListener = new BatchStateListener() {

		@Override
		public void selectedCellChanged(Cell newSelectedCell) {
			highlightRow = newSelectedCell.record + 1;
			highlightColumn = newSelectedCell.field + 1;
			updateHighlight();
		}

		@Override
		public void valueChanged(Cell cell, String newValue) {
			
		}

		@Override
		public void statusChanged(Cell cell, boolean status) {
			
		}
    };
	
	private class DrawingImage{

		private Image image;
		private Rectangle2D rect;
		
		public DrawingImage(Image image, Rectangle2D rect) {
			this.image = image;
			this.rect = rect;
		}

		public void draw(Graphics2D g2) {
			Rectangle2D transformedRect = 
					new Rectangle2D.Double(worldToDeviceX((int)rect.getX()),
											worldToDeviceY((int)rect.getY()),
											(int)(rect.getWidth() * scale),
											(int)(rect.getHeight() * scale));
			g2.drawImage(image, (int)transformedRect.getMinX(), 
					(int)transformedRect.getMinY(), 
					(int)transformedRect.getMaxX(), 
					(int)transformedRect.getMaxY(),
					0, 0, image.getWidth(null), 
					image.getHeight(null), null);

		}	
	};
	
	private class DrawingRect{

		private Rectangle2D rect;
		private Color color;
		
		public DrawingRect(Rectangle2D rect, Color color) {
			this.rect = rect;
			this.color = color;
		}

		public void draw(Graphics2D g2) {
			Rectangle2D transformedRect = 
				new Rectangle2D.Double(worldToDeviceX((int)rect.getX()),
										worldToDeviceY((int)rect.getY()),
										(int)(rect.getWidth() * scale),
										(int)(rect.getHeight() * scale));
			g2.setColor(color);
			g2.fill(transformedRect);
		}
	}
	


	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		drawBackground(g2);
		if(imageShape != null){
			imageShape.draw(g2);
		}
		if(highlightShape != null && highlight){
			highlightShape.draw(g2);
		}
	}

	public DownloadBatch_Output getOutput() {
		return output;
	}

	public void setOutput(DownloadBatch_Output output) {
		this.output = output;
	}
	
}
