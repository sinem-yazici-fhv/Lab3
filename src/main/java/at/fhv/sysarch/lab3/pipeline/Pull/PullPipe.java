package at.fhv.sysarch.lab3.pipeline.Pull;

import java.util.function.Function;

/**
 * A flexible PullPipe that transforms data of type I into type O using a mapping function.
 * This is required by your PullPipelineFactory which uses PullPipe with lambdas.
 */
public class PullPipe<I, O> extends Pull<I, O> {

    private final Function<I, O> mapper;

    public PullPipe(IPull<I> source, Function<I, O> mapper) {
        super(source);
        this.mapper = mapper;
    }

    @Override
    public O pull() {
        return mapper.apply(source.pull());
    }
}
