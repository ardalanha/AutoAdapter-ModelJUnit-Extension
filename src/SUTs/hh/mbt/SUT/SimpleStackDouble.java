package hh.mbt.SUT;

public class SimpleStackDouble {
	
	private ListElementDouble stackTop = null;

	public boolean isEmpty() {
		return stackTop == null;
	}

	public double pop() {
		double returnValue = 0;
		if (isEmpty()) {
			throw new java.lang.IllegalStateException();
		} else {
			returnValue = stackTop.value;
			stackTop = stackTop.nextElement;
		}
		return returnValue;
	}
	
    public void push(double item) {
    	ListElementDouble listElement = new ListElementDouble();
        listElement.value = item;
        listElement.nextElement = stackTop;
        stackTop = listElement;   
    }

	public double top() {
		double returnValue = 0;
		if (isEmpty()) {
			throw new java.lang.IllegalStateException();
		} else {
			returnValue = stackTop.value;
		}
		return returnValue;
	}

}
