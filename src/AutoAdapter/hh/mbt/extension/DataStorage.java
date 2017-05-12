package hh.mbt.extension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


//Test data is generated and stored in this class

public class DataStorage {
	
	private static Map<String,ArrayList<Object>> storageMap=new HashMap<String,ArrayList<Object>>();
	
	
	protected void resetStoreStorage(){
		storageMap=new HashMap<String,ArrayList<Object>>();
	}
	
	
	protected void addToStorage(boolean store, String label, Object Data){
		
		if(store){
			ArrayList<Object> storageBackupList = new ArrayList<Object>();
			if(storageMap.containsKey(label)){
				storageBackupList=storageMap.get(label);
			}
			storageBackupList.add(Data);
			storageMap.put(label, storageBackupList);
		}
				
	}
	
	public void setStorage(String label, int index, Object Data) throws ArrayIndexOutOfBoundsException, NullPointerException{
		
		ArrayList<Object> storageBackupList = new ArrayList<Object>();
		storageBackupList=storageMap.get(label);
		storageBackupList.set(index, Data);
		storageMap.put(label, storageBackupList);
				
	}
	
	public Object topStorage(String label) throws ArrayIndexOutOfBoundsException, NullPointerException{
		ArrayList<Object> storageBackupList = new ArrayList<Object>();
		storageBackupList=storageMap.get(label);
		return storageBackupList.get(storageBackupList.size() - 1);
		
	}
	
	public Object popStorage(String label) throws ArrayIndexOutOfBoundsException, NullPointerException{
		ArrayList<Object> storageBackupList = new ArrayList<Object>();
		storageBackupList=storageMap.get(label);
		Object retval = storageBackupList.get(storageBackupList.size() - 1);
		storageBackupList.remove(storageBackupList.size() - 1);
		storageMap.put(label, storageBackupList);
		return retval;		
	}
	
	public Object getStorage(String label, int index) throws ArrayIndexOutOfBoundsException, NullPointerException{
		ArrayList<Object> storageBackupList = new ArrayList<Object>();
		storageBackupList=storageMap.get(label);
		return storageBackupList.get(index);
		
	}
	
	public Object getRemStorage(String label, int index) throws ArrayIndexOutOfBoundsException, NullPointerException{
		ArrayList<Object> storageBackupList = new ArrayList<Object>();
		storageBackupList=storageMap.get(label);
		Object retval = storageBackupList.get(index);
		storageBackupList.remove(index);
		storageMap.put(label, storageBackupList);
		return retval;		
	}
	


	
}
