'use strict';
(function () {
    angular.module('ccTest').directive('calculator', function () {
            return {
                restrict : 'E',
                scope : {},
                templateUrl: 'components/calc/CalcView.html',
                controller : 'CalcCtrl'
            }
    });
})();