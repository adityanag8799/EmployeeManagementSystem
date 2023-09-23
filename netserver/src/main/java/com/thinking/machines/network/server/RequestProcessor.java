package com.thinking.machines.network.server;
import com.thinking.machines.network.common.*;
import com.thinking.machines.network.common.exceptions.*;
import java.net.*;
import java.io.*;
class RequestProcessor extends Thread
{
private RequestHandlerInterface requestHandler;
private Socket socket;
public RequestProcessor(Socket socket ,RequestHandlerInterface requestHandler)
{
this.requestHandler=requestHandler;
this.socket=socket;
start();
}
public void run()
{
try
{
InputStream is=socket.getInputStream();
OutputStream os=socket.getOutputStream();
int bytesToReceive=1024;
byte tmp[]=new byte[1024];
byte header[]=new byte[1024];
int i,j,k;
i=0;
j=0;
int bytesReadCount;
while(j<bytesToReceive)
{
bytesReadCount=is.read(tmp); 
if(bytesReadCount==-1) continue;
for(k=0;k<bytesReadCount;k++)  
{
header[i]=tmp[k];
i++;
}
j=j+bytesReadCount;
}
int requestLength=0;
j=1023;
i=1;
while(j>=0)
{
requestLength=requestLength+(header[j]*i);
i=i*10;
j--;
}
byte ack[]=new byte[1];
ack[0]=1;
os.write(ack,0,1);
os.flush();

byte requestBytes[]=new byte[requestLength];
bytesToReceive=requestLength;
j=0;
i=0;
while(j<bytesToReceive)
{
bytesReadCount=is.read(tmp); 
if(bytesReadCount==-1) continue;
for(k=0;k<bytesReadCount;k++)  
{
requestBytes[i]=tmp[k];
i++;
}
j=j+bytesReadCount;
}

// deserialize 

ByteArrayInputStream bais=new ByteArrayInputStream(requestBytes);
ObjectInputStream ois=new ObjectInputStream(bais);
Request request=(Request)ois.readObject();
Response response=requestHandler.process(request);
ByteArrayOutputStream baos=new ByteArrayOutputStream();
ObjectOutputStream oos=new ObjectOutputStream(baos);
oos.writeObject(response);
oos.flush();
byte objectBytes[]=baos.toByteArray();
int responseLength=objectBytes.length;
int x;
i=1023;
x=responseLength;
header=new byte[1024];
while(x>0)
{
header[i]=(byte)(x%10);
x=x/10;
i--;
}
os.write(header,0,1024);  // from which index,how many
os.flush(); 
System.out.println("Response Header sent "+responseLength);
while(true)
{
bytesReadCount=is.read(ack); 
// firstly it will wait till it won't get ack.If not received then it will return -1
if(bytesReadCount==-1) continue;
break; // cause we get the ack
}
System.out.println("ack received");
int bytesToSend=responseLength;
j=0;
int chunkSize=1024;
while(j<bytesToSend)
{
if((bytesToSend-j)<chunkSize) chunkSize=bytesToSend-j;
os.write(objectBytes,j,chunkSize);
os.flush();
j=j+chunkSize;
}
System.out.println("Response Sent ");
while(true)
{
bytesReadCount=is.read(ack); 
if(bytesReadCount==-1) continue;
break;
}
System.out.println("ack received");
socket.close();
}catch(Exception e)
{
System.out.println(e);
}
}
}
