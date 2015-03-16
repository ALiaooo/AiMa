package com.app.aliao.aima.blog.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Toast;

import com.app.aliao.aima.R;
import com.app.aliao.aima.news.fragment.NewsFragment;
import com.app.aliao.aima.utils.L;

/**
 * Created by 丽双 on 2015/3/9.
 */
public class BlogFragment extends Fragment{

    //Hybird App开发

    // 链接样式文件，代码块高亮的处理
    public final static String linkCss = "<script type=\"text/javascript\" src=\"file:///android_asset/shCore.js\"></script>"
            + "<script type=\"text/javascript\" src=\"file:///android_asset/brush.js\"></script>"
            + "<script type=\"text/javascript\" src=\"file:///android_asset/client.js\"></script>"
            + "<link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/shThemeDefault.css\">"
            + "<link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/shCore.css\">"
            + "<script type=\"text/javascript\">SyntaxHighlighter.all();</script>"
            + "<script type=\"text/javascript\">function showImagePreview(var url){window.location.url= url;}</script>";
    public final static String WEB_STYLE = linkCss
            + "<style>* {font-size:16px;line-height:20px;} p {color:#333;} a {color:#3E62A6;} img {max-width:310px;} "
            + "img.alignleft {float:left;max-width:120px;margin:0 10px 5px 0;border:1px solid #ccc;background:#fff;padding:2px;} "
            + "pre {font-size:9pt;line-height:12pt;font-family:Courier New,Arial;border:1px solid #ddd;border-left:5px solid #6CE26C;background:#f6f6f6;padding:5px;overflow: auto;} "
            + "a.tag {font-size:15px;text-decoration:none;background-color:#bbd6f3;border-bottom:2px solid #3E6D8E;border-right:2px solid #7F9FB6;color:#284a7b;margin:2px 2px 2px 0;padding:2px 4px;white-space:nowrap;}</style>";

    public static final String WEB_LOAD_IMAGES = "<script type=\"text/javascript\"> var allImgUrls = getAllImgSrc(document.body.innerHTML);</script>";

    private static final String IAM_API_SHOWIMAGE = "ima-api:action=showImage&data=";

    public final static String BODY = "<style type='text/css'>pre {white-space:pre-wrap;word-wrap:break-word;}</style><p><span style=\"font-weight:700;color:#444444;font-family:Tahoma, 'Microsoft Yahei', Simsun;font-size:14px;line-height:21px;background-color:#FFFFFF;\">FineUIPro v2.3.0发布了（试用版同时提供下载）！</span><span style=\"color:#444444;font-family:Tahoma, 'Microsoft Yahei', Simsun;font-size:14px;line-height:21px;background-color:#FFFFFF;\">&nbsp;</span><br><span style=\"font-size:13.3333330154419px;background-color:#FFFFFF;\"></span></p> \n" +
            "<p><span style=\"font-size:13.3333330154419px;background-color:#FFFFFF;\"><a href=\"http://fineui.com/demo_pro/\" target=\"_blank\" rel=\"nofollow\">http://fineui.com/demo_pro/</a></span><br></p> \n" +
            "<p><img src=\"http://static.oschina.net/uploads/img/201503/10125211_Yugd.png\">&nbsp;</p> \n" +
            "<p> 更新记录：</p> \n" +
            "<pre class=\"brush:html; toolbar: true; auto-links: false;\">+2015-03-10&nbsp;v2.3.0\n" +
            "\t-增加示例：第三方组件-&gt;WebUploader-&gt;WebUploader（选项卡中的上传组件）。\n" +
            "\t-增加RegionSplitHeaderClass，是否为区域分隔条使用表头样式（默认为true）。\n" +
            "\t+表格增强。\n" +
            "\t\t-修正自定义客户端Listeners后翻页排序行点击等回发事件失效的问题。\n" +
            "\t\t-增加DisableUnselectableRows属性，增加示例：表格控件-&gt;行选择-&gt;行选择（禁止选择某些行，整行变灰）。\n" +
            "\t\t-增加ExpandAllTreeNodes属性，更新示例：表格控件-&gt;树表格-&gt;树表格（展开全部行）。\n" +
            "\t\t-修正分页提示信息下半部分可能会被遮挡的问题。\n" +
            "\t\t-修正列属性HeaderToolTip无效的问题，更新示例：表格控件-&gt;标题栏-&gt;改变标题栏文本。\n" +
            "\t\t-afteredit事件的第二个参数value为修改后的单元格值，而不是渲染后的HTML片段。\n" +
            "\t\t-增加beforeedit事件，增加示例：表格控件-&gt;单元格编辑-&gt;禁止行的单元格编辑。\n" +
            "\t\t-初始选中复选框列，增加示例：表格控件-&gt;扩展列-&gt;模拟树列（全选反选）。\n" +
            "\t\t-增加示例：表格控件-&gt;单元格编辑-&gt;禁止编辑-&gt;禁止单元格的单元格编辑。\n" +
            "\t\t-修正表格KeepCurrentSelection时无法通过Shift键多选的问题。\n" +
            "\t+下拉框增强。\n" +
            "\t\t-点击下拉框或者下拉列表的清空图标时，同时更新下拉框中的选中项。\n" +
            "\t\t-增加Texts属性，同时更新相关示例（请使用Values和Texts属性初始多选下拉框）。\n" +
            "\t\t-增加阴影和圆角，修正下拉框超出页面边界的问题，下拉框随窗体大小改变而改变。\n" +
            "\t\t-增加AlwaysDisplayPopPanel属性，增加示例：表单控件-&gt;下拉框控件-&gt;杂项-&gt;总是显示弹出面板。\n" +
            "\t+选项卡增强。\n" +
            "\t\t-选项卡增加GetActiveReference方法，增加示例：其他控件-&gt;选项卡控件-&gt;初始隐藏全部选项卡。\n" +
            "\t\t-增加示例：其他控件-&gt;选项卡控件-&gt;按钮放在选项卡前面。\n" +
            "\t\t-Tab控件增加CloseOnDblclick属性，增加示例：其他控件-&gt;选项卡控件-&gt;双击关闭选项卡。\n" +
            "\t+窗体增强。\n" +
            "\t\t-增加KeepLastPosition属性（默认为false），开启此属性后，关闭再打开时左上角的位置不变。\n" +
            "\t\t-增加示例：其他控件-&gt;面板与窗体-&gt;显示窗体（保持上次位置）。\n" +
            "\t\t-点击窗体的任意位置都会使其置顶，增加示例：其他控件-&gt;面板与窗体-&gt;同时打开多个窗体。\n" +
            "\t-增加示例：杂项-&gt;表单字段的Label样式。\n" +
            "\t-修正GroupPanel中布局不能自适应的问题，更新示例：表单控件-&gt;表单布局-&gt;复杂布局（账单信息）。\n" +
            "\t-文件上传时显示正在加载的提示信息。\n" +
            "\t+按钮的高度固定（为了在表单布局时对其元素）。\n" +
            "\t\t-如果项目中使用官网示例的头部样式，可能需要为#header&nbsp;.f-btn增加height:auto;属性。\n" +
            "\t-增加示例：其他控件-&gt;窗体与面板-&gt;窗体（PNG代替图标字体）。\n" +
            "\t-表单字段的LabelWidth属性会覆盖Form的相同属性，更新示例：表单控件-&gt;杂项-&gt;自定义标签宽度。\n" +
            "\t-增加示例：杂项-&gt;消息框-&gt;响应确定按钮（点击确定按钮后，下载文件）。\n" +
            "\t-增加示例：杂项-&gt;消息框-&gt;响应确定按钮（点击确定按钮后，先隐藏窗体再下载文件）。\n" +
            "\t-增加示例：杂项-&gt;消息框-&gt;响应确定按钮（点击确定按钮后，先隐藏IFrame窗体再下载文件）。\n" +
            "\t-jQuery由版本v1.11.1升级到v1.11.2。</pre> \n" +
            "<p> 注：</p> \n" +
            "<ol> \n" +
            " <li><p><span style=\"line-height:1.5;font-size:10pt;\">AppBoxPro公开全部源代码。</span></p></li> \n" +
            " <li><p><span style=\"line-height:1.5;font-size:10pt;\">FineUIPro是商业程序，需购买授权（非喜勿喷）。</span></p></li> \n" +
            " <li><p><span style=\"line-height:1.5;font-size:10pt;\"><a href=\"http://fineui.com/\" target=\"_blank\" rel=\"nofollow\">FineUI（开源版）</a>基于Apache License 2.0 开源。</span></p></li> \n" +
            "</ol> \n" +
            "<p><strong>关于FineUI</strong> <br><span style=\"line-height:1.5;font-size:10pt;\">基于 jQuery / ExtJS 的专业 ASP.NET 控件库。</span> </p> \n" +
            "<p><strong>FineUI的使命</strong><br>创建 No JavaScript，No CSS，No UpdatePanel，No ViewState，No WebServices 的网站应用程序。</p> \n" +
            "<p><strong>支持的浏览器</strong><br>IE 8.0+、Chrome、Firefox、Opera、Safari&nbsp;</p> \n" +
            "<p><img src=\"http://static.oschina.net/uploads/space/2014/0731/100339_qesA_251200.png\" alt=\"\"> </p>";

    private WebView mWebView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pull_refresh_listview, null);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        mWebView = (WebView) view.findViewById(R.id.webview);
        fillWebViewBody();
    }

    private void fillWebViewBody() {
        String body = WEB_STYLE + BODY;
        body = setHtmlCotentSupportImagePreview(body);

        body += WEB_LOAD_IMAGES;

        addWebImageShow(getActivity(), mWebView);

        mWebView.loadDataWithBaseURL(null, body, "text/html", "utf-8", null);
    }

    public void addWebImageShow(final Context context, WebView webView){
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        mWebView.addJavascriptInterface(new ShowImage(getActivity()), "showimage");
       /* webView.addJavascriptInterface(new OnWebViewImageListener() {
            @Override
            public void onImageClick(String bigImageUrl) {
                Toast.makeText(context, "点击图片:" + bigImageUrl, Toast.LENGTH_LONG).show();
                L.d("点击图片url : " + bigImageUrl);
            }
        }, "mWebViewImageListener");*/
    }

    public class ShowImage{
        //绑定到你的Javascript的对象在另一个线程中运行，而不是在创建它的线程中运行。
        Context mContext;

        ShowImage(Context c){
            mContext = c;
        }

        public void showImagePreview(String imageUrl){
            Toast.makeText(mContext,imageUrl,Toast.LENGTH_LONG).show();

            L.d("javascript:imageUrl = "+imageUrl);
        }
    }

    public static String setHtmlCotentSupportImagePreview(String body) {
        // 读取用户设置：是否加载文章图片--默认有wifi下始终加载图片
        body = body.replaceAll("(<img[^>]*?)\\s+width\\s*=\\s*\\S+", "$1");
        body = body.replaceAll("(<img[^>]*?)\\s+height\\s*=\\s*\\S+", "$1");
        // 添加点击图片放大支持
//        body = body.replaceAll("(<img[^>]+src=\")(\\S+)\"", "$1$2\" onClick=\"showImagePreview('$2')\"");
        body = body.replaceAll("(<img[^>]+src=\")(\\S+)\"", "$1$2\" onClick=\"window.showimage.showImagePreview('$2')\"");
        return body;
    }


    public interface OnWebViewImageListener {

        /**
         * 点击webview上的图片，传入该缩略图的大图Url
         * @param bigImageUrl
         */
        void onImageClick(String bigImageUrl);

    }
}
