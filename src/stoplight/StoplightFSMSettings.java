package stoplight;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class StoplightFSMSettings {
    
    @Getter private final StoplightStateData defaultData;
    private final List<StoplightStateData> stateDataList = new ArrayList<>();
    
    public StoplightFSMSettings(StoplightStateData defaultStateData, StoplightStateData[] stateData) {
        this.defaultData = defaultStateData;
        this.stateDataList.addAll(List.of(stateData));
    }
    
    public StoplightStateData getNext() {
        return stateDataList.getFirst();
    }
    
    public StoplightStateData getNext(StoplightStateData currentData) {
        var currentIndex = stateDataList.indexOf(currentData);
        return currentIndex == stateDataList.size() - 1 
                ? getNext()
                : stateDataList.get(currentIndex + 1);
    }
}
