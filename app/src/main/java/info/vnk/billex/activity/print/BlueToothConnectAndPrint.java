package info.vnk.billex.activity.print;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Movie;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import info.vnk.billex.R;
import info.vnk.billex.activity.order.AddOrderActivity;
import info.vnk.billex.model.order.PostOrderResultModel;
import info.vnk.billex.model.print.BasicDetailsModel;
import info.vnk.billex.model.print.FinalDetailsModel;
import info.vnk.billex.model.print.ItemDetailsModel;
import info.vnk.billex.model.print.PrintMainModel;
import info.vnk.billex.model.print.PrintResultModel;
import info.vnk.billex.network.ApiClient;
import info.vnk.billex.network.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BlueToothConnectAndPrint extends AppCompatActivity {

    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";
    private static final String TAG = "BlueToothConnectAndPrint";
    private static final boolean D = true;
    private static final int REQUEST_CONNECT_DEVICE = 2;
    private static final int REQUEST_ENABLE_BT = 3;

    String receivedmsg = "";
    boolean ifbattery = false;
    String prevCmd = "";
    String printmsg = "", printadd = "";
    TextView txtprintername;
    LinearLayout btnLayout;
    String comparemsg = "";

    private String orderId;

    private Button mSendButton, button_cancel;
    private String mConnectedDeviceName = null;
    private ArrayAdapter<String> mConversationArrayAdapter;
    private StringBuffer mOutStringBuffer;
    private BluetoothAdapter mBluetoothAdapter = null;
    private BluetoothChatService mChatService = null;

    private final Handler mHandler = new Handler() {
        @SuppressLint("LongLogTag")
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case MESSAGE_STATE_CHANGE:
                    if (D)
                        Log.i(TAG, "MESSAGE_STATE_CHANGE: " + msg.arg1);
                    switch (msg.arg1) {
                        case BluetoothChatService.STATE_CONNECTED:
                            txtprintername.setText("Connected to " + mConnectedDeviceName);
                            btnLayout.setVisibility(View.VISIBLE);
                            mConversationArrayAdapter.clear();
                            try {
                                Thread.sleep(1050);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            mSendButton.setEnabled(true);
                            break;
                        case BluetoothChatService.STATE_CONNECTING:

                            txtprintername.setText("Connecting... ");

                            btnLayout.setVisibility(View.INVISIBLE);
                            mSendButton.setEnabled(false);
                            break;
                        case BluetoothChatService.STATE_LISTEN:
                        case BluetoothChatService.STATE_NONE:

                            txtprintername.setText("Not Connected... ");

                            btnLayout.setVisibility(View.VISIBLE);
                            mSendButton.setEnabled(true);
                            break;
                    }
                    break;
                case MESSAGE_WRITE:
                    try {

                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                    break;
                case MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    String readMessage = new String(readBuf, 0, msg.arg1);
                    comparemsg = comparemsg + readMessage;

                    Log.e("ChargeMEssage: ", comparemsg);
                    if (comparemsg.length() >= 5 || !comparemsg.trim().isEmpty()) {
                        comparemsg = "".trim();
                        readMessage = readMessage.trim().replace("BL=", "");
                        String chargeValue = readMessage.replaceAll("[^0-9]", "");
                        if (chargeValue.equals("")) {
                            chargeValue = "1";
                        }

                        if (chargeValue.equals("0")) {
                            txtprintername.setText("No Charge in Printer ");

                        } else {

                            ifbattery = true;

                            BlueToothConnectAndPrint.this.sendMessageTahi(receivedmsg);

                        }

                    } else {

                    }

                    break;
                case MESSAGE_DEVICE_NAME:
                    mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
                    txtprintername.setText("Connected to " + mConnectedDeviceName);
                    Toast.makeText(getApplicationContext(),
                            "Connected to " + mConnectedDeviceName, Toast.LENGTH_SHORT)
                            .show();
                    mSendButton.setEnabled(true);
                    break;
                case MESSAGE_TOAST:
                    Toast.makeText(getApplicationContext(),
                            msg.getData().getString(TOAST), Toast.LENGTH_SHORT).show();

                    break;
            }
        }
    };
    private long mLastClickTime = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            setContentView(R.layout.main);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }
        // get data via the key
        orderId = extras.getString("OrderId");

        txtprintername = (TextView) findViewById(R.id.txtprintername);
        button_cancel = (Button) findViewById(R.id.button_cancel);
        btnLayout = (LinearLayout) findViewById(R.id.btnLayout);
        /*printmsg =  "<0x00><0x01>" + getHeader() + getLines() +
                "Company\n" +
                getLines() +
                getTitleHeader() +
                "<0x0A>";*/
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            return;
        }

        button_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        } else {
            if (mChatService == null) {
                setupChat();
            }
        }

    }

    @Override
    public synchronized void onResume() {
        super.onResume();
        if (D)
            mSendButton = (Button) findViewById(R.id.button_send);
        mSendButton.setEnabled(true);
        if (mChatService != null) {
            if (mChatService.getState() == BluetoothChatService.STATE_NONE) {
                mChatService.start();
            }
        }
    }

    private void setupChat() {
        mConversationArrayAdapter = new ArrayAdapter<String>(this, R.layout.message);
        mSendButton = (Button) findViewById(R.id.button_send);
        mSendButton.setTag(1);
        mSendButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                mSendButton.setEnabled(false);
                ifbattery = false;
                if (SystemClock.elapsedRealtime() - mLastClickTime < 3000) {
                    return;
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSendButton.setEnabled(true);
                    }
                }, 3000);
                mLastClickTime = SystemClock.elapsedRealtime();
                Log.e("click", "count");
                getPrint();
                //sendMessageTahi(printmsg);
            }
        });

        mChatService = new BluetoothChatService(this, mHandler);


        mOutStringBuffer = new StringBuffer("");
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

        List<String> s = new ArrayList<String>();
        for(BluetoothDevice bt : pairedDevices) {
            s.add(bt.getName());

        }
        //00:18:E4:0A:00:01-- addres for this printer
        connectDevice("00:18:E4:0A:00:01");
    }

    @SuppressLint("LongLogTag")
    @Override
    public synchronized void onPause() {
        super.onPause();
        if (D)
            Log.e(TAG, "- ON PAUSE -");
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onStop() {
        super.onStop();
        if (D)
            Log.e(TAG, "-- ON STOP --");
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mChatService != null)
            mChatService.stop();
        if (D)
            Log.e(TAG, "--- ON DESTROY ---");
    }

    @SuppressLint("LongLogTag")
    private void ensureDiscoverable() {
        if (D)
            Log.d(TAG, "ensure discoverable");
        if (mBluetoothAdapter.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoverableIntent);
        }
    }


    public void sendMessageTahi(String message) {
        receivedmsg = message;

        Log.e("" + receivedmsg.length(), "sendMessagesendMessage     " + message);

        if (!ifbattery) {
            checkbattery();

        } else {
            try {
                Thread.sleep(1050);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (message.length() > 0) {
                String str = message;
                Toast.makeText(BlueToothConnectAndPrint.this, "  Please wait Printing is in Progress ", Toast.LENGTH_LONG).show();
                String[] strArray = str.split("<");

                try {

                    for (int i = 0; i < strArray.length; i++) {
                        String mstr = "<" + strArray[i];
                        Pattern pattern = Pattern.compile("<(.*?)>");
                        Matcher matcher = pattern.matcher(mstr);

                        byte cmd = (byte) 0x10;
                        String strPrintArray = "...........";

                        if (matcher.find()) {

                            strPrintArray = mstr.replace("<" + matcher.group(1) + ">", "");

                            try {

                                if (matcher.group(1).equals("0x09")) {
                                    //RESET
                                    byte[] m = new byte[2];
                                    byte[] m2 = new byte[3];

                                    m[0] = (byte) 0x1b;
                                    m[1] = (byte) 0x40;

                                    mChatService.write(m);

                                    m2[0] = (byte) 0x1b;
                                    m2[1] = (byte) 0x21;
                                    m2[2] = (byte) 0x00;

                                    mChatService.write(m2);

                                } else if (matcher.group(1).equals("0x00")) {
                                    //NORMAL
                                    byte[] m2 = new byte[3];
                                    m2[0] = (byte) 0x1b;
                                    m2[1] = (byte) 0x21;
                                    m2[2] = (byte) 0x00;

                                    mChatService.write(m2);
                                } else if (matcher.group(1).equals("0x20")) {
                                    //DOUBLE WIDTH
                                    byte[] m = new byte[3];
                                    m[0] = (byte) 0x1b;
                                    m[1] = (byte) 0x21;
                                    m[2] = (byte) 0x20;

                                    mChatService.write(m);

                                } else if (matcher.group(1).equals("0x10")) {
                                    //DOUBLE HEIGHT
                                    byte[] m = new byte[3];
                                    m[0] = (byte) 0x1b;
                                    m[1] = (byte) 0x21;
                                    m[2] = (byte) 0x10;

                                    if (i == 1) {
                                        Thread.sleep(1050);
                                    }

                                    mChatService.write(m);

                                } else if (matcher.group(1).equals("0x0A")) {
                                    //LINE FEED
                                    cmd = (byte) 0x0A;

                                    mChatService.write(cmd);
                                } else if (matcher.group(1).equals("0x01")) {
                                    //DOUBLE HEIGHT
                                    byte[] m = new byte[3];
                                    m[0] = (byte) 0x1B;
                                    m[1] = (byte) 0x61;
                                    m[2] = (byte) 0x01;

                                    if (i == 1) {
                                        Thread.sleep(1050);
                                    }

                                    mChatService.write(m);

                                } else if (matcher.group(1).equals("0x02")) {
                                    //DOUBLE HEIGHT
                                    byte[] m = new byte[3];
                                    m[0] = (byte) 0x1B;
                                    m[1] = (byte) 0x61;
                                    m[2] = (byte) 0x00;

                                    if (i == 1) {
                                        Thread.sleep(1050);
                                    }

                                    mChatService.write(m);

                                }else if (matcher.group(1).equals("0x03")) {
                                    //DOUBLE HEIGHT
                                    byte[] m = new byte[3];
                                    m[0] = (byte) 0x1B;
                                    m[1] = (byte) 0x61;
                                    m[2] = (byte) 0x02;

                                    if (i == 1) {
                                        Thread.sleep(1050);
                                    }

                                    mChatService.write(m);

                                }

                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            prevCmd = matcher.group(1);

                            byte[] byteStr = strPrintArray.getBytes();

                            mChatService.write(byteStr);

                        }

                    }

                } catch (Exception e) {

                    e.printStackTrace();
                }
                try {

                    mSendButton.setText("Reprint");
                    ifbattery = false;

                } catch (Exception e) {

                    // TODO: handle exception
                }

            }
        }

    }

    private void checkbattery() {

        byte[] m = new byte[3];

        m[0] = (byte) 0x1c;
        m[1] = (byte) 0x62;
        m[2] = (byte) 0x00;

        mChatService.write(m);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
                    // Bluetooth is now enabled, so set up a chat session
                    setupChat();
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }

    private void connectDevice(String address) {
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        mChatService.connect(device);
    }

    private String getHeader(){
        String header = "NO.1 FOOD PRODUCTS\nKUMARAPURAM\nGSTIN: 32ARFPJ2869Q1Z9\n";
        return header;
    }

    private String getLines(){
        String lines = "------------------------";
        return lines;
    }

    private String getTitleHeader(List<ItemDetailsModel> itemDetailsModels){
        String title = "";

        for(int i = 0 ; i < itemDetailsModels.size(); i++) {

            String value = "<0x01>%SERIAL%.%ITEM_NAME%\n<0x02>" +
                    "HSNCode: %HSN%\n" +
                    "GST %: %GST%\n" +
                    "TAX PRICE: %TAX%\n" +
                    "PRICE: %PRICE%\n" +
                    "QTY: %QTY%\n" +
                    "GROSS AMT: %GROSS_AMT%\n" +
                    "NET AMT: %NET_AMT%\n" +
                    "TAX AMT: %TAX_AMT%\n" +
                    "TOTAL AMT: %TOTAL_AMT%\n";
            value = value.replace("%SERIAL%",itemDetailsModels.get(i).getSerialNo());
            value = value.replace("%ITEM_NAME%",itemDetailsModels.get(i).getItemName());
            value = value.replace("%HSN%",itemDetailsModels.get(i).getHsnCode());
            value = value.replace("%GST%",itemDetailsModels.get(i).getGst());
            value = value.replace("%TAX%",itemDetailsModels.get(i).getTaxPrice());
            value = value.replace("%PRICE%",itemDetailsModels.get(i).getPrice());
            value = value.replace("%QTY%",itemDetailsModels.get(i).getQuantity());
            value = value.replace("%GROSS_AMT%",itemDetailsModels.get(i).getGrossAmount());
            value = value.replace("%NET_AMT%",itemDetailsModels.get(i).getNetAmount());
            value = value.replace("%TAX_AMT%",itemDetailsModels.get(i).getTaxAmount());
            value = value.replace("%TOTAL_AMT%",itemDetailsModels.get(i).getTotalAmount());

            title = title + value + getLines();

        }
        return title;
    }

    private String getFinalDetail(FinalDetailsModel finalDetailsModel){
            String value = "<0x02>" +
                    "ROUND OFF: %ROUND_OFF%\n" +
                    "TOTAL AMT: %AMOUNT%\n";
        value = value.replace("%ROUND_OFF%",finalDetailsModel.getRoundOff());
        value = value.replace("%AMOUNT%",finalDetailsModel.getGrandTotal());

        return value + getLines();
    }

    private String getCompany(BasicDetailsModel basicDetailsModel){
        String value = "<0x01>%COMPANY_NAME%\n<0x02>" +
                "BILL NO: %BILL_NO%\n" +
                "ORDER DATE: %ORDER_DATE%\n" +
                "ADDRESS: %ADDRESS%\n" +
                "GST: %GST%\n";
        value = value.replace("%COMPANY_NAME%",basicDetailsModel.getName().toString());
        value  = value.replace("%BILL_NO%",basicDetailsModel.getBillNumber().toString());
        value = value.replace("%ORDER_DATE%",basicDetailsModel.getOrderDate().toString());
        value = value.replace("%ADDRESS%",basicDetailsModel.getAddress().toString());
        value = value.replace("%GST%",basicDetailsModel.getGst().toString());
        return value + getLines();
    }

    // call api to print
    private void getPrint() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<PrintMainModel> call = apiService.getPrint(orderId);
        call.enqueue(new Callback<PrintMainModel>() {
            @Override
            public void onResponse(Call<PrintMainModel> call, Response<PrintMainModel> response) {
                //response.body().getMessage();

                PrintResultModel resultModel =  response.body().getResult();

                printmsg =  "<0x00><0x01>" + getHeader() + getLines() +
                        getCompany(resultModel.getBasicDetails()) +
                        getTitleHeader(resultModel.getItemDetails()) +
                        getFinalDetail(resultModel.getFinalDetails()) +
                        "<0x0A>";



                sendMessageTahi(printmsg);
                Toast.makeText(BlueToothConnectAndPrint.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onFailure(Call<PrintMainModel> call, Throwable t) {
                Toast.makeText(BlueToothConnectAndPrint.this, "error" + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

}
