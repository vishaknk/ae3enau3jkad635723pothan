package info.vnk.billex.network;

import info.vnk.billex.model.customer.CustomerResultModel;
import info.vnk.billex.model.customer.PostCustomerModel;
import info.vnk.billex.model.navigation.ResultModel;
import info.vnk.billex.model.order.OrderResultModel;
import info.vnk.billex.model.order.PostMainOrderModel;
import info.vnk.billex.model.order.PostOrderResultModel;
import info.vnk.billex.model.payment.PaymentDelete;
import info.vnk.billex.model.payment.PaymentResultModel;
import info.vnk.billex.model.payment.PostPaymentModel;
import info.vnk.billex.model.product.GetProductResultModel;
import info.vnk.billex.model.product.ProductResultModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface ApiInterface {

    @FormUrlEncoded
    @POST("userLogin")
    Call<ResultModel> getUser(@Field("email_phone") String username, @Field("password") String password);

    @GET("listProducts")
    Call<ProductResultModel> getProduct();

    @GET("listCustomer")
    Call<CustomerResultModel> getCustomer();

    @GET("staffOrderDetails?")
    Call<OrderResultModel> getOrderList(@Query("staff_id") String string);

    @POST("newOrder")
    Call<PostOrderResultModel> postOrder(@Body PostMainOrderModel data);

    @POST("editOrder")
    Call<PostOrderResultModel> editOrder(@Body PostMainOrderModel data);

    @GET("orderDetails")
    Call<GetProductResultModel> getOrderProduct(@Query("order_id") String string);

    @POST("addCustomer")
    Call<PostCustomerModel> postCustomer(@Body PostCustomerModel data);

    @GET("paymentDetails")
    Call<PaymentResultModel> getPayment(@Query("customer_id")String custId);

    @POST("addPayment")
    Call<PostPaymentModel> postPayment(@Body PostPaymentModel payment);

    @GET("deletePayment")
    Call<PaymentDelete> deletePayment(@Query("payment_id") String mCustomerId);

    @GET("cancelOrder")
    Call<PaymentDelete> cancelOrder(@Query("order_id") String order_Id);
}