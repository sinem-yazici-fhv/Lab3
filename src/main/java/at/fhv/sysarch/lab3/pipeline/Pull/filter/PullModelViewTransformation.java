package at.fhv.sysarch.lab3.pipeline.Pull.filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import at.fhv.sysarch.lab3.pipeline.Pull.Pull;
import at.fhv.sysarch.lab3.pipeline.Pull.IPull;
import com.hackoeur.jglm.Mat4;

/**
 * Applies rotation and model-view transformation to each face.
 */
public class PullModelViewTransformation extends Pull<Face, Face> {

    private final PipelineData pipelineData;
    private Mat4 modelViewMatrix;

    public PullModelViewTransformation(PipelineData pipelineData, IPull<Face> input) {
        super(input);
        this.pipelineData = pipelineData;
    }

    @Override
    public Face pull() {
        Face face = source.pull();
        return applyModelViewTransform(face);
    }

    private Face applyModelViewTransform(Face face) {
        if (modelViewMatrix == null) {
            throw new IllegalStateException("Model-View matrix must be set before transformation.");
        }

        return new Face(
                modelViewMatrix.multiply(face.getV1()),
                modelViewMatrix.multiply(face.getV2()),
                modelViewMatrix.multiply(face.getV3()),
                modelViewMatrix.multiply(face.getN1()),
                modelViewMatrix.multiply(face.getN2()),
                modelViewMatrix.multiply(face.getN3())
        );
    }

    public void updateRotationMatrix(Mat4 rotation) {
        this.modelViewMatrix = pipelineData.getViewTransform()
                .multiply(pipelineData.getModelTranslation())
                .multiply(rotation);
    }
}
