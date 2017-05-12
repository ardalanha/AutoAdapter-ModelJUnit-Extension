package hh.mbt.extension.model;

import java.util.ArrayList;
import org.junit.Assert;

import hh.mbt.SUT.SimpleStackDouble;
import hh.mbt.SUT.SimpleStackString;
import hh.mbt.extension.AutoAdapter;
import hh.mbt.extension.DataStorage;
import nz.ac.waikato.modeljunit.Action;
import nz.ac.waikato.modeljunit.FsmModel;
import nz.ac.waikato.modeljunit.RandomTester;
import nz.ac.waikato.modeljunit.StopOnFailureListener;
import nz.ac.waikato.modeljunit.VerboseListener;
import nz.ac.waikato.modeljunit.coverage.ActionCoverage;
import nz.ac.waikato.modeljunit.coverage.StateCoverage;
import nz.ac.waikato.modeljunit.coverage.TransitionCoverage;


public class StackEFSM implements FsmModel {

	public enum States { Empty, NotEmpty };
    private States state;
    
    //Context Variables
    private int Items;
    private final int max=2;
    

    
    //Generated test data storage
    private DataStorage storage = new DataStorage();
    
    //Adapter class connects abstract methods of model to SUT
    private AutoAdapter adapter;
    private StackOperations stackOp = new StackOperations();
    
    final ArrayList<Object> Push = stackOp.Push();
    final ArrayList<Object> Pop = stackOp.Pop();
    final ArrayList<Object> isEmpty = stackOp.isEmpty();
    
  
    
    
    public Object getState() {
        return state;
    }
    
    public void reset(boolean testing) {
    	Items = 0;
    	
    	//adapter = new AutoAdapter(new SimpleStackString(), true);
    	adapter = new AutoAdapter(new SimpleStackDouble(), false);
    	
    	state = States.Empty;

    }
    
    @Action
    public void PushE2N() throws Throwable {
    	
    	adapter.adapt(Push);
    	
    	Items++;
    	
    	boolean expected=false;
    	boolean result = (Boolean) adapter.adapt(isEmpty);
    	Assert.assertEquals(expected, result);
    	
    	state = States.NotEmpty;
    	
    }
    public boolean PushE2NGuard() {
        return state == States.Empty;
    }
    
    @Action
    public void PushN2N() throws Throwable {
    	
    	adapter.adapt(Push);
    	
    	Items++;
    	
    	boolean expected=false;
    	boolean result = (Boolean) adapter.adapt(isEmpty);
    	Assert.assertEquals(expected, result);
    	
    	state = States.NotEmpty;
    	
    }
    public boolean PushN2NGuard() {
        return state == States.NotEmpty & Items<max;
    }
    
    @Action
    public void PopN2E() throws Throwable {
    	
    	int actual = (Integer) adapter.adapt(Pop);
    	int expected1 = (Integer) storage.popStorage("PushData");
    	Assert.assertEquals(expected1, actual);
    	
    	Items--;
    	
    	boolean expected2=true;
    	boolean result = (Boolean) adapter.adapt(isEmpty);
    	Assert.assertEquals(expected2, result);
    	
    	state = States.Empty;
    	
    }
    public boolean PopN2EGuard() {
        return state == States.NotEmpty & Items==1;
    }
    
    @Action
    public void PopN2N() throws Throwable {
    	
    	int actual = (Integer) adapter.adapt(Pop);
    	int expected1 = (Integer) storage.popStorage("PushData");
    	Assert.assertEquals(expected1, actual);
    	
    	Items--;
    	
    	boolean expected2=false;
    	boolean result = (Boolean) adapter.adapt(isEmpty);
    	Assert.assertEquals(expected2, result);
    	
    	state = States.NotEmpty;
    	
    }
    public boolean PopN2NGuard() {
        return state == States.NotEmpty & Items>1;
    }
    


/*

    @Action
    public void Push() throws Throwable {
    	
    	//Action - Call Adapter
    	adapter.adapt(Push);
    	
    	//Context Variable Manipulation
    	Items++;
    	
    	//Oracle
    	//boolean expected=false;
    	//...Query Method - Call Adapter
    	//boolean result = (Boolean) adapter.adapt(isEmpty);
    	//...Assertion
    	//Assert.assertEquals(expected, result);
    	
    	//Set State
    	state = States.NotEmpty;
    }
    public boolean PushGuard() {
        return state == States.Empty || 
        		(state == States.NotEmpty & Items<max);
    }

    @Action
    public void Pop() throws Throwable {
    	
    	
    	
    	//Set State
    	if(state == States.NotEmpty){
    		//Action - Call Adapter
        	//int actual = (Integer) 
        	adapter.adapt(Pop);
        	
        	//Context Variable Manipulation
        	Items--;
        	
        	//Oracle
        	//...Get parameter data from storage
        	//int expected = (Integer) storage.popStorage("PushData");
        	//...Assertion
        	//Assert.assertEquals(expected, actual);
	    	if(Items==0)
	    		state = States.Empty;   
	    	else
	    		state = States.NotEmpty;
    	}
    	else if(state == States.Empty){
    		//Oracle
        	boolean exceptionOccurred = false;
        	try {
        		//Query Method - Call Adapter
        		adapter.adapt(Pop);
        	}
        	catch (IllegalStateException e) {
        	exceptionOccurred = true;
        	}
        	//...Assertion
        	Assert.assertTrue(exceptionOccurred);  
        	state = States.Bad;
    	}
    }
    public boolean PopGuard() {
        return state == States.NotEmpty || state == States.Empty;
    }

*/    

    public static void main(String[] argv) {
    	StackEFSM model = new StackEFSM();
        //Tester tester = new LookaheadTester(model);
        RandomTester tester = new RandomTester(model);
        //Tester tester = new AllRoundTester(model);
        //Tester tester = new GreedyTester(model);
        //tester.buildGraph();
        tester.addListener(new VerboseListener());
        tester.addListener(new StopOnFailureListener());
        tester.addCoverageMetric(new TransitionCoverage());
        tester.addCoverageMetric(new StateCoverage());
        tester.addCoverageMetric(new ActionCoverage());
        tester.generate(100);
        tester.printCoverage();
    }
}