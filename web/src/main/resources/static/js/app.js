var app = angular.module('tweetguess', ['pascalprecht.translate']);

app.controller('mainController', function ($scope, $timeout, $http, $anchorScroll, $interval, $location, $translate) {
    $scope.view = 'index';

    $scope.user = {username: ''};
    $scope.prefs = {category: {slug: null, name: null}, lang: {code: 'en', name: 'English'}};
    //$scope.locale = {id: '', name: ''}
    $scope.timer = {progress: 0, live: undefined, max: 30000};
    $scope.constants = {numQuestions: 10};

    //$translate.use($scope.locale.id);

    $scope.home = function() {
        $scope.removeUnloadEvent();
        $scope.view = 'index';
    };

    $scope.startGame = function () {
        if($scope.user.username == '') {
            $scope.view = 'index';
            return;
        }
        $scope.stopTimer();
        $scope.loadTitle = "LOAD_CATEGORIES";
        $scope.view = 'loading';
        $anchorScroll('page-top');

        $http.post('initgame', {user: $scope.user, preferences: $scope.prefs }).then(function (res) {
            $scope.view = 'category';
            $scope.showAllCats = false;
            $scope.categories = res.data.categories;
            $scope.regions = res.data.languages;
            $anchorScroll('page-top');
        });
    };

    $scope.moreCats = function () {
        $scope.showAllCats = true;
    };

    $scope.selectRegion = function(region) {
        $scope.prefs.lang = region;
        $scope.loadTitle = 'LOAD_CATEGORIES';
        $scope.view = 'loading';
        $http.post('selectregion', region).then(function(res) {
            $scope.categories = res.data;
            $scope.showAllCats = false;
            $scope.view = 'category';
        });
    };

    $scope.selectCategory = function (category) {
        $scope.loadTitle = "LOAD_GAME";
        $scope.view = 'loading';
        $anchorScroll('page-top');

        $scope.prefs.category = category;
        $scope.getQuestion();
    };

    $scope.getQuestion = function () {
        $scope.addUnloadEvent();
        if ($scope.question) {
            $scope.question.loading = true;
            if ($scope.question.index == $scope.constants.numQuestions - 1)
                $scope.question.title = "QUESTION_SAVE";
            else
                $scope.question.title = "QUESTION_NEXT";
            $scope.question.score = 0;
        }
        $http.post('getquestion', $scope.prefs).then(function (res) {
            if (res.data == '') {
                $scope.removeUnloadEvent();
                $scope.getLeaderboard();
                return;
            }
            $scope.question = res.data;
            $scope.question.title = "QUESTION_IDLE";
            $scope.question.loading = false;
            $scope.question.answered = false;
            $scope.choices = ['', '', '', ''];
            $scope.view = 'question';
            $scope.resetTimer();
            $anchorScroll('page-top');
        });
    };

    $scope.answer = function (choice) {
        if ($scope.question.answered) return;
        $scope.question.answered = true;
        $scope.question.wait = true;
        $scope.stopTimer();
        if (choice >= 0) {
            $scope.question.title = "QUESTION_CHECK";
            $http.post('answer', {choice: choice}).then(function (res) {
                if(res.data == '') {
                    $scope.question.title = "QUESTION_TIMEOUT";
                    $timeout(function () {
                        $scope.getQuestion();
                    }, 2000);
                    return;
                }
                $scope.question.wait = false;
                if (res.data.userChoice == res.data.correctChoice) {
                    $scope.question.score = res.data.score > 0 ? res.data.score : 0;
                    $scope.question.title = "QUESTION_CORRECT";
                } else {
                    $scope.question.title = "QUESTION_WRONG";
                }
                $anchorScroll('page-top');
                $scope.choices[res.data.userChoice] = 'false';
                $scope.choices[res.data.correctChoice] = 'true';

                $timeout(function () {
                    $scope.getQuestion();
                }, 2000);
            });
        } else {
            $scope.question.title = "QUESTION_TIMEOUT";
            $timeout(function () {
                $scope.getQuestion();
            }, 2000);
        }
    };

    $scope.getLeaderboard = function () {
        $scope.stopTimer();
        $scope.removeUnloadEvent();
        $scope.view = 'loading';
        $scope.loadTitle = "LOAD_LEADERBOARD";
        $http.get('leaderboard').then(function (res) {
            $scope.leaderboard = res.data;
            $scope.view = 'leaderboard';
        });
    };

    $scope.resetTimer = function () {
        $scope.timer.progress = 0;
        $scope.startTimer();
    };

    $scope.startTimer = function () {
        if (angular.isDefined($scope.timer.live)) return;

        $scope.timer.live = $interval(function () {
            //Countdown
            if ($scope.timer.progress >= 100) {
                $scope.stopTimer();
                $scope.answer(-1);
                return;
            }

            $scope.timer.progress += 100000 / $scope.timer.max;
        }, 1000);
    };

    $scope.stopTimer = function () {
        if (angular.isDefined($scope.timer.live)) {
            $interval.cancel($scope.timer.live);
            $scope.timer.live = undefined;
        }
    };

    $scope.addUnloadEvent = function () {
        if (window.addEventListener) {
            window.addEventListener("beforeunload", handleUnloadEvent);
        } else {
            //For IE browsers
            window.attachEvent("onbeforeunload", handleUnloadEvent);
        }
    };

    function handleUnloadEvent(event) {
        event.returnValue = "The game is still in progress.";
    }

    $scope.removeUnloadEvent = function () {
        if (window.removeEventListener) {
            window.removeEventListener("beforeunload", handleUnloadEvent);
        } else {
            window.detachEvent("onbeforeunload", handleUnloadEvent);
        }
    };

});