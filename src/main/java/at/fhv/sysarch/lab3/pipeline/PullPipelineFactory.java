package at.fhv.sysarch.lab3.pipeline;

import at.fhv.sysarch.lab3.animation.AnimationRenderer;
import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.obj.Model;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.Pull.*;
import at.fhv.sysarch.lab3.pipeline.Pull.filter.*;
import com.hackoeur.jglm.Matrices;
import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;

public class PullPipelineFactory {
    public static AnimationTimer createPipeline(PipelineData pd) {

        // pull from the source (model)
        PullSource<Face> source = new PullSource<>();
        PullPipe<Face, Face> toModelTransformation = new PullPipe<>(source, f -> f);

        // 1. perform model-view transformation from model to VIEW SPACE coordinates
        PullModelViewTransformation modelView = new PullModelViewTransformation(pd, toModelTransformation);
        PullPipe<Face, Face> toModelTransformPipe = new PullPipe<>(modelView, f -> f);

        // 2. perform backface culling in VIEW SPACE
        PullBackfaceCulling culling = new PullBackfaceCulling(toModelTransformPipe);
        PullPipe<Face, Face> toDepthSortingPipeline = new PullPipe<>(culling, f -> f);

        // 3. perform depth sorting in VIEW SPACE
        PullDepthSorting sorting = new PullDepthSorting(toDepthSortingPipeline);
        PullPipe<Face, Face> toColorPipeline = new PullPipe<>(sorting, f -> f);

        // 4. add coloring (space unimportant)
        PullModelColor coloring = new PullModelColor(pd, toColorPipeline);

        // lighting can be switched on/off
        IPull<Pair<Face, Color>> afterLightingOrColoring;
        if (pd.isPerformLighting()) {
            // 4a. perform lighting in VIEW SPACE
            PullPipe<Pair<Face, Color>, Pair<Face, Color>> toLighting = new PullPipe<>(coloring, p -> p);
            PullLighting lighting = new PullLighting(pd, toLighting);
            afterLightingOrColoring = new PullPipe<>(lighting, p -> p);
        } else {
            afterLightingOrColoring = new PullPipe<>(coloring, p -> p);
        }

        // 5. perform projection transformation
        PullProjectionTransformation projection = new PullProjectionTransformation(pd, afterLightingOrColoring);
        PullPipe<Pair<Face, Color>, Pair<Face, Color>> afterProjectTransformationPipe = new PullPipe<>(projection, p -> p);

        // 6. perform perspective division to screen coordinates
        PullScreenSpaceTransformation screenSpace = new PullScreenSpaceTransformation(pd, afterProjectTransformationPipe);
        PullPipe<Pair<Face, Color>, Pair<Face, Color>> afterScreenSpaceTransformation = new PullPipe<>(screenSpace, p -> p);

        // 7. feed into the sink (renderer)
        PullRenderer<Pair<Face, Color>> renderer = new PullRenderer<>(pd, afterScreenSpaceTransformation);

        // returning an animation renderer which handles clearing of the
        // viewport and computation of the praction
        return new AnimationRenderer(pd) {

            // rotation variable goes in here
            float totalRotation = 0;

            /** This method is called for every frame from the JavaFX Animation
             * system (using an AnimationTimer, see AnimationRenderer).
             * @param fraction the time which has passed since the last render call in a fraction of a second
             * @param model    the model to render
             */
            @Override
            protected void render(float fraction, Model model) {

                // compute rotation in radians
                totalRotation += fraction;
                double rad = totalRotation % (2 * Math.PI);

                // create new model rotation matrix using pd.getModelRotAxis and Matrices.rotate
                var rotationMatrix = Matrices.rotate((float) rad, pd.getModelRotAxis());

                // compute updated model-view tranformation
                modelView.updateRotationMatrix(rotationMatrix);

                // update model-view filter
                source.setSourceData(model.getFaces());

                // trigger rendering of the pipeline
                renderer.doRender();

            }
        };
    }
}
