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
				$scope.allFriends = $scope.group.userIds;

			});
			$scope.saveGroup = function() {
				$http({
					method : 'POST',
					url : '/saveFriendsGroup',
					data : $scope.group
				})
			};
			$scope.change = function(id) {
				console.log($scope.allFriends[id]);
				$scope.allFriends[id].checked = 'Yes';
				$scope.group.userIds = $scope.allFriends;
			};

		} ]);