package at.fhv.sysarch.lab3.pipeline.Push.filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.Push.IPush;
import at.fhv.sysarch.lab3.pipeline.Push.Push;


public class PushBackfaceCulling extends Push<Face, Face> {

	public PushBackfaceCulling(IPush<Face> successor) {
		super(successor);
	}

	@Override
	public void push(Face element) {
		if (element.getV1().getW() != -100) {
			if (element.getV1().dot(element.getN1()) < 0) {
				successor.push(element);
			}
		} else {
			successor.push(element);
		}
	}
}
