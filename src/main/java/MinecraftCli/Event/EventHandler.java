package MinecraftCli.Event;

public abstract class EventHandler<specEvent extends Event> {
    public final EventHandler<specEvent> type(){
        return this;
    }
    public abstract void onEventArrival(specEvent event);
}
