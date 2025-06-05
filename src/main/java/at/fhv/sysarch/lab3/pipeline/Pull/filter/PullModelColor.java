package at.fhv.sysarch.lab3.pipeline.Pull.filter;


import at.fhv.sysarch.lab3.obj.Face;
import javafx.scene.paint.Color;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.Pull.IPull;
import at.fhv.sysarch.lab3.pipeline.Pull.Pull;


/**
 * Attaches the model's base color to each face for further processing.
 */
public class PullModelColor extends Pull<Face, Pair<Face, Color>> {

    private final Color modelColor;

    public PullModelColor(PipelineData pd, IPull<Face> input) {
        super(input);
        this.modelColor = pd.getModelColor();
    }

    @Override
    public Pair<Face, Color> pull() {
        Face next = source.pull();
        if (next == null) return null;
        return new Pair<>(next, modelColor);
    }
}
