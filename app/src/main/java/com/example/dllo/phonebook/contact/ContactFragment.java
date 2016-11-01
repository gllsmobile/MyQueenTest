package com.example.dllo.phonebook.contact;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.dllo.phonebook.DB.DBTools;
import com.example.dllo.phonebook.base.BaseFragment;
import com.example.dllo.phonebook.R;

import java.util.ArrayList;

/**
 * Created by dllo on 16/4/19.
 */
public class ContactFragment extends BaseFragment implements View.OnClickListener {
    private ListView se_listView;
    private ContactAdapter contactAdapter;
    private ArrayList<StudentBade> datas;
    private Button insertButton,selectButton,deleteButton,updataButton;
    private SQLiteDatabase database;
    private DBTools tools ;

    @Override
    public int setLayout() {
        return R.layout.fragment_contact;
    }

    @Override
    public void initView(View view) {
        database = getContext().openOrCreateDatabase("studentlist.db",Context.MODE_PRIVATE,null);

        se_listView = (ListView) view.findViewById(R.id.se_listView);
        insertButton = (Button) view.findViewById(R.id.insertButton);
        selectButton = (Button) view.findViewById(R.id.selectButton);
        deleteButton = (Button) view.findViewById(R.id.deleteButton);
        updataButton = (Button) view.findViewById(R.id.updataButton);

        insertButton.setOnClickListener(this);
        selectButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
        insertButton.setOnClickListener(this);

    }

    @Override
    public void initData() {
        tools = new DBTools(context);
        contactAdapter = new ContactAdapter(getContext());
        se_listView.setAdapter(contactAdapter);
        datas = new ArrayList<>();

      datas.addAll(tools.queryAll());
        contactAdapter.setDatas(datas);
        datas = null;

    }

    @Override
    public void onClick(View v) {

    }
}
