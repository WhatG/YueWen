package com.examplel.awesome_men.yuewen.Activitys;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.examplel.awesome_men.yuewen.R;
import com.examplel.awesome_men.yuewen.Utils.AppUtils;
import com.examplel.awesome_men.yuewen.YueWenApplication;
import com.squareup.picasso.Picasso;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;

/**
 * Created by longer on 2017/6/2.
 */

public class UserIconPickAct extends Activity {
    String[] icons;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usericon_pick);
        context = UserIconPickAct.this;
        String userIconS = "http://img1.2345.com/duoteimg/qqTxImg/2/22f754f685f77d2eb6fbff3bf57995bc.jpg\n" +
                        "http://img5q.duitang.com/uploads/blog/201402/08/20140208121557_xA284.thumb.224_0.jpeg\n" +
                        "http://www.zjboqin.cn/uploads/2017/bd11415262.jpg\n" +
                        "http://v1.qzone.cc/avatar/201303/18/17/14/5146daf314dfa660.jpg!180x180.jpg\n" +
                        "http://www.2cto.com/uploadfile/Collfiles/20161109/2016110910453815713.jpg\n" +
                        "http://www.2cto.com/uploadfile/Collfiles/20161109/2016110910450014986.jpg\n" +
                        "http://cdn.duitang.com/uploads/item/201601/13/20160113143016_Lnhwf.thumb.224_0.jpeg\n" +
                        "http://img.qq745.com/uploads/hzbimg/0923/katong110994.png\n" +
                        "http://img.qq745.com/uploads/allimg/170417/19-1F41G44640R9.PNG\n" +
                        "http://www.2cto.com/uploadfile/Collfiles/20161109/2016110910441014146.jpg\n" +
                        "http://img1.skqkw.cn:888/2014/11/07/14/kycwmzce1tk-18983.jpg\n" +
                        "http://www.qq745.com/uploads/allimg/140902/1-140Z2222133.jpg\n" +
                        "http://imgtu.5011.net/uploads/content/20170414/1582871492151790.jpg\n" +
                        "http://v1.qzone.cc/avatar/201506/28/08/23/558f3e8c47e03194.jpg%21200x200.jpg\n" +
                        "http://www.2cto.com/uploadfile/Collfiles/20161109/2016110910481717193.jpg\n" +
                        "http://img01.skqkw.cn:888/touxiang/2014/06/24/01t/20140624015936117528.jpg\n" +
                        "http://imgtu.5011.net/uploads/content/20170414/3989331492151790.jpg\n" +
                        "http://wenwen.soso.com/p/20110203/20110203235245-421180575.jpg\n" +
                        "http://k2.jsqq.net/uploads/allimg/1702/7_170220145332_3.jpg\n" +
                        "http://img.qqu.cc/uploads/allimg/151221/1-151221195417.jpg\n" +
                        "http://p.3761.com/pic/77011425954472.png\n" +
                        "http://tx.haiqq.com/uploads/allimg/160618/10531921W-2.jpg\n" +
                        "http://img.duote.com/qqTxImg/2011/08/12/13131401575496.jpg\n" +
                        "http://dynamic-image.yesky.com/600x-/uploadImages/upload/20140909/upload/201409/ivuanz4hgd5jpg.jpg\n" +
                        "http://imgtu.5011.net/uploads/content/20170330/6857111490864591.jpg\n" +
                        "http://www.feizl.com/upload2007/2011_08/110812233530953.jpg\n" +
                        "http://www.qq745.com/uploads/allimg/141018/1-14101Q20914.jpg\n" +
                        "http://img.duoziwang.com/2017/04/13/B0652.jpg\n" +
                        "http://scimg.jb51.net/touxiang/201704/2017042115130919.jpg\n" +
                        "http://k2.jsqq.net/uploads/allimg/17022016/7-1F2201413160-L.jpg\n" +
                        "http://scimg.jb51.net/touxiang/201704/2017041822412540.jpg\n" +
                        "http://wmtp.net/wp-content/uploads/2017/05/0502_dongman1116_16.jpeg\n" +
                        "http://www.2cto.com/uploadfile/Collfiles/20161109/2016110910444314668.jpg\n" +
                        "http://wmtp.net/wp-content/uploads/2017/05/0502_dongman1116_9.jpeg\n" +
                        "http://img2.imgtn.bdimg.com/it/u=954557968,1740427846&fm=11&gp=0.jpg\n" +
                        "http://www.2cto.com/uploadfile/Collfiles/20161109/2016110910444414689.jpg\n" +
                        "http://img1.3lian.com/2015/gif/w3/2/1.jpg\n" +
                        "http://scimg.jb51.net/touxiang/201704/2017041417075687.jpg\n" +
                "http://www.zjboqin.cn/uploads/2017/bd119134587.jpg\n" +
                "http://www.qq1234.org/uploads/allimg/150616/8_150616162634_1.jpg\n";
        icons = userIconS.split("\n");
        GridView gridView = (GridView) findViewById(R.id.usericon_grid);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("url",icons[position]);
                UserIconPickAct.this.setResult(Activity.RESULT_OK,intent);
                UserIconPickAct.this.finish();
            }
        });
        gridView.setAdapter(new GridAdapter());
    }

    class GridAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return icons.length;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView = new ImageView(UserIconPickAct.this);
            imageView.setBackgroundResource(R.drawable.roundrect);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(AppUtils.getRawSize(context,80),AppUtils.getRawSize(context,80));
            imageView.setPadding(10,10,10,10);
            params.setMargins(10,10,10,10);
            imageView.setLayoutParams(params);
            Picasso.with(UserIconPickAct.this).load(icons[position]).into(imageView);
            return imageView;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
    }
}
