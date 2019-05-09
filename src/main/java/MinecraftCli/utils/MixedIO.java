package MinecraftCli.utils;

import MinecraftCli.Event.EventHub;
import MinecraftCli.Event.onNewCliLineArrivalEvent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MixedIO implements Runnable {
    private EventHub eventHub;

    private BlockingQueue<DataWithSourceInfo> buffer= new LinkedBlockingQueue<DataWithSourceInfo>();
    private SocketExportCliIO socketIO =new SocketExportCliIO(26545);
    private StandardIO stdIO =new StandardIO();
    private SocketExportCliIOReadThread socketCli =new SocketExportCliIOReadThread();
    private StandardIOReadThread stdCli=new StandardIOReadThread();

    private boolean shouldExit = false;

    public MixedIO(EventHub eventHub) {
        this.eventHub = eventHub;
    }

    public void run() {
        socketCli.start();
        stdCli.start();
        while(!shouldExit){  // Process all data in a single thread.
            try{
                DataWithSourceInfo tmp=buffer.take();
                if(tmp.source==SourceType.SocketExportCliIOReadThread){
                    stdIO.writeLine(tmp.data);
                }else if(tmp.source==SourceType.StandardIOReadThread){
                    socketIO.writeLine(tmp.data);
                    eventHub.publishEvent(new onNewCliLineArrivalEvent(tmp.data));
                }
            }catch(InterruptedException err){}
        }
        shouldExit = false;
    }

    public void start(){
        Thread t=new Thread(this,"MixedIOThread");
        t.start();
    }

    public void stop(){
        shouldExit=true;
        socketCli.stop();
        stdCli.stop();
    }

    private class DataWithSourceInfo{
        public String data;
        public SourceType source;

        public DataWithSourceInfo(String data, SourceType source) {
            this.data = data;
            this.source = source;
        }
    }

    private enum SourceType{
        SocketExportCliIOReadThread,StandardIOReadThread
    }

    /* Reading data from socket connection and put into blocked queue. */
    private final class SocketExportCliIOReadThread implements Runnable {
        private boolean shouldExit = false;

        public void run() {
            if(!socketIO.startListening()){return;}    //  Listening failed.
            while (!shouldExit) {
                try{
                    buffer.put(new DataWithSourceInfo(socketIO.getLine(),SourceType.SocketExportCliIOReadThread));
                }catch(InterruptedException err){
                    System.err.println(err);
                }
            }
            shouldExit = false;
        }

        public void start(){
            Thread t=new Thread(this,"SocketExportCliIOReadThread");
            t.start();
        }

        public void stop() {
            this.shouldExit = true;
        }
    }

    /* Reading data from stdIO and put into blocked queue. */
    private final class StandardIOReadThread implements Runnable {
        private boolean shouldExit = false;

        public void run() {
            while (!shouldExit) {
                try{
                    buffer.put(new DataWithSourceInfo(stdIO.getLine(),SourceType.StandardIOReadThread));
                }catch(InterruptedException err){
                    System.err.println(err);
                }
            }
            shouldExit = false;
        }

        public void start(){
            Thread t=new Thread(this,"StandardIOReadThread");
            t.start();
        }

        public void stop() {
            this.shouldExit = true;
        }
    }
}
