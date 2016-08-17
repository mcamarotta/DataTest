
$(document).ready(function(){initPage();});



function initPage(){
   // 
   initVariablesLogic();


//Event handlers
$("#btnAddRow").on("click", function(){ addRow(); });
$("#btnRemoveRow").on("click", function(){ deleteRow(); });
$("#btnAddCol").on("click", function(){ addColumn(); });
$("#btnRemoveCol").on("click", function(){ deleteColumn(); });

}
