package com.example.lenovo.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.DialerKeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity implements OnViewChangeListener, OnClickListener{
	private MyScrollLayout mScrollLayout;
	private LinearLayout[] mImageViews;
	private int mViewCount;
	private int mCurSel;


	private TextView set1;
	private TextView set2;
	private TextView set3;
	private TextView set4;
	private TextView set1Preview;
	private TextView set2Preview;
	private TextView set3Preview;

	private int set1Value=0;
	private int set2Value=0;

	private int set3Value=0;

	private int countSum=0;
	private TextView liaotian;
	private TextView faxian;
	private TextView tongxunlu;
    private TextView record;
	private boolean isOpen = false;

	public long getMlCount() {
		return mlCount;
	}

	private long mlCount = 0;
	private long mlCountTemp=0;


    public long getMlCount1() {
        return mlCount1;
    }

    private long mlCount1 = 0;

    private long mlTimerUnit = 10;

    private long mlTimerUnit1 = 10;

    public int getSec() {
        return sec;
    }

    public void setSec(int sec) {
        this.sec = sec;
    }

    public int getMillsec() {
        return millsec;
    }

    public void setMillsec(int millsec) {
        this.millsec = millsec;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min1) {
        this.min = min1;
    }

    private int min;
    private int sec;
    private int millsec;
	private  int yushu;
    private String FILE = "saveRecord";//用于保存SharedPreferences的文件
    private SharedPreferences sp = null;//声明一个SharedPreferences

	private String FILE1 = "saveSetting";//用于保存SharedPreferences的文件
	private SharedPreferences sp1 = null;//声明一个SharedPreferences

	private ListView listViewSetting;
    private TextView tvTime;
    private TextView tvTime1;
	private Button btnStartPause;
	private Button btnStartPause1;
	private Button btnStop;
	private Timer timer = null;
	private TimerTask task = null;
	private Timer timer1 = null;
	private TimerTask task1 = null;
	private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub

			if (countSum==set3Value){
				btnStartPause.setText("开始训练");

				AlertDialog.Builder alert=new AlertDialog.Builder(MainActivity.this);
				alert.setTitle("恭喜完成本次锻炼");
				alert.setMessage("可真是个运动天才");
				alert.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						mlCount=0;
						mlCountTemp=0;



					}
				});
				alert.show();
			}
			if (msg.arg1==1){
				mlCount=0;
				mlCountTemp=0;
				countSum=0;
			}



            switch(msg.what) {
                case 1:						//Toast.makeText(MainActivity.this,"aaaa",Toast.LENGTH_LONG).show();


					mlCountTemp++;
					if ((mlCount==(set1Value*100))&&(mlCountTemp!=((set1Value+set2Value)*100))){

						mlCount=(set1Value*100);

					}

					else
					{
						if (mlCountTemp==((set1Value+set2Value)*100)){
							mlCount=0;
							mlCountTemp=0;
							//Toast.makeText(MainActivity.this,"+++"+countSum,Toast.LENGTH_LONG).show();
							countSum++;
						}
						else {
							mlCount++;
						}


					}

                    int totalSec = 0;
                    int yushu;



                        // 100 millisecond
                        totalSec = (int)(mlCount / 100);
                        yushu = (int)(mlCount%100);
                        setMillsec(yushu);


                    // Set time display
                    int min = (totalSec / 60);

                    int sec = (totalSec % 60);
                    try{

                            // 100 millisecond
						if (countSum==set3Value){
							tvTime.setText(String.format("%1$02d:%2$02d:%3$02d", 0, 0, 0));

						}
						else {
							tvTime.setText(String.format("%1$02d:%2$02d:%3$02d", min, sec, yushu));

						}

                    } catch(Exception e) {
                        tvTime.setText("" + min + ":" + sec + ":" + yushu);
                        e.printStackTrace();
                        Log.e("MyTimer onCreate", "Format string error.");
                    }
                    break;

                default:
                    break;
            }

            super.handleMessage(msg);
        }

    };

	private Handler handler1 = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
            if (msg.arg1==1) {
                Dialog dialog = new AlertDialog.Builder(MainActivity.this).
                        setTitle("提示").
                        setMessage("要记录这次挑战吗？").
                        setPositiveButton("纪录", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {






									long j=sp.getLong("count",0);
                                    if (mlCount1>=sp.getLong("count", 0))
                                    {
                                        SharedPreferences.Editor edit = sp.edit();
										int sss=sec;
										int s=getMillsec();
                                        edit.putInt("min",getMin());
                                        edit.putInt("sec", getSec());
                                        edit.putInt("mill", getMillsec());
                                        edit.putLong("count", getMlCount1());
                                        edit.commit();
										record.setText("最高纪录:"+String.format("%1$02d:%2$02d:%3$02d", sp.getInt("min",0), sp.getInt("sec",0), sp.getInt("mill",0)));

									}

								mlCount1=0;

                      //Toast.makeText(MainActivity.this,"----"+sp.getInt("sec",0),Toast.LENGTH_LONG).show();



                            }
                        }).
                        setNegativeButton("不记录", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

								mlCount1=0;


                            }
                        }).show();
            }
			switch(msg.what) {
				case 1:
					mlCount1++;

					int totalSec = 0;
					



					// 100 millisecond
					totalSec = (int)(mlCount1 / 100);
					yushu = (int)(mlCount1%100);
					setMillsec(yushu);

					System.out.println("-----------"+millsec);
					// Set time display
					min = (totalSec / 60);
                    setMin(min);

					sec = (totalSec % 60);
					setSec(sec);

					try{

						// 100 millisecond
						tvTime1.setText(String.format("%1$02d:%2$02d:%3$02d", min, sec, yushu));

					} catch(Exception e) {
						tvTime1.setText("" + min + ":" + sec + ":" + yushu);
						e.printStackTrace();
						Log.e("MyTimer onCreate", "Format string error.");
					}
					break;

				default:
					break;
			}

			super.handleMessage(msg);
		}

	};
	private Handler handlerset1=new Handler(){
		@Override

		public void handleMessage(Message msg) {
			final EditText editText1=new EditText(MainActivity.this);

			editText1.setKeyListener(new DialerKeyListener());
			if (msg.arg1==1){
				AlertDialog.Builder alertSet1=new AlertDialog.Builder(MainActivity.this).
						setTitle("一次多长时间:").
						setView(editText1);
				alertSet1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {

						set1Value=Integer.parseInt(editText1.getText().toString().trim());
						set1Preview.setText(""+set1Value);

						SharedPreferences.Editor editor=sp1.edit();
						editor.putInt("oneTime",set1Value);
						editor.commit();

						//Toast.makeText(MainActivity.this,"---"+set1Value,Toast.LENGTH_LONG).show();
					}
				});
				alertSet1.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {

					}
				});
				alertSet1.show();
			}
			super.handleMessage(msg);
		}
	};
	private Handler handlerset2=new Handler(){
		@Override

		public void handleMessage(Message msg) {
			final EditText editText1=new EditText(MainActivity.this);

			editText1.setKeyListener(new DialerKeyListener());
			if (msg.arg1==1){
				AlertDialog.Builder alertSet1=new AlertDialog.Builder(MainActivity.this).
						setTitle("每次休息多长时间:").
						setView(editText1);
				alertSet1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						set2Value=Integer.parseInt(editText1.getText().toString().trim());
						set2Preview.setText(""+set2Value);

						SharedPreferences.Editor editor=sp1.edit();
						editor.putInt("oneSleep",set2Value);
						editor.commit();
					}
				});
				alertSet1.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {

					}
				});
				alertSet1.show();
			}
			super.handleMessage(msg);
		}
	};
	private Handler handlerset3=new Handler(){
		@Override

		public void handleMessage(Message msg) {
			final EditText editText1=new EditText(MainActivity.this);

			editText1.setKeyListener(new DialerKeyListener());
			if (msg.arg1==1){
				AlertDialog.Builder alertSet1=new AlertDialog.Builder(MainActivity.this).
						setTitle("一组多少次:").
						setView(editText1);
				alertSet1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						set3Value=Integer.parseInt(editText1.getText().toString().trim());
						set3Preview.setText(""+set3Value);
						SharedPreferences.Editor editor=sp1.edit();
						editor.putInt("cishu",set3Value);
						editor.commit();
					}
				});
				alertSet1.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {

					}
				});
				alertSet1.show();
			}
			super.handleMessage(msg);
		}
	};
	private Handler handlerset4 =new Handler(){
		@Override
		public void handleMessage(Message msg) {
			if (msg.arg1==1){
				Intent intent=new Intent(MainActivity.this,AuthorInfo.class);
				startActivity(intent);
			}
			super.handleMessage(msg);
		}
	};
    private Message msg = null;
    private Message msg1 = null;
	private boolean bIsRunningFlg = false;
    private boolean bIsRunningFlg1 = false;
	private static final String MYTIMER_TAG = "MYTIMER_LOG";

	// menu item




	//自定义的弹出框类
	SelectPicPopupWindow menuWindow; //弹出框
	SelectAddPopupWindow menuWindow2; //弹出框
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        sp = getSharedPreferences(FILE, MODE_PRIVATE);

		sp1=getSharedPreferences(FILE1,MODE_PRIVATE);
		init();
	}

	private void init()
	{
		liaotian = (TextView)findViewById(R.id.liaotian);
		faxian = (TextView)findViewById(R.id.faxian);
		tongxunlu = (TextView)findViewById(R.id.tongxunlu);
        record=(TextView)findViewById(R.id.textview1);


		set1=(TextView)findViewById(R.id.set1);
		set1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {

				Message messageSet1=Message.obtain();
				messageSet1.arg1=1;
				handlerset1.sendMessage(messageSet1);
			}
		});


		set2=(TextView)findViewById(R.id.set2);
		set1Preview=(TextView)findViewById(R.id.set1num);
		set2Preview=(TextView)findViewById(R.id.set2num);
		set3Preview=(TextView)findViewById(R.id.set3num);

		set2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {

				Message messageSet2=Message.obtain();
				messageSet2.arg1=1;
				handlerset2.sendMessage(messageSet2);
			}
		});


		set3=(TextView)findViewById(R.id.set3);
		set3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {

				Message messageSet3=Message.obtain();
				messageSet3.arg1=1;
				handlerset3.sendMessage(messageSet3);
			}
		});
		set4=(TextView)findViewById(R.id.set4);
		set4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Message messageSet4=Message.obtain();
				messageSet4.arg1=1;
				handlerset4.sendMessage(messageSet4);



			}
		});

		mScrollLayout = (MyScrollLayout) findViewById(R.id.ScrollLayout);
		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.lllayout);
		mViewCount = mScrollLayout.getChildCount();
		mImageViews = new LinearLayout[mViewCount];
		for(int i = 0; i < mViewCount; i++)    	{
			mImageViews[i] = (LinearLayout) linearLayout.getChildAt(i);
			mImageViews[i].setEnabled(true);
			mImageViews[i].setOnClickListener(this);
			mImageViews[i].setTag(i);
		}
		mCurSel = 0;
		mImageViews[mCurSel].setEnabled(false);
		mScrollLayout.SetOnViewChangeListener(this);


		tvTime = (TextView)findViewById(R.id.tvTime);
        tvTime1 = (TextView)findViewById(R.id.tvTime1);

        btnStartPause = (Button)findViewById(R.id.btnStartPaunse);
		btnStartPause1=(Button)findViewById(R.id.btnStartPaunse1);



        tvTime.setText(R.string.init_time_100millisecond);
		tvTime1.setText(R.string.init_time_100millisecond);
		new Thread(new loadSettingParam()).start();

		if (sp==null){
			SharedPreferences.Editor edit = sp.edit();
			edit.putInt("min", 0);
			edit.putInt("sec", 0);
			edit.putInt("mill", 0);
			edit.putLong("count", 0);

			edit.commit();
		}

		set1Preview.setText(""+sp1.getInt("oneTime",0));

		set2Preview.setText(""+sp1.getInt("oneSleep",0));
		set3Preview.setText(""+sp1.getInt("cishu",0));

		record.setText("最高纪录:" + String.format("%1$02d:%2$02d:%3$02d", sp.getInt("min", 0), sp.getInt("sec", 0), sp.getInt("mill", 0)));





        btnStartPause.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.i(MYTIMER_TAG, "Start/Pause is clicked.");

                if (null == timer) {
                    if (null == task) {
                        task = new TimerTask() {

                            @Override
                            public void run() {
                                // TODO Auto-generated method stub

								if (countSum!=set3Value){
									if (null == msg) {
										msg = new Message();
									} else {
										msg = Message.obtain();
									}
									msg.what = 1;
									handler.sendMessage(msg);
								}

                            }

                        };
                    }

                    timer = new Timer(true);


					timer.schedule(task, 0, mlTimerUnit); // set timer duration


                }

                // start
                if (!bIsRunningFlg) {
                    bIsRunningFlg = true;
                    btnStartPause.setText("停止锻炼");

                } else { // pause
                    try{
						if (countSum==set3Value){
							mlCount=0;
							mlCountTemp=0;
							countSum=0;
							btnStartPause.setText("停止锻炼");

						}
						else {
							bIsRunningFlg = false;
							task.cancel();
							task = null;
							timer.cancel(); // Cancel timer
							timer.purge();
							timer = null;
							handler.removeMessages(msg.what);
							btnStartPause.setText("重新开始");
							msg=Message.obtain();
							msg.arg1=1;
							handler.sendMessage(msg);
						}

                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }

        }
        });






		btnStartPause1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				//Log.i(MYTIMER_TAG, "Start/Pause is clicked.");

				if (null == timer1) {
					if (null == task1) {
						task1 = new TimerTask() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								if (null == msg1) {
									msg1 = new Message();
								} else {
									msg1 = Message.obtain();
								}
								msg1.what = 1;
								handler1.sendMessage(msg1);
							}

						};
					}

					timer1 = new Timer(true);
					timer1.schedule(task1, 0, mlTimerUnit); // set timer duration
				}

				// start
				if (!bIsRunningFlg1) {
					bIsRunningFlg1 = true;
					btnStartPause1.setText("结束本次挑战");



                } else { // pause
					try{

						bIsRunningFlg1 = false;
						task1.cancel();
                        task1 = null;
                        timer1.cancel(); // Cancel timer
						timer1.purge();
						timer1 = null;
						handler1.removeMessages(msg1.what);

						btnStartPause1.setText("重新开始挑战");
                        msg1=Message.obtain();
                        msg1.arg1=1;
                        handler1.sendMessage(msg1);

					} catch(Exception e) {
						e.printStackTrace();
					}
				}

			}
		});






    }



	public ArrayList<SettingItem> getSetting(){
		ArrayList<SettingItem> list=new ArrayList<SettingItem>();
		SettingItem s0=new SettingItem();
		s0.setSettingName("每组次数");
		s0.setSettingPic(R.mipmap.bind_mcontact_reco_friends+"");

		SettingItem s1=new SettingItem();
		s1.setSettingName("每次休息时间");
		s1.setSettingPic(R.mipmap.bind_mcontact_reco_friends+"");

		SettingItem s2=new SettingItem();
		s2.setSettingName("每次锻炼时间");
		s2.setSettingPic(R.mipmap.bind_mcontact_reco_friends+"");


		SettingItem s3=new SettingItem();
		s3.setSettingName("开发者信息");
		s3.setSettingPic(R.mipmap.bind_mcontact_reco_friends + "");

		SettingItem s4=new SettingItem();
		s4.setSettingName("给个好评");
		s4.setSettingPic(R.mipmap.bind_mcontact_reco_friends+"");


		return list;
	}



	private void setCurPoint(int index)
	{
		if (index < 0 || index > mViewCount - 1 || mCurSel == index){
			return ;
		}
		mImageViews[mCurSel].setEnabled(true);
		mImageViews[index].setEnabled(false);
		mCurSel = index;

		if(index == 0){
			liaotian.setTextColor(0xff228B22);
			faxian.setTextColor(Color.BLACK);
			tongxunlu.setTextColor(Color.BLACK);
		}else if(index==1){
			liaotian.setTextColor(Color.BLACK);
			faxian.setTextColor(0xff228B22);
			tongxunlu.setTextColor(Color.BLACK);
		}else{
			liaotian.setTextColor(Color.BLACK);
			faxian.setTextColor(Color.BLACK);
			tongxunlu.setTextColor(0xff228B22);
		}
	}

	@Override
	public void OnViewChange(int view) {
		// TODO Auto-generated method stub
		setCurPoint(view);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int pos = (Integer)(v.getTag());
		setCurPoint(pos);
		mScrollLayout.snapToScreen(pos);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_MENU)) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public class loadSettingParam implements Runnable{

		@Override
		public void run() {

			if (sp1==null){

				SharedPreferences.Editor editor=sp1.edit();
				editor.putInt("oneTime",3);
				editor.putInt("oneSleep",1);
				editor.putInt("cishu",3);
				editor.commit();
			}
			else {
				set1Value=sp1.getInt("oneTime",0);
				set2Value=sp1.getInt("oneSleep",0);
				set3Value=sp1.getInt("cishu",0);

			}



		}
	}

}
