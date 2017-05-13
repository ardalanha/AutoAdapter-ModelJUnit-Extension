package hh.mbt.SUT.StackString;

public class SimpleStackString {
	

	private ListElementString stackTop = null;

	public boolean isEmpty() {
		return stackTop == null;
	}

	public String pop() {
		String returnValue = null;
		if (isEmpty()) {
			throw new java.lang.IllegalStateException();
		} else {
			returnValue = stackTop.value;
			stackTop = stackTop.nextElement;
		}
		return returnValue;
	}
	
    public void push(String item) {
    	ListElementString listElement = new ListElementString();
        listElement.value = item;
        listElement.nextElement = stackTop;
        stackTop = listElement;   
    }

	public String peek() {
		String returnValue = null;
		if (isEmpty()) {
			throw new java.lang.IllegalStateException();
		} else {
			returnValue = stackTop.value;
		}
		return returnValue;
	}

}
