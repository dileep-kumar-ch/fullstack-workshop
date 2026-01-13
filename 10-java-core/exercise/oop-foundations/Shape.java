package oops.polymorphism;

public abstract class Shape {
	abstract double area();

	abstract double perimeter();

	void dispalyInfo() {

		System.out.println("Area: " + area());
		System.out.println("Perimeter: " + perimeter());

	}

}
