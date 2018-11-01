package org.sunyata.game.game.phase;


import org.sunyata.game.game.GamePhaseModel;

/**
 * Created by leo on 17/5/16.
 */
public class BetPhaseModel extends GamePhaseModel<BetPhaseData> {

    public BetPhaseModel() {

    }

    public BetPhaseModel(String gameInstanceId,String phaseName, int orderBy) {
        super(gameInstanceId,phaseName, orderBy);
    }

}
