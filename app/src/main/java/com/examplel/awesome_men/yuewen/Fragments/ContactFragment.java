package com.examplel.awesome_men.yuewen.Fragments;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.examplel.awesome_men.yuewen.Activitys.MainActivity;
import com.examplel.awesome_men.yuewen.Adapters.ConversationListAdapterEx;
import com.examplel.awesome_men.yuewen.R;

import io.rong.imkit.RongContext;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;

/**
 * Created by longer on 2017/4/18.
 */

public class ContactFragment extends Fragment {
    ConversationListFragment cf;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contact_fragment,container,false);
        Toolbar toolbar = (Toolbar)view.findViewById(R.id.contact_toolbar);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.contact_content,initConversationList());
        transaction.commit();
        return view;
    }
    private ConversationListFragment initConversationList() {
        if (cf == null) {
            cf = new ConversationListFragment();
            cf.setAdapter(new ConversationListAdapterEx(RongContext.getInstance()));

            Uri uri = Uri.parse("rong://" + getActivity().getApplicationInfo().packageName).buildUpon()
                    .appendPath("conversationlist")
                    .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话是否聚合显示
                    .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")//群组
                    .appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "false")//公共服务号
                    .appendQueryParameter(Conversation.ConversationType.APP_PUBLIC_SERVICE.getName(), "false")//订阅号
                    .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "true")//系统
                    .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")
                    .build();
            cf.setUri(uri);
            return cf;
        } else {
            return cf;
        }
    }
}
