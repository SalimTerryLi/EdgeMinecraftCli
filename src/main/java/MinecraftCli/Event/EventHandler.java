package MinecraftCli.Event;

public interface EventHandler<specEvent extends Event> {
    void onEventArrival(specEvent event);
}
