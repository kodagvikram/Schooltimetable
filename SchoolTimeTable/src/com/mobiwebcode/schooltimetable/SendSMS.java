package com.mobiwebcode.schooltimetable;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SendSMS extends Activity implements OnItemClickListener {

	List<String> name1 = new ArrayList<String>();
	List<String> phno1 = new ArrayList<String>();
	MyAdapter mAdapter;
	Button sendsms;
	String selectedcontact = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sendsms);

		getAllContacts(this.getContentResolver());
		ListView lv = (ListView) findViewById(R.id.listView1);
		mAdapter = new MyAdapter();
		lv.setAdapter(mAdapter);
		lv.setOnItemClickListener(this);
		lv.setItemsCanFocus(false);
		lv.setTextFilterEnabled(true);
		// adding
		sendsms = (Button) findViewById(R.id.sendsms);
		sendsms.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				StringBuilder checkedcontacts = new StringBuilder();

				selectedcontact = "";
				for (int i = 0; i < name1.size(); i++)

				{
					if (mAdapter.getmCheckStates().get(i) == true) {
						checkedcontacts.append(name1.get(i).toString());
						checkedcontacts.append("\n");
						checkedcontacts.append(phno1.get(i).toString());
						checkedcontacts.append("\n");
						selectedcontact = selectedcontact + ";" + phno1.get(i);

					} else {

					}

				}
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.putExtra("address", selectedcontact);
				intent.putExtra("sms_body",
						"Please checkout SKUBAG app at www.SKU_BAGS.com");
				intent.setType("vnd.android-dir/mms-sms");
				startActivity(intent);
				Toast.makeText(SendSMS.this, checkedcontacts, 1000).show();
			}
		});

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		mAdapter.toggle(arg2);
	}

	public void getAllContacts(ContentResolver cr) {

		Cursor phones = cr.query(
				ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null,
				null, null);
		while (phones.moveToNext()) {
			String name = phones
					.getString(phones
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
			String phoneNumber = phones
					.getString(phones
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
			name1.add(name);
			phno1.add(phoneNumber);
		}

		phones.close();
	}

	class MyAdapter extends BaseAdapter implements
			CompoundButton.OnCheckedChangeListener {
		private SparseBooleanArray mCheckStates;
		LayoutInflater mInflater;
		TextView tv1, tv;
		CheckBox cb;

		MyAdapter() {
			setmCheckStates(new SparseBooleanArray(name1.size()));
			mInflater = (LayoutInflater) SendSMS.this
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return name1.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub

			return 0;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			// TODO Auto-generated method stub
			View vi = convertView;
			if (convertView == null)
				vi = mInflater.inflate(R.layout.customlistview_contacts, null);
			tv = (TextView) vi.findViewById(R.id.textView1);
			tv1 = (TextView) vi.findViewById(R.id.textView2);
			cb = (CheckBox) vi.findViewById(R.id.checkBox1);
			tv.setText("Name :" + name1.get(position));
			tv1.setText("Phone No :" + phno1.get(position));
			cb.setTag(position);
			cb.setChecked(getmCheckStates().get(position, false));
			cb.setOnCheckedChangeListener(this);

			return vi;
		}

		public boolean isChecked(int position) {
			return getmCheckStates().get(position, false);
		}

		public void setChecked(int position, boolean isChecked) {
			getmCheckStates().put(position, isChecked);
		}

		public void toggle(int position) {
			setChecked(position, !isChecked(position));
		}

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			// TODO Auto-generated method stub

			getmCheckStates().put((Integer) buttonView.getTag(), isChecked);
		}

		public SparseBooleanArray getmCheckStates() {
			return mCheckStates;
		}

		public void setmCheckStates(SparseBooleanArray mCheckStates) {
			this.mCheckStates = mCheckStates;
		}
	}
}
