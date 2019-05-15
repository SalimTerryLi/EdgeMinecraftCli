package MinecraftCli.Mod;

import MinecraftCli.Event.EventHub;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ModHandler {
    private EventHub eventHub;
    private ArrayList<Loader> modLoaders = new ArrayList<Loader>();

    public ModHandler(EventHub eventHub) {
        this.eventHub = eventHub;
    }

    public void init() {
        findMods();
        loadEventHandlers();
    }

    private void findMods() {
        try {
            File jarDir = new File("." + File.separatorChar + "mods");
            if (!jarDir.exists()) {
                System.err.println("Mods dir missing.");
                return;
            }
            List<File> jars = Arrays.asList(jarDir.listFiles());
            for (File jar : jars) {
                if (!jar.getName().endsWith(".jar")) {
                    continue;
                }
                URL url = jar.toURI().toURL();
                URL[] urls = new URL[]{url};
                ClassLoader cl = new URLClassLoader(urls);
                JarFile jarFile = new JarFile(url.getFile());
                Enumeration<JarEntry> entries = jarFile.entries();
                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    if (entry.getName().endsWith(".class") && !entry.isDirectory()) {
                        Class<?> cls = cl.loadClass(entry.getName().substring(0, entry.getName().length() - 6));
                        if (Arrays.asList(cls.getGenericInterfaces()).toString().contains("interface MinecraftCli.Mod.Loader")) {
                            modLoaders.add((Loader) cls.newInstance());
                        }
                    }
                }
            }
        } catch (Exception err) {
            System.err.println(err);
        }
    }

    private void loadEventHandlers() {
        for (Loader modLoader : modLoaders) {
            eventHub.registerHandle(modLoader.getEventHandlers());
            System.err.println(modLoader.getModName() + " Loaded.");
        }
    }
}
