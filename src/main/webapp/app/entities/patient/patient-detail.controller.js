(function() {
    'use strict';

    angular
        .module('myPharmacyApp')
        .controller('PatientDetailController', PatientDetailController);

    PatientDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Patient', 'User'];

    function PatientDetailController($scope, $rootScope, $stateParams, previousState, entity, Patient, User) {
        var vm = this;

        vm.patient = entity;
        vm.previousState = previousState.name;
        var unsubscribe = $rootScope.$on('myPharmacyApp:patientUpdate', function(event, result) {
            vm.patient = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
