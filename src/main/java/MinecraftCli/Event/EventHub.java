package MinecraftCli.Event;

import MinecraftCli.ServerManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class EventHub {
    private List<EventHandler> handlers = new ArrayList<EventHandler>();

    public EventHub(ServerManager serverManager) {
    }

    public void registerHandle(EventHandler handler) {
        handlers.add(handler);
    }

    public void registerHandle(ArrayList<EventHandler> handlers) {
        this.handlers.addAll(handlers);
    }

    public void publishEvent(final Event event) {
        for (EventHandler handler : handlers) {
            if (Arrays.asList(handler.getClass().getGenericInterfaces()).toString().contains(event.getClass().getCanonicalName())) {          // Need a better implement.
                handler.onEventArrival(event);
            }

        }
    }
}
