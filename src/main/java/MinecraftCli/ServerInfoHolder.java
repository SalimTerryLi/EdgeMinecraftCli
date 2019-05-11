package MinecraftCli;

import java.net.InetAddress;

public class ServerInfoHolder {
    public boolean isFullyStarted=false;
    public String version;
    public String defaultGameType;
    public boolean onLineMode=true;
    public String listeningAddress;
    public int recipeCount;
    public int advancementsCount;
    public String levelName;
    public String resourcePackName;
    public float timeElaspedDuringStartup;

    @Override
    public String toString() {
        String ret="Minecraft server "+version+" "+ defaultGameType+ " " + (onLineMode?"OnLineMode":"OffLineMode")+ " "+ listeningAddress+ " "+ levelName+ " "+resourcePackName+ " "+timeElaspedDuringStartup;
        return ret;
    }
}
