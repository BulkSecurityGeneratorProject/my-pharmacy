(function() {
    'use strict';

    angular
        .module('myPharmacyApp')
        .controller('ExperimentController', ExperimentController);

    ExperimentController.$inject = ['$scope','$state','Patient', 'Drug'];

    function ExperimentController ($scope, $state, Patient, Drug) {
        var vm = this;
        vm.drug = null;
        vm.busulfan = false;
        vm.patients = Patient.query();
        vm.drugs = Drug.query();
        vm.changedValue = ChangedValue;

        function ChangedValue(item) {
            console.log(item);
        }

    }
})();
