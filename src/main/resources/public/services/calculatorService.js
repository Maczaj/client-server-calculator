'use strict';
(function () {
    angular.module('ccTest').service('calculatorService', function ($http) {
        var calcSrv = {};

        calcSrv.requestCalculation = function (expression) {
            return $http.post('/api/calculation/expression', {expression: expression});
        };

        calcSrv.requestIntegralCalculation = function (request) {
            return $http.post('/api/calculation/integral', request);
        };

        return calcSrv;
    })
})();