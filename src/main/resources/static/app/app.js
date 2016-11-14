(function(angular) {
  angular.module("fileupApp.controllers", []);
  angular.module("fileupApp.services", []);
  angular.module("fileupApp", ["ngFileUpload","ngResource", "fileupApp.controllers", "fileupApp.services"]);
}(angular));