package at.fhv.sysarch.lab3.pipeline.Push.filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.Push.IPush;
import at.fhv.sysarch.lab3.pipeline.Push.Push;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import javafx.scene.paint.Color;

public class PushModelColor extends Push<Face, Pair<Face, Color>> {

	private final Color modelColor;

	public PushModelColor(IPush<Pair<Face, Color>> next, Color color) {
		super(next);
		this.modelColor = color;
	}

	@Override
	public void push(Face face) {
		Pair<Face, Color> coloredPair = new Pair<>(face, modelColor);
		successor.push(coloredPair);
	}
}
