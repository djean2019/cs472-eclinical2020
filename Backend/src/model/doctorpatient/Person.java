package model.doctorpatient;

import java.util.List;

public class Person {
	private int personId;
	private String personNumber;
	private String firstName;
	private String middleName;
	private String lastName;
	private String contactPhone;
	private String address;
	private List<Specialization> specialization;
	public Person() {}
	
	public Person(int personId) {
		super();
		this.personId = personId;
	}

	public Person(int personId, String personNumber, String firstName, String middleName, String lastName,
			String contactPhone, String address, List<Specialization> specialization) {
		super();
		this.personId = personId;
		this.personNumber = personNumber;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.contactPhone = contactPhone;
		this.address = address;
		this.specialization = specialization;
	}

	public Person(int personId, String personNumber, String firstName, String middleName, String lastName,
			String contactPhone, String address) {
		super();
		this.personId = personId;
		this.personNumber = personNumber;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.contactPhone = contactPhone;
		this.address = address;
	}
	public Person(String firstName, String middleName, String lastName, String contactPhone,
			String address) {
		super();
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.contactPhone = contactPhone;
		this.address = address;
	}

	public Person(int personId, String firstName, String middleName, String lastName, String contactPhone,
			String address) {
		super();
		this.personId = personId;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.contactPhone = contactPhone;
		this.address = address;
	}
	public int getPersonId() {
		return personId;
	}
	public void setPersonId(int personId) {
		this.personId = personId;
	}
	public String getPersonNumber() {
		return personNumber;
	}
	public void setPersonNumber(String personNumber) {
		this.personNumber = personNumber;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public List<Specialization> getSpecialization() {
		return specialization;
	}

	public void setSpecialization(List<Specialization> specialization) {
		this.specialization = specialization;
	}
}
