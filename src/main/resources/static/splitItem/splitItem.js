angular.module('app.splitItem', ['ngRoute', 'ngStorage'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/splitItem', {
    templateUrl: './splitItem/splitItem.html',
    controller: 'SplitItemCtrl'
  })
}])
.controller('SplitItemCtrl', function($scope, $http,  $localStorage){
	$scope.itemsArray = $localStorage.receipts.items;
	console.log($scope.itemsArray);
	$scope.allFriends = [];
	$scope.currentItemIndex = 0;
	$http({
		method: 'GET',
		url: '/getUserFriends'
	}).then(function(response){
		if((response.data).length>0){
			$scope.friendList = [];
			$scope.allFriends = response.data;
        }
	});

	$scope.addfrnds = function(id){
		$scope.currentItemIndex = id;
	}

	$scope.frnd = function(){
		console.log("In frnd " + $scope.currentItemIndex);
		console.log($scope.itemsArray);
		$scope.itemsArray[$scope.currentItemIndex].splitIds = $scope.friendList;
	}

	$scope.updateDB = function(){
		$http({
		method: 'POST',
		url: '/addFriends'
	}).then(function(response){
		console.log("in updateDB" + response);
	});
	}

});