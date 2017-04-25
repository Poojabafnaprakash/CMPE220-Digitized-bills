angular.module('app.receiptsView', ['ngRoute', 'ngStorage'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/receiptsView', {
    templateUrl: './receiptsView/receiptsView.html',
    controller: 'ReceiptsViewCtrl'
  })
}])
.controller('ReceiptsViewCtrl', function($scope, $http,  $sessionStorage){
	$scope.itms = {};
	$scope.itemsArray = [];
		  $http({
				method: 'GET',
				url: '/receiptInfo'
			}).then(function(response){
					 $scope.itms = response.data;
					 $scope.itemsArray = response.data.items;
			});
});