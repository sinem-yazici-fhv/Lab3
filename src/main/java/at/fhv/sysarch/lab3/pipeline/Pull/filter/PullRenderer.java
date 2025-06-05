package at.fhv.sysarch.lab3.pipeline.Pull.filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.Pull.IPull;
import at.fhv.sysarch.lab3.pipeline.Pull.Pull;
import javafx.scene.paint.Color;

/**
 * Letzter Schritt der Pipeline: zeichnet die Flächen mit Farbe auf die Leinwand.

 */
public class PullRenderer<T extends Pair<Face, Color>> extends Pull<T, T> {

    private final PipelineData pipelineData;

    public PullRenderer(PipelineData pipelineData, IPull<T> input) {
        super(input);
        this.pipelineData = pipelineData;
    }

    @Override
    public T pull() {
        throw new UnsupportedOperationException("PullRenderer is a terminal stage and cannot be pulled.");
    }

    @Override
    public boolean hasNext() {
        throw new UnsupportedOperationException("PullRenderer is a sink in the pull pipeline.");
    }

    public void doRender() {
        var gc = pipelineData.getGraphicsContext();
        var mode = pipelineData.getRenderingMode();

        while (source.hasNext()) {
            Pair<Face, Color> pair = source.pull();
            Face face = pair.fst();
            Color color = pair.snd();

            gc.setStroke(color);
            gc.setFill(color);

            double[] xPoints = {
                face.getV1().getX(), face.getV2().getX(), face.getV3().getX()
            };

            double[] yPoints = {
                face.getV1().getY(), face.getV2().getY(), face.getV3().getY()
            };

            switch (mode) {
                case POINT -> gc.fillOval(xPoints[0], yPoints[0], 2, 2);
                case WIREFRAME -> gc.strokePolygon(xPoints, yPoints, 3);
                case FILLED -> {
                    gc.fillPolygon(xPoints, yPoints, 3);
                    gc.strokePolygon(xPoints, yPoints, 3);
                }
            }
        }
    }
}