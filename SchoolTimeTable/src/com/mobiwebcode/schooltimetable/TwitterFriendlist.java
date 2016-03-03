package com.mobiwebcode.schooltimetable;

import java.util.ArrayList;

import twitter4j.DirectMessage;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import android.app.Activity;
import android.net.wifi.WifiConfiguration.Status;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import com.mobiwebcode.schooltimetable.VO.FriendlistVo;

public class TwitterFriendlist extends Activity implements OnItemClickListener {
	ListView listView;
	CustomArrayList adapter;
	public static String selectedfriends = "";
	String message = "Please check out SKUBAGS app at SKUBAGS.COM!!!";
	DirectMessage tmessage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.twitterlistview);
		listView = (ListView) findViewById(R.id.listView);
		listView.setItemsCanFocus(false);
		listView.setTextFilterEnabled(true);
		Button sendmsg = (Button) findViewById(R.id.btn_Select);

		sendmsg.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				selectedfriends = new String();

				for (int i = 0; i < Invite.list.size(); i++)

				{
					if (adapter.mCheckStates.get(i) == true) {
						FriendlistVo jsVo = (FriendlistVo) Invite.list.get(i);

						selectedfriends = jsVo.screenname;

					}
					Invite.list.remove(i);
				}

				// Twitter twitter = TwitterFactory.getSingleton();
				// Status status = null;
				// try {
				// status = (Status) twitter.updateStatus(message);
				// } catch (Exception e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
				// System.out.println("Successfully updated the status to ["
				// + ((DirectMessage) status).getText() + "].");
				// TwitterFactory tf = new
				// TwitterFactory(Invite.builder.build());
				// Twitter sender = new TwitterFactory().getSingleton();
				// try {
				// tmessage = sender.sendDirectMessage(selectedfriends,
				// message);
				// System.out.println("Direct message successfully sent to "
				// + tmessage.getSender());
				//
				// } catch (Exception te) {
				// te.printStackTrace();
				// System.out.println("Failed to send a direct message: "
				// + te.getMessage());
				//
				// }

				ConfigurationBuilder cb = new ConfigurationBuilder();
				cb.setDebugEnabled(true)
						.setOAuthConsumerKey(Invite.consumerKey)
						.setOAuthConsumerSecret(Invite.consumerSecret)
						.setOAuthAccessToken(Invite.OAuthAccessToken)
						.setOAuthAccessTokenSecret(
								Invite.OAuthAccessTokenSecret);

				TwitterFactory tf = new TwitterFactory(cb.build());
				Twitter sender = tf.getInstance();

				// Twitter sender = TwitterFactory.getSingleton();

				try {
					tmessage = sender.sendDirectMessage(selectedfriends,
							"Check out SKU.BAGS App on google play");
					System.out.println("\n message is sent");
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();

				} catch (TwitterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// System.out.println("Sent: " + " to @" + message.getId());

			}
		});

		adapter = new CustomArrayList(TwitterFriendlist.this, Invite.list);

		listView.setAdapter(adapter);

	}

	public class CustomArrayList extends BaseAdapter implements
			CompoundButton.OnCheckedChangeListener {
		private final Activity context;

		ArrayList<FriendlistVo> adapterTimetableList;
		TextView followername;
		CheckBox checkbox;
		public SparseBooleanArray mCheckStates;

		CustomArrayList(Activity context,
				ArrayList<FriendlistVo> mainTimetableList_) {
			mCheckStates = new SparseBooleanArray(mainTimetableList_.size());
			this.context = context;
			adapterTimetableList = mainTimetableList_;
		}

		@Override
		public View getView(final int position, final View view,
				ViewGroup parent) {
			LayoutInflater inflater = context.getLayoutInflater();
			View rowView = inflater.inflate(R.layout.simple_list_item_1, null,
					true);
			FriendlistVo jsVo = Invite.list.get(position);
			followername = (TextView) rowView.findViewById(R.id.followername);
			followername.setText(jsVo.username);
			checkbox = (CheckBox) rowView.findViewById(R.id.checkBox1);
			checkbox.setTag(position);
			checkbox.setChecked(mCheckStates.get(position, false));
			checkbox.setOnCheckedChangeListener(this);

			return rowView;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return adapterTimetableList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		public boolean isChecked(int position) {
			return mCheckStates.get(position, false);
		}

		public void setChecked(int position, boolean isChecked) {
			mCheckStates.put(position, isChecked);
		}

		public void toggle(int position) {
			setChecked(position, !isChecked(position));
		}

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			// TODO Auto-generated method stub
			mCheckStates.put((Integer) buttonView.getTag(), isChecked);

		}

		public void setmCheckStates(SparseBooleanArray mCheckStates) {
			// TODO Auto-generated method stub
			this.mCheckStates = mCheckStates;

		}

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		adapter.toggle(arg2);

	}
}
