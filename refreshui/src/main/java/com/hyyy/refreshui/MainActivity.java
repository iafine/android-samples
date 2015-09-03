package com.hyyy.refreshui;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener{

    public static final int UPDATE_TEXT = 1;
    private Button mButton;
    private TextView mTextView;
    /**
     * 方法一：使用异步消息处理机制完成更新UI
     * private Handler mHandler = new Handler(){
    @Override
    public void handleMessage(Message msg) {
    switch (msg.what){
    case UPDATE_TEXT:
    mTextView.setText(msg.obj.toString());
    }
    }
    };*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mButton = (Button) findViewById(R.id.btn);
        mButton.setOnClickListener(this);
        mTextView = (TextView) findViewById(R.id.txt);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn:
                /**
                 *  方法一：使用异步消息处理机制完成更新UI操作
                 new Thread(new Runnable() {
                @Override
                public void run() {
                for (int i=0; i<100; i++){
                Message msg = new Message();
                msg.what = UPDATE_TEXT;
                msg.obj = "学习多线程操作"+i;
                mHandler.sendMessage(msg);
                try {
                Thread.sleep(1000);
                } catch (InterruptedException e) {
                e.printStackTrace();
                }
                }
                }
                }).start();
                 */

                /**
                 * 方法二：使用AsyncTask完成UI刷新
                 */
                new RefreshTask().execute();
        }
    }

    /**
     * 方法二：使用AsyncTask完成UI刷新
     */
    class RefreshTask extends AsyncTask<Void, Integer, Boolean>{

        private int i = 1;
        @Override
        protected Boolean doInBackground(Void... params) {
            while(true){
                publishProgress(i);
                if(i >= 100){
                    break;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i++;
            }
            return true;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            mTextView.setText("学习多线程操作"+values[0]);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean){
                Toast.makeText(MainActivity.this, "界面更新完成！", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(MainActivity.this, "界面更新失败", Toast.LENGTH_LONG).show();
            }
        }
    }
}