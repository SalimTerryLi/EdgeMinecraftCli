package MinecraftCli;

import MinecraftCli.Event.EventHandler;
import MinecraftCli.Event.EventHub;
import MinecraftCli.Event.onNewServerIdentified;
import MinecraftCli.LineParser.LineParserHub;
import MinecraftCli.utils.MixedIO;

public final class ServerManager implements EventHandler<onNewServerIdentified> {
    public void onEventArrival(onNewServerIdentified event) {
        isServerReady = true;
    }

    private static class ServerManagerHolder {
        private static ServerManager instance = new ServerManager();
    }

    public static ServerManager getInstance() {
        return ServerManagerHolder.instance;
    }

    private ServerManager() {
        mixedIO.start();
        eventHub.registerHandle(this);
    }

    private boolean isServerReady = false;

    public boolean isServerReady() {
        return isServerReady;
    }

    private EventHub eventHub = new EventHub(this);
    private LineParserHub lineParserHub = new LineParserHub(eventHub);
    private MixedIO mixedIO = new MixedIO(eventHub);
}
