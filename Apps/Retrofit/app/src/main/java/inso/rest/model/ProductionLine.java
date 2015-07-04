package inso.rest.model;

import java.util.List;

public class ProductionLine {

    private Integer id;
	private Integer powerPlantId;
	private String name;
	private Double nominalHead;
	private Double nominalDischarge;
	private Double nominalCapacity;
	private Double nominalSpeed;
	private Double runnerDiameter;
	private Double nominalEfficiency;
	private Double actualEfficiency;
	private Double distanceFactor;
	private Double ageFactor;
	private List<Component> components;
    private Evaluation evaluation;

    public ProductionLine() {
    }

	public ProductionLine(String name, Evaluation evaluation) {
		this.name = name;
		this.evaluation = evaluation;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getNominalHead() {
		return nominalHead;
	}

	public void setNominalHead(Double nominalHead) {
		this.nominalHead = nominalHead;
	}

	public Double getNominalDischarge() {
		return nominalDischarge;
	}

	public void setNominalDischarge(Double nominalDischarge) {
		this.nominalDischarge = nominalDischarge;
	}

	public Double getNominalCapacity() {
		return nominalCapacity;
	}

	public void setNominalCapacity(Double nominalCapacity) {
		this.nominalCapacity = nominalCapacity;
	}

	public Double getNominalSpeed() {
		return nominalSpeed;
	}

	public void setNominalSpeed(Double nominalSpeed) {
		this.nominalSpeed = nominalSpeed;
	}

	public Double getRunnerDiameter() {
		return runnerDiameter;
	}

	public void setRunnerDiameter(Double runnerDiameter) {
		this.runnerDiameter = runnerDiameter;
	}

	public Double getNominalEfficiency() {
		return nominalEfficiency;
	}

	public void setNominalEfficiency(Double nominalEfficiency) {
		this.nominalEfficiency = nominalEfficiency;
	}

	public Double getActualEfficiency() {
		return actualEfficiency;
	}

	public void setActualEfficiency(Double actualEfficiency) {
		this.actualEfficiency = actualEfficiency;
	}

	public Double getDistanceFactor() {
		return distanceFactor;
	}

	public void setDistanceFactor(Double distanceFactor) {
		this.distanceFactor = distanceFactor;
	}

	public Double getAgeFactor() {
		return ageFactor;
	}

	public void setAgeFactor(Double ageFactor) {
		this.ageFactor = ageFactor;
	}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPowerPlantId() {
        return powerPlantId;
    }

    public void setPowerPlantId(Integer powerPlantId) {
        this.powerPlantId = powerPlantId;
    }

    public Evaluation getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Evaluation evaluation) {
        this.evaluation = evaluation;
    }

    public List<Component> getComponents() {
        return components;
    }

    public void setComponents(List<Component> components) {
        this.components = components;
    }
}
