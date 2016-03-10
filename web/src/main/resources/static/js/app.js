var app = angular.module('tweetguess', []);

$(function () {
  $.get('/language.json').done(function (data) {
    // Set your constant provider
    angular.module('tweetguess').constant('$lang', data);

    // Bootstrap your angular app
    angular.bootstrap(document, ['tweetguess']);
  });
});

app.controller('mainController', function ($scope, $timeout, $http, $anchorScroll, $interval, $location, $lang) {
    $scope.view = 'index';

    $scope.user = {username: ''};
    $scope.prefs = {category: {slug: null, name: null}, lang: {code:'en', name:'English'}};
    $scope.timer = {progress: 0, live: undefined, max: 30000};
    $scope.constants = {numQuestions: 10};

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
        $scope.loadTitle = $lang['load_categories'];
        $scope.view = 'loading';
        $anchorScroll('page-top');

        $http.post('initprefs', $scope.user).then(function (res) {
            $scope.view = 'category';
            $scope.showAllCats = false;
            $scope.langCatMap = res.data;
            for(var i=0; i<$scope.langCatMap.length; i++) {
                if($scope.langCatMap[i].language.code === $scope.prefs.lang.code) {
                    $scope.categories = $scope.langCatMap[i].categories;
                    break;
                }
            }
            $anchorScroll('page-top');
        });
    };

    $scope.moreCats = function () {
        $scope.showAllCats = true;
    };

    $scope.selectRegion = function(region) {
        $scope.prefs.lang = region.language;
        $scope.loadTitle = $lang['load_categories'];
        $scope.view = 'loading';
        $scope.categories = region.categories;
        $scope.showAllCats = false;
        $scope.view = 'category';
    };

    function loadCategories() {

    }

    $scope.selectCategory = function (category) {
        $scope.loadTitle = $lang['load_game'];
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
                $scope.question.title = $lang['question_save'];
            else
                $scope.question.title = $lang['question_next'];
            $scope.question.score = 0;
        }
        $http.post('getquestion', $scope.prefs).then(function (res) {
            if (res.data == '') {
                $scope.removeUnloadEvent();
                $scope.getLeaderboard();
                return;
            }
            $scope.question = res.data;
            $scope.question.title = $lang['question_idle'];
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
            $scope.question.title = $lang['question_check'];
            $http.post('answer', {choice: choice}).then(function (res) {
                if(res.data == '') {
                    $scope.question.title = $lang['question_timeout'];
                    $timeout(function () {
                        $scope.getQuestion();
                    }, 2000);
                    return;
                }
                $scope.question.wait = false;
                if (res.data.userChoice == res.data.correctChoice) {
                    $scope.question.score = res.data.score > 0 ? res.data.score : 0;
                    $scope.question.title = $lang['question_correct'];
                } else {
                    $scope.question.title = $lang['question_wrong'];
                }
                $anchorScroll('page-top');
                $scope.choices[res.data.userChoice] = 'false';
                $scope.choices[res.data.correctChoice] = 'true';

                $timeout(function () {
                    $scope.getQuestion();
                }, 2000);
            });
        } else {
            $scope.question.title = $lang['question_timeout'];
            $timeout(function () {
                $scope.getQuestion();
            }, 2000);
        }
    };

    $scope.getLeaderboard = function () {
        $scope.stopTimer();
        $scope.removeUnloadEvent();
        $scope.view = 'loading';
        $scope.loadTitle = $lang['load_leaderboard'];
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