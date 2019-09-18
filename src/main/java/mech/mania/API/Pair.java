package mech.mania.API;

public class Pair<FIRST, SECOND> {
    private FIRST first;
    private SECOND second;

    public Pair(FIRST aFirst, SECOND aSecond) {
        first = aFirst;
        second = aSecond;
    }

    public FIRST getFirst() {
        return first;
    }

    public SECOND getSecond() {
        return second;
    }
}
