package com.hangzhou.love.lovemyself.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.hangzhou.love.lovemyself.R;


/**
 * Custom alert dialog
 * 
 * @author dujiliang
 */
public class MOPTextDialog {

	private Context context;
	private String title;
	private String content;
	private AlertDialog alertDialog;
	private OnClickListener confrimClickListener;
	private OnClickListener cancelClickListener;
	private boolean isText = true;
	private BaseAdapter baseAdapter;
	private OnItemClickListener onItemClickListener;
	private Button btn_confirm;
	private Button btn_cancel;
	private String confrimText;
	private String cancelText;
	private Window window;
	private FrameLayout frameLayout;
	private LinearLayout btnLayout ;

	/**
	 * Constructor
	 * 
	 * @param context
	 */
	public MOPTextDialog(Context context ) {
		this.context = context;
		alertDialog = new AlertDialog.Builder(this.context).create();
		alertDialog.setCanceledOnTouchOutside(false);
		alertDialog.setCancelable(false);
		alertDialog.show();
		alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
		window = alertDialog.getWindow();
		window.setContentView(R.layout.layout_alert_dialog);
		btn_confirm = (Button) window.findViewById(R.id.btn_confirm);
		btn_cancel = (Button) window.findViewById(R.id.btn_cancel);
		frameLayout = (FrameLayout) window.findViewById(R.id.alert_view);
		btnLayout =(LinearLayout) window.findViewById(R.id.btn_view);
	}
	
	/**
	 * Constructor
	 * 
	 * @param context
	 */
	public MOPTextDialog(Context context , boolean cancelAble , boolean cancelOntouchOutSide ) {
		this.context = context;
		alertDialog = new AlertDialog.Builder(this.context).create();
		alertDialog.setCanceledOnTouchOutside(cancelOntouchOutSide);
		alertDialog.setCancelable(cancelAble);
		alertDialog.show();
		alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
		window = alertDialog.getWindow();
		window.setContentView(R.layout.layout_alert_dialog);
		btn_confirm = (Button) window.findViewById(R.id.btn_confirm);
		btn_cancel = (Button) window.findViewById(R.id.btn_cancel);
		frameLayout = (FrameLayout) window.findViewById(R.id.alert_view);
		btnLayout =(LinearLayout) window.findViewById(R.id.btn_view);
	}
	
	/**
	 * set title for alert dialog
	 * 
	 * @param title
	 */
	public MOPTextDialog setTitle(String title) {
		this.title = title;
		return this;
	}

	/**
	 * set content for alert dialog
	 * 
	 * @param content
	 */
	public MOPTextDialog setContent(String text) {
		this.content = text;
		return this;
	}

	/**
	 * set list view for alert dialog
	 * 
	 * @param content
	 */
	public void setContent(BaseAdapter listAdapter,
			OnItemClickListener itemClickListener) {
		isText = false;
		this.baseAdapter = listAdapter;
		this.onItemClickListener = itemClickListener;
	}

	/**
	 * set title for alert dialog
	 * 
	 * @param titleId
	 */
	public void setTitle(int titleId) {
		this.title = context.getString(titleId);
	}

	/**
	 * set content for alert dialog
	 * 
	 * @param contentId
	 */
	public void setContent(int contentId) {
		this.content = context.getString(contentId);
	}

	public void show() {

		TextView tv_title = (TextView) window.findViewById(R.id.alert_title);
		TextView tv_content = (TextView) window.findViewById(R.id.alert_content);
		tv_title.setText(title);

		if (content==null) {
			tv_content.setVisibility(View.GONE);
		} else {
			tv_content.setVisibility(View.VISIBLE);
			tv_content.setText(content);
		}

	}
	/**
	 * The layout of button whether or not shown
	 * default :Visible
	 */
	public void setClickable(boolean able){
		if (!able) btnLayout.setVisibility(View.GONE);
	}

	/**
	 * The view of cancel whether or not shown
	 * default :Visible
	 */
	public void setCancelable(boolean able) {
		if (!able) btn_cancel.setVisibility(View.GONE);
	}
	
	/**
	 * The view of Confirm whether or not shown
	 * default :Visible
	 */
	public void setConfirmable(boolean able) {
		if (!able) btn_confirm.setVisibility(View.GONE);
	}
	
	/**
	 * set listener for the confirm button
	 * 
	 * @param cancelClickListener
	 */
	public void setCancelClickListener(String cancelText,
			OnClickListener cancelClickListener) {
		this.cancelText = cancelText;
		btn_cancel.setText(this.cancelText);
		this.cancelClickListener = cancelClickListener;
		btn_cancel.setOnClickListener(this.cancelClickListener);
	}

	/**
	 * set listener for the cancel button
	 * 
	 * @param confrimClickListener
	 */
	public void setConfirmClickListener(String confrimText,
			OnClickListener confrimClickListener) {
		this.confrimClickListener = confrimClickListener;
		this.confrimText = confrimText;
		btn_confirm.setText(this.confrimText);
		btn_confirm.setOnClickListener(this.confrimClickListener);
	}

	/**
	 * dismiss the alert dialog
	 */
	public void cancel() {
		alertDialog.cancel();
	}

	public void setView(View view) {
		frameLayout.addView(view);
	}
	
	public void setView(View view ,LayoutParams params) {
		frameLayout.addView(view ,params);
	}

	public void dismiss() {
		alertDialog.dismiss();
	}
}
