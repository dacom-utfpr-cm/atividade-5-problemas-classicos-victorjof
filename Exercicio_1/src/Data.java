public class Data {
    private int field;

    public void write(int value){
        System.out.printf("Writing value %d to field of past value %d%n",value,this.field);
        this.field = value;
    }

    public void read(){
        System.out.println("read value is "+field);
    }


    Data(){
        this.field = 0;
    }
}
