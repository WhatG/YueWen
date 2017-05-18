package com.examplel.awesome_men.yuewen.Activitys;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.examplel.awesome_men.yuewen.CusViews.TabButton;
import com.examplel.awesome_men.yuewen.Fragments.ArticalFragment;
import com.examplel.awesome_men.yuewen.Fragments.BookFragment;
import com.examplel.awesome_men.yuewen.Fragments.ContactFragment;
import com.examplel.awesome_men.yuewen.Fragments.MineFragment;
import com.examplel.awesome_men.yuewen.R;

public class MainActivity extends AppCompatActivity {
    private String TAG = "MainActivity";
    private int currentFragment =0;
    int[] buttonIndexs = {
            R.id.tab1,
            R.id.tab2,
            R.id.tab3,
            R.id.tab4,
    };
    int[] buttonImg = {
            R.drawable.ic_artical,
            R.drawable.ic_book,
            R.drawable.ic_message,
            R.drawable.ic_person1
    };
    int[] buttonImgPress = {
            R.drawable.articalp,
            R.drawable.booksp,
            R.drawable.personsp,
            R.drawable.mep
    };
    private String[] buttonNames = {"文章","书籍","联系","我"};
    private TabButton[] bottomButtons = new TabButton[4];


    private ArticalFragment af;
    private BookFragment bf;
    private ContactFragment cf;
    private MineFragment mf;

    private TabButton.OnTabClickListener bottomTabListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomTabListener = new TabButton.OnTabClickListener() {
            @Override
            public void onTabClick(int index) {
                if(index!=-1&&currentFragment==index){
                    return;
                }
                bottomButtons[currentFragment].clearSelected();
                bottomButtons[index].setSelected();



                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction fts = fm.beginTransaction();

                switch(index){
                    case 0:
                        if(af==null){
                            af = new ArticalFragment();
                        }
                        fts.replace(R.id.home_content,af);
                        fts.commit();
                        break;
                    case 1:
                        if(bf==null){
                            bf = new BookFragment();
                        }
                        fts.replace(R.id.home_content,bf);
                        fts.commit();
                        break;
                    case 2:
                        if(cf==null){
                            cf = new ContactFragment();
                        }
                        fts.replace(R.id.home_content,cf);
                        fts.commit();
                        break;
                    case 3:
                        if(mf==null){
                            mf = new MineFragment();
                        }
                        fts.replace(R.id.home_content,mf);
                        fts.commit();
                        break;
                }
                currentFragment = index;
            }
        };

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fts = fm.beginTransaction();
        fts.replace(R.id.home_content,new ArticalFragment());
        fts.commit();
        initTab();
    }

    private void initTab(){
        for(int i = 0;i<buttonIndexs.length;i++){
            final TabButton tabButton = (TabButton) findViewById(buttonIndexs[i]);
            tabButton.setIndex(i);
            tabButton.setContent(buttonNames[i],buttonImg[i],buttonImgPress[i]);
            tabButton.setOnTabClickListener(bottomTabListener);
            bottomButtons[i] = tabButton;
            if(i==0){
                bottomButtons[i].setSelected();
            }
        }
    }
}
