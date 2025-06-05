package at.fhv.sysarch.lab3.pipeline.Pull.filter;


import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.Pull.IPull;
import at.fhv.sysarch.lab3.pipeline.Pull.Pull;


import java.util.*;

/**
 * Sortiert die Flächen nach ihrer Tiefe (Z-Wert),
 * damit weiter hinten liegende Flächen zuerst gezeichnet werden.
 */

public class PullDepthSorting extends Pull<Face, Face> {

    private final Deque<Face> queue = new ArrayDeque<>();

    public PullDepthSorting(IPull<Face> input) {
        super(input);
    }

    @Override
    public Face pull() {
        return queue.pollFirst();
    }

    @Override
    public boolean hasNext() {
        if (source.hasNext() && queue.isEmpty()) {
            collectAndSortFaces();
        }
        return !queue.isEmpty();
    }

    private void collectAndSortFaces() {
        List<Face> unsorted = new ArrayList<>();
        while (source.hasNext()) {
            Face f = source.pull();
            unsorted.add(f);
        }

        unsorted.sort((a, b) -> Double.compare(
                averageZ(b), averageZ(a)
        ));

        queue.addAll(unsorted);
    }

    private double averageZ(Face f) {
        return (f.getV1().getZ() + f.getV2().getZ() + f.getV3().getZ()) / 3.0;
    }
}
