package MinecraftCli.Event;

import MinecraftCli.Player.Player;

public class onPlayerLoggedInOut implements Event {
    public enum PlayerStatus {LoggedIn, LoggedOut}

    public Player playerInfo;
    public PlayerStatus status;

    public onPlayerLoggedInOut(Player playerInfo, PlayerStatus status) {
        this.playerInfo = playerInfo;
        this.status = status;
    }

    @Override
    public int getSomething() {
        return 0;
    }
}
