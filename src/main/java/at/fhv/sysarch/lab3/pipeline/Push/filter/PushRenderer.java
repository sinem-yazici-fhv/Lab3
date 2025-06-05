package at.fhv.sysarch.lab3.pipeline.Push.filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.Push.Push;
import at.fhv.sysarch.lab3.rendering.RenderingMode;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PushRenderer extends Push<Pair<Face, Color>, Pair<Face, Color>> {
	private final GraphicsContext graphicsContext;
	private final RenderingMode renderingMode;

	public PushRenderer(GraphicsContext gd, RenderingMode rm) {
		super(null); // letzter Schritt, kein Nachfolger mehr
		this.graphicsContext = gd;
		this.renderingMode = rm;
	}

	@Override
	public void push(Pair<Face, Color> element) {
		Color color = element.snd();
		graphicsContext.setStroke(color);
		graphicsContext.setFill(color);

		Face f = element.fst();
		double[] cordX = { f.getV1().getX(), f.getV2().getX(), f.getV3().getX() };
		double[] cordY = { f.getV1().getY(), f.getV2().getY(), f.getV3().getY() };

		switch (renderingMode) {
			case POINT -> graphicsContext.fillOval(cordX[0], cordY[0], 2, 2);
			case WIREFRAME -> graphicsContext.strokePolygon(cordX, cordY, 3);
			case FILLED -> {
				graphicsContext.fillPolygon(cordX, cordY, 3);
				graphicsContext.strokePolygon(cordX, cordY, 3);
			}
		}
	}
}
