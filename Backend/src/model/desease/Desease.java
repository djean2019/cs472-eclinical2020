package model.desease;

public class Desease {
	private int deseaseId;
	private String deseaseName;
	private DeseaseCategory deseaseCategory;
	public Desease() {}
	public Desease(int deseaseId, String deseaseName, DeseaseCategory deseaseCategory) {
		super();
		this.deseaseId = deseaseId;
		this.deseaseName = deseaseName;
		this.deseaseCategory = deseaseCategory;
	}
	public Desease(String deseaseName, DeseaseCategory deseaseCategory) {
		super();
		this.deseaseName = deseaseName;
		this.deseaseCategory = deseaseCategory;
	}

	public int getDeseaseId() {
		return deseaseId;
	}
	public void setDeseaseId(int deseaseId) {
		this.deseaseId = deseaseId;
	}
	public String getDeseaseName() {
		return deseaseName;
	}
	public void setDeseaseName(String deseaseName) {
		this.deseaseName = deseaseName;
	}
	public DeseaseCategory getdeseaseCategory() {
		return deseaseCategory;
	}
	public void setdeseaseCategory(DeseaseCategory deseaseCategory) {
		this.deseaseCategory = deseaseCategory;
	}
}
