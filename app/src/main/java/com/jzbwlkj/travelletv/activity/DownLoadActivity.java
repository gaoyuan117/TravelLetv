package com.jzbwlkj.travelletv.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.jzbwlkj.travelletv.R;
import com.jzbwlkj.travelletv.adapter.DownLoadAdapter;
import com.jzbwlkj.travelletv.application.App;
import com.jzbwlkj.travelletv.base.BaseActivity;
import com.jzbwlkj.travelletv.bean.DownLoadBean;
import com.jzbwlkj.travelletv.db.DownDao;
import com.jzbwlkj.travelletv.db.DownDaoDao;
import com.jzbwlkj.travelletv.util.RxUtils;
import com.jzbwlkj.travelletv.view.Dialog;
import com.jzbwlkj.travelletv.view.TitleBar;
import com.orhanobut.logger.Logger;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelpay.PayReq;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

public class DownLoadActivity extends BaseActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    @BindView(R.id.lv_down)
    ListView mListView;

    private DownLoadAdapter mAdapter;
    private List<DownLoadBean> mList;
    private DownDaoDao dao;
    private File[] files;
    private List<DownDao> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_load);
        ButterKnife.bind(this);
    }

    @Override
    protected void init() {
        new TitleBar(this).setTitle(getString(R.string.down_load));
        mList = new ArrayList<>();
        mAdapter = new DownLoadAdapter(this, mList);
        dao = App.getDaoInstant().getDownDaoDao();
        files = getExternalFilesDir(Environment.DIRECTORY_MOVIES).listFiles();
    }

    @Override
    protected void initData() {
        getDownVideo();
    }

    @Override
    protected void setData() {
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
        mListView.setOnItemLongClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(this, DownLoadPlayActivity.class);
        intent.putExtra("data", mList.get(i));
        Logger.d("path:"+mList.get(i).getVideoPath());
        startActivity(intent);
    }


    private void deleteDialog(final int paramInt) {
        new Dialog(this).setMessage("是否删除该下载视频").setPositiveClick("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                String filePath = list.get(paramInt).getFilePath();
                File file = new File(filePath);
                file.delete();
                dao.delete(list.get(paramInt));
                mList.remove(paramInt);
                mAdapter.notifyDataSetChanged();
            }
        }).showDialog();
    }


    private void getDownVideo() {
        list = dao.queryBuilder().list();
        Observable.from(dao.queryBuilder().list())
                .compose(RxUtils.<DownDao>io_main())
                .filter(new Func1<DownDao, Boolean>() {
                    @Override
                    public Boolean call(DownDao downDao) {
                        if (!downDao.getUser().equals(App.user)) {
                            return false;
                        }
                        for (int i = 0; i < files.length; i++) {
                            String[] arrayOfString = files[i].getName().split("-");
                            if ((arrayOfString.length == 0) || (!downDao.getVideoId().equals(arrayOfString[0]))) {
                                return false;
                            }
                        }
                        return true;
                    }
                })
                .doOnCompleted(new Action0() {
                    @Override
                    public void call() {
                        mAdapter.notifyDataSetChanged();
                    }
                })
                .subscribe(new Action1<DownDao>() {
                    @Override
                    public void call(DownDao downDao) {
                        DownLoadBean localDownLoadBean = new DownLoadBean();
                        localDownLoadBean.setVideoId(downDao.getVideoId());
                        localDownLoadBean.setTitle(downDao.getTitle());
                        localDownLoadBean.setVideoPath(downDao.getFilePath());
                        localDownLoadBean.setDes(downDao.getDes());
                        mList.add(localDownLoadBean);
                    }
                });
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        deleteDialog(position);
        return true;
    }
}
