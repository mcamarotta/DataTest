function Algorithms(config) {
	this.config = config;
};

Algorithms.prototype.buildDropDown = function (dropDown) {
	var me = this;
	for (var i in this.config.algorithms) {
		$(dropDown).append('<option value=' + this.config.algorithms[i].id + '>' + this.config.algorithms[i].name + '</option>');
		$(dropDown).on("change", function(){
				// alert(me.getAlgorithm(dropDown).usePairs);		
			if (me.getAlgorithm(dropDown).usePairs === true){
				$($("#sidebarNavigation a")[2]).show(100);
			}else{
				$($("#sidebarNavigation a")[2]).hide(100);
			}
		});		
		
	}
};

Algorithms.prototype.getAlgorithm = function (dropDown) {
	var selectedAlgorithm = dropDown.val();

	for (var i in this.config.algorithms) {
		if (this.config.algorithms[i].id == selectedAlgorithm)
			return this.config.algorithms[i];
	}
};


