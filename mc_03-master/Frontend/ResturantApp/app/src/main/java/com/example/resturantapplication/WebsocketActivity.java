/*package com.example.resturantapplication;

import android.os.Bundle;

import com.example.resturantapplication.BufferPage;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

import androidx.appcompat.app.AppCompatActivity;

public class WebsocketActivity extends Fragment {

    private WebSocketClient mWebSocketClient;

    private Button bConnect, bDisconnect, bSendButton;
    private TextView mOutput;
    private EditText mInput;

    String uid;
    String cookie;
    String zip = "50014";


    public WebsocketActivity() {
        // Required empty public constructor
    }
    public void setCookie(String cookie){
        this.cookie = cookie;
    }
    public void setUserId(String userId){
        this.uid = userId;
    }
    public void setZip(String zip){
        this.zip = zip;
    }
    //for testing purposes only
    public String getZip(){
        return zip;
    }
    public static WebsocketActivity newInstance(String param1, String param2) {
        WebsocketActivity fragment = new WebsocketActivity();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         //setContentView(R.layout.activity_main);

        // Get the buttons
         //bConnect = findViewById(R.id.b_connect);
         //bSendButton = findViewById(R.id.b_sendMessage);
         //bDisconnect = findViewById(R.id.b_Disconnect);

        // Get the textview
         //mOutput = findViewById(R.id.m_output);

        // Add scrolling
        mOutput.setMovementMethod(new ScrollingMovementMethod());

        //Get the editText
         //mInput = findViewById(R.id.m_input);

        // Add handlers to the buttons
        bConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectWebSocket();
            }
        });

        bDisconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebSocketClient.close();
                mOutput.setText("");
            }
        });

        bSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the message from the input
                String message = mInput.getText().toString();

                // If the message is not empty, send the message
                if(message != null && message.length() > 0){
                    mWebSocketClient.send(message);
                }
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_websocket_activity, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mWebSocketClient.close();
    }

    private void connectWebSocket() {
        URI uri;
        try {
            //uri = new URI("localhost:8080/chat/" + zip + "/" + uid + "/"+cookie); // 10.0.2.2 = localhost
            uri = new URI("ws://echo.websocket.org");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        mWebSocketClient = new WebSocketClient(uri) {

            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                Log.i("Websocket", "Opened");
            }

            @Override
            public void onMessage(String msg) {
                Log.i("Websocket", "Message Received");
                // Appends the message received to the previous messages
                mOutput.append("\n" + msg);
            }

            @Override
            public void onClose(int errorCode, String reason, boolean remote) {
                Log.i("Websocket", "Closed " + reason);
            }

            @Override
            public void onError(Exception e) {
                Log.i("Websocket", "Error " + e.getMessage());
            }
        };
        mWebSocketClient.connect();
    }



}*/