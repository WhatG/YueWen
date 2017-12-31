package com.examplel.awesome_men.yuewen.Activitys;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.examplel.awesome_men.yuewen.Adapters.ConversationListAdapterEx;
import com.examplel.awesome_men.yuewen.CusViews.TabButton;
import com.examplel.awesome_men.yuewen.Fragments.ArticalFragment;
import com.examplel.awesome_men.yuewen.Fragments.BookFragment;
import com.examplel.awesome_men.yuewen.Fragments.ContactFragment;
import com.examplel.awesome_men.yuewen.Fragments.MineFragment;
import com.examplel.awesome_men.yuewen.R;

import io.rong.imkit.RongContext;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;

public class MainActivity extends AppCompatActivity {
    private String TAG = "MainActivity";
    private int currentFragment =0;
    private Conversation.ConversationType[] mConversationsTypes = null;
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
            R.drawable.ic_person
    };
    int[] buttonImgPress = {
            R.drawable.ic_artical_s,
            R.drawable.ic_book_s,
            R.drawable.ic_message_s,
            R.drawable.ic_person_s
    };
    private String[] buttonNames = {"文章","书籍","联系","我"};
    private TabButton[] bottomButtons = new TabButton[4];


    private ArticalFragment af;
    private BookFragment bf;
    private ContactFragment cf;
//    private ConversationListFragment cf;
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
                hideFragments(fts);

                switch(index){
                    case 0:
                        if(af==null){
                            af = new ArticalFragment();
                            fts.add(R.id.home_content,af);
                        }
                       else{
                            fts.show(af);
                        }
                        break;
                    case 1:
                        if(bf==null){
                            bf = new BookFragment();
                            fts.add(R.id.home_content,bf);
                        }
                        else{
                            fts.show(bf);
                        }
                        break;
                    case 2:
                        if(cf==null){
                            cf = new ContactFragment();
//                            cf = new ConversationListFragment();
                            //cf = initConversationList();
                            fts.add(R.id.home_content,cf);
                        }
                       else{
                            fts.show(cf);
                        }
                        break;
                    case 3:
                        if(mf==null){
                            mf = new MineFragment();
                            fts.add(R.id.home_content,mf);
                        }
                       else{
                            fts.show(mf);
                        }
                        break;
                }
                fts.commit();
                currentFragment = index;
            }
        };

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fts = fm.beginTransaction();
        af = new ArticalFragment();
        fts.add(R.id.home_content,af);
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
    public void hideFragments(FragmentTransaction ft) {
        if (af != null)
            ft.hide(af);
        if (bf != null)
            ft.hide(bf);
        if (cf != null)
            ft.hide(cf);
        if (mf != null)
            ft.hide(mf);
    }
    private ConversationListFragment mConversationListFragment = null;

//    private ConversationListFragment initConversationList() {
//        if (cf == null) {
//             cf = new ConversationListFragment();
//            cf.setAdapter(new ConversationListAdapterEx(RongContext.getInstance()));
////            if (isDebug) {
////
////
////            } else {
////                uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
////                        .appendPath("conversationlist")
////                        .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话是否聚合显示
////                        .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")//群组
////                        .appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "false")//公共服务号
////                        .appendQueryParameter(Conversation.ConversationType.APP_PUBLIC_SERVICE.getName(), "false")//订阅号
////                        .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "true")//系统
////                        .build();
////                mConversationsTypes = new Conversation.ConversationType[]{Conversation.ConversationType.PRIVATE,
////                        Conversation.ConversationType.GROUP,
////                        Conversation.ConversationType.PUBLIC_SERVICE,
////                        Conversation.ConversationType.APP_PUBLIC_SERVICE,
////                        Conversation.ConversationType.SYSTEM
////                };
////            }
//            Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
//                    .appendPath("conversationlist")
//                    .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话是否聚合显示
//                    .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")//群组
//                    .appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "false")//公共服务号
//                    .appendQueryParameter(Conversation.ConversationType.APP_PUBLIC_SERVICE.getName(), "false")//订阅号
//                    .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "true")//系统
//                    .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")
//                    .build();
//            cf.setUri(uri);
////            cf = listFragment;
////            return listFragment;
//            return cf;
//        } else {
//            return cf;
//        }
//    }
}
