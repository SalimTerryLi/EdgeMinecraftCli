package MinecraftCli.Mod;

import MinecraftCli.Event.EventHandler;

import java.util.ArrayList;

public interface Loader {
    public String getModName();

    public ArrayList<EventHandler> getEventHandlers();
}
