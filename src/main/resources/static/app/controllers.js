(function(angular) {
  
	 angular
     .module('fileupApp.controllers')
     .controller('FileUpController', [
        '$scope', 'Upload', 'FileUpSvc',
        FileUpController
     ]);
	 
  function FileUpController($scope, Upload, FileUpSvc) {
	 	 	  
	    function refreshUpFiles() {
	    	FileUpSvc
            .getFiles()
            .$promise.then( function( upfls ) {
            	$scope.upFiles = upfls ? upfls : [];
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

	    $scope.upload = function(file, fmeta) {
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

	  
}(angular));