package revex.inso.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Evaluation {

	private Double state;
	private boolean operable;

	public Evaluation() {
	}

	public Evaluation(Double state, boolean operable) {
		this.state = state;
		this.operable = operable;
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
