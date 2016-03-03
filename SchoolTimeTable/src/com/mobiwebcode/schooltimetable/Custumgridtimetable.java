package com.mobiwebcode.schooltimetable;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
public class Custumgridtimetable extends BaseAdapter{
    private Context mContext;
    private final String[] web;
    private final int[] Imageid;
      public Custumgridtimetable(Context c,String[] web,int[] Imageid ) {
          mContext = c;
          this.Imageid = Imageid;
          this.web = web;
      }
    @Override
    public int getCount() {
      // TODO Auto-generated method stub
      return web.length;
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
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      // TODO Auto-generated method stub
      View grid;
      LayoutInflater inflater = (LayoutInflater) mContext
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
          if (convertView == null) {
            grid = new View(mContext);
            grid = inflater.inflate(R.layout.custum_texttimetable, null);
            TextView textdate = (TextView) grid.findViewById(R.id.textdate);
            TextView texttime = (TextView) grid.findViewById(R.id.texttime);
            TextView texthrs = (TextView) grid.findViewById(R.id.texthrs);
            TextView layoutdesc = (TextView) grid.findViewById(R.id.layoutdesc);
            TextView textid = (TextView) grid.findViewById(R.id.textid);
            TextView textsubject = (TextView) grid.findViewById(R.id.textsubject);
          
            textdate.setText("2edf2e2");
            texttime.setText(web[position]);
            texthrs.setText(web[position]);
            layoutdesc.setText(web[position]);
            textid.setText(web[position]);
            textsubject.setText(web[position]);
        
          } else {
            grid = (View) convertView;
          }
      return grid;
    }
}