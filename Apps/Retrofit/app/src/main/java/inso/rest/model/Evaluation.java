package inso.rest.model;

public class Evaluation {

	private Double state;
	private boolean operable;

	public Evaluation() {
	}

	public Double getState() {
		return state;
	}

	public void setState(Double state) {
		this.state = state;
	}

	public boolean isOperable() {
		return operable;
	}

	public void setOperable(boolean operable) {
		this.operable = operable;
	}
}
