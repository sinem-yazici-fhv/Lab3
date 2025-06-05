package at.fhv.sysarch.lab3.pipeline.Push;

// Dieses Interface wird von Pipe und Filter genutzt,
// damit sie die push()-Methode aufrufen können, um Daten an den Nachfolger weiterzugeben.
// Ablauf:
// Quelle        -> Pipe       -> Filter     -> Pipe       -> Ziel
// push()        -> push()     -> push()     -> push()     -> Daten anzeigen
public interface IPush<E> {
	void push(E element);
}
