package at.fhv.sysarch.lab3.pipeline.Pull;
// T        ... Eingabetyp
// R        ... Ausgabetyp
// source   ... Vorgänger in der Pipeline (liefert die Daten)

public abstract class Pull<I, O> implements IPull<O> {

    protected final IPull<I> source;

    protected Pull(IPull<I> source) {
        this.source = source;
    }

    @Override
    public boolean hasNext() {
        return source.hasNext();
    }
}
