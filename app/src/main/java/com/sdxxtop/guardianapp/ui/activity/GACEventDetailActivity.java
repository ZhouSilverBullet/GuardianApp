package com.sdxxtop.guardianapp.ui.activity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.model.bean.EnterpriseCompanyBean;
import com.sdxxtop.guardianapp.model.bean.compar.EnterpriseCompanyCompar;
import com.sdxxtop.guardianapp.presenter.GACEPresenter;
import com.sdxxtop.guardianapp.presenter.contract.GACEContract;
import com.sdxxtop.guardianapp.ui.adapter.GACEDetailAdapter;
import com.sdxxtop.guardianapp.ui.pop.AreaSelectPopWindow;
import com.sdxxtop.guardianapp.ui.widget.CustomAreaSelectView;
import com.sdxxtop.guardianapp.ui.widget.EnterpriseTabTitleView;
import com.sdxxtop.guardianapp.ui.widget.TitleView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * GridAndCompanyEventDetailActivity
 */
public class GACEventDetailActivity extends BaseMvpActivity<GACEPresenter> implements GACEContract.IView {

    @BindView(R.id.title)
    TitleView title;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.casv_left)
    CustomAreaSelectView casvLeft;
    @BindView(R.id.casv_right)
    CustomAreaSelectView casvRight;
    @BindView(R.id.tv_bg)
    TextView tvBg;
    @BindView(R.id.ett_view)
    EnterpriseTabTitleView ettView;
    @BindView(R.id.ll_containor)
    LinearLayout llContainor;

    private GACEDetailAdapter adapter;
    private List<AreaSelectPopWindow.PopWindowDataBean> leftData = new ArrayList<>();
    private List<AreaSelectPopWindow.PopWindowDataBean> rightData = new ArrayList<>();
    private int part_typeid, parent_id;

    @Override
    protected int getLayout() {
        return R.layout.activity_gacevent_detail;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void showError(String error) {

    }

    @Override
    protected void initView() {
        super.initView();
        casvRight.tvArea.setText("全部");
        ettView.setOnTextClickListener(new EnterpriseTabTitleView.OnTextClickListener() {
            @Override
            public void onTextClick(int num, boolean isOrder) {
                List<EnterpriseCompanyBean.PartInfo> data = adapter.getData();
                Collections.sort(data, new EnterpriseCompanyCompar(num, isOrder));
                adapter.replaceData(data);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new GACEDetailAdapter(R.layout.item_gace_view, null);
        recyclerView.setAdapter(adapter);

        casvLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AreaSelectPopWindow popWindow = new AreaSelectPopWindow(GACEventDetailActivity.this, llContainor, leftData, casvLeft.tvArea, tvBg);
                popWindow.setOnPopItemClickListener(new AreaSelectPopWindow.OnPopItemClickListener() {
                    @Override
                    public void onPopItemClick(int partTypeid, String partName) {
                        casvLeft.tvArea.setText(partName);
                        casvRight.tvArea.setText("所有企业");
                        part_typeid = partTypeid;
                        mPresenter.enterpriseCompany(part_typeid, 0);
                    }
                });
            }
        });

        casvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AreaSelectPopWindow popWindow = new AreaSelectPopWindow(GACEventDetailActivity.this, llContainor, rightData, casvRight.tvArea, tvBg);
                popWindow.setOnPopItemClickListener(new AreaSelectPopWindow.OnPopItemClickListener() {
                    @Override
                    public void onPopItemClick(int partTypeid, String partName) {
                        casvRight.tvArea.setText(partName);
                        parent_id = partTypeid;
                        mPresenter.enterpriseCompany(part_typeid, parent_id);
                    }
                });
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.enterpriseCompany(part_typeid, parent_id);
    }

    @Override
    public void showData(EnterpriseCompanyBean bean) {
        adapter.replaceData(bean.getPart_info());
        leftData.clear();
        rightData.clear();
        for (int i = 0; i < bean.getUser_part().size(); i++) {
            EnterpriseCompanyBean.PartData partData = bean.getUser_part().get(i);
            leftData.add(new AreaSelectPopWindow.PopWindowDataBean(partData.getPart_id(), partData.getPart_name()));
        }
        for (int i = 0; i < bean.getPart_data().size(); i++) {
            EnterpriseCompanyBean.PartData partData = bean.getPart_data().get(i);
            rightData.add(new AreaSelectPopWindow.PopWindowDataBean(partData.getPart_id(), partData.getPart_name()));
        }
    }
}
