angular.module('app.createGroup', [ 'ngRoute', 'ngStorage' ])

.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/createGroup', {
		templateUrl : './group/createGroup.html',
		controller : 'CreateGroupCtrl'
	})
} ]).controller('CreateGroupCtrl',
		[ '$scope', '$http', function($scope, $http, $sessionStorage) {
			console.log("In create group");
			$scope.allFriends = [];
			$scope.group = {};
			$http({
				method : 'GET',
				url : '/createGroup'
			}).then(function(response) {
				console.log(response.data);
				$scope.group = response.data;
				$scope.allFriends = response.data.userIds;

			});
			$scope.saveGroup = function() {
				$http({
					method : 'POST',
					url : '/saveFriendsGroup',
					data : $scope.group
				})
			};

		} ]);