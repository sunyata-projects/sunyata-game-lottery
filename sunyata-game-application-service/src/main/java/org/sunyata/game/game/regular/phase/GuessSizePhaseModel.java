package org.sunyata.game.game.regular.phase;


import org.sunyata.game.game.GamePhaseModel;
import org.sunyata.game.game.regular.GameRegularState;

/**
 * Created by leo on 17/5/16.
 */
public class GuessSizePhaseModel extends GamePhaseModel<GuessSizePhaseData> {

    public GuessSizePhaseModel() {

    }

    public GuessSizePhaseModel(String gameInstanceId) {
        super(gameInstanceId, GameRegularState.GuessSize.getValue(),6);
    }

}
