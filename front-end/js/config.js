function Config() {
	/// 1 = Each choice
	/// 2 = All combinataions
	/// 3 = Simple pairwise
	/// 4 = Prow
	this.algorithms =
		[	{ "id": 1, "name": "Each choice", "url": "getEachChoice" },
			{ "id": 2, "name": "All combinations", "url": "getAllCombination" },
			{ "id": 3, "name": "Simple pairwise", "url": "getSimplePairWise", "usePairs" : true },
			{ "id": 4, "name": "Prow", "url": 'NOT IMPLEMENTED' }];

	//this.urlBase = 'http://localhost:8080/rest/services/';
	this.urlBase = 'http://localhost:8080/testdata-0.0.1-SNAPSHOT/rest/services/';

	this.animationDuration = 100;
};