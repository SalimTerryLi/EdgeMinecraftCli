package MinecraftCli.LineParser;

import MinecraftCli.Event.Event;
import MinecraftCli.Event.EventHandler;
import MinecraftCli.Event.EventHub;
import MinecraftCli.Event.onNewCliLineArrivalEvent;
import MinecraftCli.ServerManager;

public final class LineParserHub {
    private EventHub eventHub;
    private LineParserHubOnNewCliLineArrivalHandler handler=new LineParserHubOnNewCliLineArrivalHandler(this);

    public LineParserHub(EventHub eventHub) {
        this.eventHub=eventHub;
        this.eventHub.registerHandle(this.handler);
    }

    public void parseNewLine(String line){
        System.err.println(line+ "parser");
    }

}

final class LineParserHubOnNewCliLineArrivalHandler implements EventHandler<onNewCliLineArrivalEvent>  {
    private LineParserHub hub;

    public LineParserHubOnNewCliLineArrivalHandler(LineParserHub hub) {
        this.hub = hub;
    }

    public void onEventArrival(onNewCliLineArrivalEvent event) {
        hub.parseNewLine(event.line);
    }
}