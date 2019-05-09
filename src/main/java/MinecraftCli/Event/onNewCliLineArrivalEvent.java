package MinecraftCli.Event;

public class onNewCliLineArrivalEvent implements Event {
    public String line;

    public onNewCliLineArrivalEvent(String line) {
        this.line = line;
    }

    public int getSomething() {
        return 0;
    }
}
