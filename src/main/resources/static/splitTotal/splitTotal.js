angular.module('app.splitTotal', ['ngRoute', 'ngStorage', 'msieurtoph.ngCheckboxes'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/splitTotal', {
    templateUrl: './splitTotal/splitTotal.html',
    controller: 'SplitTotalCtrl'
  })
}])
.controller('SplitTotalCtrl', function($scope, $http,  $localStorage){
  $scope.itms = $localStorage.receipts;
  $scope.allFriends = [];
	$http({
		method: 'GET',
		url: '/getUserFriends'
	}).then(function(response){
		if((response.data).length>0){
			$scope.friendList = [];
			$scope.allFriends = response.data;
        }
	});

	$scope.addfrnds = function(){
		$scope.itms.splitIds = $scope.friendList;
		console.log($scope.itms);
	}
});
