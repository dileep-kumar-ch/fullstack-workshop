package oops.interfaces;

public class PaymentTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Payable[] payables = {
			    new Invoice("P001", "Laptop", 2, 1000),
			    new Employee("John", 5000)
			};

			double total = 0;
			for (Payable p : payables) {
			    p.printPaymentInfo();
			    total += p.getPaymentAmount();
			    System.out.println();
			}
			System.out.println("Total: $" + total);

	}

}
