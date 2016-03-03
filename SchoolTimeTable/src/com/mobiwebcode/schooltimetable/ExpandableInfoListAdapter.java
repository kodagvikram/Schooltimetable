package com.mobiwebcode.schooltimetable;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.mobiwebcode.schooltimetable.VO.DefinationVo;
import com.mobiwebcode.schooltimetable.VO.SchoolinfoVo;

public class ExpandableInfoListAdapter extends BaseExpandableListAdapter {
	  private Context _context;

	    private ArrayList<SchoolinfoVo> infolist; // header titles
	    // child data in format of header title, child title
	 
	    public ExpandableInfoListAdapter(Context context, ArrayList<SchoolinfoVo> infolist) {
	        this._context = context;
	        this.infolist = infolist;
	    }
	 
	    @Override
	    public Object getChild(int groupPosition, int childPosititon) {
	    	SchoolinfoVo sVo = infolist.get(groupPosition);
	        return sVo.infodescription;
	    }
	 
	    @Override
	    public long getChildId(int groupPosition, int childPosition) {
	        return childPosition;
	    }
	 
	    @Override
	    public View getChildView(int groupPosition, final int childPosition,
	            boolean isLastChild, View convertView, ViewGroup parent) {
	 
	        final String childText = (String) getChild(groupPosition, childPosition);
	 
	        if (convertView == null) {
	            LayoutInflater infalInflater = (LayoutInflater) this._context
	                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            convertView = infalInflater.inflate(R.layout.expandable_list_item, null);
	        }
	 
	        TextView txtListChild = (TextView) convertView
	                .findViewById(R.id.lblListItem);
	 
	        txtListChild.setText(childText);
	        return convertView;
	    }
	 
	    @Override
	    public int getChildrenCount(int groupPosition) {
	        return 1;
	    }
	 
	    @Override
	    public Object getGroup(int groupPosition) {
	    	SchoolinfoVo sVo= infolist.get(groupPosition);
	        return sVo.infoheading;
	    }
	    
	 
	    @Override
	    public int getGroupCount() {
	        return this.infolist.size();
	    }
	 
	    @Override
	    public long getGroupId(int groupPosition) {
	        return groupPosition;
	    }
	 
	    @Override
	    public View getGroupView(int groupPosition, boolean isExpanded,
	            View convertView, ViewGroup parent) {
	        String headerTitle = (String) getGroup(groupPosition);
	        if (convertView == null) {
	            LayoutInflater infalInflater = (LayoutInflater) this._context
	                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            convertView = infalInflater.inflate(R.layout.expandable_list_group, null);
	        }
	 
	        TextView lblListHeader = (TextView) convertView
	                .findViewById(R.id.lblListHeader);
	        lblListHeader.setTypeface(null, Typeface.BOLD);
	        lblListHeader.setText(headerTitle);
	 
	        return convertView;
	    }
	 
	    @Override
	    public boolean hasStableIds() {
	        return false;
	    }
	 
	    @Override
	    public boolean isChildSelectable(int groupPosition, int childPosition) {
	        return true;
	    }
}
