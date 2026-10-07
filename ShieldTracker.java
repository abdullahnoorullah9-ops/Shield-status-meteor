package com.shieldstatus;

import java.util.HashMap;
import java.util.Map;

/** Remembers when each player's shield was last disabled. */
public final class ShieldTracker {
    /** How long the red "disabled" state shows. Vanilla axe cooldown is 5 seconds. Edit if your server differs. */
    public static final long DISABLE_MS = 5000L;

    /** Set to false to silence the log line printed when a shield-disable is detected. */
    public static final boolean DEBUG_LOG = true;

    private static final Map<Integer, Long> DISABLED_AT = new HashMap<>();

    private ShieldTracker() {}

    public static void markDisabled(int entityId) {
        DISABLED_AT.put(entityId, System.currentTimeMillis());
    }

    /** Milliseconds of "disabled" time left for this player, or 0 if not disabled. */
    public static long remainingMs(int entityId) {
        Long at = DISABLED_AT.get(entityId);
        if (at == null) return 0L;
        long left = DISABLE_MS - (System.currentTimeMillis() - at);
        if (left <= 0) {
            DISABLED_AT.remove(entityId);
            return 0L;
        }
        return left;
    }
}
