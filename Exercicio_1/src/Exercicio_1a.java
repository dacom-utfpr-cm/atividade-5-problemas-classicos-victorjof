/* 
Author: Victor Figueira
Date:  14/10/2019
Task: 1a. Implemente  o problema dos Leitores-Escritores usando semárofos ou monitores que:
   * não cause inanição (*starvation*) de leitores ou escritores.
  */
public class Exercicio_1a {

    static WriterFirst controller = new WriterFirst();

    public static void main(String[] args) {
        Data data = new Data();

        //Create multiple threads that read and write to a single record
        for(int i =0; i<1000; i++) {
            new Thread(() -> {
                try {
                    write_loop(data);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();

            new Thread(() -> {
                try {
                    read_loop(data);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();

        }

    }


    public static void write_loop(Data data) throws InterruptedException {

        for(int i = 0; i <10; i++){

            //Writing
            controller.startWrite();
            data.write(i);
            controller.endWrite();


        }
    }


    public static void read_loop(Data data) throws InterruptedException {

        for(int i = 0; i <10; i++){

            //Reading
            controller.startRead();
            data.read();
            controller.endRead();
        }

        }
    }

