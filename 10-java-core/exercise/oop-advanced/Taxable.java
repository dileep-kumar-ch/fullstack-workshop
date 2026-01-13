package oops.interfaces;

public interface Taxable {
	double calculateTax();
	static double getTaxRate()  {
		return 0.18; //18%
	}

}
