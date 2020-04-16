package model.desease;

public class DeseaseCategory {
	private int deseaseTypeId;
	private String deseaseTypeName;
	public DeseaseCategory() {}
	public DeseaseCategory(int deseaseTypeId, String deseaseTypeName) {
		super();
		this.deseaseTypeId = deseaseTypeId;
		this.deseaseTypeName = deseaseTypeName;
	}
	
	public DeseaseCategory(int deseaseTypeId) {
		super();
		this.deseaseTypeId = deseaseTypeId;
	}
	public DeseaseCategory(String deseaseTypeName) {
		super();
		this.deseaseTypeName = deseaseTypeName;
	}

	public int getDeseaseTypeId() {
		return deseaseTypeId;
	}
	public void setDeseaseTypeId(int deseaseTypeId) {
		this.deseaseTypeId = deseaseTypeId;
	}
	public String getDeseaseTypeName() {
		return deseaseTypeName;
	}
	public void setDeseaseTypeName(String deseaseTypeName) {
		this.deseaseTypeName = deseaseTypeName;
	}
}
