package MinecraftCli.LineParser;

import MinecraftCli.Event.EventHandler;
import MinecraftCli.Event.EventHub;
import MinecraftCli.Event.onNewCliLineArrivalEvent;

import java.util.ArrayList;

public final class LineParserHub implements EventHandler<onNewCliLineArrivalEvent> {
    private EventHub eventHub;
    private ArrayList<LineParser> lineParsers=new ArrayList<LineParser>();

    public LineParserHub(EventHub eventHub) {
        this.eventHub=eventHub;
        this.eventHub.registerHandle(this);
        this.loadDefaultParsers();
    }

    private void parseNewLine(String line){
        for (LineParser parser:lineParsers) {
            parser.parse(line);
        }
    }

    public void registerNewLineParser(LineParser lineParser){
        this.lineParsers.add(lineParser);
    }

    private void loadDefaultParsers(){

    }

    public void onEventArrival(onNewCliLineArrivalEvent event) {
        this.parseNewLine(event.line);
    }
}