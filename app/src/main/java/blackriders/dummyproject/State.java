package blackriders.dummyproject;

/**
 * Created by Sanwal Singh on 4/4/17.
 */

public class State {

    public String stateID, stateName;

    public State(String stateID, String stateName) {
        this.stateID = stateID;
        this.stateName = stateName;
    }

    public State() {
    }

    public String getStateID() {
        return stateID;
    }

    public void setStateID(String stateID) {
        this.stateID = stateID;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

}
