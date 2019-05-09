package MinecraftCli.Event;

public interface EventHandler<specEvent extends Event> {
    public abstract void onEventArrival(specEvent event);
}
