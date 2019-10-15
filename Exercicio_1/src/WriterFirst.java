import java.util.concurrent.Semaphore;

public class WriterFirst {
    int num_readers, num_writers;
    private Semaphore reader_mutex =  new Semaphore(1);
    private Semaphore writer_mutex =  new Semaphore(1);
    private Semaphore allowRead =  new Semaphore(1);
    private Semaphore resource =  new Semaphore(1);


    public void startRead() throws InterruptedException {

        allowRead.acquire(); //If no writer exists receives the lock
        reader_mutex.acquire(); //Avoids race condition with other readers

        //if you are the first reader to acquire resource lock, if not just keep reading(idempotent).
        if(this.num_readers == 0){
            resource.acquire();
        }
        num_readers+=1;
        reader_mutex.release();
        allowRead.release();

    }

    public void endRead() throws InterruptedException {
        reader_mutex.acquire();//Avoids race condition with other readers
        num_readers-=1;

        if(num_readers==0){
            resource.release();//last reader -> release lock
        }

        reader_mutex.release();

    }

    public void startWrite() throws InterruptedException {
        writer_mutex.acquire();//Avoids race condition with other writers
        if(num_writers == 0){
            allowRead.acquire();//Blocks any new reader -> Gives preference to writers
        }
        num_writers+=1;

        writer_mutex.release();
        resource.acquire();//Avoids writer-writer conflict
    }

    public void endWrite() throws InterruptedException {
        resource.release();//write operation was done so release resource

        writer_mutex.acquire();
        num_writers-=1;
        if(num_writers == 0){
            allowRead.release();//If there`s no writer left allow readers to operate, therefore a constant flow of writers can cause readers starvation.
        }

        writer_mutex.release();

    }


}
