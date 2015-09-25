package me.nereo.multi_image_selector.utils;

public class Constants {
    public static final String DATE_PATTERN                = "yyyy-MM-dd";
    /**
     * 默认选择的数据集
     */
    public static final String EXTRA_DEFAULT_SELECTED_LIST = "default_result";
    public static final String EXTRA_LOG                   = "log";
    /**
     * 选择结果，返回为 ArrayList&lt;String&gt; 图片路径集合
     */
    public static final String EXTRA_RESULT = "select_result";
    /**
     * 最大图片选择次数，int类型
     */
    public static final String EXTRA_SELECT_COUNT          = "max_select_count";
    /**
     * 图片选择模式，int类型
     */
    public static final String EXTRA_SELECT_MODE           = "select_count_mode";
    /**
     * 是否显示相机，boolean类型
     */
    public static final String EXTRA_SHOW_CAMERA           = "show_camera";
    // 不同loader定义
    public static final int    LOADER_ALL                  = 0;
    public static final int    LOADER_CATEGORY             = 1;
    /**
     * 多选
     */
    public static final int    MODE_MULTI   = 1;
    /**
     * 单选
     */
    public static final int    MODE_SINGLE                 = 0;
    // 请求加载系统照相机
    public static final int    REQUEST_CAMERA              = 100;
    public static final String TAG                         = "MultiImageSelector";
    // Default selecting images count
    public static final int DEFAULT_SELECTING_COUNT = -1;
}
