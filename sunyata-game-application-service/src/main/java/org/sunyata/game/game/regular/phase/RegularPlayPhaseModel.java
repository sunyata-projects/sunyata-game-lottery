package org.sunyata.game.game.regular.phase;


import org.sunyata.game.game.GamePhaseModel;
import org.sunyata.game.game.regular.GameRegularState;

/**
 * Created by leo on 17/5/16.
 */
public class RegularPlayPhaseModel extends GamePhaseModel<RegularPlayPhaseData> {

    public RegularPlayPhaseModel() {

    }

    public RegularPlayPhaseModel(String gameInstanceId) {
        super(gameInstanceId, GameRegularState.Playing.getValue(), 5);
    }

}
