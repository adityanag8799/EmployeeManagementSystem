package com.thinking.machines.hr.server;
import com.thinking.machines.network.server.*;
import com.thinking.machines.network.common.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;

public class RequestHandler implements RequestHandlerInterface
{
private DesignationManagerInterface designationManager;
public RequestHandler()
{
try
{
designationManager=DesignationManager.getDesignationManager();
}catch(BLException blException)
{
// do nothing.
}
}
public Response process(Request request)
{
Response response=new Response();
String manager=request.getManager();
String action=request.getAction();
Object [] arguments=request.getArguments();
if(manager.equals("designationManager"))
{
if(designationManager==null)
{
// will implement later.
}
if(action.equals("getDesignations"))
{
Object result=designationManager.getDesignations();
response.setSuccess(true);
response.setException(null);
response.setResult(result);
}

if(action.equals("addDesignation")) {
    try {
        DesignationInterface designationInterface;
        designationInterface = (DesignationInterface) arguments[0];
        designationManager.addDesignation(designationInterface);
        response.setSuccess(true);
        response.setException(null);
        response.setResult(null);
    } catch (BLException blException) {
        response.setSuccess(false);
        response.setException(blException);
        response.setResult(null);
    }
}
    if(action.equals("updateDesignation")) {
        try {
            DesignationInterface designationInterface;
            designationInterface = (DesignationInterface) arguments[0];
            designationManager.updateDesignation(designationInterface);
            response.setSuccess(true);
            response.setException(null);
            response.setResult(null);
        } catch (BLException blException) {
            response.setSuccess(false);
            response.setException(blException);
            response.setResult(null);
        }
    }

    if(action.equals("removeDesignation")) {
        try {
            Integer code;
            code = (Integer) arguments[0];
            designationManager.removeDesignation(code);
            response.setSuccess(true);
            response.setException(null);
            response.setResult(null);
        } catch (BLException blException) {
            response.setSuccess(false);
            response.setException(blException);
            response.setResult(null);
        }
    }

    if(action.equals("getByDesignationCode")) {
        try {
            Integer code;
            code = (Integer) arguments[0];
            Object result=designationManager.getByDesignationCode(code);
            response.setSuccess(true);
            response.setException(null);
            response.setResult(result);
        } catch (BLException blException) {
            response.setSuccess(false);
            response.setException(blException);
            response.setResult(null);
        }
    }
    if(action.equals("getByDesignationTitle")) {
        try {
            String title;
            title = (String) arguments[0];
            Object result=designationManager.getByDesignationTitle(title);
            response.setSuccess(true);
            response.setException(null);
            response.setResult(result);
        } catch (BLException blException) {
            response.setSuccess(false);
            response.setException(blException);
            response.setResult(null);
        }
    }

    if(action.equals("getDesignationCount"))
    {
            Object result=designationManager.getDesignationCount();
            response.setSuccess(true);
            response.setException(null);
            response.setResult(result);
    }
    if(action.equals("designationCodeExists"))
    {

            Integer code;
            code = (Integer) arguments[0];
            Object result=designationManager.designationCodeExists(code);
            response.setSuccess(true);
            response.setException(null);
            response.setResult(result);
    }

    if(action.equals("designationTitleExists"))
    {
            String title;
            title = (String) arguments[0];
            Object result=designationManager.designationTitleExists(title);
            response.setSuccess(true);
            response.setException(null);
            response.setResult(result);
    }
} //  designationManager part ends here.

return response;
}
}

