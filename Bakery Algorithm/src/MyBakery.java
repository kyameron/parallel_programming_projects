/*
	Cameron Boyd
	CECS 327
	
	Dictates which thread goes when based on the Bakery
	algorithm.

*/

public class MyBakery {
	public static void main(String[] args){
		Bakery bakery = new Bakery(1000);
		
		Thread a = new Thread(bakery);
		Thread b = new Thread(bakery);
		Thread c = new Thread(bakery);
		
		a.start();
		b.start();
		c.start();
	}
}
