package at.fhv.sysarch.lab3.pipeline.Push;

/**
 * Leitet Daten unverändert an das nächste Element weiter.
 * Wird genutzt, um Daten einfach durch die Push-Pipeline zu schleusen.
 */
public class PushPipe<E> extends Push<E, E> {

    public PushPipe(IPush<E> successor){
        super(successor);
    }

    @Override
    public void push(E e) {
        successor.push(e);
    }
}
