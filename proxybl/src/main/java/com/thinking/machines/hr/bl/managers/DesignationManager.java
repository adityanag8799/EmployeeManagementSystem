package com.thinking.machines.hr.bl.managers;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import java.util.*;
import com.thinking.machines.network.common.*;
import com.thinking.machines.network.client.*;
import com.thinking.machines.network.common.exceptions.*;

public class DesignationManager implements DesignationManagerInterface {
    private static DesignationManagerInterface designationManager = null;

    private DesignationManager() throws BLException {
    }

    public static DesignationManagerInterface getDesignationManager() throws BLException {
        if (designationManager == null) {
            designationManager = new DesignationManager();
        }
        return designationManager;
    }

    public void addDesignation(DesignationInterface designation) throws BLException {
        BLException blException;
        blException = new BLException();
        if (designation == null) {
            blException.setGenericException("designation required.");
            throw blException;
        }
        int code = designation.getCode();
        String title = designation.getTitle();
        if (code != 0) blException.addException("code", "code should be zero");
        if (title == null) {
            blException.addException("title", "title required.");
            title = "";
        }
        title = title.trim();
        if (title.length() == 0) {
            blException.addException("title", "Title required.");
        }
        if (blException.hasExceptions()) {
            throw blException;
        }
        Request request = new Request();
        request.setManager(Managers.getManagerType(Managers.DESIGNATION));
        request.setAction(Managers.getAction(Managers.Designation.ADD_DESIGNATION));
        request.setArguments(designation);
        NetworkClient client = new NetworkClient();
        try {
            Response response = client.send(request);
            if (response.hasException()) {
                blException = (BLException) response.getException();
                throw blException;
            }
// V V V imp line in case of autoincerement 
            DesignationInterface d = (DesignationInterface) response.getResult();
            designation.setCode(d.getCode());
        } catch (NetworkException networkException) {
            blException.setGenericException(networkException.getMessage());
            throw blException;
        }
    }

    public void updateDesignation(DesignationInterface designation) throws BLException {
        BLException blException;
        blException = new BLException();
        if (designation == null) {
            blException.setGenericException("designation required.");
            throw blException;
        }
        int code = designation.getCode();
        String title = designation.getTitle();
        if (code != 0) blException.addException("code", "code should be zero");
        if (title == null) {
            blException = new BLException();
            blException.addException("title", "title required.");
            throw blException;
        }
        title = title.trim();
        if (title.length() == 0) {
            blException.addException("title", "Title required.");
            throw blException;
        }
        Request request = new Request();
        request.setManager(Managers.getManagerType(Managers.DESIGNATION));
        request.setAction(Managers.getAction(Managers.Designation.UPDATE_DESIGNATION));
        request.setArguments(designation);
        NetworkClient client = new NetworkClient();
        try {
            Response response = client.send(request);
            if (response.hasException()) {
                blException = (BLException) response.getException();
                throw blException;
            }
        } catch (NetworkException networkException) {
            blException = new BLException();
            blException.setGenericException(networkException.getMessage());
            throw blException;
        }
    }

    public void removeDesignation(int code) throws BLException {
        if (code <= 0) {
            BLException blException;
            blException = new BLException();
            blException.addException("code", "code should not be zero");
            throw blException;
        }
        Request request = new Request();
        request.setManager(Managers.getManagerType(Managers.DESIGNATION));
        request.setAction(Managers.getAction(Managers.Designation.REMOVE_DESIGNATION));
        request.setArguments(new Integer(code));
        NetworkClient client = new NetworkClient();
        try {
            Response response = client.send(request);
            if (response.hasException()) {
                BLException blException;
                blException = new BLException();
                blException = (BLException) response.getException();
                throw blException;
            }
        } catch (NetworkException networkException) {
            BLException blException;
            blException = new BLException();
            blException.setGenericException(networkException.getMessage());
            throw blException;
        }
    }

    public DesignationInterface getByDesignationCode(int code) throws BLException {
        if (code <= 0) {
            BLException blException;
            blException = new BLException();
            blException.addException("code", "code cannot be zero");
            throw blException;
        }
        Request request = new Request();
        request.setManager(Managers.getManagerType(Managers.DESIGNATION));
        request.setAction(Managers.getAction(Managers.Designation.GET_BY_DESIGNATION_CODE));
        request.setArguments(new Integer(code));
        NetworkClient client = new NetworkClient();
        try {
            Response response = client.send(request);
            DesignationInterface designation = (DesignationInterface) response.getResult();
            if (response.hasException())
            {
                BLException blException;
                blException = new BLException();
                blException = (BLException) response.getException();
                throw blException;
            }
            return designation;
        } catch (NetworkException networkException) {
            BLException blException;
            blException = new BLException();
            blException.setGenericException(networkException.getMessage());
            throw blException;
        }
    }

    public DesignationInterface getByDesignationTitle(String title) throws BLException {
        if (title == null) {
            BLException blException;
            blException = new BLException();
            blException.addException("title", "title required.");
            throw blException;
        }
        title = title.trim();
        if (title.length() == 0) {
            BLException blException;
            blException = new BLException();
            blException.addException("title", "Title required.");
            throw blException;
        }
        Request request = new Request();
        request.setManager(Managers.getManagerType(Managers.DESIGNATION));
        request.setAction(Managers.getAction(Managers.Designation.GET_BY_DESIGNATION_TITLE));
        request.setArguments(title);
        NetworkClient client = new NetworkClient();
        try {
            Response response = client.send(request);
            DesignationInterface designation = (DesignationInterface) response.getResult();

            if (response.hasException()) {
                BLException blException;
                blException = new BLException();
                blException = (BLException) response.getException();
                throw blException;
            }
            return designation;
        } catch (NetworkException networkException) {
            BLException blException;
            blException = new BLException();
            blException.setGenericException(networkException.getMessage());
            throw blException;
        }
    }

    public Set<DesignationInterface> getDesignations() {
        Request request = new Request();
        request.setManager(Managers.getManagerType(Managers.DESIGNATION));
        request.setAction(Managers.getAction(Managers.Designation.GET_DESIGNATIONS));
        NetworkClient client = new NetworkClient();
        try {
            Response response = client.send(request);
            Set<DesignationInterface> designations = (Set<DesignationInterface>) response.getResult();
            return designations;
        } catch (NetworkException networkException) {
            throw new RuntimeException(networkException.getMessage());
        }
    }

    public int getDesignationCount() {
        Request request = new Request();
        request.setManager(Managers.getManagerType(Managers.DESIGNATION));
        request.setAction(Managers.getAction(Managers.Designation.GET_DESIGNATION_COUNT));
        NetworkClient client = new NetworkClient();
        try {
            Response response = client.send(request);
            Integer designationCount = (Integer) response.getResult();
            return designationCount;
        } catch (NetworkException networkException) {
            throw new RuntimeException(networkException.getMessage());
        }
    }

    public boolean designationCodeExists(int code) {
        if (code <= 0) return false;
        Request request = new Request();
        request.setManager(Managers.getManagerType(Managers.DESIGNATION));
        request.setAction(Managers.getAction(Managers.Designation.DESIGNATION_CODE_EXISTS));
        request.setArguments(new Integer(code));
        NetworkClient client = new NetworkClient();
        try {
            Response response = client.send(request);
            Boolean codeExists = (Boolean) response.getResult();
            return codeExists;
        } catch (NetworkException networkException) {
            throw new RuntimeException(networkException.getMessage());
        }
    }

    public boolean designationTitleExists(String title) {
        if (title == null) return false;
        title = title.trim();
        if (title.length() == 0) return false;
        Request request = new Request();
        request.setManager(Managers.getManagerType(Managers.DESIGNATION));
        request.setAction(Managers.getAction(Managers.Designation.DESIGNATION_TITLE_EXISTS));
        request.setArguments(title);
        NetworkClient client = new NetworkClient();
        try {
            Response response = client.send(request);
            Boolean titleExists = (Boolean) response.getResult();
            return titleExists;
        } catch (NetworkException networkException) {
            throw new RuntimeException(networkException.getMessage());
        }
    }
}