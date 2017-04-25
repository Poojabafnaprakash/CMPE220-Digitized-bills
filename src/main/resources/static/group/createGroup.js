angular.module('app.createGroup', ['ngRoute', 'ngStorage'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/createGroup', {
    templateUrl: './group/createGroup.html',
    controller: 'CreateGroupCtrl'
  })
}])
.controller('CreateGroupCtrl', function($scope, $http,  $sessionStorage){
	  
});