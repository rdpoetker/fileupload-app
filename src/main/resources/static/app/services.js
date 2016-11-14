(function(angular) {

  angular.module('fileupApp.services')
  	.factory('GetFiles', ['$resource',
  	                      function($resource){
	                      	return $resource('/files/');
	                     }]);
	
  angular.module('fileupApp.services')
  	.service('FileUpSvc', ['$http','$q','GetFiles', FileUpSvc]);

  function FileUpSvc( $http, $q, GetFiles ){
	
	var self = this;
	
	self.getFiles = getFiles;
	
	function getFiles() {
	   return GetFiles.query();
	}
		
  }

})(angular);