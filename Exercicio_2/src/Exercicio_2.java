/* 
Author: Victor Figueira
Date:  14/10/2019
Task: 2. Implemente uma solução para o problema do Jantar dos Filósofos que não resulte em impasse (*deadlock*).
 */

public class Exercicio_2 {

    public static void main(String[] args) {
//        DiningPhilosopherDeadlock philosopher = new DiningPhilosopherDeadlock(5);
        DiningPhilosopherDeadlock philosopher = new DiningPhilosopher(5);
        for (int i = 0; i < 5; i++) {
            new Thread(new Philosopher(i, philosopher)).start();
        }
    }
}
