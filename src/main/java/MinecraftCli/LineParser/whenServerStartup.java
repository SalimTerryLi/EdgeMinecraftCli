package MinecraftCli.LineParser;

import MinecraftCli.Event.EventHub;
import MinecraftCli.Event.onNewServerIdentified;
import MinecraftCli.ServerInfoHolder;
import MinecraftCli.ServerManager;

import java.util.Arrays;

public class whenServerStartup extends LineParser {
    private static String Start_minecraft_server_version ="[Server thread/INFO]: Starting minecraft server version";
    private static String Default_game_type="[Server thread/INFO]: Default game type:";
    private static String Starting_Minecraft_server_on="[Server thread/INFO]: Starting Minecraft server on";
    private static String Server_is_running_in_OFFLINE_mode="**** SERVER IS RUNNING IN OFFLINE/INSECURE MODE!";
    private static String Preparing_level="[Server thread/INFO]: Preparing level";
    private static String Reloading_resourcemanager="[Server thread/INFO]: Reloading ResourceManager:";
    private static String loaded_recipeoradvancement="[Server thread/INFO]: Loaded";
    private static String server_done="[Server thread/INFO]: Done";

    private static ServerInfoHolder serverInfo;

    public whenServerStartup(EventHub eventHub) {
        super(eventHub);
    }

    public boolean parse(String payload) {
        if (payload.contains(Start_minecraft_server_version)){
            serverInfo=new ServerInfoHolder();
            serverInfo.version= payload.split(" ")[7];
            return true;
        }
        if (serverInfo!=null){
            if(payload.contains(Default_game_type)){
                serverInfo.defaultGameType=payload.split(" ")[6];
            }else if(payload.contains(Starting_Minecraft_server_on)){
                serverInfo.listeningAddress=payload.split(" ")[7];
            }else if(payload.contains(Server_is_running_in_OFFLINE_mode)){
                serverInfo.onLineMode=false;
            }else if(payload.contains(Preparing_level)){
                serverInfo.levelName=payload.split(" ")[5].replace("\"","");
            }else if(payload.contains(Reloading_resourcemanager)){
                serverInfo.resourcePackName=payload.split(" ")[5];
            }else if(payload.contains(loaded_recipeoradvancement)){
                if(payload.contains("recipes")){
                    serverInfo.recipeCount=Integer.valueOf(payload.split(" ")[4]);
                }else if (payload.contains("advancements")){
                    serverInfo.advancementsCount=Integer.valueOf(payload.split(" ")[4]);
                }
            }else if(payload.contains(server_done)){
                serverInfo.timeElaspedDuringStartup=Float.valueOf(payload.split("\\(")[1].split("s")[0]);
                serverInfo.isFullyStarted=true;
                eventHub.publishEvent(new onNewServerIdentified(serverInfo));
            }
            System.err.println(serverInfo);
        }else{
            System.err.println("Invalid server message.");
        }
        return true;
    }
}
