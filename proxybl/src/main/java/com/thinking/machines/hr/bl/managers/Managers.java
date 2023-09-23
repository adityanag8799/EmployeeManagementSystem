package com.thinking.machines.hr.bl.managers;
public class Managers {
    private enum ManagerType {DESIGNATION, EMPLOYEE}

    ;
    public static ManagerType DESIGNATION = ManagerType.DESIGNATION;
    public static ManagerType EMPLOYEE = ManagerType.EMPLOYEE;

    public static String getManagerType(ManagerType managerType) {
        if (managerType == ManagerType.DESIGNATION) return "designationManager";
        if (managerType == ManagerType.EMPLOYEE) return "employeeManager";
        return ""; // this will never happen;
    }

    public static class Designation {
        private enum Action {
            ADD_DESIGNATION,
            UPDATE_DESIGNATION,
            REMOVE_DESIGNATION,
            GET_BY_DESIGNATION_CODE,
            GET_BY_DESIGNATION_TITLE,
            GET_DESIGNATION_COUNT,
            GET_DESIGNATIONS,
            DESIGNATION_CODE_EXISTS,
            DESIGNATION_TITLE_EXISTS
        }

        ;
        public static Action ADD_DESIGNATION = Action.ADD_DESIGNATION;
        public static Action UPDATE_DESIGNATION = Action.UPDATE_DESIGNATION;
        public static Action REMOVE_DESIGNATION = Action.REMOVE_DESIGNATION;
        public static Action GET_BY_DESIGNATION_CODE = Action.GET_BY_DESIGNATION_CODE;
        public static Action GET_BY_DESIGNATION_TITLE = Action.GET_BY_DESIGNATION_TITLE;
        public static Action GET_DESIGNATION_COUNT = Action.GET_DESIGNATION_COUNT;
        public static Action GET_DESIGNATIONS = Action.GET_DESIGNATIONS;
        public static Action DESIGNATION_CODE_EXISTS = Action.DESIGNATION_CODE_EXISTS;
        public static Action DESIGNATION_TITLE_EXISTS = Action.DESIGNATION_TITLE_EXISTS;
    }
        public static String getAction(Designation.Action action) {
            if (action == Designation.ADD_DESIGNATION) return "addDesignation";
            if (action == Designation.UPDATE_DESIGNATION) return "updateDesignation";
            if (action == Designation.REMOVE_DESIGNATION) return "removeDesignation";
            if (action == Designation.GET_BY_DESIGNATION_CODE) return "getByDesigantionCode";
            if (action == Designation.GET_BY_DESIGNATION_TITLE) return "getByDesignationTitle";
            if (action == Designation.GET_DESIGNATION_COUNT) return "getDesignationCount";
            if (action == Designation.GET_DESIGNATIONS) return "getDesignations";
            if (action == Designation.DESIGNATION_CODE_EXISTS) return "designationCodeExists";
            if (action == Designation.DESIGNATION_TITLE_EXISTS) return "designationTitleExists";
            return ""; // this will never happen;
        }
    }

