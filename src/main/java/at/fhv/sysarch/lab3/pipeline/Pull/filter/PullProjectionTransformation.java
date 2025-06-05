package at.fhv.sysarch.lab3.pipeline.Pull.filter;



import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.Pull.IPull;
import at.fhv.sysarch.lab3.pipeline.Pull.Pull;
import com.hackoeur.jglm.Mat4;
import javafx.scene.paint.Color;

/**
 * Wandelt die Ecken der Fläche mit der Projektionsmatrix um.
 */
public class PullProjectionTransformation extends Pull<Pair<Face, Color>, Pair<Face, Color>> {

    private final Mat4 projectionMatrix;

    public PullProjectionTransformation(PipelineData pipelineData, IPull<Pair<Face, Color>> input) {
        super(input);
        this.projectionMatrix = pipelineData.getProjTransform();
    }

    @Override
    public Pair<Face, Color> pull() {
        Pair<Face, Color> pair = source.pull();
        if (pair == null) return null;

        Face face = pair.fst();
        Color color = pair.snd();

        Face projected = new Face(
                projectionMatrix.multiply(face.getV1()),
                projectionMatrix.multiply(face.getV2()),
                projectionMatrix.multiply(face.getV3()),
                face
        );

        return new Pair<>(projected, color);
    }
}
