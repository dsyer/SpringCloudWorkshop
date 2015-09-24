//angular.module('hello', [])
//  .controller('home', function($scope) {
//    $scope.greeting = {id: 'xxx', content: 'Hello World!'}
//})

angular.module('hello', [])
  .controller('home', function($scope, $http) {
  $http.get('/resource/').success(function(data) {
    $scope.greeting = data;
  })
});