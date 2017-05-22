angular.module('app.dashboard', ['ngRoute', 'ngStorage', 'zingchart-angularjs', 'ngFileUpload'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/dashboard', {
    templateUrl: './dashboard/dashboard.html',
    controller: 'DashboardCtrl'
  })
}])

.controller('DashboardCtrl', ['$scope', '$http', 'Upload', '$timeout', function($scope, $http, Upload, $timeout){
	
    $scope.monthlyExp = [];
    $scope.youOwe = 200;
    $scope.youAreOwed = 400;
    $scope.myPieData = [];
    $scope.myBarData = [];
    $scope.myBarDataScaleX = [];
    $scope.allReceipts = [];
    $scope.monthMap = ["Jan", "Feb", "March", "April", "May", "June", "July", "Aug", "Sept", "Oct", "Nov", "Dec"];
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
                var x = document.getElementById('viewReceipt');
                var y = document.getElementById('uploadButton');
                if (x.style.display === 'none') {
                    x.style.display = 'block';
                    y.style.display = 'none';
                } else {
                    x.style.display = 'none';
                    y.style.display = 'block';
                }
                
            });
        }   
    }  
	
    // $http({
    //     method: 'GET',
    //     url: '/getEditableBills'
    // }).then(function(response){
    //     console.log("bills:"+response);
    //     if((response.data).length>0){
    //          $scope.allReceipts = response.data;
    //          console.log($scope.allReceipts);
    //     }
    // });

	$scope.allFriends = [];
	$http({
		method: 'GET',
		url: '/getUserFriends'
	}).then(function(response){
		if((response.data).length>0){
			 $scope.allFriends = response.data;
        }
	});
	
	$http({
		method: 'GET',
		url: '/user'
	}).then(function(response){
			 $scope.user = response.data;
	});
	
    $http({
        method: 'GET',
        url: '/monthlyExpenditure'
    }).then(function(response){
    	console.log(response);
        for(var i=0; i<response.data.length; i++){
            $scope.monthlyExp.push(Math.round(response.data[i].expen));
            $scope.myBarDataScaleX.push($scope.monthMap[response.data[i].month]);
        }

        //$scope.monthlyExp = response.data;
        console.log("monthly exp" + $scope.monthlyExp);
        $scope.myPieData = [30,20];
        //$scope.myBarData = $scope.monthlyExp;
        $scope.myBarData = [12, 34];
        $scope.scaleX = ["Jan", "Feb"];

    });

	$scope.receiptsView = function(){
		window.location.assign("/#!receiptsView");
	}
	
	$scope.logout = function() {
		$http({
			method : 'GET',
			url : '/connect/logout'
		})
	};
	
    $scope.myPieJson = {
        "scale-x": {
            "labels": $scope.myBarDataScaleX
        },
        globals: {
            shadow: false,
            fontFamily: "Verdana",
            fontWeight: "100"
        },
        backgroundColor: "#fff",
        tooltip: {
            text: "Expense %v"
        },
        series: [{
            text: "",
            backgroundColor: "#F1C795 #feebd2"
        }]
    };

    $scope.myBar = {
        "scale-x": {
            "labels": $scope.myBarDataScaleX
        },
        globals: {
            shadow: false,
            fontFamily: "Verdana",
            fontWeight: "100"
        },
        backgroundColor: "#fff",
        tooltip: {
            text: "Expense %v "
        },
        series: [{
            text: "",
            backgroundColor: "#FA6E6E #FA9494"
        }]
    };
   
}]);