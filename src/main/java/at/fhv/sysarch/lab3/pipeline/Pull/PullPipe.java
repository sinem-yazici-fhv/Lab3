package at.fhv.sysarch.lab3.pipeline.Pull;

import java.util.function.Function;

/**
 * Eine flexible PullPipe, die Daten vom Typ I in Typ O umwandelt.
 * Wird von der PullPipelineFactory mit Lambda-Funktionen verwendet.
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
