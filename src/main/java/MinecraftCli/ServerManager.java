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
    public EventHub getEventHub() {
        return eventHub;
    }
    private LineParserHub lineParserHub=new LineParserHub(this);
    public LineParserHub getLineParserHub() {
        return lineParserHub;
    }
    private PlayerHub playerHub=new PlayerHub(this);
    public PlayerHub getPlayerHub() {
        return playerHub;
    }
    public MixedIO mixedIO=new MixedIO();
    public MixedIO getMixedIO() {
        return mixedIO;
    }

    public void start(){
        mixedIO.start();
    }
}
