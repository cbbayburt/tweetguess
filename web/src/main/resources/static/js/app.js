var app = angular.module('tweetguess', ['ngRoute']);

app.controller('mainController', function($scope, $timeout, $http, $anchorScroll, $interval) {
    $scope.view = 'index';

    $scope.user = { username: '' };
    $scope.prefs = { category: {slug: null, name: null}, lang: {code: 'en', name: 'EN'}};
    $scope.timer = { progress: 0, live: undefined, max: 30000 };
    $scope.constants = { numQuestions: 10 };

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
        $scope.loadTitle = "Starting a new game...";
        $scope.view = 'loading';
        $anchorScroll('page-top');

        $scope.prefs.category = category;
        $scope.getQuestion();
    };

    $scope.getQuestion = function() {
        if($scope.question) {
            $scope.question.loading = true;
            if($scope.question.index == $scope.constants.numQuestions - 1)
                $scope.question.title = "Saving progress...";
            else
                $scope.question.title = "Next question...";
            $scope.question.score = 0;
        }
        $http.post('getquestion', $scope.prefs).then(function(res){
            if(res.data == '') {
                $scope.getLeaderboard();
                return;
            }
            $scope.question = res.data;
            $scope.question.title = "Who tweeted this?";
            $scope.question.loading = false;
            $scope.question.answered = false;
            $scope.choices = ['', '', '', ''];
            $scope.view = 'question';
            $scope.resetTimer();
            $anchorScroll('page-top');
        });
    };

    $scope.answer = function(choice) {
        if($scope.question.answered) return;
        $scope.question.answered = true;
        $scope.question.wait = true;
        $scope.stopTimer();
        if(choice >= 0) {
            $scope.question.title = "Checking...";
            $http.post('answer', {choice: choice}).then(function (res) {
                $scope.question.wait = false;
                if(res.data.userChoice == res.data.correctChoice) {
                    $scope.question.score = res.data.score > 0 ? res.data.score : 0;
                    $scope.question.title = "Correct!";
                } else {
                    $scope.question.title = "Wrong!";
                }
                $anchorScroll('page-top');
                $scope.choices[res.data.userChoice] = 'false';
                $scope.choices[res.data.correctChoice] = 'true';

                $timeout(function () {
                    $scope.getQuestion();
                }, 2000);
            });
        } else {
            $scope.question.title = "Time is over.";
            $timeout(function () {
                $scope.getQuestion();
            }, 2000);
        }
    };

    $scope.getLeaderboard = function() {
        $scope.view = 'loading';
        $scope.loadTitle = "Loading leaderboard...";
        $http.get('leaderboard').then(function(res) {
            $scope.leaderboard = res.data;
            $scope.view = 'leaderboard';
        });
    };

    $scope.resetTimer = function() {
        $scope.timer.progress = 0;
        $scope.startTimer();
    };

    $scope.startTimer = function() {
        if(angular.isDefined($scope.timer.live)) return;

        $scope.timer.live = $interval(function() {
            //Countdown
            if($scope.timer.progress >= 100) {
                $scope.stopTimer();
                $scope.answer(-1);
                return;
            }

            $scope.timer.progress += 100000 / $scope.timer.max;
        }, 1000);
    };

    $scope.stopTimer = function() {
        if(angular.isDefined($scope.timer.live)) {
            $interval.cancel($scope.timer.live);
            $scope.timer.live = undefined;
        }
    }

});