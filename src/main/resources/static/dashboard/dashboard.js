angular.module('app.dashboard', ['ngRoute', 'ngStorage', 'zingchart-angularjs', 'ngFileUpload'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/dashboard', {
    templateUrl: './dashboard/dashboard.html',
    controller: 'DashboardCtrl'
  })
}])

.controller('DashboardCtrl', ['$scope', '$http', 'Upload', '$timeout', function($scope, $http, Upload, $timeout){
	
    $scope.monthlyExp = [];
    $scope.myPieData = [];
    $scope.myBarData = [];
    $scope.myBarDataScaleX = [];
    $scope.allReceipts = [];
    $scope.totalYouOwe = {};
    $scope.bills = [];
    $scope.allGroups = [];
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
	

	$scope.allFriends = [];
	$http({
		method: 'GET',
		url: '/getUserFriends'
	}).then(function(response){
		if((response.data).length>0){
			 $scope.allFriends = response.data;
        } else{
            $scope.noFrnds = "No friends."
        }
	});

    $scope.notFrnds = [];

    $scope.addNewFriends = function(){
        $http({
            method: 'GET',
            url: '/notFriends'
        }).then(function(response){
            if((response.data).length>0){
                $scope.notFrnds = [];
                $scope.notFrnds = response.data;
                var x = document.getElementById('addFrndButton');
                x.style.display = 'block';
            } else{
                var x = document.getElementById('addFrndButton');
                x.style.display = 'none';
                $scope.noFriendsDisplay = "No users to display";
            }
        });
    }

    $scope.friendList = [];

    $scope.addNotfrnd = function(){
        $http({
                    method : 'POST',
                    url : '/addNotFriends',
                    data : $scope.friendList
                }).then(function(){
                    window.location.assign("/#!dashboard");
                });
    }

   $http({
       method: 'GET',
       url: '/getEditableBills'
   }).then(function(response){
        
        if(response.data.length==0){
            $scope.NoBill = "No Receipts";
            var x = document.getElementById('billtable');
            x.style.display = 'none';
        }
        else{
            $scope.bills = response.data;
        }
        console.log("scope bills" + $scope.bills);
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
            $scope.myBarDataScaleX.push($scope.monthMap[response.data[i].month-1]);
            console.log($scope.monthMap[response.data[i].month-1]);
        }

        //$scope.monthlyExp = response.data;
        console.log("monthly exp" + $scope.monthlyExp);
        $scope.myPieData = $scope.monthlyExp;
        $scope.myBarData = $scope.monthlyExp;

    });

    $http({
        method: 'GET',
        url: '/totalYouOwe'
    }).then(function(response){
        console.log(response);
        $scope.totalYouOwe = JSON.parse(response.data);
    });

    $http({
        method: 'GET',
        url: '/viewGroups'
    }).then(function(response){
        console.log(response.data);
        for(var i=0; i<response.data.length; i++){
           $scope.allGroups.push(response.data[i].groupName); 
        }
        if(response.data.length == 0){
            $scope.nogroups = "No Groups";
        }
    });



    $scope.sendNotification = function() {
        $http({
            method : 'GET',
            url : '/sendNotification'
        })
    };
 

     $http({
        method: 'GET',
        url: '/totalYouAreOwed'
     }).then(function(response){
        $scope.totalYouAreOwed = response.data;
     });
 
     $scope.youAreOwed = [];
     $http({
        method: 'GET',
        url: '/youAreOwed'
     }).then(function(response){
        if((response.data).length>0){
            $scope.youAreOwed = response.data;
        } else{
            $scope.noExpense = "None owes you.";
        }
     });
 
 $scope.youOwe = [];
 $http({
  method: 'GET',
  url: '/youOwe'
 }).then(function(response){
  if((response.data).length>0){
    $scope.youOwe = response.data;
    } else{
        $scope.noExpense = "You do not owe.";
    }
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
            text: "$ %v"
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
            text: "$ %v "
        },
        series: [{
            text: "",
            backgroundColor: "#FA6E6E #FA9494"
        }]
    };
   
}]);