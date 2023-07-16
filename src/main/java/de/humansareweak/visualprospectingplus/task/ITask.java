package de.humansareweak.visualprospectingplus.task;

public interface ITask {

    /**
     * Is called every game tick. Return true to terminate task
     */
    public boolean process();
}
