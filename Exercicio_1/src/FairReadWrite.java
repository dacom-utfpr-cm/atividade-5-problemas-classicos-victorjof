import java.util.concurrent.Semaphore;

public class FairReadWrite {
    int num_readers, num_writers;

    /*
    Semaphore(_,true)-> ensures that waiting threads are granted a permit
    in the order in which they requested access, avoiding starvation.
    */
    Semaphore queue  = new Semaphore(1,true);
    Semaphore reader_mutex = new Semaphore(1, true);
    Semaphore resource = new Semaphore(1);

    public void startRead() throws InterruptedException {
        queue.acquire();//Avoids writer starvation
        reader_mutex.acquire();//Avoids race condition with other readers

        if(num_readers == 0){
            /*If you're the first reader to acquire the lock, otherwise just keep reading(idempotent).
             Since both writers and readers must first acquire the queue lock(FIFO),
             there's no risk of writer starvation.
              */
            resource.acquire();
        }
        num_readers+=1;

        reader_mutex.release();
        queue.release();


    }

    public void endRead() throws InterruptedException {
        reader_mutex.acquire();
        num_readers-=1;
        if(num_readers == 0){//If there's no reader left release the lock
            resource.release();
        }
        reader_mutex.release();

    }

    public void startWrite() throws InterruptedException {

        queue.acquire();//Allows fairness
        resource.acquire();//Avoids writer-writer conflict
        queue.release();

    }

    public void endWrite() throws InterruptedException {
        resource.release();

    }

}
