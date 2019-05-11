package MinecraftCli.Event;

import MinecraftCli.ServerInfoHolder;

public class onNewServerIdentified implements Event {
    public ServerInfoHolder serverInfo;

    public onNewServerIdentified(ServerInfoHolder serverInfo) {
        this.serverInfo = serverInfo;
    }

    public int getSomething() {
        return 0;
    }
}
