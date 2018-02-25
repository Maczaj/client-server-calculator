'use strict';
(function () {
    angular.module('ccTest').controller('CalcCtrl', CalcCtrl);

    function CalcCtrl($scope, $timeout, calculatorService) {

        var operators = ['+', '-', '÷', '&radic;', '×'];
        const enclosingBrackets = [')', ']', '}'];
        const openingBrackets = ['(', '[', '{'];

        $scope.expression = '0';
        $scope.history = [];
        $scope.historyOpen = false;
        $scope.integralForm = {threads: 1, segments: 1};
        $scope.activeTab = 'expr';

        $scope.toggleHistory = function () {
            $scope.historyOpen = !$scope.historyOpen;
        };

        $scope.clearExpression = function () {
            $scope.expression = '0';
        };

        $scope.switchTab = function (newTab) {
            $scope.activeTab = newTab;
        };

        $scope.calculateIntegral = function ($form) {
            delete $scope.integralValue;
            if ($form.$valid) {
                calculatorService.requestIntegralCalculation($scope.integralForm)
                    .then(function (res) {
                        $scope.integralValue = res.data.result;
                    }, function (err) {
                        $scope.errorMsg = err.data.message;
                    });
            }
        };

        $scope.handleComma = function () {
            if (endsWithNumber() && !commaAlreadyPresentInLastNumber()) {
                $scope.expression += ',';
            }
        };

        $scope.handleOperator = function ($event) {
            var operator = angular.element($event.target).text();
            if (endsWithNumber() || endsWithEnclosingBracket() || endsWithPower()) {
                $scope.expression += operator;
            }
        };


        $scope.handleRoot = function ($event) {
            var symbol = angular.element($event.target).text();
            if (endsWithOperator() || endsWithOpeningBracket() || isEmpty()) {
                if (isEmpty()) {
                    $scope.expression = symbol;
                } else {
                    $scope.expression += symbol;
                }

            }
        };

        $scope.handleEnclosingBracket = function ($event) {
            var bracket = angular.element($event.target).text();
            if (!endsWithOperator() && bracketStructCorrectSoFar(bracket)) {
                $scope.expression += bracket;
            }
        };


        $scope.handleOpeningBracket = function ($event) {
            var bracket = angular.element($event.target).text();
            if (endsWithOperator() || endsWithOpeningBracket() || endsWithRoot()) {
                $scope.expression += bracket;
            } else if (isEmpty()) {
                $scope.expression = bracket;
            }
        };

        $scope.handleZero = function () {
            if (isEmpty()) {
                return;
            }
            if (endsWithOperator() || endsWithNonzeroNumber()) {
                $scope.expression += 0;
            }
        };

        $scope.submitExpression = function () {
            delete $scope.errorMsg;
            if ($scope.expression === getLastHistoryResult()) {
                return;
            }
            calculatorService.requestCalculation($scope.expression)
                .then(function (res) {
                    var calcResult = res.data.result;
                    $scope.history.unshift({expression: $scope.expression, result: calcResult});
                    $scope.expression = calcResult.toString();
                }).catch(function (err) {
                $scope.errorMsg = err.data.message;
            })
        };

        $scope.handleSquare = function () {
            if (endsWithNumber() || endsWithEnclosingBracket()) {
                $scope.expression += '²';
            }
        };

        $timeout(function () {
            angular.element('.calculator button:not([ng-click])').bind('click', function (event) {
                var element = angular.element(event.target);
                $scope.$apply(function () {
                    if (endsWithPower() || endsWithEnclosingBracket()) {
                        return;
                    }

                    var text = element.text();
                    if (!isNaN(text) && isEmpty()) {
                        $scope.expression = '';
                    }

                    addNumberOrReplaceLeadingZero(text);
                    element.blur();
                });
            })
        });


        function getLastHistoryResult() {
            if (!$scope.history.length) {
                return '';
            }
            return $scope.history[0].result;
        }

        function endsWithNumber() {
            return !isNaN($scope.expression[$scope.expression.length - 1]);
        }

        function commaAlreadyPresentInLastNumber() {
            var i = $scope.expression.length - 1;
            while (!isNaN($scope.expression[i])) {
                --i;
            }

            return $scope.expression[i] === ',';
        }

        function endsWithEnclosingBracket() {
            return enclosingBrackets.indexOf($scope.expression[$scope.expression.length - 1]) !== -1;
        }

        function endsWithPower() {
            return $scope.expression.lastIndexOf('²') === $scope.expression.length - 1;
        }

        function isClosingBracket(c) {
            return enclosingBrackets.indexOf(c) !== -1;
        }

        function isOperator(c) {
            return operators.indexOf(c) !== -1;
        }

        function endsWithOperator() {
            return isOperator($scope.expression[$scope.expression.length - 1]);
        }

        function isOpeningBracket(c) {
            return openingBrackets.indexOf(c) !== -1;
        }

        function endsWithOpeningBracket() {
            return isOpeningBracket($scope.expression[$scope.expression.length - 1]);
        }

        function bracketStructCorrectSoFar(bracket) {
            var i = 0;
            var stack = [];
            while (i < $scope.expression.length) {
                var current = $scope.expression[i];
                if (isOpeningBracket(current)) {
                    stack.push(current);
                } else if (isClosingBracket(current)) {
                    var fromStack = stack.pop();
                }

                ++i;
            }

            if (!stack.length) {
                return false;
            }

            fromStack = stack.pop();

            return openingBrackets.indexOf(fromStack) === enclosingBrackets.indexOf(bracket);
        }

        function isEmpty() {
            return $scope.expression === '0';
        }

        function endsWithRoot() {
            return $scope.expression.lastIndexOf('√') === $scope.expression.length - 1;
        }


        function endsWithNonzeroNumber() {
            var i = $scope.expression.length - 1;
            while (i > 0 && $scope.expression[i] === '0') {
                --i;
            }

            var lastSymbol = $scope.expression[i];
            return (lastSymbol === ',' || (lastSymbol !== '0' && !isNaN(lastSymbol)));
        }


        function addNumberOrReplaceLeadingZero(text) {
            if (!$scope.expression.length) {
                $scope.expression = text;
                return;
            }

            var numberStartIndex = $scope.expression.length - 1;
            while (!isNaN($scope.expression[numberStartIndex])) {
                --numberStartIndex;
            }

            if ($scope.expression[numberStartIndex] !== ',' && $scope.expression[numberStartIndex + 1] === '0') {
                $scope.expression = $scope.expression.substring(0, numberStartIndex + 1) + text;
            } else {
                $scope.expression += text;
            }
        }

    }
})();