package csebd.yousuf.myphonebook;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


/**
 * Created by Yousuf on 3/9/2016.
 */
public class PhonebookAdapter extends ArrayAdapter<Phonebook> {

    private Context context;

    public PhonebookAdapter(Context context, int resource, List<Phonebook> objects) {
        super(context, 0, objects);
        this.context = context;
    }

    private static class ViewHolder {
        TextView tvMainName;
        TextView tvMainPhone;
        ImageView ivMainImage;
        TextView tvMainText;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder viewHolder = new ViewHolder();
        if (row == null ) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.row_item, parent, false);
            viewHolder.tvMainName = (TextView) row.findViewById(R.id.tvMainName);
            viewHolder.tvMainPhone = (TextView) row.findViewById(R.id.tvMainPhone);
            viewHolder.tvMainText = (TextView) row.findViewById(R.id.tvMainText);

            /*if(viewHolder.ivMainImage != null) {
                ((BitmapDrawable)viewHolder.ivMainImage.getDrawable()).getBitmap().recycle();
            }*/

            viewHolder.ivMainImage = (ImageView) row.findViewById(R.id.ivMainImage);
            row.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) row.getTag();
        }
        Phonebook phonebook = getItem(position);
        viewHolder.tvMainName.setText(phonebook.getName());
        viewHolder.tvMainPhone.setText(phonebook.getPhone());
        if (phonebook.getImagePath().length() == 1) {
            viewHolder.tvMainText.setText(phonebook.getImagePath().toString());
        } else {
            viewHolder.ivMainImage.setImageBitmap(BitmapFactory.decodeFile(phonebook.getImagePath()));
            viewHolder.tvMainText.setVisibility(View.INVISIBLE);
        }

        return row;
    }
}
