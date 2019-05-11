package MinecraftCli.LineParser;

import MinecraftCli.Event.EventHub;

public abstract class LineParser {
    protected EventHub eventHub;

    public LineParser(EventHub eventHub) {
        this.eventHub = eventHub;
    }

    public abstract boolean parse(String payload);
}
