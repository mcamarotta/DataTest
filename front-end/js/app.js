
$(document).ready(function(){initPage();});



function initPage(){
   // 
   initVariablesLogic();
   initResultsLogic();


//Event handlers
$("#btnAddRow").on("click", function(){ addRow(); });
$("#btnRemoveRow").on("click", function(){ deleteRow(); });
$("#btnAddCol").on("click", function(){ addColumn(); });
$("#btnRemoveCol").on("click", function(){ deleteColumn(); });
$("#btnClear").on("click", function(){ clearVariables(); });
$("#btnExecuteStepOne").on("click", function(){ executeStepOne(); });


}

function executeStepOne(){
	//TODO: Make a post request to the real service
	// postRequest('ServiceUrl', buildRequestJson(), function(data, status){
	// 	console.log(status);
	// });



	getRequest('mock/eachchoice.json', null, function(data, status){
		setResults(data);
	});
}

function buildRequestJson(){
	var dataToSend = [];
	// Loop table rows
	for(var headerItem in ourTable.header){
		dataToSend.push({"values" : [], "variableName" : ourTable.header[headerItem] } );

		for(var row in ourTable.rows){
			dataToSend[headerItem].values.push(ourTable.rows[row].columns[headerItem]);
		}
	}	
	return dataToSend;
}



