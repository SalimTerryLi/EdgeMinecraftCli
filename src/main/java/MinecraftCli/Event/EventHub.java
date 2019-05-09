package MinecraftCli.Event;

import MinecraftCli.ServerManager;

import java.util.ArrayList;
import java.util.List;

public final class EventHub {
    public EventHub(ServerManager serverManager) {
    }

    private List<EventHandler> handlers=new ArrayList<EventHandler>();

    public void registerHandle(EventHandler handler){
        handlers.add(handler);
    }

    public void publishEvent(Event event){
        for (EventHandler handler:handlers) {
            if(handler.type().getClass().isInstance(handler)){          // Need a better implement.
                handler.onEventArrival(event);
            }

        }
    }
}
