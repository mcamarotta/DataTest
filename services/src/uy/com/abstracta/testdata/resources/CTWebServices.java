package uy.com.abstracta.testdata.resources;






import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import datatypes.DataCombination;
import datatypes.DataVariableAndValues;
import handler.handlerCombinationCTWebNewLogic;







@Path("services")
public class CTWebServices {

	/**
	 * Invoke from browser http://localhost:8080/testdata/rest/services/getSimplePairWise
	 * @return
	 */
	@POST
    @Path("getSimplePairWise")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public  ArrayList<DataCombination> getSimplePairWiseCombination(ArrayList<DataVariableAndValues> variableAndValues2) {
        
    	ArrayList<DataVariableAndValues> variableAndValues = new ArrayList<DataVariableAndValues>();
    	
    	ArrayList<String> values1= new ArrayList<>();
    	values1.add("ValueVar11");
    	values1.add("ValueVar12");
    	DataVariableAndValues var1= new DataVariableAndValues("Var1",values1 );
    	variableAndValues.add(var1);
    	
    	ArrayList<String> values2= new ArrayList<>();
    	values2.add("ValueVar21");
    	values2.add("ValueVar22");
    	DataVariableAndValues var2= new DataVariableAndValues("Var1",values2 );
    	
    	variableAndValues.add(var2);
    	
    	
    	ArrayList<DataCombination> combinationResponse = handlerCombinationCTWebNewLogic.getPairWiseCombinationWithSimplePairWise(variableAndValues);
    	
    	return combinationResponse;
    }
	
	@POST
    @Path("getEachChoice")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public  ArrayList<DataCombination> getPairWiseCombinationWithEachChoice(ArrayList<DataVariableAndValues> variableAndValues2) {
        
    	ArrayList<DataVariableAndValues> variableAndValues = new ArrayList<DataVariableAndValues>();
    	
    	ArrayList<String> values1= new ArrayList<>();
    	values1.add("ValueVar11");
    	values1.add("ValueVar12");
    	DataVariableAndValues var1= new DataVariableAndValues("Var1",values1 );
    	variableAndValues.add(var1);
    	
    	ArrayList<String> values2= new ArrayList<>();
    	values2.add("ValueVar21");
    	values2.add("ValueVar22");
    	DataVariableAndValues var2= new DataVariableAndValues("Var1",values2 );
    	
    	variableAndValues.add(var2);
    	
    	
    	ArrayList<DataCombination> combinationResponse = handlerCombinationCTWebNewLogic.getPairWiseCombinationWithEachChoice(variableAndValues);
    	
    	return combinationResponse;
    }
	@POST
    @Path("getAllCombination")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public  ArrayList<DataCombination> getPairWiseCombinationWithAllCombination(ArrayList<DataVariableAndValues> variableAndValues2) {
        
    	ArrayList<DataVariableAndValues> variableAndValues = new ArrayList<DataVariableAndValues>();
    	
    	ArrayList<String> values1= new ArrayList<>();
    	values1.add("ValueVar11");
    	values1.add("ValueVar12");
    	DataVariableAndValues var1= new DataVariableAndValues("Var1",values1 );
    	variableAndValues.add(var1);
    	
    	ArrayList<String> values2= new ArrayList<>();
    	values2.add("ValueVar21");
    	values2.add("ValueVar22");
    	DataVariableAndValues var2= new DataVariableAndValues("Var1",values2 );
    	
    	variableAndValues.add(var2);
    	
    	
    	ArrayList<DataCombination> combinationResponse = handlerCombinationCTWebNewLogic.getPairWiseCombinationWithAllCombination(variableAndValues);
    	
    	return combinationResponse;
    }
	
	
	@POST
    @Path("testJSONVariableAndValuesPassing")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public  ArrayList<DataCombination> test2(ArrayList<DataVariableAndValues> variableAndValues) {
        
//    	ArrayList<DataVariableAndValues> variableAndValues = new ArrayList<DataVariableAndValues>();
    	
//    	ArrayList<String> values1= new ArrayList<>();
//    	values1.add("ValueVar11");
//    	values1.add("ValueVar12");
//    	DataVariableAndValues var1= new DataVariableAndValues("Var1",values1 );
//    	variableAndValues.add(var1);
//    	
//    	ArrayList<String> values2= new ArrayList<>();
//    	values2.add("ValueVar21");
//    	values2.add("ValueVar22");
//    	DataVariableAndValues var2= new DataVariableAndValues("Var1",values2 );
//    	
//    	variableAndValues.add(var2);
    	
    	
    	ArrayList<DataCombination> combinationResponse = handlerCombinationCTWebNewLogic.getPairWiseCombinationWithSimplePairWise(variableAndValues);
    	
    	return combinationResponse;
    }
}
