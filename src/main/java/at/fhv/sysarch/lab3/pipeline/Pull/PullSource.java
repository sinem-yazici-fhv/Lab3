package at.fhv.sysarch.lab3.pipeline.Pull;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Generic source for the pull-based pipeline. It serves as the entry point that provides data to be processed.
 */
public class PullSource<T> extends Pull<T, T> {

    private final Queue<T> dataQueue = new LinkedList<>();

    public PullSource() {
        super(null); // no upstream filter
    }

    /**
     * Sets the input data that should be streamed through the pipeline.
     */
    public void setSourceData(List<T> input) {
        dataQueue.clear(); // ensure fresh data
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
