package gradlepluginv2.exeptions;

public class GitNotFoundException extends RuntimeException{
    public GitNotFoundException(String message) {
        super(message);
    }
}
