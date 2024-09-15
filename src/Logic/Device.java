package Logic;

public abstract class Device extends Thread{
    private boolean working=false;
    private boolean suspended=false;
    private int sleepingTime;

    public Device(String name){
        super(name);
    }
    //setters
    public void setFrequency(int milliseconds){
        sleepingTime=milliseconds;
    }


    @Override
    public void run() {
        while(working) {

            try{
                synchronized (this) {
                    while (suspended) wait();
                }
            } catch(InterruptedException ignored){}

            performAction();

            try {
                Thread.sleep(sleepingTime);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


        }
    }
    public abstract void performAction();

    @Override
    public void start() {
        super.start();
        working=true;
    }

    public void setActive(){
        suspended=false;
        synchronized (this){
            notify();
        }
        System.out.println("active");
    }
    public void setWaiting(){
        System.out.println("waiting");
        suspended=true;
    }

    public void terminate(){
        working=false;
    }
}





