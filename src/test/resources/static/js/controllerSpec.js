describe('FileUpController', function() {

	beforeEach(module('fileupApp'));
			
	beforeEach(function () {
        angular.mock.inject(function ($injector) {
            $httpBackend = $injector.get('$httpBackend');
        })
    });
						
	it('should initialize with a call to get files', inject(function($controller) {
			var scope = {};
						
			var mockfiles = [{inspId:1}];
		    $httpBackend.expectGET('/files').respond(mockfiles);
		    
		    var ctrl = $controller('FileUpController', {$scope:scope});
		   			    		    
		    $httpBackend.flush();
		    	    		    
		    expect(scope.upFiles[0].inspId).toEqual(1);
	}));
		
	it('should submit the form', inject(function($controller) {
		var scope = {};
		scope.form = {$valid:true, 'file':{$valid:true}};
		scope.file = {$valid:true};
		
		scope.filemeta = {'title':'testtitle','description':'testdescription'};
		
		var mockfiles = [{inspId:1}];
	    $httpBackend.expectGET('/files').respond(mockfiles);
	    
	    var ctrl = $controller('FileUpController', {$scope:scope});
	   	
	    $httpBackend.flush();
	    
	    var mockResp = {code:0};
	    $httpBackend.expectPOST('files').respond(mockResp);
	    
	    var mockfiles = [{inspId:1}];
	    $httpBackend.expectGET('/files').respond(mockfiles);
	   	   	    
	    scope.submit();
	    
	    $httpBackend.flush();
	   	   	    
	    expect(scope.upFiles[0].inspId).toEqual(1);
	}));
	
	it('should not submit the form when form is not valid', inject(function($controller) {
		var scope = {};
		scope.form = {$valid:false, 'file':{$valid:true}};
		scope.file = {$valid:true};
		
		scope.filemeta = {'title':'testtitle','description':'testdescription'};
		
		var mockfiles = [{inspId:1}];
	    $httpBackend.expectGET('/files').respond(mockfiles);
	    
	    var ctrl = $controller('FileUpController', {$scope:scope});
	   	
	    $httpBackend.flush();
	    	    	   	   	    
	    scope.submit();
	    	    
	    expect(scope.errorMessage).toEqual('Please select a valid file to upload');
	}));
	
});