(function(angular) {

  angular.module('fileupApp.services')
  	.factory('GetFiles', ['$resource',
  	                      function($resource){
	                      	return $resource('/files/:id');
	                     }]);
	
  angular.module('fileupApp.services')
  	.service('FileUpSvc', ['$http','$q','GetFiles', FileUpSvc]);

  function FileUpSvc( $http, $q, GetFiles ){
	
	var self = this;
	
	self.getFiles = getFiles;
	self.deleteFile = deleteFile;
	
	function getFiles() {
	   return GetFiles.query();
	}
	
	function deleteFile(fileId) {
		return GetFiles.remove({id:fileId});
	}
		
  }

})(angular);