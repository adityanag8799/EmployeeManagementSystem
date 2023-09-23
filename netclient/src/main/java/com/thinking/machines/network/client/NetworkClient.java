package com.thinking.machines.network.client;
import com.thinking.machines.network.common.*;
import com.thinking.machines.network.common.exceptions.*;
import java.io.*;
import java.net.*;
public class NetworkClient
{
public Response send(Request request) throws NetworkException
{
try
{
ByteArrayOutputStream baos=new ByteArrayOutputStream();
ObjectOutputStream oos=new ObjectOutputStream(baos);
oos.writeObject(request);
oos.flush();
byte objectBytes[];
objectBytes=baos.toByteArray();
int requestLength=objectBytes.length;
byte header[]=new byte[1024];
int x;
int i;
i=1023;
x=requestLength;
while(x>0)
{
header[i]=(byte)(x%10);
x=x/10;
i--;
}
Socket socket=new Socket(Configuration.getHost(),Configuration.getPort());
OutputStream os=socket.getOutputStream();
os.write(header,0,1024);  // from which index,how many
os.flush(); 
// we want send our data right now, firstly we will receive ack of 1 byte from other end.
InputStream is=socket.getInputStream();
byte ack[]=new byte[1];
// we will wait till we won't get ack.
int bytesReadCount;
while(true)
{
bytesReadCount=is.read(ack); 
// firstly it will wait till it won't get ack.If not received then it will return -1
if(bytesReadCount==-1) continue;
break; // cause we get the ack
}
// Now we will send data/content
int bytesToSend=requestLength;
int j=0;
int chunkSize=1024;
while(j<bytesToSend)
{
if((bytesToSend-j)<chunkSize) chunkSize=bytesToSend-j;
os.write(objectBytes,j,chunkSize);
os.flush();
j=j+chunkSize;
}
int bytesToReceive=1024;
j=0;
byte tmp[]=new byte[1024];
int k;
i=0;
while(j<bytesToReceive)
{
bytesReadCount=is.read(tmp);  // problem -- unable to read all 1024 bytes at once.
	   		   //So It will considered that figure.
if(bytesReadCount==-1) continue;

for(k=0;k<bytesReadCount;k++)  
{
header[i]=tmp[k];
i++;
}
j=j+bytesReadCount;
}
/*
aana 1024 hai, but kisi karan se pade 100 , 100 tmp mein h, bytesReadCount ki 
value 100 ,-1 nahi h , k 0 se 99 tk chalega  , 0 se 99 tk ka masala header mein 0 se 99 
tkk rakha jaega,agle chakkar mein phir woh padega ab manlo 200 pad liye
tmp mein 0 se 199 tk h, pr jo header mein h woh 0 se nahi rakhe jaenge, jitne rakhe 
jaa chuke hai uske aage se rakhe jaenge. yeh loop khtm hoga toh 100% 1024 pade jaa chuke honge
*/
// now we will send ack

int responseLength=0;
j=1023;
i=1;
while(j>=0)
{
responseLength=responseLength+(header[j]*i);
i=i*10;
j--;
}
System.out.println("Header received : "+responseLength);
ack[0]=1;
os.write(ack,0,1);
os.flush();

// response length hai utna data aane wala h.
System.out.println("ack sent");
byte response[]=new byte[responseLength];
bytesToReceive=responseLength;
j=0;
i=0;
System.out.println("Now receiving response");
while(j<bytesToReceive)
{
bytesReadCount=is.read(tmp); 
if(bytesReadCount==-1) continue;
for(k=0;k<bytesReadCount;k++)  
{
response[i]=tmp[k];
i++;
}
j=j+bytesReadCount;
}
System.out.println("response received");
ack[0]=1;
os.write(ack);
os.flush();
socket.close();

// jo response aaya usko deserialize karna 
ByteArrayInputStream bais=new ByteArrayInputStream(response);
ObjectInputStream ois=new ObjectInputStream(bais);
Response responseObject=(Response)ois.readObject();
return responseObject;
}
catch(Exception exception)
{
throw new NetworkException(exception.getMessage());
}
}

}