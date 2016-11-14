(function(angular) {
  
  var FileUpController = function($scope, Upload, FileUpSvc) {
	  
	    function refreshUpFiles() {
	    	FileUpSvc.getFiles(function(response) {
	  	      $scope.upFiles = response ? response : [];
	  	    });
	    };
	    
	    function resetForm() {
	    	$scope.filemeta = {};
	    	$scope.file = null;
	    	$scope.errorMessage = null;
	    }
	  		  
	    $scope.submit = function() {
	      if ($scope.form.$valid && $scope.file && $scope.form.file.$valid) {
	        $scope.upload($scope.file, $scope.filemeta);
	      } else {
	    	$scope.errorMessage = 'Please select a valid file to upload';
	      }
	    };

	    $scope.upload = function (file, fmeta) {
	        Upload.upload({
	            url: 'files',
	            data: {file: file, 'fmTitle': fmeta.title, 'fmDesc':fmeta.description }
	        }).then(function (resp) {
	        	refreshUpFiles();
	        	resetForm();
	        }, function (resp) {
	            $scope.errorMessage = 'Error uploading: ' + resp.status;
	        });
	    };
	    
	    refreshUpFiles();
	    
	  };
	  
	  FileUpController.$inject = ['$scope', 'Upload', 'FileUpSvc'];
	  angular.module("fileupApp.controllers").controller("FileUpController", FileUpController);
	  
}(angular));