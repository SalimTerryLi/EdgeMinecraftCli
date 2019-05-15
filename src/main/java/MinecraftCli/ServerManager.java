package MinecraftCli;

import MinecraftCli.Event.EventHandler;
import MinecraftCli.Event.EventHub;
import MinecraftCli.Event.onNewServerIdentified;
import MinecraftCli.LineParser.LineParserHub;
import MinecraftCli.Mod.ModHandler;
import MinecraftCli.utils.MixedIO;

public final class ServerManager implements EventHandler<onNewServerIdentified> {
    private boolean isServerReady = false;
    private EventHub eventHub = new EventHub(this);
    private LineParserHub lineParserHub = new LineParserHub(eventHub);
    private MixedIO mixedIO = new MixedIO(eventHub);
    private ModHandler modHandler = new ModHandler(eventHub);

    private ServerManager() {
        mixedIO.start();
        eventHub.registerHandle(this);
        modHandler.init();
    }

    public static ServerManager getInstance() {
        return ServerManagerHolder.instance;
    }

    public void onEventArrival(onNewServerIdentified event) {
        isServerReady = true;
    }

    public boolean isServerReady() {
        return isServerReady;
    }

    private static class ServerManagerHolder {
        private static ServerManager instance = new ServerManager();
    }
}
