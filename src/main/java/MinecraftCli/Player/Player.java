package MinecraftCli.Player;

public class Player {
    public String name;
    public String address;
    public long entityID;
    public Position loggedPosition = new Position(0, 0, 0);

    public Player(String name, String address, long entityID, double x, double y, double z) {
        this.name = name;
        this.address = address;
        this.entityID = entityID;
        this.loggedPosition.x = x;
        this.loggedPosition.y = y;
        this.loggedPosition.z = z;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", entityID=" + entityID +
                ", loggedPosition=" + loggedPosition +
                '}';
    }
}

class Position {
    double x, y, z;

    public Position(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
