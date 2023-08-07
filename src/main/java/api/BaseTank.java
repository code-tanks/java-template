package api;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class BaseTank {
    public final ArrayList<Integer> commands = new ArrayList<>();

    public abstract void run();

    public abstract void onEvent(HashMap<String, Object> event);
}

