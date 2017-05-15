package info.vnk.billex.adapter.order.listener;

import info.vnk.billex.model.order.OrderListModel;

/**
 * Created by Visak on 14/05/17.
 */

public interface ListOrderListener {
    public void onEditClick(OrderListModel model);
    public void onDeleteClick();
}
