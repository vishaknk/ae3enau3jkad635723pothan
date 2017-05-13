package info.vnk.billex.adapter.order.listener;

/**
 * Created by Visak on 01/05/17.
 */

public interface OrderListener {
    public void plusClick(int position);
    public void minusClick(int position);
    public void deleteClick(int position);
    public Long quantityClick(int position, Long qunatity);
    public void discountAdded(int position, String discount);
}
