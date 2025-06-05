package at.fhv.sysarch.lab3.pipeline.Push;
// T           ... Datentyp in dieser Klasse
// N           ... Datentyp im nächsten Schritt
// successor   ... Nächstes Element in der Pipeline
public abstract class Push<T, N> implements IPush<T>{

    protected final IPush<N> successor;

    protected Push(IPush<N> successor) {
        this.successor = successor;
    }
}
