package com.mobiwebcode.schooltimetable;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class ChooseCountry1Activity extends Activity {
	public static String selectedcountry = "";
	ArrayList<String> countryList = new ArrayList<String>();
	String[] countryArray = new String[] { "Angola, Antigua & Deps",
			"Argentina, Armenia", "Australia", "Austria", "Azerbaijan",
			"Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus",
			"Belgium", "Belize", "Benin", "Bhutan", "Bolivia",
			"Bosnia Herzegovina", "Botswana", "Brazil", "Brunei", "Bulgaria",
			"Burkina", "Burma", "Burundi", "Cambodia", "Cameroon", "Canada",
			"Cape Verde", "Central African Rep", "Chad", "Chile",
			"People's Republic of China", "Republic of China", "Colombia",
			"Comoros", "Democratic Republic of the Congo",
			"Republic of the Congo", "Costa Rica", "Croatia", "Cuba", "Cyprus",
			"Czech Republic", "Danzig", "Denmark", "Djibouti", "Dominica",
			"Dominican Republic", "East Timor", "Ecuador", "Egypt",
			"El Salvador", "Equatorial Guinea", "Eritrea", "Estonia",
			"Ethiopia", "Fiji", "Finland", "France", "Gabon", "Gaza Strip",
			"The Gambia", "Georgia", "Germany", "Ghana", "Greece", "Grenada",
			"Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti",
			"Holy Roman Empire", "Honduras", "Hungary", "Iceland", "India",
			"Indonesia", "Iran", "Iraq", "Republic of Ireland", "Israel",
			"Italy", "Ivory Coast", "Jamaica", "Japan", "Jonathanland",
			"Jordan", "Kazakhstan", "Kenya", "Kiribati", "North Korea",
			"South Korea", "Kosovo", "Kuwait", "Kyrgyzstan", "Laos", "Latvia",
			"Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein",
			"Lithuania", "Luxembourg", "Macedonia", "Madagascar", " Malawi",
			"Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands",
			"Mauritania", "Mauritius", "Mexico", "Micronesia", "Moldova",
			"Monaco", "Mongolia", "Montenegro", "Morocco", "Mount Athos",
			"Mozambique", "Namibia", "Nauru", "Nepal", "Newfoundland",
			"Netherlands", "New Zealand", "Nicaragua", "Niger", "Nigeria",
			"Norway", "Oman", "Ottoman Empire", "Pakistan", "Palau", "Panama",
			"Papua New Guinea", "Paraguay", "Peru", "Philippines", "Poland",
			"Portugal", "Prussia", "Qatar", "Romania", "Rome",
			"Russian Federation", "Rwanda", "St Kitts & Nevis", "St Lucia",
			"Saint Vincent & the", "Grenadines", "Samoa", "San Marino",
			"Sao Tome & Principe", "Saudi Arabia", "Senegal", "Serbia",
			"Seychelles", "Sierra Leone", "Singapore", "Slovakia", "Slovenia",
			"Solomon Islands", "Somalia", "South Africa", "Spain", "Sri Lanka",
			"Sudan", "Suriname", "Swaziland", "Sweden", "Switzerland", "Syria",
			"Tajikistan", "Tanzania", "Thailand", "Togo", "Tonga",
			"Trinidad & Tobago", "Tunisia", "Turkey", "Turkmenistan", "Tuvalu",
			"Uganda", "Ukraine", "United Arab Emirates", "United Kingdom",
			"Uruguay", "Uzbekistan", " Vanuatu", "Vatican City", "Venezuela",
			"Vietnam", "Yemen", "Zambia", "Zimbabwe" };

	ListView countryListview;

	FrameLayout mainFrameLyout;
	FrameLayout.LayoutParams menuPanelParameters;
	FrameLayout.LayoutParams slidingPanelParameters;
	LinearLayout.LayoutParams headerPanelParameters;
	LinearLayout.LayoutParams listViewParameters;
	Button menuBtn;
	private DisplayMetrics metrics;
	private RelativeLayout slidingPanel;
	int panelWidth = 0;
	private boolean isExpanded;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choosecountry1_main);

		metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		panelWidth = (int) ((metrics.widthPixels) * 0.75);
		slidingPanel = (RelativeLayout) findViewById(R.id.mainRelativeLayout);
		slidingPanelParameters = (FrameLayout.LayoutParams) slidingPanel
				.getLayoutParams();
		slidingPanelParameters.width = metrics.widthPixels;
		slidingPanel.setLayoutParams(slidingPanelParameters);

		menuBtn = (Button) findViewById(R.id.menuBtn);
		menuBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (!isExpanded) {
					isExpanded = true;

					// Expand
					new ExpandAnimation(slidingPanel, panelWidth,
							Animation.RELATIVE_TO_SELF, 0.0f,
							Animation.RELATIVE_TO_SELF, 0.75f, 0, 0.0f, 0, 0.0f);
				} else {
					isExpanded = false;

					// Collapse
					new CollapseAnimation(slidingPanel, panelWidth,
							TranslateAnimation.RELATIVE_TO_SELF, 0.75f,
							TranslateAnimation.RELATIVE_TO_SELF, 0.0f, 0, 0.0f,
							0, 0.0f);

				}
			}
		});

		countryListview = (ListView) findViewById(R.id.countryListview);
		fillCountryList();
		countryListview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				selectedcountry = countryArray[arg2];
			}
		});

		Button btnsetuptimetable = (Button) findViewById(R.id.btn_setuptimetable);
		Button btngeneratetimetable = (Button) findViewById(R.id.btn_generatetimetable);
		Button btngradecalculate = (Button) findViewById(R.id.btn_gradecalculate);
		Button btntertiary = (Button) findViewById(R.id.btn_tertiary);
		Button btndefination = (Button) findViewById(R.id.btn_Definition);
		Button btnpasco = (Button) findViewById(R.id.btn_pasco);
		Button btniqtest = (Button) findViewById(R.id.btn_iqtest);
		Button btnedutainment = (Button) findViewById(R.id.btn_edutainment);
		Button btnchat = (Button) findViewById(R.id.btn_chat);

		btnsetuptimetable.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent myintent2 = new Intent(ChooseCountry1Activity.this,
						OptionActivity.class);
				startActivity(myintent2);

			}
		});

		btngradecalculate.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent myintent2 = new Intent(ChooseCountry1Activity.this,
						GradecalculatorActivity.class);
				startActivity(myintent2);

			}
		});

		btntertiary.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent myintent2 = new Intent(ChooseCountry1Activity.this,
						TertiaryinfoActivity.class);
				startActivity(myintent2);

			}
		});

		btndefination.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent myintent2 = new Intent(ChooseCountry1Activity.this,
						SelectsubjectDefination.class);
				startActivity(myintent2);

			}
		});

		btnpasco.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent myintent2 = new Intent(ChooseCountry1Activity.this,
						PascoActivity.class);
				startActivity(myintent2);

			}
		});

		btniqtest.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent myintent2 = new Intent(ChooseCountry1Activity.this,
						IQTestActivity.class);
				startActivity(myintent2);

			}
		});
		btnedutainment.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent myintent3 = new Intent(ChooseCountry1Activity.this,
						EdutainmentActivity.class);
				startActivity(myintent3);

			}
		});
		btnchat.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent myintent3 = new Intent(ChooseCountry1Activity.this,
						ChatActivity.class);
				startActivity(myintent3);

			}
		});

	}

	public void countrySelected(View view) {
		Intent intent = new Intent(ChooseCountry1Activity.this,
				MainOptionsActivity.class);
		startActivity(intent);
	}

	private void fillCountryList() {
		for (int i = 0; i < countryArray.length; i++) {
			countryList.add(countryArray[i]);
		}
		ArrayAdapter adapter = new ArrayAdapter<String>(this,
				R.layout.custom_textview, countryList);
		countryListview.setAdapter(adapter);

		Button buttoncountry = (Button) findViewById(R.id.selectCountryButton);

		buttoncountry.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				if (selectedcountry.equals("")) {

					final AlertDialog alertDialog = new AlertDialog.Builder(
							ChooseCountry1Activity.this).create();
					alertDialog.setIcon(R.drawable.ic_launcher);
					alertDialog.setTitle("SKUBAG");
					alertDialog.setMessage("Please choose your country");
					alertDialog.setButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {

									alertDialog.dismiss();
								}
							});
					alertDialog.show();
				} else {
					Intent myintent2 = new Intent(ChooseCountry1Activity.this,
							TertiaryinfoActivity.class);
					startActivity(myintent2);
				}
			}

		});
	}

}
