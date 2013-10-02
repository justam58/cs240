package shared.communication;

/** Class that store the output of submitting the batch */
public class SubmitBatch_Output {
	
	private boolean valid;
	
	/** Constructor with all parameters */
	public SubmitBatch_Output(boolean valid) {
		this.valid = valid;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}
	
	@Override
	public String toString() {
		if(valid){
			return "TRUE\n";
		}
		else{
			return "FAILED\n";
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		SubmitBatch_Output o = (SubmitBatch_Output) obj;
		if((this == null && o != null) || (this != null && o == null)){
			return false;
		}
		return(this.isValid() == o.isValid());
	}
}
