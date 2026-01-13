package oops.interfaces;

public class Employee implements Payable, Taxable {

	private String name;
	private double salary;

	public Employee(String name, double salary) {
		super();
		this.name = name;
		this.salary = salary;
	}

	@Override
	public double getPaymentAmount() {
		// TODO Auto-generated method stub
		return salary + calculateTax();
	}

	@Override
	public double calculateTax() {
		// TODO Auto-generated method stub
		return salary + Taxable.getTaxRate();
	}

	@Override
	public void printPaymentInfo() {
		System.out.println("Employee: " + name);
		System.out.println("Salary: $" + salary);
		System.out.println("Tax: $" + calculateTax());
		System.out.println("Total Pay: $" + getPaymentAmount());
	}

}
