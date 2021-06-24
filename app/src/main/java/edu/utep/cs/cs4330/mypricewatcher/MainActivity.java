//Cesar Lopez - CS4330
//Added price finding
//Added wifi check

package edu.utep.cs.cs4330.mypricewatcher;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{
    //Database
    private DatabaseHelper dbHelper;
    //main view
    private EditText name;
    private EditText url;
    private Button add;
    private Button remove;
    private ListView lv;
    //Item adapter
    private ItemAdapter ItemAdapter;
    //input_box view
    private EditText et;
    private Button bt;
    //price finder for data
    private PriceFinder PF;
    //Store values, used to help later in the code.
    private View clicked;
    private int x;
    private String oldName;
    private String oldURL;
    WifiChecker wf = new WifiChecker();

    //here all views are placed into appropriate variables.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        add = findViewById(R.id.addButton);
        add.setOnClickListener(this::addClicked);
        remove = findViewById(R.id.removeButton);
        remove.setOnClickListener(this::removeClicked);
        dbHelper = new DatabaseHelper(this);
        ItemAdapter = new ItemAdapter(this, R.layout.item, dbHelper.allItems());
        ItemAdapter.setItemClikListener(item->dbHelper.update(item));
        lv = findViewById(R.id.listView);
        lv.setAdapter(ItemAdapter);
        PF = new PriceFinder();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //here we save the view that is clicked, the oldName of the view, the old url of the view
            //one will be changed if the option is selected on the popup menu
            //popup menu opens last, we save the info so we know which item was selected form the list
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                clicked = view;
                x = i;
                oldName = ItemAdapter.getName(i);
                oldURL = ItemAdapter.getURL(i);
                showPopup(view);
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intent = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(wf.wifiReceiver,intent);

    }

    //this method displays the input box, this happens after the change Name or change URL menu options
    //are selected
    public void showInputBox(String text, int index, String mode){
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.setTitle("Input Box");
        dialog.setContentView(R.layout.input_box);
        TextView message = (TextView)dialog.findViewById(R.id.txt);
        message.setText("Change "+mode);
        et = (EditText)dialog.findViewById(R.id.input);
        et.setText(text);
        bt = (Button)dialog.findViewById(R.id.button);
        //once string is ready to submit, this button checks if clicked and updates
        bt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ItemAdapter.set(index, et.getText().toString(), clicked, mode);
                ItemAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    //shows up after a view is selected, shows popup menu
    public void showPopup(View v){
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.popup_menu);
        popup.show();
    }

    //switch statement checks which option selected, then does according to the option
    @Override
    public boolean onMenuItemClick(MenuItem item){
        switch(item.getItemId()){
            case R.id.View:
                Item item2 = ItemAdapter.getItem(x);
                Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                String URL = item2.URL();
                intent.putExtra("url", URL);
                startActivity(intent);
                return true;
            case R.id.Change:
                showInputBox(oldName, x, "Name");
                return true;
            case R.id.Change2:
                showInputBox(oldURL, x, "URL");
                return true;
            case R.id.Update:
                try {
                    Item item3 = ItemAdapter.getItem(x);
                    String URL2 = item3.URL();
                    getPrice gp = new getPrice();
                    gp.execute(URL2);
                    String NPrice = gp.get();
                    ItemAdapter.set(x, NPrice, clicked, "Update");
                    ItemAdapter.notifyDataSetChanged();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                catch (ExecutionException e) {
                    e.printStackTrace();
                }
                return true;
            case R.id.Delete:
                Item item3 = ItemAdapter.getItem(x);
                ItemAdapter.remove(item3);
                return true;
            default:
                return false;
        }
    }

    //if add button is clicked, update listView to add a todoitem. adapter is used to update. Code
    //from powerpoints
    private void addClicked(View view){
        try {
            name = findViewById(R.id.ItemEdit);
            String Name = name.getText().toString();
            url = findViewById(R.id.ItemEditURL);
            String URL = url.getText().toString();
            getPrice gp = new getPrice();
            gp.execute(URL);
            String Price = gp.get();
            String NPrice = gp.get();
            String Percent = String.format("Change: %.2f", percent(Price, NPrice));
            Item item = new Item(Name, URL, Price, NPrice, Percent);
            dbHelper.addItem(item);
            ItemAdapter.add(item);
            ItemAdapter.notifyDataSetChanged();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    //if remove button is clicked, all todoitems are removed. Code from powerpoints
    private void removeClicked(View view){
        dbHelper.deleteAll();
        ItemAdapter.clear();
        ItemAdapter.notifyDataSetChanged();
    }

    //Percent based of price change
    public double percent(String originalPrice, String newPrice){
        return (((Double.parseDouble(originalPrice)/Double.parseDouble(newPrice))*100)-100);
    }
}