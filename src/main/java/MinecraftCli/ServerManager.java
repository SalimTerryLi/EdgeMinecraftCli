package MinecraftCli;

import MinecraftCli.Event.EventHub;
import MinecraftCli.LineParser.LineParserHub;
import MinecraftCli.Player.PlayerHub;
import MinecraftCli.utils.MixedIO;

public final class ServerManager {
    private static class ServerManagerHolder{
        private static ServerManager instance=new ServerManager();
    }

    public static ServerManager getInstance(){
        return ServerManagerHolder.instance;
    }

    private ServerManager(){
    }

    private EventHub eventHub=new EventHub(this);
    private LineParserHub lineParserHub=new LineParserHub(eventHub);
    //private PlayerHub playerHub=new PlayerHub(eventHub);
    private MixedIO mixedIO=new MixedIO(eventHub);

    public void start(){
        mixedIO.start();
    }
}
