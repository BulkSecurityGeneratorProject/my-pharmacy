(function() {
    'use strict';

    angular
        .module('myPharmacyApp')
        .controller('PatientDialogController', PatientDialogController);

    PatientDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Principal', 'Patient', 'User'];

    function PatientDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Principal, Patient, User) {
        var vm = this;

        vm.patient = entity;
        vm.clear = clear;
        vm.save = save;
        getAccount();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
                var val = User.get({ login: account.login}, function() {
                    console.log(vm.users);
                }); // get() returns a single entry
                vm.users = [val];
                vm.patient.user = val;

            });
        }

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.patient.id !== null) {
                Patient.update(vm.patient, onSaveSuccess, onSaveError);
            } else {
                Patient.save(vm.patient, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('myPharmacyApp:patientUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }
    }
})();
