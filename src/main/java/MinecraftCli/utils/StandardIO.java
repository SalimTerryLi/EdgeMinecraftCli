package MinecraftCli.utils;

import java.io.*;

public final class StandardIO {
    private BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    public String getLine() {
        try {
            return in.readLine();
        } catch (IOException err) {
        }
        return "";
    }

    public void writeLine(String data) {
        try {
            out.write(data);
            out.newLine();
            out.flush();
        } catch (IOException err) {
        }
    }
}
