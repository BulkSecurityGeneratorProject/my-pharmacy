(function() {
        'use strict';

        angular
            .module('myPharmacyApp')
            .controller('ExperimentResultController', ExperimentResultController);

        ExperimentResultController.$inject = ['$http','$stateParams','$scope', '$state','$uibModalInstance','entity'];

        function ExperimentResultController($http,$stateParams, $scope, $state, $uibModalInstance, entity) {
            var vm = this;
            vm.clear = clear;
            console.log($stateParams.experiment);
            vm.experiment=$stateParams.experiment;
            vm.recommendedDosage = $stateParams.experiment.doseAdjusted;
            vm.stats = $stateParams.stats;
            vm.imgResult="";
            vm.img = entity;
            vm.img.x = "Time(h)";
            vm.img.y = "C";
            vm.img.yys = [vm.stats.concentration1,vm.stats.concentration2,vm.stats.concentration3,vm.stats.concentration4,vm.stats.concentration5];
            vm.img.xxs = [0.0,vm.experiment.relativeTime1,vm.experiment.relativeTime2,vm.experiment.relativeTime3,vm.experiment.relativeTime4],


            $http.get('api/experiments/simplePlot',{params: vm.img} ).then(
                function onSaveSuccess (response) {
                console.log(response.data);
                vm.imgResult=response.data;
            },  function onSaveError () {
                console.log("WHOOPS");
            });


            function clear () {
                $uibModalInstance.dismiss('cancel');
            }
        }
    }
)();
