package oops.interfaces;

public class Invoice implements Payable{
	private String partNumber;
	private String description;
	private int quantity;
	private double pricePerItem;
	
	

	public Invoice(String partNumber, String description, int quantity, double pricePerItem) {
		super();
		this.partNumber = partNumber;
		this.description = description;
		this.quantity = quantity;
		this.pricePerItem = pricePerItem;
	}

	@Override
	public double getPaymentAmount() {
		// TODO Auto-generated method stub
		return quantity * pricePerItem;
	}
//
//	@Override
//	public void printPaymentInfo() {
//		// TODO Auto-generated method stub
//		System.out.println("Invoice");
//		System.out.println("part: " + description);
//		System.out.println("amount $: " + getPaymentAmount());
//		
//	}

}
