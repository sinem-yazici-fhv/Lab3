package at.fhv.sysarch.lab3.pipeline.Pull.filter;


import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.Pull.IPull;
import at.fhv.sysarch.lab3.pipeline.Pull.Pull;
/**
 * Versteckte Flächen ausblenden, um die Leistung zu verbessern
 */
public class PullBackfaceCulling extends Pull<Face, Face> {

    private Face cachedFace = null;

    public PullBackfaceCulling(IPull<Face> input) {
        super(input);
    }

    @Override
    public Face pull() {
        fetchNextVisibleFace();
        Face result = cachedFace;
        cachedFace = null;
        return result;
    }

    @Override
    public boolean hasNext() {
        fetchNextVisibleFace();
        return cachedFace != null;
    }

     private void fetchNextVisibleFace() {
        while (cachedFace == null && source.hasNext()) {
            Face face = source.pull();
            if (face.getV1().dot(face.getN1()) < 0) {
                cachedFace = face;
            }
        }
    }
}
