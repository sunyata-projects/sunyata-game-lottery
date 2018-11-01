package org.sunyata.core.util.collect;

public interface ConcurrentArrayBagListener<E extends CoreGoods> {
    void onChanged();

    void onChangedItem(E e, int index);
}
