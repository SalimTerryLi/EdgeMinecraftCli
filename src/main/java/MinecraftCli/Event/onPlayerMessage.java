package MinecraftCli.Event;

import MinecraftCli.Player.Player;

public class onPlayerMessage implements Event {
    public Player playerInfo;
    public String message;

    public onPlayerMessage(Player playerInfo, String message) {
        this.playerInfo = playerInfo;
        this.message = message;
    }

    @Override
    public int getSomething() {
        return 0;
    }
}
