package model;

/**
 * Created by jelle on 30/01/2017.
 */
public class RemotePlayer extends Player {
    @Override
    public Mark determineMove(Board board) {
        return null;
    }

    public RemotePlayer(String name, Mark mark) {
        super(name, mark);
    }
}
