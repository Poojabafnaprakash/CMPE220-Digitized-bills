angular.module('app.receiptsView', ['ngRoute', 'ngStorage'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/receiptsView', {
    templateUrl: './receiptsView/receiptsView.html',
    controller: 'ReceiptsViewCtrl'
  })
}])
.controller('ReceiptsViewCtrl', function($scope, $http){
	$scope.itms = {};
	$scope.itemsArray = [];
		  $http({
				method: 'GET',
				url: '/receiptInfo'
			}).then(function(response){
					 $scope.itms = response.data;
					 $scope.itemsArray = response.data.items;
			});

      // cancel all changes
  $scope.cancel = function() {
    for (var i = $scope.itemsArray.length; i--;) {
      var item = $scope.itemsArray[i];
      // undelete
      if (item.isDeleted) {
        delete item.isDeleted;
      }
      // remove new
      if (item.isNew) {
        $scope.itemsArray.splice(i, 1);
      }
    };
  };

  $scope.saveTable = function() {
	  console.log("in save table");
    var results = [];
    for (var i = $scope.itemsArray.length; i--;) {
      var item = $scope.itemsArray[i];
      // actually delete user
      if (item.isDeleted) {
        $scope.itemsArray.splice(i, 1);
      }
      // mark as not new
      if (item.isNew) {
        item.isNew = false;
      }
      //results.push($http.post('/saveUser', user));
      //console.log($scope.itms);
    }
    if($scope.split === 'I'){
      $scope.itms.flag = 'I'
    }
    else{
      $scope.itms.flag = 'T'
    }

    // send on server
    console.log($scope.itms);

    //return $q.all(results);
  };

});
