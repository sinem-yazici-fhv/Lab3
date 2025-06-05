package at.fhv.sysarch.lab3.pipeline.Push.filter;



import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.Push.IPush;
import at.fhv.sysarch.lab3.pipeline.Push.Push;


import java.util.*;

public class PushDepthSorting extends Push<Face, Face> {

	private final List<Face> buffer = new ArrayList<>();

	public PushDepthSorting(IPush<Face> successor) {
		super(successor);
	}

	@Override
	public void push(Face face) {
		// Use a flag value or a separate call to flush the pipeline
		if (isFlushSignal(face)) {
			flushSorted();
		} else {
			buffer.add(face);
		}
	}

	private boolean isFlushSignal(Face face) {
		// Example: if all vertices have W == -100
		return face.getV1().getW() == -100;
	}

	private void flushSorted() {
		buffer.sort(Comparator.comparingDouble(this::avgZ).reversed());
		for (Face f : buffer) {
			successor.push(f);
		}
		buffer.clear();
	}

	private double avgZ(Face face) {
		return (face.getV1().getZ() + face.getV2().getZ() + face.getV3().getZ()) / 3.0;
	}
}
