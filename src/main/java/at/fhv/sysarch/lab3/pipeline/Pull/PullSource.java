package at.fhv.sysarch.lab3.pipeline.Pull;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Startpunkt der Pull-Pipeline: liefert die Eingabedaten.
 */
public class PullSource<T> extends Pull<T, T> {

    private final Queue<T> dataQueue = new LinkedList<>();

    public PullSource() {
        super(null);
    }

    /**
     * Setzt die Eingabedaten, die durch die Pipeline laufen sollen.
     */
    public void setSourceData(List<T> input) {
        dataQueue.clear();
        dataQueue.addAll(input);
    }


    @Override
    public T pull() {
        return dataQueue.poll();
    }

    @Override
    public boolean hasNext() {
        return !dataQueue.isEmpty();
    }
}
