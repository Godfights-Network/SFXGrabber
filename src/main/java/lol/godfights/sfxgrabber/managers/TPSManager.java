package lol.godfights.sfxgrabber.managers;

public class TPSManager {
    private long lastServerTick = -1L;

    private long lastServerTimeUpdate = -1L;

    private double mspt = -1.0D;

    private double tps = -1.0D;

    public void onServerTimeUpdate(long totalWorldTime) {
        long currentTime = System.nanoTime();
        if (this.lastServerTick != -1L && this.lastServerTimeUpdate != -1L) {
            long elapsedTicks = totalWorldTime - this.lastServerTick;
            if (elapsedTicks > 0L) {
                this.mspt = (currentTime - this.lastServerTimeUpdate) / elapsedTicks / 1000000.0D;
                if (this.mspt >= 48.0D)
                    this.tps = (this.mspt <= 50.0D) ? 20.0D : ((int)(100000.0D / this.mspt) / 100.0D);
            }
        }
        this.lastServerTimeUpdate = currentTime;
        this.lastServerTick = totalWorldTime;
    }

    public double getTps() {
        return this.tps;
    }

    public double getMspt() {
        return this.mspt;
    }

}
