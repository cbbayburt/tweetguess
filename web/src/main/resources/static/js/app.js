var app = angular.module('tweetguess', ['ngRoute']);

app.controller('mainController', function($scope, $timeout, $http, $anchorScroll) {
    $scope.view = 'index';

    $scope.user = { username: '' };
    $scope.prefs = { category: '', lang: 'en'};

    $scope.startGame = function() {
        $http.post('initgame', $scope.user).then(function() {
            $scope.view = 'category';
            $anchorScroll('page-top');
        });
    };

    $scope.selectCategory = function(category) {
        $scope.view = 'loading';
        $anchorScroll('page-top');

        $scope.prefs.category = category;
        $scope.getQuestion();
    };

    $scope.getQuestion = function() {
        $http.post('getquestion', $scope.prefs).then(function(res){
            $scope.question = res.data;
            $scope.question.answered = false;
            $scope.choices = ['', '', '', ''];
            $scope.view = 'question';
            $anchorScroll('page-top');
        });
    };

    $scope.answer = function(choice) {
        if($scope.question.answered) return;
        $scope.question.answered = true;
        $http.post('answer', {choice: choice}).then(function(res) {
            $scope.question.tweet = res.data.correctChoice == res.data.userChoice ? 'True' : 'False';
            $scope.choices[res.data.userChoice] = 'false';
            $scope.choices[res.data.correctChoice] = 'true';

            $timeout(function() {
                $scope.getQuestion();
            }, 2000);
        });
    };
});