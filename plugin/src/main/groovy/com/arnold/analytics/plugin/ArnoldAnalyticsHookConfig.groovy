package com.arnold.analytics.plugin

import org.objectweb.asm.Opcodes

class ArnoldAnalyticsHookConfig{
    public static final String SENSORS_ANALYTICS_API = "com/eebochina/train/analytics/PluginAutoTrackHelper"
    public final static HashMap<String, ArnoldAnalyticsMethodCell> INTERFACE_METHODS = new HashMap<>()
    public final static HashMap<String, ArnoldAnalyticsMethodCell> CLASS_METHODS = new HashMap<>()


    static {
        addInterfaceMethod(new ArnoldAnalyticsMethodCell(
                'onCheckedChanged',
                '(Landroid/widget/CompoundButton;Z)V',
                'android/widget/CompoundButton$OnCheckedChangeListener',
                'trackViewOnClick',
                '(Landroid/view/View;)V',
                1, 1,
                [Opcodes.ALOAD]))
        addInterfaceMethod(new ArnoldAnalyticsMethodCell(
                'onRatingChanged',
                '(Landroid/widget/RatingBar;FZ)V',
                'android/widget/RatingBar$OnRatingBarChangeListener',
                'trackViewOnClick',
                '(Landroid/view/View;)V',
                1, 1,
                [Opcodes.ALOAD]))
        addInterfaceMethod(new ArnoldAnalyticsMethodCell(
                'onStopTrackingTouch',
                '(Landroid/widget/SeekBar;)V',
                'android/widget/SeekBar$OnSeekBarChangeListener',
                'trackViewOnClick',
                '(Landroid/view/View;)V',
                1, 1,
                [Opcodes.ALOAD]))
        addInterfaceMethod(new ArnoldAnalyticsMethodCell(
                'onCheckedChanged',
                '(Landroid/widget/RadioGroup;I)V',
                'android/widget/RadioGroup$OnCheckedChangeListener',
                'trackRadioGroup',
                '(Landroid/widget/RadioGroup;I)V',
                1, 2,
                [Opcodes.ALOAD, Opcodes.ILOAD]))
        addInterfaceMethod(new ArnoldAnalyticsMethodCell(
                'onClick',
                '(Landroid/content/DialogInterface;I)V',
                'android/content/DialogInterface$OnClickListener',
                'trackDialog',
                '(Landroid/content/DialogInterface;I)V',
                1, 2,
                [Opcodes.ALOAD, Opcodes.ILOAD]))
        addInterfaceMethod(new ArnoldAnalyticsMethodCell(
                'onItemSelected',
                '(Landroid/widget/AdapterView;Landroid/view/View;IJ)V',
                'android/widget/AdapterView$OnItemSelectedListener',
                'trackListView',
                '(Landroid/widget/AdapterView;Landroid/view/View;I)V',
                1, 3,
                [Opcodes.ALOAD, Opcodes.ALOAD, Opcodes.ILOAD]))
        addInterfaceMethod(new ArnoldAnalyticsMethodCell(
                'onGroupClick',
                '(Landroid/widget/ExpandableListView;Landroid/view/View;IJ)Z',
                'android/widget/ExpandableListView$OnGroupClickListener',
                'trackExpandableListViewOnGroupClick',
                '(Landroid/widget/ExpandableListView;Landroid/view/View;I)V',
                1, 3,
                [Opcodes.ALOAD, Opcodes.ALOAD, Opcodes.ILOAD]))
        addInterfaceMethod(new ArnoldAnalyticsMethodCell(
                'onChildClick',
                '(Landroid/widget/ExpandableListView;Landroid/view/View;IIJ)Z',
                'android/widget/ExpandableListView$OnChildClickListener',
                'trackExpandableListViewOnChildClick',
                '(Landroid/widget/ExpandableListView;Landroid/view/View;II)V',
                1, 4,
                [Opcodes.ALOAD, Opcodes.ALOAD, Opcodes.ILOAD, Opcodes.ILOAD]))
        addInterfaceMethod(new ArnoldAnalyticsMethodCell(
                'onTabChanged',
                '(Ljava/lang/String;)V',
                'android/widget/TabHost$OnTabChangeListener',
                'trackTabHost',
                '(Ljava/lang/String;)V',
                1, 1,
                [Opcodes.ALOAD]))
        addInterfaceMethod(new ArnoldAnalyticsMethodCell(
                'onTabSelected',
                '(Landroid/support/design/widget/TabLayout$Tab;)V',
                'android/support/design/widget/TabLayout$OnTabSelectedListener',
                'trackTabLayoutSelected',
                '(Ljava/lang/Object;Ljava/lang/Object;)V',
                0, 2,
                [Opcodes.ALOAD, Opcodes.ALOAD]))
        addInterfaceMethod(new ArnoldAnalyticsMethodCell(
                'onTabSelected',
                '(Lcom/google/android/material/tabs/TabLayout$Tab;)V',
                'com/google/android/material/tabs/TabLayout$OnTabSelectedListener',
                'trackTabLayoutSelected',
                '(Ljava/lang/Object;Ljava/lang/Object;)V',
                0, 2,
                [Opcodes.ALOAD, Opcodes.ALOAD]))
        addInterfaceMethod(new ArnoldAnalyticsMethodCell(
                'onMenuItemClick',
                '(Landroid/view/MenuItem;)Z',
                'android/widget/Toolbar$OnMenuItemClickListener',
                'trackMenuItem',
                '(Landroid/view/MenuItem;)V',
                1, 1,
                [Opcodes.ALOAD]))
        addInterfaceMethod(new ArnoldAnalyticsMethodCell(
                'onMenuItemClick',
                '(Landroid/view/MenuItem;)Z',
                'android/support/v7/widget/Toolbar$OnMenuItemClickListener',
                'trackMenuItem',
                '(Landroid/view/MenuItem;)V',
                1, 1,
                [Opcodes.ALOAD]))
        addInterfaceMethod(new ArnoldAnalyticsMethodCell(
                'onMenuItemClick',
                '(Landroid/view/MenuItem;)Z',
                'androidx/appcompat/widget/Toolbar$OnMenuItemClickListener',
                'trackMenuItem',
                '(Landroid/view/MenuItem;)V',
                1, 1,
                [Opcodes.ALOAD]))
        addInterfaceMethod(new ArnoldAnalyticsMethodCell(
                'onClick',
                '(Landroid/content/DialogInterface;IZ)V',
                'android/content/DialogInterface$OnMultiChoiceClickListener',
                'trackDialog',
                '(Landroid/content/DialogInterface;I)V',
                1, 2,
                [Opcodes.ALOAD, Opcodes.ILOAD]))
        addInterfaceMethod(new ArnoldAnalyticsMethodCell(
                'onMenuItemClick',
                '(Landroid/view/MenuItem;)Z',
                'android/widget/PopupMenu$OnMenuItemClickListener',
                'trackMenuItem',
                '(Landroid/view/MenuItem;)V',
                1, 1,
                [Opcodes.ALOAD]))
        addInterfaceMethod(new ArnoldAnalyticsMethodCell(
                'onMenuItemClick',
                '(Landroid/view/MenuItem;)Z',
                'androidx/appcompat/widget/PopupMenu$OnMenuItemClickListener',
                'trackMenuItem',
                '(Landroid/view/MenuItem;)V',
                1, 1,
                [Opcodes.ALOAD]))
        addInterfaceMethod(new ArnoldAnalyticsMethodCell(
                'onMenuItemClick',
                '(Landroid/view/MenuItem;)Z',
                'android/support/v7/widget/PopupMenu$OnMenuItemClickListener',
                'trackMenuItem',
                '(Landroid/view/MenuItem;)V',
                1, 1,
                [Opcodes.ALOAD]))
        addInterfaceMethod(new ArnoldAnalyticsMethodCell(
                'onNavigationItemSelected',
                '(Landroid/view/MenuItem;)Z',
                'com/google/android/material/navigation/NavigationView$OnNavigationItemSelectedListener',
                'trackMenuItem',
                '(Landroid/view/MenuItem;)V',
                1, 1,
                [Opcodes.ALOAD]))
        addInterfaceMethod(new ArnoldAnalyticsMethodCell(
                'onNavigationItemSelected',
                '(Landroid/view/MenuItem;)Z',
                'android/support/design/widget/NavigationView$OnNavigationItemSelectedListener',
                'trackMenuItem',
                '(Landroid/view/MenuItem;)V',
                1, 1,
                [Opcodes.ALOAD]))
        addInterfaceMethod(new ArnoldAnalyticsMethodCell(
                'onNavigationItemSelected',
                '(Landroid/view/MenuItem;)Z',
                'android/support/design/widget/BottomNavigationView$OnNavigationItemSelectedListener',
                'trackMenuItem',
                '(Landroid/view/MenuItem;)V',
                1, 1,
                [Opcodes.ALOAD]))
        addInterfaceMethod(new ArnoldAnalyticsMethodCell(
                'onNavigationItemSelected',
                '(Landroid/view/MenuItem;)Z',
                'com/google/android/material/bottomnavigation/BottomNavigationView$OnNavigationItemSelectedListener',
                'trackMenuItem',
                '(Landroid/view/MenuItem;)V',
                1, 1,
                [Opcodes.ALOAD]))
    }

    static {
        addClassMethod(new ArnoldAnalyticsMethodCell(
                'performClick',
                '()Z',
                'androidx/appcompat/widget/ActionMenuPresenter$OverflowMenuButton',
                'trackViewOnClick',
                '(Landroid/view/View;)V',
                0, 1,
                [Opcodes.ALOAD]))

        addClassMethod(new ArnoldAnalyticsMethodCell(
                'performClick',
                '()Z',
                'android/support/v7/widget/ActionMenuPresenter$OverflowMenuButton',
                'trackViewOnClick',
                '(Landroid/view/View;)V',
                0, 1,
                [Opcodes.ALOAD]))

        addClassMethod(new ArnoldAnalyticsMethodCell(
                'performClick',
                '()Z',
                'android/widget/ActionMenuPresenter$OverflowMenuButton',
                'trackViewOnClick',
                '(Landroid/view/View;)V',
                0, 1,
                [Opcodes.ALOAD]))
    }


    static void addInterfaceMethod(ArnoldAnalyticsMethodCell sensorsAnalyticsMethodCell) {
        if (sensorsAnalyticsMethodCell != null) {
            INTERFACE_METHODS.put(sensorsAnalyticsMethodCell.parent + sensorsAnalyticsMethodCell.name + sensorsAnalyticsMethodCell.desc, sensorsAnalyticsMethodCell)
        }
    }

    static void addClassMethod(ArnoldAnalyticsMethodCell sensorsAnalyticsMethodCell) {
        if (sensorsAnalyticsMethodCell != null) {
            CLASS_METHODS.put(sensorsAnalyticsMethodCell.parent + sensorsAnalyticsMethodCell.name + sensorsAnalyticsMethodCell.desc, sensorsAnalyticsMethodCell)
        }
    }



    /**
     * android.gradle 3.2.1 版本中，针对 Lambda 表达式处理
     */

    public final static HashMap<String, ArnoldAnalyticsMethodCell> LAMBDA_METHODS = new HashMap<>()
    //lambda 参数优化取样
    public final static ArrayList<ArnoldAnalyticsMethodCell> SAMPLING_LAMBDA_METHODS = new ArrayList<>()
    static {
        addLambdaMethod(new ArnoldAnalyticsMethodCell(
                'onClick',
                '(Landroid/view/View;)V',
                'Landroid/view/View$OnClickListener;',
                'trackViewOnClick',
                '(Landroid/view/View;)V',
                1, 1,
                [Opcodes.ALOAD]))
        SAMPLING_LAMBDA_METHODS.add(new ArnoldAnalyticsMethodCell(
                'onClick',
                '(Landroid/view/View;)V',
                'Landroid/view/View$OnClickListener;',
                'trackViewOnClick',
                '(Landroid/view/View;)V',
                1, 1,
                [Opcodes.ALOAD]))
        addLambdaMethod(new ArnoldAnalyticsMethodCell(
                'onCheckedChanged',
                '(Landroid/widget/CompoundButton;Z)V',
                'Landroid/widget/CompoundButton$OnCheckedChangeListener;',
                'trackViewOnClick',
                '(Landroid/view/View;)V',
                1, 1,
                [Opcodes.ALOAD]))
        addLambdaMethod(new ArnoldAnalyticsMethodCell(
                'onRatingChanged',
                '(Landroid/widget/RatingBar;FZ)V',
                'Landroid/widget/RatingBar$OnRatingBarChangeListener;',
                'trackViewOnClick',
                '(Landroid/view/View;)V',
                1, 1,
                [Opcodes.ALOAD]))
        addLambdaMethod(new ArnoldAnalyticsMethodCell(
                'onCheckedChanged',
                '(Landroid/widget/RadioGroup;I)V',
                'Landroid/widget/RadioGroup$OnCheckedChangeListener;',
                'trackRadioGroup',
                '(Landroid/widget/RadioGroup;I)V',
                1, 2,
                [Opcodes.ALOAD, Opcodes.ILOAD]))
        SAMPLING_LAMBDA_METHODS.add(new ArnoldAnalyticsMethodCell(
                'onCheckedChanged',
                '(Landroid/widget/RadioGroup;I)V',
                'Landroid/widget/RadioGroup$OnCheckedChangeListener;',
                'trackRadioGroup',
                '(Landroid/widget/RadioGroup;I)V',
                1, 2,
                [Opcodes.ALOAD, Opcodes.ILOAD]))
        addLambdaMethod(new ArnoldAnalyticsMethodCell(
                'onClick',
                '(Landroid/content/DialogInterface;I)V',
                'Landroid/content/DialogInterface$OnClickListener;',
                'trackDialog',
                '(Landroid/content/DialogInterface;I)V',
                1, 2,
                [Opcodes.ALOAD, Opcodes.ILOAD]))
        addLambdaMethod(new ArnoldAnalyticsMethodCell(
                'onItemClick',
                '(Landroid/widget/AdapterView;Landroid/view/View;IJ)V',
                'Landroid/widget/AdapterView$OnItemClickListener;',
                'trackListView',
                '(Landroid/widget/AdapterView;Landroid/view/View;I)V',
                1, 3,
                [Opcodes.ALOAD, Opcodes.ALOAD, Opcodes.ILOAD]))
        SAMPLING_LAMBDA_METHODS.add(new ArnoldAnalyticsMethodCell(
                'onItemClick',
                '(Landroid/widget/AdapterView;Landroid/view/View;IJ)V',
                'Landroid/widget/AdapterView$OnItemClickListener;',
                'trackListView',
                '(Landroid/widget/AdapterView;Landroid/view/View;I)V',
                1, 3,
                [Opcodes.ALOAD, Opcodes.ALOAD, Opcodes.ILOAD]))
        addLambdaMethod(new ArnoldAnalyticsMethodCell(
                'onGroupClick',
                '(Landroid/widget/ExpandableListView;Landroid/view/View;IJ)Z',
                'Landroid/widget/ExpandableListView$OnGroupClickListener;',
                'trackExpandableListViewOnGroupClick',
                '(Landroid/widget/ExpandableListView;Landroid/view/View;I)V',
                1, 3,
                [Opcodes.ALOAD, Opcodes.ALOAD, Opcodes.ILOAD]))
//        addLambdaMethod(new ArnoldAnalyticsMethodCell(
//                'onChildClick',
//                '(Landroid/widget/ExpandableListView;Landroid/view/View;IIJ)Z',
//                'Landroid/widget/ExpandableListView$OnChildClickListener;',
//                'trackExpandableListViewOnChildClick',
//                '(Landroid/widget/ExpandableListView;Landroid/view/View;II)V',
//                '(Landroid/widget/ExpandableListView;Landroid/view/View;II)V',
//                1, 4,
//                [Opcodes.ALOAD, Opcodes.ALOAD, Opcodes.ILOAD, Opcodes.ILOAD]))
        addLambdaMethod(new ArnoldAnalyticsMethodCell(
                'onTabChanged',
                '(Ljava/lang/String;)V',
                'Landroid/widget/TabHost$OnTabChangeListener;',
                'trackTabHost',
                '(Ljava/lang/String;)V',
                1, 1,
                [Opcodes.ALOAD]))
        addLambdaMethod(new ArnoldAnalyticsMethodCell(
                'onNavigationItemSelected',
                '(Landroid/view/MenuItem;)Z',
                'Lcom/google/android/material/navigation/NavigationView$OnNavigationItemSelectedListener;',
                'trackMenuItem',
                '(Landroid/view/MenuItem;)V',
                1, 1,
                [Opcodes.ALOAD]))
        addLambdaMethod(new ArnoldAnalyticsMethodCell(
                'onNavigationItemSelected',
                '(Landroid/view/MenuItem;)Z',
                'Landroid/support/design/widget/NavigationView$OnNavigationItemSelectedListener;',
                'trackMenuItem',
                '(Landroid/view/MenuItem;)V',
                1, 1,
                [Opcodes.ALOAD]))
        addLambdaMethod(new ArnoldAnalyticsMethodCell(
                'onNavigationItemSelected',
                '(Landroid/view/MenuItem;)Z',
                'Landroid/support/design/widget/BottomNavigationView$OnNavigationItemSelectedListener;',
                'trackMenuItem',
                '(Landroid/view/MenuItem;)V',
                1, 1,
                [Opcodes.ALOAD]))
        addLambdaMethod(new ArnoldAnalyticsMethodCell(
                'onNavigationItemSelected',
                '(Landroid/view/MenuItem;)Z',
                'Lcom/google/android/material/bottomnavigation/BottomNavigationView$OnNavigationItemSelectedListener;',
                'trackMenuItem',
                '(Landroid/view/MenuItem;)V',
                1, 1,
                [Opcodes.ALOAD]))
        addLambdaMethod(new ArnoldAnalyticsMethodCell(
                'onMenuItemClick',
                '(Landroid/view/MenuItem;)Z',
                'Landroid/widget/Toolbar$OnMenuItemClickListener;',
                'trackMenuItem',
                '(Landroid/view/MenuItem;)V',
                1, 1,
                [Opcodes.ALOAD]))
        addLambdaMethod(new ArnoldAnalyticsMethodCell(
                'onMenuItemClick',
                '(Landroid/view/MenuItem;)Z',
                'Landroid/support/v7/widget/Toolbar$OnMenuItemClickListener;',
                'trackMenuItem',
                '(Landroid/view/MenuItem;)V',
                1, 1,
                [Opcodes.ALOAD]))
        addLambdaMethod(new ArnoldAnalyticsMethodCell(
                'onMenuItemClick',
                '(Landroid/view/MenuItem;)Z',
                'Landroidx/appcompat/widget/Toolbar$OnMenuItemClickListener;',
                'trackMenuItem',
                '(Landroid/view/MenuItem;)V',
                1, 1,
                [Opcodes.ALOAD]))
        addLambdaMethod(new ArnoldAnalyticsMethodCell(
                'onClick',
                '(Landroid/content/DialogInterface;IZ)V',
                'Landroid/content/DialogInterface$OnMultiChoiceClickListener;',
                'trackDialog',
                '(Landroid/content/DialogInterface;I)V',
                1, 2,
                [Opcodes.ALOAD, Opcodes.ILOAD]))
        addLambdaMethod(new ArnoldAnalyticsMethodCell(
                'onMenuItemClick',
                '(Landroid/view/MenuItem;)Z',
                'Landroid/widget/PopupMenu$OnMenuItemClickListener;',
                'trackMenuItem',
                '(Landroid/view/MenuItem;)V',
                1, 1,
                [Opcodes.ALOAD]))
        addLambdaMethod(new ArnoldAnalyticsMethodCell(
                'onMenuItemClick',
                '(Landroid/view/MenuItem;)Z',
                'Landroidx/appcompat/widget/PopupMenu$OnMenuItemClickListener;',
                'trackMenuItem',
                '(Landroid/view/MenuItem;)V',
                1, 1,
                [Opcodes.ALOAD]))
        addLambdaMethod(new ArnoldAnalyticsMethodCell(
                'onMenuItemClick',
                '(Landroid/view/MenuItem;)Z',
                'Landroid/support/v7/widget/PopupMenu$OnMenuItemClickListener;',
                'trackMenuItem',
                '(Landroid/view/MenuItem;)V',
                1, 1,
                [Opcodes.ALOAD]))

        // Todo: 扩展
    }

    static void addLambdaMethod(ArnoldAnalyticsMethodCell sensorsAnalyticsMethodCell) {
        if (sensorsAnalyticsMethodCell != null) {
            LAMBDA_METHODS.put(sensorsAnalyticsMethodCell.parent + sensorsAnalyticsMethodCell.name + sensorsAnalyticsMethodCell.desc, sensorsAnalyticsMethodCell)
        }
    }
}