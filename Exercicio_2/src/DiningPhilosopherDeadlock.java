
import java.util.concurrent.Semaphore;

class DiningPhilosopherDeadlock implements Resource{
    /*Can cause deadlock*/
    int n = 0;
    Semaphore[] fork = null;

    public DiningPhilosopherDeadlock(int initN) {
        n = initN;
        //n -> number of forks, each fork with it's own semaphore to avoid being caught by two philosophers
        fork = new Semaphore[n];
        for (int i = 0; i < n; i++) {
            fork[i] = new Semaphore(1);
        }
    }

    @Override
    public void pickup(int i) {
        try {
            //philosopher's left hand fork
            fork[i].acquire();
            //philosopher's right hand fork
            fork[(i + 1) % n].acquire();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void drop(int i) {
        fork[i].release();
        fork[(i + 1) % n].release();
    }

    }
