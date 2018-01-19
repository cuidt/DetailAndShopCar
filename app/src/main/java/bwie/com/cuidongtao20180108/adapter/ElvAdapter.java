package bwie.com.cuidongtao20180108.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import bwie.com.cuidongtao20180108.R;
import bwie.com.cuidongtao20180108.activity.ThirdActivity;
import bwie.com.cuidongtao20180108.bean.PriceAndCount;
import bwie.com.cuidongtao20180108.bean.ThirdBean;

public class ElvAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<ThirdBean.DataBean> group;
    private List<List<ThirdBean.DataBean.ListBean>> child;
    private LayoutInflater inflater;

    public ElvAdapter(Context context, List<ThirdBean.DataBean> group, List<List<ThirdBean.DataBean.ListBean>> child) {
        this.context = context;
        this.group = group;
        this.child = child;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public int getGroupCount() {
        return group.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return child.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return group.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return child.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view;
        final GroupViewHolder holder;
        if (convertView == null) {
            view = inflater.inflate(R.layout.elv_group, null);
            holder = new GroupViewHolder();
            holder.tv = view.findViewById(R.id.tvGroup);
            holder.cbGroup = view.findViewById(R.id.cbGroup);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (GroupViewHolder) view.getTag();
        }
        final ThirdBean.DataBean dataBean = group.get(groupPosition);
        holder.tv.setText(dataBean.getSellerName());
        holder.cbGroup.setChecked(dataBean.isChecked());

        holder.cbGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataBean.setChecked(holder.cbGroup.isChecked());
                //改变子列表中的所有checkbox的状态
                setChildsChecked(groupPosition, holder.cbGroup.isChecked());
                //改变全选状态
                ((ThirdActivity) context).setAllSelect(isGroupCbChecked());
                //计算钱和数量
                setPriceAndCount();
                //刷新列表
                notifyDataSetChanged();
            }
        });

        return view;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view;
        final ChildViewHolder holder;
        if (convertView == null) {
            view = inflater.inflate(R.layout.elv_child, null);
            holder = new ChildViewHolder();
            holder.iv = view.findViewById(R.id.iv);
            holder.tvTitle = view.findViewById(R.id.tvTitle);
            holder.tvSubhead = view.findViewById(R.id.tvSubhead);
            holder.tvPrice = view.findViewById(R.id.tvPrice);
            holder.cbChild = view.findViewById(R.id.cbChild);
            holder.btDel = view.findViewById(R.id.btDel);
            holder.tvNum = view.findViewById(R.id.tvNum);
            holder.ivDel = view.findViewById(R.id.ivDel);
            holder.ivAdd = view.findViewById(R.id.ivAdd);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ChildViewHolder) view.getTag();
        }
        final ThirdBean.DataBean.ListBean listBean = child.get(groupPosition).get(childPosition);
        String images = listBean.getImages();
        Glide.with(context).load(images.split("\\|")[0]).into(holder.iv);
        holder.tvTitle.setText(listBean.getTitle());
        holder.cbChild.setChecked(child.get(groupPosition).get(childPosition).isChecked());
        holder.tvSubhead.setText(listBean.getSubhead());
        holder.tvPrice.setText(listBean.getPrice() + "元");
        holder.tvNum.setText(listBean.getCount() + "");

        holder.btDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //删除
                setPriceAndCount();
                child.get(groupPosition).remove(childPosition);
                if (child.get(groupPosition).size() == 0) {
                    child.remove(groupPosition);
                    group.remove(groupPosition);
                }
                notifyDataSetChanged();

            }
        });

        //给减号设置点击事件
        holder.ivDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int c = listBean.getCount();
                if (c <= 1) {
                    Toast.makeText(context, "最少选一件", Toast.LENGTH_SHORT).show();
                    c = 1;
                } else {
                    c--;
                }
                setNum(listBean, holder, c);
            }
        });
        //给加号设置点击事件
        holder.ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int c = listBean.getCount();
                c++;
                setNum(listBean, holder, c);
            }
        });

        //给子列表的checkbox设置点击事件
        holder.cbChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击checkbox的时候，给对应的属性值设置true或者false
                listBean.setChecked(holder.cbChild.isChecked());
                group.get(groupPosition).setChecked(isChildsCbChecked(groupPosition));
                //如果某个一级列表下的二级列表全部选中时，则要判断其它的一级列表是否都选中，去改变“全选”状态
                ((ThirdActivity) context).setAllSelect(isGroupCbChecked());
                setPriceAndCount();
                //刷新页面
                notifyDataSetChanged();
            }
        });
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class GroupViewHolder {
        TextView tv;
        CheckBox cbGroup;
    }

    class ChildViewHolder {
        ImageView iv;
        TextView tvTitle;
        TextView tvSubhead;
        TextView tvPrice;
        CheckBox cbChild;
        Button btDel;
        TextView tvNum;
        ImageView ivDel;
        ImageView ivAdd;
    }

    private void setNum(ThirdBean.DataBean.ListBean listBean, ChildViewHolder holder, int c) {
        //改变bean里的那个值
        listBean.setCount(c);
        holder.tvNum.setText(c + "");
        //计算钱和数量，并显示
        setPriceAndCount();
        //刷新页面
        notifyDataSetChanged();
    }

    /**
     * 改变子列表中的所有checkbox的状态
     *
     * @param groupPosition
     * @param bool
     */
    private void setChildsChecked(int groupPosition, boolean bool) {
        List<ThirdBean.DataBean.ListBean> listBeans = child.get(groupPosition);
        for (int i = 0; i < listBeans.size(); i++) {
            ThirdBean.DataBean.ListBean listBean = listBeans.get(i);
            listBean.setChecked(bool);
        }
    }

    /**
     * 判断一级列表是否全部选中
     *
     * @return
     */
    private boolean isGroupCbChecked() {
        if (group.size() == 0) {
            return false;
        }
        for (int i = 0; i < group.size(); i++) {
            ThirdBean.DataBean dataBean = group.get(i);
            if (!dataBean.isChecked()) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断同一商家下的子列表checkbox是否都选中
     *
     * @param groupPosition
     * @return
     */
    private boolean isChildsCbChecked(int groupPosition) {
        List<ThirdBean.DataBean.ListBean> listBeans = child.get(groupPosition);
        for (int i = 0; i < listBeans.size(); i++) {
            ThirdBean.DataBean.ListBean listBean = listBeans.get(i);
            if (!listBean.isChecked()) {
                return false;
            }
        }
        return true;
    }

    /**
     * 设置钱和数量
     */
    private void setPriceAndCount() {
        PriceAndCount priceAndCount = computePrice();
        ((ThirdActivity) context).setMoney(priceAndCount.getPrice());
        ((ThirdActivity) context).setCount(priceAndCount.getCount());
    }

    /**
     * 计算钱
     */
    private PriceAndCount computePrice() {
        double sum = 0;
        int count = 0;
        for (int i = 0; i < child.size(); i++) {
            List<ThirdBean.DataBean.ListBean> listBeans = child.get(i);
            for (int j = 0; j < listBeans.size(); j++) {
                ThirdBean.DataBean.ListBean listBean = listBeans.get(j);
                //表示是否选中
                if (listBean.isChecked()) {
                    count += listBean.getCount();
                    sum += listBean.getPrice() * listBean.getCount();
                }

            }
        }
        return new PriceAndCount(sum, count);
    }


    /**
     * “全选”改变状态
     *
     * @param bool
     */
    public void setAllChecked(boolean bool) {
        for (int i = 0; i < group.size(); i++) {
            ThirdBean.DataBean dataBean = group.get(i);
            dataBean.setChecked(bool);
            setChildsChecked(i, bool);
        }
        setPriceAndCount();
        //刷新页面
        notifyDataSetChanged();

    }
}