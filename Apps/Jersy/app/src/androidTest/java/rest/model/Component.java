package rest.model;

import java.math.BigDecimal;
import java.util.Date;

public class Component {

	private Integer id;
	private Integer partId;
	private String name;
	
	// "Inbetriebnahme"
	private Date commissioningDate;
	
	// "Anschaffungspreis"
	private BigDecimal costPrice;
	
	// "Wertungsgruppe"
	private Integer riskGroup;
	
	//Graph f. Berechnung der Betriebsdauerpunkte
	private Integer operatingTimeGraphPoint1;

	private Integer operatingTimeGraphPoint2;
	
	// "Schwachstellenfaktor"
	private Double vulnerabilityFactor;
	
	// "Gewichtung"
	private Double weight;

    private Evaluation evaluation;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPartId() {
		return partId;
	}

	public void setPartId(Integer partId) {
		this.partId = partId;
	}

	public Date getCommissioningDate() {
		return commissioningDate;
	}

	public void setCommissioningDate(Date commissioningDate) {
		this.commissioningDate = commissioningDate;
	}

	public BigDecimal getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(BigDecimal costPrice) {
		this.costPrice = costPrice;
	}

	public Integer getRiskGroup() {
		return riskGroup;
	}

	public void setRiskGroup(Integer riskGroup) {
		this.riskGroup = riskGroup;
	}

	public Integer getOperatingTimeGraphPoint1() {
		return operatingTimeGraphPoint1;
	}

	public void setOperatingTimeGraphPoint1(Integer operatingTimeGraphPoint1) {
		this.operatingTimeGraphPoint1 = operatingTimeGraphPoint1;
	}

	public Integer getOperatingTimeGraphPoint2() {
		return operatingTimeGraphPoint2;
	}

	public void setOperatingTimeGraphPoint2(Integer operatingTimeGraphPoint2) {
		this.operatingTimeGraphPoint2 = operatingTimeGraphPoint2;
	}

	public Double getVulnerabilityFactor() {
		return vulnerabilityFactor;
	}

	public void setVulnerabilityFactor(Double vulnerabilityFactor) {
		this.vulnerabilityFactor = vulnerabilityFactor;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

    public Evaluation getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Evaluation evaluation) {
        this.evaluation = evaluation;
    }
}
