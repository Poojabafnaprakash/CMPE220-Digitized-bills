angular.module('app.addReceipts', ['ngRoute', 'ngStorage', 'ngFileUpload'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/addReceipts', {
    templateUrl: './addReceipts/addReceipts.html',
    controller: 'AddReceiptsCtrl'
  })
}])

.controller('AddReceiptsCtrl', ['$scope', '$http', 'Upload', '$timeout', function($scope, $http, Upload, $timeout){
	
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
	
	$scope.receiptsView = function(){
		window.location.assign("/#!receiptsView");
	}
	
}]);