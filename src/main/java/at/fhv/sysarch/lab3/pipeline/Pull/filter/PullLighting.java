package at.fhv.sysarch.lab3.pipeline.Pull.filter;


import at.fhv.sysarch.lab3.obj.Face;
import javafx.scene.paint.Color;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.Pull.Pull;
import at.fhv.sysarch.lab3.pipeline.Pull.IPull;

/**
 * Applies simple flat shading to the given face-color pairs.
 * Modifies brightness of the color based on light direction.
 */
public class PullLighting extends Pull<Pair<Face, Color>, Pair<Face, Color>> {

    private final PipelineData pd;

    public PullLighting(PipelineData pd, IPull<Pair<Face, Color>> input) {
        super(input);
        this.pd = pd;
    }

    @Override
    public Pair<Face, Color> pull() {
        Pair<Face, Color> original = source.pull();
        if (original == null) return null;

        Face face = original.fst();
        Color baseColor = original.snd();

        float intensity = face.getN1().toVec3().dot(pd.getLightPos().getUnitVector());
        intensity = Math.max(0, intensity); // avoid negative brightness

        Color shadedColor = baseColor.deriveColor(0, 1, intensity, 1);
        return new Pair<>(face, shadedColor);
    }
}
