package at.fhv.sysarch.lab3.pipeline;

import at.fhv.sysarch.lab3.animation.AnimationRenderer;
import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.obj.Model;
import at.fhv.sysarch.lab3.pipeline.Push.*;
import at.fhv.sysarch.lab3.pipeline.Push.filter.*;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import com.hackoeur.jglm.Matrices;
import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;


public class PushPipelineFactory {
    public static AnimationTimer createPipeline(PipelineData pd) {

        // TODO: push from the source (model)
        PushRenderer renderer = new PushRenderer(pd);

        // TODO 7. feed into the sink (renderer)
        PushPipe<Pair<Face, Color>> toRenderer = new PushPipe<>(renderer);
        PushScreenSpaceTransformation screenSpaceTransform = new PushScreenSpaceTransformation(toRenderer, pd.getViewportTransform());

        // TODO 6. perform perspective division to screen coordinates
        PushPipe<Pair<Face, Color>> toScreen = new PushPipe<>(screenSpaceTransform);
        PushProjectionTransformation projection = new PushProjectionTransformation(toScreen, pd.getProjTransform());

        // TODO 5. perform projection transformation
        PushPipe<Pair<Face, Color>> toLightingOrProjection;
        if (pd.isPerformLighting()) {
            // 4a. TODO perform lighting in VIEW SPACE
            PushPipe<Pair<Face, Color>> toLighting = new PushPipe<>(projection);
            PushLighting lighting = new PushLighting(toLighting, pd.getLightPos().getUnitVector());
            toLightingOrProjection = new PushPipe<>(lighting);
        } else {
            toLightingOrProjection = new PushPipe<>(projection);
        }

        // TODO 4. add coloring (space unimportant)
        PushModelColor coloring = new PushModelColor(toLightingOrProjection, pd.getModelColor());

        // TODO 3. perform depth sorting in VIEW SPACE
        PushPipe<Face> toColor = new PushPipe<>(coloring);
        PushDepthSorting sorting = new PushDepthSorting(toColor);

        // TODO 2. perform backface culling in VIEW SPACE
        PushPipe<Face> toSorting = new PushPipe<>(sorting);
        PushBackfaceCulling culling = new PushBackfaceCulling(toSorting);

        // TODO 1. perform model-view transformation from model to VIEW SPACE coordinates
        PushPipe<Face> toCulling = new PushPipe<>(culling);
        PushModelViewTransformation modelView = new PushModelViewTransformation(toCulling, pd.getViewTransform(), pd.getModelTranslation());

        PushPipe<Face> toStart = new PushPipe<>(modelView);
        PushSource source = new PushSource(toStart);

        // returning an animation renderer which handles clearing of the
        // viewport and computation of the praction
        return new AnimationRenderer(pd) {
            // TODO rotation variable goes in here
            float totalRotation = 0;

            /** This method is called for every frame from the JavaFX Animation
             * system (using an AnimationTimer, see AnimationRenderer).
             * @param fraction the time which has passed since the last render call in a fraction of a second
             * @param model    the model to render
             */
            @Override
            protected void render(float fraction, Model model) {

                // TODO compute rotation in radians
                totalRotation += fraction;
                float radians = totalRotation % (float) (2 * Math.PI);

                // TODO create new model rotation matrix using pd.modelRotAxis
                var rotation = Matrices.rotate(radians, pd.getModelRotAxis());

                // TODO compute updated model-view tranformation
                modelView.updateRotationMatrix(rotation);

                // TODO update model-view filter
                source.setSourceData(model.getFaces());

                // TODO trigger rendering of the pipeline
                // (pushPipeline is executed by setting the data at the source)
            }
        };
    }
}