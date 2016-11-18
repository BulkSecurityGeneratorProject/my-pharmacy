(function() {
    'use strict';

    angular
        .module('myPharmacyApp')
        .controller('ExperimentController', ExperimentController);

    ExperimentController.$inject = ['$scope','$state','Patient', 'Drug','entity','Experiment'];

    function ExperimentController ($scope, $state, Patient, Drug, entity, Experiment) {
        var vm = this;
        vm.stats = entity;
        vm.drug = null;
        vm.busulfan = false;
        vm.patients = Patient.query();
        vm.drugs = Drug.query();
        vm.save=save;
        vm.changedValue = ChangedValue;

        function ChangedValue(item) {
            console.log(item);
            console.log(vm.stats);
        }

        function save () {
            console.log(vm.stats);
            Experiment.post(vm.stats, onSaveSuccess, onSaveError);

        }

        function onSaveSuccess (result) {
            console.log(result);
            $state.go('experiments.result', {experiment: result, stats: vm.stats});// { reload: true, inherit: true, notify: true });        }
        }
        function onSaveError () {
            console.log("WHOOPS");

        }
    }
})();
