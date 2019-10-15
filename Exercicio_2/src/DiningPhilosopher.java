import java.util.concurrent.Semaphore;

public class DiningPhilosopher extends DiningPhilosopherDeadlock {
    /*Cant cause deadlock*/
    Semaphore fork_mutex;

    public DiningPhilosopher(int initN) {
        super(initN);
        fork_mutex = new Semaphore(1);
    }

    @Override
    public void pickup(int i) {
        try {

            fork_mutex.acquire();//Ensures that one philosopher pick up 2 forks at once, avoiding deadlock.
            fork[i].acquire();
            fork[(i + 1) % n].acquire();
            fork_mutex.release();

        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

}
