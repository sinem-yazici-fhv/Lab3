package at.fhv.sysarch.lab3.pipeline.Pull;

public class PullPipe<T> extends Pull<T, T> {

    public PullPipe(IPull<T> source) {
        super(source);
    }

    @Override
    public T pull() {
        return source.pull();
    }
}
