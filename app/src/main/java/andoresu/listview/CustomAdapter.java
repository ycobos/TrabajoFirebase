package andoresu.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.Firebase;

/**
 * Created by dell on 04/04/2016.
 */
public class CustomAdapter extends BaseAdapter{

    private static final String TAG = "AS_ListView";
    private Context context;
    private String[] values;
    private String [] keys;
    Firebase miFireRef;

    public CustomAdapter(Context context, String[] values, String[] keys, Firebase miFireRef ){
        this.context = context;
        this.values = values;
        this.keys= keys;
        this.miFireRef= miFireRef;

    }

    @Override
    public int getCount() {
        return  values.length;
    }

    @Override
    public Object getItem(int position) {
        return values[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String s = values[position];
        final String key = keys[position];
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_row, null);
        }
        Button button = (Button) convertView.findViewById(R.id.btnRemove);
        button.setFocusableInTouchMode(false);
        button.setFocusable(false);
        button.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                miFireRef.child(key).removeValue();
            }
        });
        TextView textView = (TextView) convertView.findViewById(R.id.tvField1);
        textView.setText(s);
        convertView.setTag(s);
        return  convertView;
    }
}
