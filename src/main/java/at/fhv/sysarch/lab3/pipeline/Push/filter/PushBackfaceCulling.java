package at.fhv.sysarch.lab3.pipeline.Push.filter;


import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.Push.IPush;
import at.fhv.sysarch.lab3.pipeline.Push.Push;


/**
 * Filters out faces whose normals point away from the camera.
 * Uses dot product between vertex and normal to determine visibility.
 */
public class PushBackfaceCulling extends Push<Face, Face> {

	public PushBackfaceCulling(IPush<Face> next) {
		super(next);
	}

	@Override
	public void push(Face face) {
		if (shouldRender(face)) {
			successor.push(face);
		}
	}

	private boolean shouldRender(Face face) {
		// Face is visible if the dot product is negative
		return face.getV1().dot(face.getN1()) < 0;
	}
}
