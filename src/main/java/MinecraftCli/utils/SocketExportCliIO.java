package MinecraftCli.utils;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public final class SocketExportCliIO {

    public SocketExportCliIO(int portNumber) {
        try{
            serverSocket=new ServerSocket(portNumber,1, InetAddress.getByName("localhost"));
        }catch(IOException err){System.err.println(err);}
    }

    private Socket socket;
    private ServerSocket serverSocket;
    private InputStreamReader isr;
    private OutputStreamWriter osw;
    private BufferedReader in;
    private BufferedWriter out;

    private boolean isConnected=false;

    public boolean startListening(){
        try{
            socket=serverSocket.accept();
            isConnected=true;
            isr=new InputStreamReader(socket.getInputStream());
            in=new BufferedReader(isr);
            osw=new OutputStreamWriter(socket.getOutputStream());
            out=new BufferedWriter(osw);
            return true;
        }catch(IOException err){System.err.println(err);}
        return false;
    }

    public void stopListening(){
        try{
            in.close();
            out.close();
            isr.close();
            osw.close();
            socket.close();
        }catch(IOException err){System.err.println(err);}
        isConnected=false;
    }

    public String getLine(){
        try{
            return in.readLine();
        }catch(IOException err){System.err.println(err);}
        this.stopListening();
        this.startListening();
        return getLine();
    }

    public void writeLine(String data){
        if(isConnected) {
            try {
                out.write(data);
                out.newLine();
                out.flush();
            } catch (IOException err) {
            }
        }
    }
}
