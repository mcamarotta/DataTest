
$(document).ready(function(){initPage();});



function initPage(){
	//HTML elements logic
	//Dropdown Algorithms
	buildAlgorithmsDropdown();

   // Init other pages
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

	getRequest(getAlgorithmUrl(), null, function(data, status){
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


function buildAlgorithmsDropdown(){
	for(var i in algorithms){
		$('#ddlAlgorithms').append('<option value='+algorithms[i].id+'>'+algorithms[i].name+'</option>');
	}	
}

function getAlgorithmUrl(){
	var selectedAlgorithm = $('#ddlAlgorithms').val();

	for(var i in algorithms){
		if (algorithms[i].id == selectedAlgorithm)
			return algorithms[i].url;
	}
}




