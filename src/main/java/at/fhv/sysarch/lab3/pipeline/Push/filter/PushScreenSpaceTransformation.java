package at.fhv.sysarch.lab3.pipeline.Push.filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.Push.IPush;
import at.fhv.sysarch.lab3.pipeline.Push.Push;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Vec4;
import javafx.scene.paint.Color;

public class PushScreenSpaceTransformation extends Push<Pair<Face, Color>, Pair<Face, Color>> {

	private final Mat4 transform;

	public PushScreenSpaceTransformation(IPush<Pair<Face, Color>> successor, Mat4 transform) {
		super(successor);
		this.transform = transform;
	}

	@Override
	public void push(Pair<Face, Color> element) {
		successor.push(transformPair(element));
	}

	private Pair<Face, Color> transformPair(Pair<Face, Color> pair) {
		Face face = pair.fst();      // angepasst
		Color color = pair.snd();    // angepasst
		Face transformed = new Face(
				applyPerspectiveDivision(face.getV1()),
				applyPerspectiveDivision(face.getV2()),
				applyPerspectiveDivision(face.getV3()),
				face
		);
		return new Pair<>(transformed, color);
	}

	private Vec4 applyPerspectiveDivision(Vec4 vec) {
		return transform.multiply(vec.multiply(1f / vec.getW()));
	}
}
