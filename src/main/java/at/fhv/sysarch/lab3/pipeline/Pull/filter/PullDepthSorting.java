package at.fhv.sysarch.lab3.pipeline.Pull.filter;


import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.Pull.IPull;
import at.fhv.sysarch.lab3.pipeline.Pull.Pull;


import java.util.*;


public class PullDepthSorting extends Pull<Face, Face> {

    private final Deque<Face> queue = new ArrayDeque<>();

    public PullDepthSorting(IPull<Face> input) {
        super(input);
    }

    @Override
    public Face pull() {
        return queue.pollFirst(); // retrieves and removes the head of the queue
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

        // Sort by average Z of the three vertices (descending → far to near)
        unsorted.sort((a, b) -> Double.compare(
                averageZ(b), averageZ(a)
        ));

        queue.addAll(unsorted);
    }

    private double averageZ(Face f) {
        return (f.getV1().getZ() + f.getV2().getZ() + f.getV3().getZ()) / 3.0;
    }
}
