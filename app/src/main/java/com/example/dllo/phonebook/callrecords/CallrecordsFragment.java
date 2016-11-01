package com.example.dllo.phonebook.callrecords;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dllo.phonebook.DB.DBTools;
import com.example.dllo.phonebook.base.BaseFragment;
import com.example.dllo.phonebook.R;

import java.util.ArrayList;

/**
 * Created by dllo on 16/4/19.
 */
public class CallrecordsFragment extends BaseFragment implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    private ListView listView;
    private CallrecordBaseAdapter myBaseAdapter;
    private ArrayList<CallrecordData> datas;
    private Button btn_Determine, btn_Delete;
    private EditText et_call;
    private String result = new String();
    private DBTools tools;



    @Override
    public int setLayout() {
        return R.layout.fragment_call_records;
    }

    @Override
    public void initView(View view) {
        listView = (ListView) view.findViewById(R.id.listView);
        btn_Determine = (Button) view.findViewById(R.id.btn_Determine);
        btn_Delete = (Button) view.findViewById(R.id.btn_Delete);
        et_call = (EditText) view.findViewById(R.id.et_call);


        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);
        btn_Determine.setOnClickListener(this);
        btn_Delete.setOnClickListener(this);
        myBaseAdapter = new CallrecordBaseAdapter(getContext());
        listView.setAdapter(myBaseAdapter);
    }

    @Override
    public void initData() {
        tools = new DBTools(context);
        datas = new ArrayList<>();
       datas.addAll(tools.queryDBAll());
       myBaseAdapter.setDatas(datas);

       /* MyTask myTask = new MyTask();
        myTask.execute("http://project.lanou3g.com/teacher/yihuiyun/lanouproject/activitylist.php");*/

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_Determine:
              myBaseAdapter.addData(new CallrecordData
                      (et_call.getText().toString(),R.mipmap.ic_launcher), 0 );
                et_call.setText(null);
            break;
            case R.id.btn_Delete:
                myBaseAdapter.deleData(0);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getContext(), "点了第:" + position + "个", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        myBaseAdapter.deleData(position);
        return true;
    }

/*    //开线程的内部类,解析数据
    class MyTask extends AsyncTask<String, Void, ArrayList<AdressBook>> {
        ArrayList<AdressBook> adressBooks = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        //主线程又叫UI线程,凡是有UI刷新操作,与UI有交互都在主线程进行
        //凡是耗时任务都在子线程进行
        @Override
        protected ArrayList<AdressBook> doInBackground(String... params) {
            String url = params[0];
            Log.d("####", url);
            try {
                URL u = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) u.openConnection();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream is = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        result += line;
                    }
                    reader.close();
                    is.close();
                    connection.disconnect();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                JSONObject object = new JSONObject(result);
                if (object.has("events")) {
                    JSONArray array = object.getJSONArray("events");

                    for (int i = 0; i < array.length(); i++) {
                        AdressBook adressBook = new AdressBook();
                        JSONObject o = array.getJSONObject(i);
                        if (o.has("title")) {
                            adressBook.setName(o.getString("title"));

                        }
                        if (o.has("address")) {
                            adressBook.setTeleNum(o.getString("address"));
                        }
                        adressBook.setImgId(R.mipmap.ic_launcher);
//                      Log.d("~~~~~~~~", adressBook.getName());
                        adressBooks.add(adressBook);

                    }
                    for (AdressBook i : adressBooks
                            ) {
//                        Log.d("~~~~~~~~", i.getName());
                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return adressBooks;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(ArrayList<AdressBook> adressBooks) {
            super.onPostExecute(adressBooks);
            listViewAdapter.setAdressBooks(adressBooks);
        }*/
}
