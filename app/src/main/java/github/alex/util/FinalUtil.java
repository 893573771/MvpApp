package github.alex.util;

import android.graphics.Color;

import com.alex.app.R;

import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.ThemeConfig;

/**
 * Created by hasee on 2016/6/30.
 */
public class FinalUtil {
    public static ThemeConfig getThemeConfig(){
        ThemeConfig theme = new ThemeConfig.Builder()
                .setTitleBarBgColor(Color.rgb(0xFF, 0x57, 0x22))
                .setTitleBarTextColor(Color.BLACK)
                .setTitleBarIconColor(Color.BLACK)
                .setFabNornalColor(Color.RED)
                .setFabPressedColor(Color.BLUE)
                .setCheckNornalColor(Color.WHITE)
                .setCheckSelectedColor(Color.BLACK)
                .setIconBack(R.drawable.ic_action_previous_item)
                .setIconRotate(R.drawable.ic_action_repeat)
                .setIconCrop(R.drawable.ic_action_crop)
                .setIconCamera(R.drawable.ic_action_camera)
                .build();
        return theme;
    }

    public static FunctionConfig getFunctionConfig(int mutiSelectMaxSize) {
        FunctionConfig functionConfig = new FunctionConfig.Builder().setMutiSelectMaxSize(mutiSelectMaxSize).build();
        return functionConfig;
    }
}
