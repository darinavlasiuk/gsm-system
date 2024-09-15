package Logic;

import java.util.ArrayList;
import java.util.List;


public abstract class Layer {
    private final TechnicalManager manager;
    private final List<Station> stations=new ArrayList<>();
    private int stationCounter=0;

    public Layer(TechnicalManager manager){
        this.manager=manager;
    }
    //getters
    public TechnicalManager manager(){
        return manager;
    }
    public List<Station> stations(){
        return stations;
    }
    public int stationCounter(){
        return stationCounter;
    }
    //setter
    public void increaseStationCounter(){
        stationCounter++;
    }

    public abstract void addStation();
    public Station leastBusyStation(){
        Station leastBusyStation=stations.get(0);
        for(int i=1; i<stations.size(); ++i){
            if(stations.get(i).numberOfStoredMessages()<leastBusyStation.numberOfStoredMessages()){
                leastBusyStation=stations.get(i);
            }
        }

        return notTakenLeastBusyStation(leastBusyStation);
    }

    public Station notTakenLeastBusyStation(Station previouslyLeastBusyStation){
        synchronized (this){
            if(previouslyLeastBusyStation.isBusy()){
                if(stations.get(stations.size()-1).isBusy()){
                    addStation();
                }
                stations.get(stations.size()-1).addWaitingMessage();
                return stations.get(stations.size()-1);
            }
        }
        previouslyLeastBusyStation.addWaitingMessage();
        return previouslyLeastBusyStation;
    }

    public void receiveMessage(byte[] message) {
        if(stations.size()==0) addStation();
        Station leastBusyStation=leastBusyStation();
        passMessage(leastBusyStation, message);
    }
    public void passMessage(Station recipient, byte[] message){
        recipient.receiveSMS(message);
    }
    public void removeStation(Station station){
        stations.remove(station);
    }

}




