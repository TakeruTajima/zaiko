package com.mr2.zaiko.ui.itemDetailBrowser;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.helper.widget.Flow;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.mr2.zaiko.R;
import com.mr2.zaiko.domain.inhouse.equipment.EquipmentId;
import com.mr2.zaiko.domain.inhouse.equipment.Keyword;
import com.mr2.zaiko.domain.inhouse.equipment.Photo;
import com.mr2.zaiko.domain.outside.commodity.CommodityId;
import com.mr2.zaiko.domain.outside.company.CompanyId;
import com.mr2.zaiko.domain.outside.product.ProductId;
import com.mr2.zaiko.ui.dialog.DialogFragment;
import com.mr2.zaiko.ui.imageViewer.ImageViewerFragment;
import com.mr2.zaiko.ui.imageViewer.ImageViewerResource;

import java.util.ArrayList;
import java.util.List;


public class ItemDetailBrowserFragment extends Fragment implements ContractItemDetailBrowser.View{
    /* ---------------------------------------------------------------------- */
    /* Field                                                                  */
    /* ---------------------------------------------------------------------- */
    public static final String TAG = ItemDetailBrowserFragment.class.getSimpleName() + "(4156)";

    private View view = null;
    private Context context;
    private ContractItemDetailBrowser.Presenter presenter;
    /* Example */
    private ItemDetailBrowserFragmentListener listener = null;

    private int quantityWantToPutCart = 1;
    private ItemDetailBrowserResource resource;

    /* ---------------------------------------------------------------------- */
    /* Listener                                                               */
    /* ---------------------------------------------------------------------- */
    /* Example */
    public interface ItemDetailBrowserFragmentListener {
        void onHogeEvent();
    }

    /* ---------------------------------------------------------------------- */
    /* Lifecycle                                                              */
    /* ---------------------------------------------------------------------- */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach");
        this.context = context;
        presenter = ItemDetailBrowserPresenter.getInstance(this);
        /* Example */
        if (!(context instanceof ItemDetailBrowserFragmentListener)) {
            throw new UnsupportedOperationException(
                    TAG + ":" + "Listener is not Implementation.");
        } else {
            listener = (ItemDetailBrowserFragmentListener) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        presenter.onCreate(null);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
//        view = inflater.inflate(R.layout./*このフラグメントで使用するレイアウトのID*/, container, false);
        view = inflater.inflate(R.layout.fragment_item_detail_browser, container, false);
//        SpinnerAdapter adapter =
//        Spinner spinner = view.findViewById(R.id.itemDetailBrowserSpinner);
//        spinner.setAdapter(adapter); //TODO: 数量選択spinner実装途中 選択した数量はquantityWantToPutCartに入れる

        setThumbnail();
        setListener();
        setViews();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach");
    }

    /* ---------------------------------------------------------------------- */
    /* other method                                                           */
    /* ---------------------------------------------------------------------- */

    private ImageViewerResource getImageResource(){
        List<Photo> photos = new ArrayList<>();
        for (int i = 0; 10 >= i; i++){
            photos.add(new Photo("20200309033935.jpg"));
        }
        return new ImageViewerResource(photos, context.getFilesDir().getAbsolutePath());
    }

    private void setThumbnail(){
        ImageViewerFragment fragment = ImageViewerFragment.getThumbnail(resource.getImageResource());
        fragment.setOnImageClickListener(this::showImageViewer);
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.replace(R.id.itemDetailBrowserImageContainer, fragment);
        ft.commit();
    }

    @Override
    public void showImageViewer(ImageViewerResource resource, int position) {
        ImageViewerFragment fragment = ImageViewerFragment.getFullSize(resource, position);
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.replace(R.id.itemDetailBrowserMainContainer, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void setResource(ItemDetailBrowserResource resource) {
        if (null != this.resource) return;
        this.resource = resource;
    }

    private void setListener(){
        view.findViewById(R.id.itemDetailBrowserPrimaryName).setOnClickListener(v -> presenter.onClickPrimaryName());
        view.findViewById(R.id.itemDetailBrowserMakerName).setOnClickListener(v -> presenter.onClickMakerName());
        view.findViewById(R.id.itemDetailBrowserInventoryMore).setOnClickListener(v -> presenter.onClickInventoryMore());
//        view.findViewById(R.id.itemDetailBrowserKeywords).setOnClickListener(v -> presenter.onClickKeyword("キーワードに関連する部品の一覧を表示する予定です"));
        view.findViewById(R.id.itemDetailBrowserSellerName).setOnClickListener(v -> presenter.onCLickSellerName());
        view.findViewById(R.id.itemDetailBrowserCommodityMore).setOnClickListener(v -> presenter.onClickCommodityMore());
        view.findViewById(R.id.itemDetailBrowserButtonPutCart).setOnClickListener(v -> presenter.onClickPutShoppingCart(quantityWantToPutCart));
        view.findViewById(R.id.itemDetailBrowserStoringHistoryMore).setOnClickListener(v -> presenter.onClickStoringMore());
        view.findViewById(R.id.itemDetailBrowserBuyHistoryMore).setOnClickListener(v -> presenter.onClickBuyHistoryMore());
    }

    private void setViews(){
        String primaryName = resource.getEquipment().name().name().equals("") ?
                resource.getProduct().name().name() : resource.getEquipment().name().name();
        setPrimaryName(primaryName);
        setMakerName(resource.getMaker().name());
        setModel(resource.getProduct().model().model());
        setProductName(resource.getProduct().name().name());
        String productPrice = resource.getProduct().price().price() + "/" + resource.getProduct().unit().unit();
        setProductPrice(productPrice);
        String inventoryPrice = resource.getEquipment().price().price() + "/" + resource.getEquipment().unit().unit();
        setInventoryPrice(inventoryPrice);
//        String stock = resource.getStorageLocationList().
//        ((TextView)view.findViewById(R.id.itemDetailBrowserStock))
        String[] array = new String[resource.getEquipment().keywordSet().size()];
        int i = 0;
        for (Keyword k: resource.getEquipment().keywordSet()){
            array[i] = k.word();
            i++;
        }
        setKeyword(array);
        setSellerName(resource.getSeller().name());
        setPrimaryCommodityName(resource.getCommodity().name().name());
        String primaryCommodityPrice = resource.getCommodity().price().price() + "/" + resource.getCommodity().unit().unit();
        setPrimaryCommodityPrice(primaryCommodityPrice);
    }

    public void setPrimaryCommodityPrice(String primaryCommodityPrice) {
        ((TextView)view.findViewById(R.id.itemDetailBrowserCommodityPrice)).setText(primaryCommodityPrice);
    }

    public void setPrimaryCommodityName(String name) {
        ((TextView)view.findViewById(R.id.itemDetailBrowserCommodityName)).setText(name);
    }


    public void setPrimaryName(String primaryName){
        ((TextView)view.findViewById(R.id.itemDetailBrowserPrimaryName)).setText(primaryName);
    }

    public void setMakerName(String makerName){
        ((TextView)view.findViewById(R.id.itemDetailBrowserMakerName)).setText(makerName);
    }

    public void setModel(String model){
        ((TextView)view.findViewById(R.id.itemDetailBrowserProductModel)).setText(model);
    }

    public void setProductName(String productName){
        ((TextView)view.findViewById(R.id.itemDetailBrowserProductName)).setText(productName);
    }

    public void setProductPrice(String productPrice){
        ((TextView)view.findViewById(R.id.itemDetailBrowserProductPrice)).setText(productPrice);
    }

    public void setInventoryPrice(String inventoryPrice){
        ((TextView)view.findViewById(R.id.itemDetailBrowserEquipmentPrice)).setText(inventoryPrice);
    }

    public void setKeyword(String[] keyword){
        ConstraintLayout cl = view.findViewById(R.id.itemDetailBrowserConstraint);
        Flow flow = view.findViewById(R.id.itemDetailBrowserFlow);
        for (int i = 0; keyword.length > i; i++){
            TextView t = getKeywordTextView(keyword[i]);
            t.setId(View.generateViewId());
            cl.addView(t, i);
            flow.addView(t);
        }
    }

    private TextView getKeywordTextView(String keyword){
//        TextView tv = new TextView(context);
        KeywordTextView ktv = new KeywordTextView(context, keyword);
        String s = "#" + keyword;
        ktv.setText(s);
        ktv.setTextColor(ContextCompat.getColor(context, R.color.colorTextLink));
        ktv.setOnClickListener(v -> transitionToListOfItemByKeyword(((KeywordTextView)v).getKeyword()));
        return ktv;
    }

    public void setSellerName(String sellerName){
        ((TextView)view.findViewById(R.id.itemDetailBrowserSellerName)).setText(sellerName);
    }

    @Override
    public void transitionToInventoryList(EquipmentId equipmentId) {
        showDialog("この部品の在庫が(複数箇所の)どこに、いくつ(ずつ)あるのかをリストで表示します。");
    }

    @Override
    public void transitionToListOfSeller(ProductId productId) {
        showDialog("この製品が登録されている他の商社を表示します");
    }

    @Override
    public void openImageCapture() {
        showDialog("カメラを開く予定です");
    }

    @Override
    public void transitionToItemDetailEditor(ProductId productId) {
        showDialog("部品詳細を編集する画面に移動します");
    }

    @Override
    public void transitionToListOfItemByKeyword(String keyword) {
        showDialog("キーワード [" + keyword + "] に関連する部品の一覧に移動します");
    }

    @Override
    public void transitionToListOfItemByMaker(CompanyId makerId) {
        showDialog("メーカーの製品一覧に移動します");
    }

    @Override
    public void transitionToListOfCommodity(CommodityId commodityId) {
        showDialog("この商社から購入できる商品の一覧を開きます");
    }

    @Override
    public void transitionToListOfBackorder(EquipmentId equipmentId) {
        showDialog("納入待ち一覧を開きます");
    }

    @Override
    public void transitionToListOfExternalBarcode(EquipmentId equipmentId) {
        showDialog("登録済み社外バーコードの一覧を開きます　開きません");
    }

    @Override
    public void transitionToListOfStoringHistory(EquipmentId equipmentId) {
        showDialog("入出庫の履歴を開きます");
    }

    @Override
    public void transitionToListOfBuyHistory(EquipmentId equipmentId) {
        showDialog("購入履歴の一覧を開きます");
    }

    @Override
    public void showDialog(String message) {
        DialogFragment dialog = DialogFragment.newInstance(message, "cancel", "OK");

        assert getFragmentManager() != null;
        dialog.show(getFragmentManager(), "");
    }

    private static class KeywordTextView extends androidx.appcompat.widget.AppCompatTextView{
        private final String keyword;

        public KeywordTextView(Context context, String keyword) {
            super(context);
            this.keyword = keyword;
        }

        public String getKeyword() {
            return keyword;
        }
    }
}
