package com.mobiwebcode.schooltimetable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.mobiwebcode.schooltimetable.VO.DefinationVo;
import com.mobiwebcode.schooltimetable.VO.DefinitionListVO;
import com.mobiwebcode.schooltimetable.VO.Topic_SubjectVO;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import app.MWC.AsyncImageload.BitmapImageLoader;
import app.MWC.AsyncImageload.ImageLoader;
import app.tabsample.SmartImageView.NormalSmartImageView;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
	
//	static class ViewHolder {
//		  ImageView imageview;
//		  TextView txtListChild; 
//		 }
	
	BitmapImageLoader imageLoader;
	
	  private Context _context;
	    private ArrayList<DefinitionListVO> definationList; // header titles
	    // child data in format of header title, child title
	 
	    public ExpandableListAdapter(Context context, ArrayList<DefinitionListVO> definationList) {
	        this._context = context;
	        this.definationList = definationList;
	        imageLoader=new BitmapImageLoader(this._context);
	    }
	 
	    @Override
	    public Object getChild(int groupPosition, int childPosititon) {
	    	DefinitionListVO dVo = definationList.get(groupPosition);
	        return dVo.def_expl;
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
//	        ViewHolder viewHolder = new ViewHolder();
//	        viewHolder.imageview = (ImageView) convertView.findViewById(R.id.rowImageView);
//	        viewHolder.txtListChild = (TextView) convertView.findViewById(R.id.rowTextView);
//	        convertView.setTag(viewHolder); 
	        DefinitionListVO dVO =definationList.get(childPosition);
	       ImageView imageview = (ImageView)convertView.findViewById(R.id.image);
	       ProgressBar myProgressBar=(ProgressBar)convertView.findViewById(R.id.progressBar1);
			
	       if(!dVO.imagename.equalsIgnoreCase(""))
	       {
	    	   myProgressBar.setVisibility(View.VISIBLE);
	    	   imageLoader.DisplayImage(dVO.imagename, imageview, myProgressBar);
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
	    	DefinitionListVO dVo= definationList.get(groupPosition);
	        return dVo.def_name;
	    }
	    @Override
	    public int getGroupCount() {
	        return this.definationList.size();
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


