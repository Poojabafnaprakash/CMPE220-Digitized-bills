angular.module('app.dashboard', ['ngRoute', 'ngStorage', 'ngFileUpload'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/dashboard', {
    templateUrl: './dashboard/dashboard.html',
    controller: 'DashboardCtrl'
  })
}])

.controller('DashboardCtrl', ['$scope', 'Upload', '$timeout', function($scope, Upload, $timeout){
	
	$scope.uploadFiles = function(file, errFiles) {
        $scope.f = file;
        $scope.errFile = errFiles && errFiles[0];
        if (file) {
            file.upload = Upload.upload({
                url: '/uploadFile',
                data: {file: file}
            });
            file.upload.success(function (response) {
                console.log('file ' + 'uploaded. Response: ');
            });
        }   
    }  
}]);
