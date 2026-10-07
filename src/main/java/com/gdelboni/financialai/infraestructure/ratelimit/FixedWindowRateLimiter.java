package com.gdelboni.financialai.infraestructure.ratelimit;

import java.time.Clock;
import java.util.concurrent.ConcurrentHashMap;

public class FixedWindowRateLimiter {
    private static final int MAX_TRACKED_KEYS = 10_000;

    public record Decision(boolean allowed, long retryAfterSeconds) {}
    private record Window(long startMillis, int count) {}

    private final int limit;
    private final long windowMillis;
    private final Clock clock;
    private final ConcurrentHashMap<String, Window> windows = new ConcurrentHashMap<>();

    public FixedWindowRateLimiter(int limit, long windowMillis, Clock clock) {
        this.limit = limit; this.windowMillis = windowMillis; this.clock = clock;
    }

    public Decision tryAcquire(String key) {
        long now = clock.millis();
        Window w = windows.compute(key, (k, cur) ->
                (cur == null || now - cur.startMillis() >= windowMillis)
                        ? new Window(now, 1)
                        : new Window(cur.startMillis(), Math.min(cur.count() + 1, limit + 1)));
        if (windows.size() > MAX_TRACKED_KEYS) {
            windows.entrySet().removeIf(e -> now - e.getValue().startMillis() >= windowMillis);
        }
        boolean allowed = w.count() <= limit;
        long remaining = w.startMillis() + windowMillis - now;
        return new Decision(allowed, allowed ? 0 : Math.max(1, (remaining + 999) / 1000));
    }
}