package at.fhv.sysarch.lab3.pipeline.Pull.filter;


import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.Pull.IPull;
import at.fhv.sysarch.lab3.pipeline.Pull.Pull;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Vec4;
import javafx.scene.paint.Color;

/**
 * Converts the face coordinates from clip space to screen space.
 */
public class PullScreenSpaceTransformation extends Pull<Pair<Face, Color>, Pair<Face, Color>> {

    private final Mat4 screenMatrix;

    public PullScreenSpaceTransformation(PipelineData pipelineData, IPull<Pair<Face, Color>> input) {
        super(input);
        this.screenMatrix = pipelineData.getViewportTransform();
    }

    @Override
    public Pair<Face, Color> pull() {
        Pair<Face, Color> inputPair = source.pull();
        Face screenFace = transformToScreen(inputPair.fst());
        return new Pair<>(screenFace, inputPair.snd());
    }

    private Face transformToScreen(Face f) {
        return new Face(
            convert(f.getV1()),
            convert(f.getV2()),
            convert(f.getV3()),
            f // normals stay the same
        );
    }

    private Vec4 convert(Vec4 vec) {
        // divide by w (perspective division), then multiply with viewport matrix
        return screenMatrix.multiply(vec.multiply(1f / vec.getW()));
    }
}
