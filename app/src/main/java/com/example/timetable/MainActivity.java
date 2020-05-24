package com.example.timetable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Spinner department,semester,course,day,lecture;
    EditText room;
    Dialog dialog,dialog1,dialog2;
    FloatingActionButton floatingActionButton;
    Button filter,fil;
    ListView view;
    String d,s,c;
    List<data> dat;
    TableLayout tableLayout;
    TextView m1,m2,m3,m4,m5,m6,m7,m8,t1,t2,t3,t4,t5,t6,t7,t8,w1,w2,w3,w4,w5,w6,w7,w8,th1,th2,th3,th4,th5,th6,th7,th8,f1,f2,f3,f4,f5
            ,f6,f7,f8;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dialog=new Dialog(MainActivity.this);
        dialog1=new Dialog(MainActivity.this);
        dat=new ArrayList<>();
        dialog2=new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.timetabledialoge);
        dialog1.setContentView(R.layout.dialoge1);
        dialog2.setContentView(R.layout.dialoge2);
        Button button=dialog1.findViewById(R.id.flecture);
        Button button1=dialog1.findViewById(R.id.fsemester);
        department=dialog.findViewById(R.id.department);
        fil=dialog2.findViewById(R.id.fil);
        day=dialog2.findViewById(R.id.dayy);
        lecture=dialog2.findViewById(R.id.lecturee);
        room=dialog2.findViewById(R.id.roomm);
        semester=dialog.findViewById(R.id.semester);
        view=(ListView)findViewById(R.id.listview);
        tableLayout=(TableLayout)findViewById(R.id.scroll);
        tableLayout.setVisibility(View.GONE);
        dialog1.show();
        dialog1.setCancelable(false);
        dialog.setCancelable(false);
        dialog2.setCancelable(false);
        filter=dialog.findViewById(R.id.filter);
        String[] da={"day","Monday","Tuesday","Wednesday","Thursday","Friday"};
        floatingActionButton=(FloatingActionButton)findViewById(R.id.dial);
        course=dialog.findViewById(R.id.cours);
        Button button2=dialog2.findViewById(R.id.back);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2.dismiss();
                dialog1.show();
            }
        });
        Button button3=dialog.findViewById(R.id.back);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                dialog1.show();
            }
        });
        final String[] cours={"Course","B.Tech","M.Tech"};
        String[] sem={"semester","1","2","3","4","5","6","7","8"};
        String[] no={"lecture no.","1","2","3","4","5","6","7","8"};
        final String[] depart={"Department","Cse 1","Cse 2","Mechanical 1","Mechanical 2","Electrical","Electronics and Communication","Civil","Chemical","MSME"};
        ArrayAdapter<CharSequence> adapter=new ArrayAdapter<CharSequence>(this,R.layout.support_simple_spinner_dropdown_item,depart);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        department.setAdapter(adapter);
        ArrayAdapter<CharSequence> adapter1=new ArrayAdapter<CharSequence>(this,R.layout.support_simple_spinner_dropdown_item,cours);
        adapter1.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        course.setAdapter(adapter1);
        ArrayAdapter<CharSequence>adapter2=new ArrayAdapter<CharSequence>(this,R.layout.support_simple_spinner_dropdown_item,sem);
        adapter2.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        semester.setAdapter(adapter2);
        ArrayAdapter<CharSequence>adapter4=new ArrayAdapter<CharSequence>(this,R.layout.support_simple_spinner_dropdown_item,da);
        adapter4.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        day.setAdapter(adapter4);
        ArrayAdapter<CharSequence>adapter5=new ArrayAdapter<CharSequence>(this,R.layout.support_simple_spinner_dropdown_item,no);
        adapter5.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        lecture.setAdapter(adapter5);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.show();
                dialog1.setCancelable(true);
                dialog.setCancelable(true);
                dialog2.setCancelable(true);
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager cm = (ConnectivityManager) getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);
                if(cm.getActiveNetworkInfo()==null || !cm.getActiveNetworkInfo().isConnected())
                    Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
                else{
                dialog.show();
                dialog1.dismiss();}
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager cm = (ConnectivityManager) getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);
                if(cm.getActiveNetworkInfo()==null || !cm.getActiveNetworkInfo().isConnected())
                    Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
                else{
                dialog2.show();
                dialog1.dismiss();}
            }
        });
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d=department.getSelectedItem().toString();
                s=semester.getSelectedItem().toString();
                c=course.getSelectedItem().toString();
                ConnectivityManager cm = (ConnectivityManager) getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);
                if(cm.getActiveNetworkInfo()==null || !cm.getActiveNetworkInfo().isConnected())
                    Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
                else if(d.equals("Department"))
                    Toast.makeText(getApplicationContext(),"select department",Toast.LENGTH_SHORT).show();
                else if(s.equals("semester"))
                    Toast.makeText(getApplicationContext(),"select semester",Toast.LENGTH_SHORT).show();
                else if(c.equals("Course"))
                    Toast.makeText(getApplicationContext(),"select course",Toast.LENGTH_SHORT).show();
                else{
                    tableLayout.setVisibility(View.VISIBLE);
                    view.setVisibility(View.GONE);
                    new background1().execute();
                    dialog.dismiss();}
            }
        });
        fil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String da=day.getSelectedItem().toString();
                String le=lecture.getSelectedItem().toString();
                ConnectivityManager cm = (ConnectivityManager) getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);
                if(cm.getActiveNetworkInfo()==null || !cm.getActiveNetworkInfo().isConnected())
                    Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
                else if(da.equals("day"))
                    Toast.makeText(getApplicationContext(),"select day",Toast.LENGTH_SHORT).show();
                else if(le.equals("lecture no."))
                    Toast.makeText(getApplicationContext(),"select lecture",Toast.LENGTH_SHORT).show();
                else{
                    new background2().execute();
                    dialog2.dismiss();
                }
            }
        });
        m1=(TextView)findViewById(R.id.m1);t1=(TextView)findViewById(R.id.t1);w1=(TextView)findViewById(R.id.w1); th1=(TextView)findViewById(R.id.Th1); f1=(TextView)findViewById(R.id.f1);
        m2=(TextView)findViewById(R.id.m2);t2=(TextView)findViewById(R.id.t2);w2=(TextView)findViewById(R.id.w2); th2=(TextView)findViewById(R.id.Th2);f2=(TextView)findViewById(R.id.f2);
        m3=(TextView)findViewById(R.id.m3);t3=(TextView)findViewById(R.id.t3);w3=(TextView)findViewById(R.id.w3);th3=(TextView)findViewById(R.id.Th3);f3=(TextView)findViewById(R.id.f3);
        m4=(TextView)findViewById(R.id.m4);t4=(TextView)findViewById(R.id.t4);w4=(TextView)findViewById(R.id.w4);th4=(TextView)findViewById(R.id.Th4);f4=(TextView)findViewById(R.id.f4);
        m5=(TextView)findViewById(R.id.m5);t5=(TextView)findViewById(R.id.t5);w5=(TextView)findViewById(R.id.w5);th5=(TextView)findViewById(R.id.Th5);f5=(TextView)findViewById(R.id.f5);
        m6=(TextView)findViewById(R.id.m6);t6 =(TextView)findViewById(R.id.t6);w6=(TextView)findViewById(R.id.w6);th6=(TextView)findViewById(R.id.Th6);f6=(TextView)findViewById(R.id.f6);
        m7=(TextView)findViewById(R.id.m7);t7=(TextView)findViewById(R.id.t7);w7=(TextView)findViewById(R.id.w7);th7=(TextView)findViewById(R.id.Th7);f7=(TextView)findViewById(R.id.f7);
        m8=(TextView)findViewById(R.id.m8);t8=(TextView)findViewById(R.id.t8);w8=(TextView)findViewById(R.id.w8);th8=(TextView)findViewById(R.id.Th8);f8=(TextView)findViewById(R.id.f8);
    }
    class background1 extends AsyncTask<String,String,String> {
        ProgressDialog builder;
        background1(){
            builder =new ProgressDialog(MainActivity.this);
            builder.setCancelable(false);
        }
        @Override
        protected String doInBackground(String... strings) {

            String result="";
            String host="http://www.timetablemanit.dx.am/retrievetime.php";
            try {
                URL url=new URL(host);
                try {
                    HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream=httpURLConnection.getOutputStream();
                    OutputStreamWriter outputStreamWriter=new OutputStreamWriter(outputStream,"UTF-8");
                    BufferedWriter bufferedWriter=new BufferedWriter(outputStreamWriter);
                    String inertial= URLEncoder.encode("department","UTF-8")+"="+URLEncoder.encode(d,"UTF-8")+"&"+
                            URLEncoder.encode("course","UTF-8")+"="+URLEncoder.encode(c,"UTF-8")+"&"+
                            URLEncoder.encode("semester","UTF-8")+"="+URLEncoder.encode(s,"UTF-8");
                    bufferedWriter.write(inertial);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    InputStream inputStream=httpURLConnection.getInputStream();
                    BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                    String line;
                    StringBuffer buffer=new StringBuffer("");
                    while((line=bufferedReader.readLine())!=null){
                        buffer.append(line);break;}
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    result=buffer.toString();
                    return result;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            builder.setMessage("Loading");
            builder.show();
        }

        @Override
        protected void onPostExecute(String s) {
            if(s!=null)
                builder.dismiss();
            if(s.trim().equals("not"))
                Toast.makeText(getApplicationContext(),"data not available",Toast.LENGTH_SHORT).show();
            try {
                JSONObject object=new JSONObject(s);
                JSONArray array=object.getJSONArray("data");
                for(int i=0;i<array.length();i++){
                    JSONObject jsonObject=array.getJSONObject(i);
                    String name=jsonObject.getString("Faculty");
                    String dp=jsonObject.getString("subject");
                    String r=jsonObject.getString("room");
                    String d=jsonObject.getString("day");
                    String l=jsonObject.getString("lecture");
                    String n=name+" "+"("+dp+")"+" "+r;
                    if(l.equals("1") && d.equals("Monday"))
                        m1.setText(n);
                    else if(l.equals("2") && d.equals("Monday")){  m2.setText(n);}
                    else if(l.equals("3") && d.equals("Monday")){  m3.setText(n);}
                    else if(l.equals("4") && d.equals("Monday")){  m4.setText(n);}
                    else if(l.equals("5") && d.equals("Monday")){  m5.setText(n);}
                    else if(l.equals("6") && d.equals("Monday")){  m6.setText(n);}
                    else if(l.equals("7") && d.equals("Monday")){  m7.setText(n);}
                    else if(l.equals("8") && d.equals("Monday")){  m8.setText(n);}
                    else if(l.equals("1") && d.equals("Tuesday")){  t1.setText(n);}
                    else if(l.equals("2") && d.equals("Tuesday")){  t2.setText(n);}
                    else if(l.equals("3") && d.equals("Tuesday")){  t3.setText(n);}
                    else if(l.equals("4") && d.equals("Tuesday")){  t4.setText(n);}
                    else if(l.equals("5") && d.equals("Tuesday")){  t5.setText(n);}
                    else if(l.equals("6") && d.equals("Tuesday")){  t6.setText(n);}
                    else if(l.equals("7") && d.equals("Tuesday")){  t7.setText(n);}
                    else if(l.equals("8") && d.equals("Tuesday")){  t8.setText(n);}
                    else if(l.equals("1") && d.equals("Wednesday")){  w1.setText(n);}
                    else if(l.equals("2") && d.equals("Wednesday")){  w2.setText(n);}
                    else if(l.equals("3") && d.equals("Wednesday")){  w3.setText(n);}
                    else if(l.equals("4") && d.equals("Wednesday")){  w4.setText(n);}
                    else if(l.equals("5") && d.equals("Wednesday")){  w5.setText(n);}
                    else if(l.equals("6") && d.equals("Wednesday")){  w6.setText(n);}
                    else if(l.equals("7") && d.equals("Wednesday")){  w7.setText(n);}
                    else if(l.equals("8") && d.equals("Wednesday")){  w8.setText(n);}
                    else if(l.equals("1") && d.equals("Thursday")){  th1.setText(n);}
                    else if(l.equals("2") && d.equals("Thursday")){  th2.setText(n);}
                    else if(l.equals("3") && d.equals("Thursday")){  th3.setText(n);}
                    else if(l.equals("4") && d.equals("Thursday")){  th4.setText(n);}
                    else if(l.equals("5") && d.equals("Thursday")){  th5.setText(n);}
                    else if(l.equals("6") && d.equals("Thursday")){  th6.setText(n);}
                    else if(l.equals("7") && d.equals("Thursday")){  th7.setText(n);}
                    else if(l.equals("8") && d.equals("Thursday")){  th8.setText(n);}
                    else if(l.equals("1") && d.equals("Friday")){  f1.setText(n);}
                    else if(l.equals("2") && d.equals("Friday")){  f2.setText(n);}
                    else if(l.equals("3") && d.equals("Friday")){  f3.setText(n);}
                    else if(l.equals("4") && d.equals("Friday")){  f4.setText(n);}
                    else if(l.equals("5") && d.equals("Friday")){  f5.setText(n);}
                    else if(l.equals("6") && d.equals("Friday")){  f6.setText(n);}
                    else if(l.equals("7") && d.equals("Friday")){  f7.setText(n);}
                    else if(l.equals("8") && d.equals("Friday")){  f8.setText(n);}


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            super.onPostExecute(s);
        }
    }
    class background2 extends AsyncTask<String,String,String>{
        ProgressDialog builder;
        background2(){
            builder =new ProgressDialog(MainActivity.this);
            builder.setCancelable(false);

        }
        @Override
        protected String doInBackground(String... strings) {

            String result="";
            String host="http://www.timetablemanit.dx.am/new.php";
            try {
                URL url=new URL(host);
                try {
                    String d;
                    if(TextUtils.isEmpty(room.getText().toString()))
                        d="null";
                    else
                        d=room.getText().toString();
                    HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream=httpURLConnection.getOutputStream();
                    OutputStreamWriter outputStreamWriter=new OutputStreamWriter(outputStream,"UTF-8");
                    BufferedWriter bufferedWriter=new BufferedWriter(outputStreamWriter);
                    String inertial= URLEncoder.encode("day","UTF-8")+"="+URLEncoder.encode(day.getSelectedItem().toString(),"UTF-8")+"&"+
                            URLEncoder.encode("lecture","UTF-8")+"="+URLEncoder.encode(lecture.getSelectedItem().toString(),"UTF-8")+"&"+
                            URLEncoder.encode("room","UTF-8")+"="+URLEncoder.encode(d,"UTF-8");
                    bufferedWriter.write(inertial);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    InputStream inputStream=httpURLConnection.getInputStream();
                    BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                    String line;
                    StringBuffer buffer=new StringBuffer("");
                    while((line=bufferedReader.readLine())!=null){
                        buffer.append(line);break;}
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    result=buffer.toString();
                    return result;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            builder.setMessage("Loading");
            dat.clear();
            builder.show();
        }

        @Override
        protected void onPostExecute(String s) {
            if(s!=null){
                builder.dismiss();
                view.setVisibility(View.VISIBLE);
                tableLayout.setVisibility(View.GONE);}
            if(s.trim().equals("not")){
                dat.clear();
                adapter adapter=new adapter(getApplicationContext(),dat);
                view.setAdapter(adapter);
                Toast.makeText(getApplicationContext(),"data not available",Toast.LENGTH_LONG).show();}
            try {
                JSONObject object=new JSONObject(s);
                JSONArray array=object.getJSONArray("data");
                for(int i=0;i<array.length();i++){
                    JSONObject jsonObject=array.getJSONObject(i);
                    String name=jsonObject.getString("Faculty");
                    String dp=jsonObject.getString("subject");
                    String r=jsonObject.getString("room");
                    data data=new data(r,name,dp);
                    dat.add(data);
                }
                adapter adapter=new adapter(getApplicationContext(),dat);
                view.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            super.onPostExecute(s);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}
class data{
    String room,faculty,sub;

    public data(String room, String faculty, String sub) {
        this.room = room;
        this.faculty = faculty;
        this.sub = sub;
    }

    public String getRoom() {
        return room;
    }

    public String getFaculty() {
        return faculty;
    }

    public String getSub() {
        return sub;
    }
}
class adapter extends ArrayAdapter<data>{
    Context context;
    List<data>dat=new ArrayList<>();
    public adapter(@NonNull Context context, List<data>dat) {
        super(context, R.layout.viewlayout,dat);
        this.context=context;
        this.dat=dat;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view= LayoutInflater.from(context).inflate(R.layout.viewlayout,parent,false);
        TextView textView=view.findViewById(R.id.rom);
        TextView textView1=view.findViewById(R.id.fac);
        TextView textView2=view.findViewById(R.id.sub);
        textView.setText(dat.get(position).getRoom());
        textView1.setText(dat.get(position).getFaculty());
        textView2.setText("("+dat.get(position).getSub()+")");
        return view;
    }
}

