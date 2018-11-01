package org.sunyata.game.currency.models;


import org.sunyata.game.EnumField;
import org.sunyata.game.EnumInterface;

import java.util.HashMap;
import java.util.Map;

/**
 * 资金帐户类型
 * Created by luxin on 16/7/18.
 */
public enum FundAccountType implements EnumInterface {

    @EnumField(name = "积分")
    Score(1),

    @EnumField(name = "房卡")
    RoomCard(2);

    public int getValue() {
        return value;
    }

    private int value = 0;

    FundAccountType(int value) {
        this.value = value;
    }

    // Mapping difficulty to difficulty id
    private static final Map<Integer, FundAccountType> _map = new HashMap<Integer, FundAccountType>();

    static {
        for (FundAccountType difficulty : FundAccountType.values())
            _map.put(difficulty.value, difficulty);
    }

    /**
     * Get difficulty from value
     *
     * @param value Value
     * @return Difficulty
     */
    public static FundAccountType from(int value) {
        return _map.get(value);
    }
}
