package com.example.txl.redesign.fragment.wanandroid;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.txl.gankio.R;
import com.example.txl.gankio.viewimpl.WebActivity;
import com.example.txl.redesign.data.XmlyFmData;
import com.example.txl.redesign.data.wanandroid.WanAndroidArticle;
import com.example.txl.redesign.fragment.xmlyfm.XmlyFmViewHolder;
import com.example.txl.redesign.utils.NewsItemClickHandle;

/**
 * @author TXL
 * description :wan android 文章
 */
public class WanAndroidArticleViewHolder extends XmlyFmViewHolder {
    ViewGroup rootView;
    TextView author, title, publishTime;
    CheckBox zanButton, collectButton;
    public WanAndroidArticleViewHolder(View itemView) {
        super(itemView);
        rootView = (ViewGroup) itemView;
        author = itemView.findViewById( R.id.tv_article_author );
        title = itemView.findViewById( R.id.tv_article_title );
        publishTime = itemView.findViewById( R.id.tv_article_publish_time );
        zanButton = itemView.findViewById( R.id.rb_article_zan );
        collectButton = itemView.findViewById( R.id.rb_article_collect );
    }

    @Override
    public void onBindViewHolder(int position, XmlyFmData data) {
        WanAndroidArticle article = data.getAndroidArticle();
        author.setText("作者："+ article.getAuthor() );
        title.setText( article.getTitle() );
        publishTime.setText( article.getNiceDate() );
        zanButton.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int zan = article.getZan();
                if(isChecked){
                    zan++;
                }else {
                    zan--;
                }
                article.setZan( zan );
                zanButton.setText( "  "+zan );
            }
        } );
        zanButton.setText( "  "+article.getZan() );
        collectButton.setChecked( article.isCollect() );
        collectButton.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                article.setCollect( isChecked );
            }
        } );
        rootView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewsItemClickHandle.fmItemClick( rootView.getContext(),data );
            }
        } );
    }
}
