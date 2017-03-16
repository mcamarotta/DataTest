package uy.com.abstracta.testdata.resources;

import java.util.ArrayList;
import javax.ws.rs.Consumes;
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
   	
    	ArrayList<DataCombination> combinationResponse = handlerCombinationCTWebNewLogic.getPairWiseCombinationWithSimplePairWise(variableAndValues2);
    	
    	return combinationResponse;
    }
	
	@POST
    @Path("getEachChoice")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public  ArrayList<DataCombination> getPairWiseCombinationWithEachChoice(ArrayList<DataVariableAndValues> variableAndValues2) {
        
		ArrayList<DataCombination> combinationResponse = handlerCombinationCTWebNewLogic.getPairWiseCombinationWithEachChoice(variableAndValues2);
		
    	return combinationResponse;
    }
	@POST
    @Path("getAllCombination")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public  ArrayList<DataCombination> getPairWiseCombinationWithAllCombination(ArrayList<DataVariableAndValues> variableAndValues2) {

    	ArrayList<DataCombination> combinationResponse = handlerCombinationCTWebNewLogic.getPairWiseCombinationWithAllCombination(variableAndValues2);
    	
    	return combinationResponse;
    }
	
	@POST
    @Path("getProwCombinationStep1")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public  ArrayList<DataCombination> getPairWiseCombinationWithProwStep1(ArrayList<DataVariableAndValues> variableAndValues2) {
        
    	  	
    	ArrayList<DataCombination> combinationResponse = handlerCombinationCTWebNewLogic.getPairWiseCombinationWithProwFirstStep(variableAndValues2);
    	
    	return combinationResponse;
    }
	
	@POST
    @Path("getProwCombinationStep2")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public  ArrayList<DataCombination> getPairWiseCombinationWithProwStep2(ArrayList<DataVariableAndValues> variableAndValues2) {
        
    	//TODO: Add new datatype with 4 values like the legacy tool in the second step of PROW. 
		//It means DataVariableValuesWeightBooleanToRemove and add it on testDataBackEnd project as DataVariableAndValues
		//
    	ArrayList<DataCombination> combinationResponse = handlerCombinationCTWebNewLogic.getPairWiseCombinationWithProwSecondStep(variableAndValues2);
    	
    	return combinationResponse;
    }
}
