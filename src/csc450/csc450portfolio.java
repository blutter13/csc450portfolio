package csc450;
//Benjamin Lutter CSC450 Mod 8 Portfolio Project
class concurrencyCounter { //Counter class - uses synchronized methods to obtain a lock on the counter object, ensuring thread safety 
	private int counter = 0;
	public synchronized void add() {
		counter++;
	}
	public synchronized void sub() {
		counter--;
	}
	public synchronized int get() {
		return counter;
	}
}
public class csc450portfolio {
	public static void main(String[] args) {
		concurrencyCounter counter = new concurrencyCounter();
		Runnable increase = () -> { //Runnable to increase the counter in thread 1
			while(counter.get() < 20) {
				counter.add();
				System.out.println("Thread 1 - Current step is: " + counter.get());
			}
		};
		Runnable decrease = () -> { //Runnable to decrease the counter in thread 2
			while(counter.get() > 0) {
				counter.sub();
				System.out.println("Thread 2 - Current step is: " + counter.get());
			}
		};
		Thread thread1 = new Thread(increase);
		Thread thread2 = new Thread(decrease);
		thread1.start();
		try {
			thread1.join(); //thread1 join to prevent thread2 from decrementing the counter while thread1 is still incrementing, causing a race condition 
		}
		catch(InterruptedException e) {
			Thread.currentThread().interrupt();
			System.err.println("Thread 1 interrupted.");
		}
		thread2.start();
		
	}
	
	
}
 