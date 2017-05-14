package hh.mbt.extension.model.CRUDEFSM;

import java.util.ArrayList;
import org.junit.Assert;

import hh.mbt.extension.AutoAdapter;
import hh.mbt.extension.DataStorage;
import hh.mbt.extension.model.CRUDEFSM.AbstractOperations.FlightBookingOps;
import nz.ac.waikato.modeljunit.Action;
import nz.ac.waikato.modeljunit.FsmModel;
import nz.ac.waikato.modeljunit.RandomTester;
import nz.ac.waikato.modeljunit.StopOnFailureListener;
import nz.ac.waikato.modeljunit.VerboseListener;
import nz.ac.waikato.modeljunit.coverage.ActionCoverage;
import nz.ac.waikato.modeljunit.coverage.StateCoverage;
import nz.ac.waikato.modeljunit.coverage.TransitionCoverage;
import restClientPackage.ClientSide;

////Link for FlightBooking SUT: https://github.com/raminarmanfar/Online-Ticket-booking-Distributed-program-using-RESTFul-Tomcat-JAVA-

public class CRUDEFSM implements FsmModel {


    
    private static int NumberOfEntry;
    
    private final int Max = 20;
    
    private static int EntryFromModel;
    
    public enum Actions {
    	Create("Create"), Update("Update"), Delete("Delete");
    	private String actionname; 
        private Actions(String action) { 
            this.actionname = action; 
        } 
        
        @Override 
        public String toString(){ 
            return actionname; 
        } 

    	}
    
    private static Actions actionOnRun;

    
    //Generated test data storage
    private DataStorage storage = new DataStorage();
    //conversion repository specific to target SUTs
    private CRUDConversionRepo convRep = new CRUDConversionRepo();
    //Model specific generators
    CRUDGenerators gens = new CRUDGenerators();
    
    //Adapter class connects abstract methods of model to SUT
    ClientSide SUT = new ClientSide();
    private AutoAdapter adapter = new AutoAdapter(SUT, convRep, true);
    
    private FlightBookingOps CrudOp = new FlightBookingOps();
    
    final ArrayList<Object> Init = CrudOp.Init();
    final ArrayList<Object> Create = CrudOp.Create();
    final ArrayList<Object> Retrieve = CrudOp.Retrieve();
    final ArrayList<Object> Update = CrudOp.Update();
    final ArrayList<Object> Delete = CrudOp.Delete();
    
    public CRUDEFSM(){
    	adapter.addGenerator(gens);
    }
  
    
    
    public Object getState() {
        return "Stable";
    }
    
    public void reset(boolean testing) {
    	
    	NumberOfEntry=0;
    	
    	//SUT Init
    	try {
			adapter.adapt(Init);
		} catch (Throwable e) {
			e.printStackTrace();
		}   	
  	

    }
    
    @Action
    public void CreateEntry() throws Throwable {
    	setActionOnRun(Actions.Create);
    	
    	adapter.adapt(Create);
    	
    	   	
    	NumberOfEntry++;
    	
    	//Assertion (RetrieveAll)
    	for(int i = 0; i < NumberOfEntry; i++){
    		setEntryFromModel(i);
    		Object actual[] = (Object[]) adapter.adapt(Retrieve);
        	Object expected[] = {storage.getStorage("Name", i),storage.getStorage("Value", i)};
        	Assert.assertEquals("Assert Updated Name",actual[0],expected[0]);
        	Assert.assertEquals("Assert Updated Value",actual[1],expected[1]);
    	}
    	
    	
    }
    public boolean CreateEntryGuard() {
        return NumberOfEntry<Max;
    }
    
    @Action
    public void UpdateAnyEntry() throws Throwable {
    	setActionOnRun(Actions.Update);
    	
    	adapter.adapt(Update);
    	
    	//Update Storage State
    	storage.setStorage("Name", (Integer)storage.topStorage("RandModelEntry"), storage.popStorage("Name"));
    	storage.setStorage("Value", (Integer)storage.popStorage("RandModelEntry"), storage.popStorage("Value"));
    	
    	//Assertion (RetrieveAll)
    	for(int i = 0; i < NumberOfEntry; i++){
    		setEntryFromModel(i);
    		Object actual[] = (Object[]) adapter.adapt(Retrieve);
        	Object expected[] = {storage.getStorage("Name", i),storage.getStorage("Value", i)};
        	Assert.assertEquals("Assert Updated Name",actual[0],expected[0]);
        	Assert.assertEquals("Assert Updated Value",actual[1],expected[1]);
    	}
    	    	
    	
    }
    public boolean UpdateAnyEntryGuard() {
        return NumberOfEntry>0;
    }
    
    
    @Action
    public void DeleteAnyEntry() throws Throwable {
    	setActionOnRun(Actions.Delete);
    	
    	adapter.adapt(Delete);
    	
    	NumberOfEntry--;
    	
    	
    	//Assertion (Retrieve Deleted Entry)
    	Object[] actualE = (Object[]) adapter.adapt(Retrieve);
    	Assert.assertNull("Assert that the rand entry is deleted",actualE);
    	
    	//Update Storage State
    	storage.getRemStorage("Name", (Integer)storage.topStorage("RandModelEntry"));
    	storage.getRemStorage("Value", (Integer)storage.topStorage("RandModelEntry"));
    	storage.getRemStorage("PNR", (Integer)storage.popStorage("RandModelEntry"));
    	
    	//Assertion (RetrieveAll)
    	for(int i = 0; i < NumberOfEntry; i++){
    		setEntryFromModel(i);
    		Object actual[] = (Object[]) adapter.adapt(Retrieve);
        	Object expected[] = {storage.getStorage("Name", i),storage.getStorage("Value", i)};
        	Assert.assertEquals("Assert Updated Name",actual[0],expected[0]);
        	Assert.assertEquals("Assert Updated Value",actual[1],expected[1]);
    	}
    	
    	
    }
    public boolean DeleteAnyEntryGuard() {
    	return NumberOfEntry>0;
    }
    

    

    public static void main(String[] argv) {
    	CRUDEFSM model = new CRUDEFSM();
        //Tester tester = new LookaheadTester(model);
        RandomTester tester = new RandomTester(model);
        //Tester tester = new AllRoundTester(model);
        //Tester tester = new GreedyTester(model);
        //tester.buildGraph();
        tester.setResetProbability(0);
        tester.addListener(new VerboseListener());
        tester.addListener(new StopOnFailureListener());
        tester.addCoverageMetric(new TransitionCoverage());
        tester.addCoverageMetric(new StateCoverage());
        tester.addCoverageMetric(new ActionCoverage());
        tester.generate(100);
        tester.printCoverage();
    }

	
	
	public static int getNumberOfEntry() {
		return NumberOfEntry;
	}

	public static String getActionOnRun() {
		return actionOnRun.toString();
	}

	private static void setActionOnRun(Actions actionOnRun) {
		CRUDEFSM.actionOnRun = actionOnRun;
	}

	public static int getEntryFromModel() {
		return EntryFromModel;
	}

	private static void setEntryFromModel(int entryFromModel) {
		EntryFromModel = entryFromModel;
	}


}