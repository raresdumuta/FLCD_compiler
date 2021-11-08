package Lab3;

public class Pair {
    private final int code;
    private final int position;

    public Pair(int code, int position) {
        this.code = code;
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return  code + ":" + position ;
    }
}
