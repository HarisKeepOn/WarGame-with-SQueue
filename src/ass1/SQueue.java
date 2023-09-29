package ass1;

import java.util.Random;

public class SQueue<T> implements QueueInterface<T>, Shufflable {
	private T[] queue; // Circular array
	private int frontIndex;
	private int backIndex;
	private boolean initialized = false;
	private static final int DEFAULT_CAPACITY = 50;
	private static final int MAX_CAPACITY = 10000;

	public SQueue() {
		this(DEFAULT_CAPACITY);
	}

	public SQueue(int initialCapacity) {
		if (initialCapacity <= MAX_CAPACITY) {
			@SuppressWarnings("unchecked")
			T[] tempQueue = (T[]) new Object[initialCapacity + 1];
			queue = tempQueue;
			frontIndex = 0;
			backIndex = initialCapacity;
			initialized = true;
		} else
			throw new IllegalStateException("Attempt to create a queue " +
					"whose capacity exceeds " +
					"allowed maximum.");
	}

	private void checkInitialization() {
		if (!initialized)
			throw new SecurityException("SQueue object is not initialized " +
					"properly.");
	}

	public void enqueue(T newEntry) {
		checkInitialization();
		backIndex = (backIndex + 1) % queue.length;
		if (backIndex == frontIndex) { // Queue is full
			throw new FullQueueException();
		}
		else {
			queue[backIndex] = newEntry;
		}
	}

	public T getFront() {
		checkInitialization();
		if (isEmpty())
			throw new EmptyQueueException();
		else
			return queue[frontIndex];
	}

	public T dequeue() {
		checkInitialization();
		if (isEmpty())
			throw new EmptyQueueException();
		else {
			T front = queue[frontIndex];
			queue[frontIndex] = null;
			frontIndex = (frontIndex + 1) % queue.length;
			return front;
		}
	}

	public boolean isEmpty() {
		return frontIndex == ((backIndex + 1) % queue.length);
	}

	public void clear() {
		if (!isEmpty()) {
			for (int index = frontIndex; index != backIndex; index = (index+1)%queue.length ) {
				queue[index] = null;
			}

			queue[backIndex] = null;
		}

		frontIndex = 0;
		backIndex = queue.length - 1;
	}

	public boolean isFull() {
		return frontIndex == ((backIndex + 2) % queue.length);
	}

	// Shuffle elements 
	public void shuffle() {
		checkInitialization();
		int size = (backIndex - frontIndex + queue.length) % queue.length;
		Random random = new Random();
		
		// Fisher-Yates shuffle algorithm
		for (int i = size - 1; i > 0; i--) {
			int index = random.nextInt(i + 1);
			
			// Swap
			T temp = queue[(frontIndex + index) % queue.length];
			queue[(frontIndex + index) % queue.length] = queue[(frontIndex + i) % queue.length];
			queue[(frontIndex + i) % queue.length] = temp;
		}


	}

	public int getSize() {
		return (backIndex - frontIndex + queue.length) % queue.length;
	}

	public String toString()
	{
		String result = "SQueue object; size = " + getSize() + ":\n";
		for (int index = 0; index < queue.length; index++)
		{
			result = result + "[" + index + "] ";
			if ( (index >= frontIndex && index <= backIndex) ||
				(backIndex < frontIndex && (index >= frontIndex || index <= backIndex)))
				result = result + queue[index] + "\n";
		}
		return result;
	} 
}
