package com.modularinsurance.pojo;

public class FormPojo {

	Person person;
	
	String type;
	String yourAge;
	String price;
	
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public String getYourAge() {
		return yourAge;
	}

	public void setYourAge(String yourAge) {
		this.yourAge = yourAge;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public FormPojo() {

	}

	class Person {
		String firstName;
		String secondName;

		public Person(String firstName, String secondName) {
			this.firstName = firstName;
			this.secondName = secondName;
		}

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getSecondName() {
			return secondName;
		}

		public void setSecondName(String secondName) {
			this.secondName = secondName;
		}
	}
}
