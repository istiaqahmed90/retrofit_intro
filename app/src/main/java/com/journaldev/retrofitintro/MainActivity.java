package com.journaldev.retrofitintro;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.journaldev.retrofitintro.pojo.MultipleResource;
import com.journaldev.retrofitintro.pojo.RequiredVarification;
import com.journaldev.retrofitintro.pojo.User;
import com.journaldev.retrofitintro.pojo.UserList;

import java.io.IOException;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextView responseText, create_use, list_users, post_name_and_job, tv_back_office;
    APIInterface apiInterface, apiInterface2;

    static  String base_url = "https://reqres.in";
    static  String base_url2 = "http://192.168.178.55/apiTest/";

    String tag = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intiUi();

        apiInterface = APIClient.getClient(base_url).create(APIInterface.class);

        apiInterface2 = APIClient.getClient(base_url2).create(APIInterface.class);

        getListResources(apiInterface);
//        getRequiredVarification(apiInterface2);
        createNewUser(apiInterface);
        getListUsers(apiInterface);
        postNameAndJob(apiInterface);

    }


//    private String isBackOfficeVarificationRequired() {
//        okhttp3.Response response = null;
//
//        OkHttpClient client = new OkHttpClient();
//
//        RequestBody formBody = new FormBody.Builder()
//                .add("message", "Your message")
//                .build();
//        Request request = new Request.Builder()
//                .url("http://192.168.178.55/apiTest/index.php")
//                .post(formBody)
//                .build();
//
//        try {
//           response = client.newCall(request).execute();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return  response.toString();
//    }

    private void intiUi() {
        responseText = (TextView) findViewById(R.id.responseText);
        create_use = (TextView) findViewById(R.id.create_use);
        list_users = (TextView) findViewById(R.id.list_users);
        post_name_and_job = (TextView) findViewById(R.id.post_name_and_job);
        tv_back_office = (TextView) findViewById(R.id.tv_back_office);
    }

    private void postNameAndJob(APIInterface _apiInterface) {
        Call<UserList> call3 = _apiInterface.doCreateUserWithField("morpheus","leader");
        call3.enqueue(new Callback<UserList>() {
            @Override
            public void onResponse(Call<UserList> call, Response<UserList> response) {
                UserList userList = response.body();
                Integer text = userList.page;
                Integer total = userList.total;
                Integer totalPages = userList.totalPages;
                List<UserList.Datum> datumList = userList.data;
//                Toast.makeText(getApplicationContext(), text + " page\n" + total + " total\n" + totalPages + " totalPages\n", Toast.LENGTH_SHORT).show();

                for (UserList.Datum datum : datumList) {
//                    Toast.makeText(getApplicationContext(), "id : " + datum.id + " name: " + datum.first_name + " " + datum.last_name + " avatar: " + datum.avatar, Toast.LENGTH_SHORT).show();
                    post_name_and_job.setText("postNameAndJob() name: " + datum.first_name);
                }

            }

            @Override
            public void onFailure(Call<UserList> call, Throwable t) {
                call.cancel();
                post_name_and_job.setText(" postNameAndJob() call.cancel()");
            }
        });
    }

    private void getListUsers( APIInterface _apiInterface ) {
        Call<UserList> call2 = _apiInterface.doGetUserList("2");
        call2.enqueue(new Callback<UserList>() {
            @Override
            public void onResponse(Call<UserList> call, Response<UserList> response) {

                UserList userList = response.body();
                Integer text = userList.page;
                Integer total = userList.total;
                Integer totalPages = userList.totalPages;
                List<UserList.Datum> datumList = userList.data;
//                Toast.makeText(getApplicationContext(), text + " page\n" + total + " total\n" + totalPages + " totalPages\n", Toast.LENGTH_SHORT).show();

                for (UserList.Datum datum : datumList) {
//                    Toast.makeText(getApplicationContext(), "id : " + datum.id + " name: " + datum.first_name + " " + datum.last_name + " avatar: " + datum.avatar, Toast.LENGTH_SHORT).show();
                    list_users.setText("getListUsers()  datum.first_name=" + datum.first_name);
                }


            }

            @Override
            public void onFailure(Call<UserList> call, Throwable t) {
                call.cancel();
            }
        });
    }

    private void getRequiredVarification(APIInterface _apiInterface) {
        Call<RequiredVarification> call = _apiInterface.getListRequiredVarification();
        call.enqueue(new Callback<RequiredVarification>() {
            @Override
            public void onResponse(Call<RequiredVarification> call, Response<RequiredVarification> response) {


                Log.e(tag,response.code()+"");

                String displayResponse = "";

                RequiredVarification resource = response.body();
                Integer is_backoffice_varification_required = resource.is_backoffice_varification_required;
                Integer is_video_varification_required = resource.is_video_varification_required;



                displayResponse +=   " is_backoffice_varification_required=" + is_backoffice_varification_required + " \n is_video_varification_required=" + is_video_varification_required + "\n";



                responseText.setText(displayResponse);

            }

            @Override
            public void onFailure(Call<RequiredVarification> call, Throwable t) {
                call.cancel();
            }
        });
    }

    private void getListResources(APIInterface _apiInterface) {
        Call<MultipleResource> call = _apiInterface.doGetListResources();
        call.enqueue(new Callback<MultipleResource>() {
            @Override
            public void onResponse(Call<MultipleResource> call, Response<MultipleResource> response) {


                Log.d("TAG",response.code()+"");

                String displayResponse = "";

                MultipleResource resource = response.body();
                Integer text = resource.page;
                Integer total = resource.total;
                Integer totalPages = resource.totalPages;
                List<MultipleResource.Datum> datumList = resource.data;

                displayResponse += text + " Page\n" + total + " Total\n" + totalPages + " Total Pages\n";

                for (MultipleResource.Datum datum : datumList) {
                    displayResponse += datum.id + " " + datum.name + " " + datum.pantoneValue + " " + datum.year + "\n";
                }

                responseText.setText(displayResponse);

            }

            @Override
            public void onFailure(Call<MultipleResource> call, Throwable t) {
                call.cancel();
            }
        });
    }

    private void createNewUser(APIInterface _apiInterface) {
        User user = new User("morpheus aaa", "leader");
        Call<User> call1 = _apiInterface.createUser(user);
        call1.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user1 = response.body();
                //Toast.makeText(getApplicationContext(), user1.name + " " + user1.job + " " + user1.id + " " + user1.createdAt, Toast.LENGTH_SHORT).show();
                create_use.setText("createNewUser() name=" + user1.name + " job=" + user1.job);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                call.cancel();
            }
        });
    }
}
