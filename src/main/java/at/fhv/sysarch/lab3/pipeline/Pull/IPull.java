package at.fhv.sysarch.lab3.pipeline.Pull;
// Dieses Interface wird von Pipes und Filtern verwendet.
// So können sie einfach source.pull() aufrufen, um Daten vom Vorgänger zu holen.
// Beispiel:
// Quelle           <- Pipe         <- Filter       <- Pipe         <- Ziel
// Daten liefern    <- pull()       <- pull()       <- pull()       <- pull()
public interface IPull<R> {

    R pull();

    boolean hasNext();

}
