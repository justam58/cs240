package shared.communication;

import shared.model.Image;

/** Class that store the output of getting the sample image */
public class GetSampleImage_Output {
	
	private Image image;
	
	/** Constructor with all parameters */
	public GetSampleImage_Output(Image image) {
		this.image = image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Image getImage() {
		return image;
	}

	@Override
	public String toString() {
		return image.getFile() + "\n";
	}
	
	@Override
	public boolean equals(Object obj) {
		GetSampleImage_Output o = (GetSampleImage_Output) obj;
		if((this == null && o != null) || (this != null && o == null)){
			return false;
		}
		return(this.getImage().equals(o.getImage()));
	}
}
