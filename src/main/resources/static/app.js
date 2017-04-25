
angular.module('app', ['ngRoute','ngResource', 'app.userAuth', 'app.dashboard','app.landingPage','app.receiptsView','app.receiptsViewWithOption','app.splitItem','app.splitTotal', 'app.addReceipts', 'app.createGroup'])
.config(function($routeProvider, $locationProvider) {
  $routeProvider
  .when('/', {
	templateUrl: './landingPage/landingPage.html',
    controller: 'LandingPageCtrl'
  }) 
  .when('/userAuth', {
    templateUrl: './user/userAuth.html',
    controller: 'UserAuthCtrl'
  })
  .when('/dashboard', {
	templateUrl: './dashboard/dashboard.html',
    controller: 'DashboardCtrl'
  }) 
  .when('/addReceipts', {
	templateUrl: './addReceipts/addReceipts.html',
	controller: 'AddReceiptsCtrl'
  }) 
  .when('/receiptsView', {
	templateUrl: './receiptsView/receiptsView.html',
	controller: 'ReceiptsViewCtrl'
  })
  .when('/receiptsViewWithOption', {
	templateUrl: './receiptsViewWithOption/receiptsViewWithOption.html',
	controller: 'ReceiptsViewWithOptionCtrl'
  }) 
  .when('/splitItem', {
	templateUrl: './splitItem/splitItem.html',
	controller: 'SplitItemCtrl'
  }) 
  .when('/splitTotal', {
	templateUrl: './splitTotal/splitTotal.html',
	controller: 'SplitTotalCtrl'
  }) 
  .when('/createGroup', {
	templateUrl: './group/createGroup.html',
	controller: 'CreateGroupCtrl'
  }) 
  
})
.directive("navBar", function() {
    return {
    	templateUrl : './navigationMenu/navBar.html'
    };
});
