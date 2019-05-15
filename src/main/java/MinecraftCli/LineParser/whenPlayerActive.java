package MinecraftCli.LineParser;

import MinecraftCli.Event.EventHub;
import MinecraftCli.Event.onPlayerLoggedInOut;
import MinecraftCli.Event.onPlayerMessage;
import MinecraftCli.Player.Player;
import MinecraftCli.ServerManager;

import java.util.ArrayList;
import java.util.Iterator;

public class whenPlayerActive extends LineParser {
    private ArrayList<Player> unHandledPlayers = new ArrayList<Player>();

    public whenPlayerActive(EventHub eventHub) {
        super(eventHub);
    }

    public boolean parse(String payload) {
        if (!ServerManager.getInstance().isServerReady()) {
            return true;
        }

        if (payload.contains("<") && payload.contains(">")) {       // Chatting messages
            for (Player player : unHandledPlayers) {
                if (player.name.equals(payload.substring(34, payload.indexOf(">")))) {
                    eventHub.publishEvent(new onPlayerMessage(player, payload.substring(payload.indexOf(">") + 2)));
                }
            }
        } else {      // Server status.
            if (payload.contains("[Server thread/INFO]: ")) {
                if (payload.contains(" joined the game")) {
                    for (Player player : unHandledPlayers) {
                        if (player.name.equals(payload.substring(33, payload.length() - 16 - 1))) {
                            eventHub.publishEvent(new onPlayerLoggedInOut(player, onPlayerLoggedInOut.PlayerStatus.LoggedIn));
                        }
                    }
                } else if (payload.contains("logged in with entity id")) {
                    // Following codes are read-ONLY!
                    String[] tmp1 = payload.substring(33, payload.indexOf(" logged in with entity id ")).split("\\[");
                    String[] tmp2 = payload.substring(payload.indexOf(" logged in with entity id ")).split("\\(")[1].split("\\)")[0].split(",");
                    unHandledPlayers.add(new Player(
                            tmp1[0], tmp1[1].split("\\]")[0],
                            Long.valueOf(payload.substring(payload.indexOf("logged in with entity id ")).split(" ")[5]),
                            Double.valueOf(tmp2[0]), Double.valueOf(tmp2[1]), Double.valueOf(tmp2[2])
                    ));
                } else if (payload.contains(" left the game")) {
                    Iterator<Player> it = unHandledPlayers.iterator();
                    while (it.hasNext()) {
                        Player curPlayer = it.next();
                        if (curPlayer.name.equals(payload.substring(33, payload.length() - 14))) {      // BUG_CHECK
                            eventHub.publishEvent(new onPlayerLoggedInOut(curPlayer, onPlayerLoggedInOut.PlayerStatus.LoggedOut));
                            it.remove();
                        }
                    }
                }
            } else if (payload.contains(" lost connection:")) {

            }
        }
        return true;
    }
}
