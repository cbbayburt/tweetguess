var app = angular.module('tweetguess', ['ngRoute']);

app.controller('mainController', function($scope, $timeout, $http, $anchorScroll) {
    $scope.view = 'index';

    $scope.user = { username: '' };
    $scope.prefs = { category: '', lang: {code: 'en', name: 'EN'}};

    $scope.startGame = function() {
        $http.post('initgame', $scope.user).then(function(res) {
            $scope.view = 'category';
            $scope.showAllCats = false;
            $scope.categories = res.data.categories;
            $scope.languages = res.data.languages;
            $anchorScroll('page-top');
        });
    };

    $scope.moreCats = function() {
        $scope.showAllCats = true;
    };

    $scope.selectCategory = function(category) {
        $scope.view = 'loading';
        $anchorScroll('page-top');

        $scope.prefs.category = category;
        $scope.getQuestion();
    };

    $scope.getQuestion = function() {
        if($scope.question) {
            $scope.question.loading = true;
        }
        $http.post('getquestion', $scope.prefs).then(function(res){
            $scope.question = res.data;
            if($scope.question.index == -1) {
                $scope.getLeaderboard();
                return;
            }
            $scope.question.loading = false;
            $scope.question.answered = false;
            $scope.choices = ['', '', '', ''];
            $scope.view = 'question';
            $anchorScroll('page-top');
        });
    };

    $scope.answer = function(choice) {
        if($scope.question.answered) return;
        $scope.question.answered = true;
        $scope.question.wait = true;
        $http.post('answer', {choice: choice}).then(function(res) {
            $scope.question.wait = false;
            $scope.choices[res.data.userChoice] = 'false';
            $scope.choices[res.data.correctChoice] = 'true';

            $timeout(function() {
                $scope.getQuestion();
            }, 2000);
        });
    };

    $scope.getLeaderboard = function() {
        $scope.view = 'loading';
        $http.get('leaderboard').then(function(res) {
            $scope.leaderboard = res.data;
            $scope.view = 'leaderboard';
        });
    }

});