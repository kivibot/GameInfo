package fi.kivibot.gameinfoback;

/**
 *
 * @author Nicklas
 */
@Deprecated
public class RuneStat {

    private final String name;
    private final double value;

    public RuneStat(String name, double value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "RuneStat{" + "name=" + name + ", value=" + value + '}';
    }
}
