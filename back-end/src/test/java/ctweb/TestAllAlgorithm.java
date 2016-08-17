package ctweb;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;

//import static org.junit.Assert.assertArrayEquals;
//import static org.junit.Assert.fail;

import org.testng.annotations.Test;

//import org.junit.Test;
//import org.junit.runner.RunWith;

//import com.tngtech.java.junit.dataprovider.DataProvider;
//import com.tngtech.java.junit.dataprovider.UseDataProvider;
//import com.tngtech.java.junit.dataprovider.DataProviderRunner;

import datatypes.DataCombination;
import datatypes.DataVariableAndValues;
import datatypes.EnumAlgorithmNames;
import handler.handlerCombinationCTWebNewLogic;


public class TestAllAlgorithm {

	@DataProvider(name="setUpDataForTests")
	public static Object[][] setUpDataForTests() throws Exception {

		//reading all the files on the files test directories. This directory has one level of tree. 
		//TODO:Cambiar el absolutpath para uno relativo
		ArrayList<Path> files = getFiles(Paths.get("C:/Users/Michel/workspace/CTWebLogic/src/test/resources/testGoodFiles"));
		
		
		if(files == null || files.isEmpty())
			throw new Exception("There is no files on the directory or something happens when reading the files");
			
		//The variables to return at the end.
		Object[][] variablesAndExpectedResults = new Object[files.size()][4];
		String algorithmName=null;
		ArrayList<DataVariableAndValues> variableAndValues;
		ArrayList<DataCombination> combinationsExpected;
			
		//To count the rows to add on array of Objects
		int ii=0;
		
		//Read and parse all the files on the directory
		for (Path path : files) {
				
			variableAndValues = new ArrayList<DataVariableAndValues>();
			combinationsExpected = new ArrayList<DataCombination>();
	
			List<String> file = Files.readAllLines(path);
			
			// File Format:
			//Algorithm Name
			//#
			// (Variable Name, (Values,)+\n)+ --at least one value for variable)
			// #
			// --Combination Expected
			// (Value,)+\n)+ -- the number of values is same of number of variables
			// on the first part of the file. The number of lines
			// is related with the Oracle used to calculate the expected result.
	
			// To separate the two parts of the file, Variable & Values and the
			// Expected Result
			
			//Read the Name of the Algorithm which will be executed with the data on the file
			int indexOfFinalOfAlgoritmName =file.indexOf("#");
			
			//To help who makes the file
			if(indexOfFinalOfAlgoritmName==-1){
				throw new Exception("This "+path+" do not have the first # separator, after the algorithm name");
			}
			
			List<String> algorithmNamePart = file.subList(0, indexOfFinalOfAlgoritmName);
			//is not necessary use a for structure, any way
			for (String string : algorithmNamePart) {
				if(!string.isEmpty())
					algorithmName=string;
				else
					throw new Exception("This "+path+" has empty the algorithm name");
			}
			if(algorithmName==null)
				throw new Exception("This "+path+" do not have algorithm name at the top of file");
			
			
			int indexOfLastPartNumberOfCombination=file.indexOf("@");
			if(indexOfLastPartNumberOfCombination==-1){
				throw new Exception("This "+path+" do not have the last separator @, after the combinations. With the max number of combination");
			}
			//Quite the algorithm name and the separator (#) and the number of combination locate after @ separator
			file=file.subList(indexOfFinalOfAlgoritmName+1,indexOfLastPartNumberOfCombination );
			
			int indexOfMidiumPart = file.indexOf("#");
			if (indexOfMidiumPart == -1)
				throw new Exception("This "+path+" do not have the separator # beteween the Imput and the Expected Result");

			List<String> firstPart = file.subList(0, indexOfMidiumPart);
			List<String> secondPart = file.subList(indexOfMidiumPart + 1,file.size());
			//TODO:Put here the number of max combinations
	
			DataVariableAndValues dataVarValues;
			// Read the Variables and Values, it all separated by comma.
			for (String line : firstPart) {
				if (line.contains(",")) {
					String[] splited = line.split(",");
					String variableName = splited[0];
					ArrayList<String> values = new ArrayList<String>();
					for (int i = 1; i < splited.length; i++) {
						String value = splited[i].trim();
						values.add(value);
					}
					dataVarValues = new DataVariableAndValues(variableName, values);
					variableAndValues.add(dataVarValues);
				}
			}
			// Read the Expected Result
			for (String line : secondPart) {
				if (line.contains(",")) {
					combinationsExpected.add(constructDataCombinationFromStringFormatedOnTheLegacyWay(line));
				}
			}
			variablesAndExpectedResults[ii][0]=EnumAlgorithmNames.valueOf(algorithmName);
			variablesAndExpectedResults[ii][1]=path;
			variablesAndExpectedResults[ii][2]=variableAndValues;
			variablesAndExpectedResults[ii][3]=combinationsExpected;
			ii++;
			
		}
		return variablesAndExpectedResults;
		

	}

	@Test(dataProvider="setUpDataForTests")
	public void testTheCombination(EnumAlgorithmNames algorithmName,Path filePath, ArrayList<DataVariableAndValues> variableAndValues,
			ArrayList<DataCombination> combinationsExpected) {

		ArrayList<DataCombination> combinations=null;
		switch (algorithmName) {
		
		case allCombinations:
			combinations = handlerCombinationCTWebNewLogic.getPairWiseCombinationWithAllCombination(variableAndValues);
			break;
		case eachChoice:
			combinations = handlerCombinationCTWebNewLogic.getPairWiseCombinationWithEachChoice(variableAndValues);
			break;
			
		case simplePairwise:
			combinations = handlerCombinationCTWebNewLogic.getPairWiseCombinationWithSimplePairWise(variableAndValues);
			break;
		case prow:
			combinations = handlerCombinationCTWebNewLogic.getPairWiseCombinationWithProwFirstStep(variableAndValues);
			break;
			
		}
		
		if(combinations==null){
			Assert.fail("No combinatios was calculate on this case. Something happens, maybe with the name of algorithm on the file related");
		}
//		Assert.assertEquals(combinationsExpected.toArray(), combinations.toArray());


	}

	/**
	 * This construct a combination result from this format
	 * {Ludo,Dice,Person,2,Quiz}
	 * 
	 * @param stringCombination
	 * @return DataCombination
	 */
	public static DataCombination constructDataCombinationFromStringFormatedOnTheLegacyWay(String stringCombination) {

		stringCombination = stringCombination.substring(1, stringCombination.indexOf("}"));

		String[] values = stringCombination.split(",");

		ArrayList<String> valuesList = new ArrayList<String>();

		for (int i = 0; i < values.length; i++) {
			valuesList.add(values[i].trim());
		}

		DataCombination combination = new DataCombination(valuesList);

		return combination;
	}

	/**
	 * Return all path files from a parent directory, including all files on subfolders in deep. 
	 * 
	 * @param path
	 * @return ArrayList of Path with path all files on directory and subdirectories 
	 * @throws IOException
	 */
	private static ArrayList<Path> getFiles(Path path) throws IOException {
		ArrayList<Path> files = new ArrayList<Path>();
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
	        for (Path entry : stream) {
	            if (Files.isDirectory(entry)) {
	                files.addAll(getFiles(entry));
	            }
	            //Only add files!
	            else{
	            	
	            	files.add(entry);
	            }
	        }
	        return files;
	    }
	}
	
}
