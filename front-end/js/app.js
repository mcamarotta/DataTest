
$(document).ready(function(){initPage();});



function initPage(){
   // 
   initVariablesLogic();


//Event handlers
$("#btnAddRow").on("click", function(){ addRow(); });
$("#btnRemoveRow").on("click", function(){ deleteRow(); });
$("#btnAddCol").on("click", function(){ addColumn(); });
$("#btnRemoveCol").on("click", function(){ deleteColumn(); });
$("#btnExecuteStepOne").on("click", function(){ executeStepOne(); });

}

function executeStepOne(){
	postRequest('mock/eachchoice.json', buildRequestJson(), function(data, status){
		console.log(status);
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



