package at.fhv.sysarch.lab3.pipeline.Push;

import at.fhv.sysarch.lab3.obj.Face;
import com.hackoeur.jglm.Vec4;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

/**
 * Startpunkt der Push-Pipeline.
 * Lädt die Eingabedaten und schickt sie Schritt für Schritt an das nächste Element.
 * push() darf hier nicht aufgerufen werden, da die Quelle nur sendet, aber nichts empfängt.
 */

public class PushSource extends Push<Face, Face> {
    private Queue<Face> sourceData = new ArrayDeque<>();

    public PushSource(IPush<Face> successor) {
        super(successor);
    }

    public void setSourceData(List<Face> sourceData) {
        this.sourceData.addAll(sourceData);
        Vec4 dummyData = new Vec4(69,73,420,-100);
        this.sourceData.add(new Face(
                dummyData,
                dummyData,
                dummyData,
                dummyData,
                dummyData,
                dummyData));
        while (!this.sourceData.isEmpty()) {
            successor.push(this.sourceData.poll());
        }
    }

    @Override
    public void push(Face element) {
        throw new IllegalCallerException("PushSource is a source class of the push structure, therefore it cannot accept a push element. Use start() instead");
    }
}
