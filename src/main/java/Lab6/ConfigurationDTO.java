package Lab6;

import java.util.List;

public class ConfigurationDTO {
    public StateValues state;
    public int position;
    public List<String> workingStack;
    public List<String> inputStack;

    public enum  StateValues {
        q,
        b,
        f,
        e
    }
}
