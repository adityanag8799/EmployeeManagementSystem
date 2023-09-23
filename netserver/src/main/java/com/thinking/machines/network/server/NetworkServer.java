package com.thinking.machines.network.server;
import com.thinking.machines.network.common.*;
import com.thinking.machines.network.common.exceptions.*;
import java.net.*;
public class NetworkServer
{
private RequestHandlerInterface requestHandler;
public NetworkServer(RequestHandlerInterface requestHandler) throws NetworkException
{
if(requestHandler==null)
{
throw new NetworkException("Request Handler Missing");
}
this.requestHandler=requestHandler;
}
public void start() throws NetworkException  // Will  run on main thread
{
ServerSocket serverSocket=null;
try
{
serverSocket=new ServerSocket(Configuration.getPort());
}catch(Exception exception)
{
throw new NetworkException("Unable to start server on port : "+Configuration.getPort());
}

try
{
Socket socket;
RequestProcessor requestProcessor;
while(true)
{
System.out.println("Server is ready to accept request on port : "+Configuration.getPort());
socket=serverSocket.accept();
requestProcessor=new RequestProcessor(socket,requestHandler);
}
}catch(Exception e)
{
System.out.println(e);
}
}
}