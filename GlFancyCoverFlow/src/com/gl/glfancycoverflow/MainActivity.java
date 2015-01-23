package com.gl.glfancycoverflow;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import at.technikum.mti.fancycoverflow.FancyCoverFlow;

import com.gl.glfancycoverflow.model.FilmInfo;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * @author LittleLiByte
 * 
 */
public class MainActivity extends Activity {

	DisplayImageOptions options;

	private ImageLoader imageLoader;

	private FancyCoverFlow fancyCoverFlow;

	private List<FilmInfo> filmList;

	private ImageAdapter adapter;


	private int cur_index = 0;
	private int count_drawble;
	private static int MSG_UPDATE = 1;
	// 定时任务
	private ScheduledExecutorService scheduledExecutorService;

	// 通过handler来更新主界面
	private Handler handler = new Handler() {

		public void handleMessage(Message msg) {
			if (msg.what == MSG_UPDATE) {
				fancyCoverFlow.setSelection(cur_index);
			}
		}
	};

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		filmList = FilmInfoTest.getfilmInfo();
		// 配置option
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.logo)
				.showImageForEmptyUri(R.drawable.logo)
				.showImageOnFail(R.drawable.ic_error).cacheInMemory(true)
				.cacheOnDisc(true).bitmapConfig(Bitmap.Config.RGB_565).build();

		imageLoader = ImageLoader.getInstance();

		adapter = new ImageAdapter(this, filmList, options, imageLoader);

		fancyCoverFlow = (FancyCoverFlow) findViewById(R.id.fancyCoverFlow);

		// item之间的间隙可以近似认为是imageview的宽度与缩放比例的乘积的一半
		fancyCoverFlow.setSpacing(-72);
		fancyCoverFlow.setAdapter(adapter);
		fancyCoverFlow.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				cur_index = position;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});

		// 点击事件
		fancyCoverFlow.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Toast.makeText(MainActivity.this,
						filmList.get(position % filmList.size()).getFilmName(),
						0).show();
			}
		});

		// 开启自动轮播
		count_drawble = adapter.getCount();
		startPlay();
	}

	/**
	 * 开始轮播图切换
	 */
	private void startPlay() {
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		scheduledExecutorService.scheduleAtFixedRate(new AutoPlayTask(), 1, 4,
				TimeUnit.SECONDS);
	}

	/**
	 * 停止轮播图切换
	 */
	private void stopPlay() {
		scheduledExecutorService.shutdown();
	}

	/**
	 * 执行轮播图切换任务
	 * 
	 */
	private class AutoPlayTask implements Runnable {

		@Override
		public void run() {

			cur_index = cur_index % count_drawble; // 图片区间[0,count_drawable)
			Message msg = handler.obtainMessage(MSG_UPDATE);
			handler.sendMessage(msg);
			cur_index++;
		}

	}

	@Override
	protected void onStop() {
		imageLoader.stop();
		super.onStop();
	}

}