# FloodViewing

FloodViewing(水情APP)

1.	项目总体结构分为3个模块，雨情信息查询，地图展示，通讯聊天。
2.	项目目录结构
    a)	api: 存放的网络请求的接口
    b)	base: 存放的基类，activity的基类，fragment的基类，presenter,view的基类(mvp模式的视图层和主持者层)
    c)	data: 数据库的操作
    d)	di: 依赖注入的配置
    e)	file: 路由的ip地址
    f)	modle: 实体类
    g)	module: 存放的各个模块
        i.	home: 项目的主界面
            1.	homeIndex: 与雨情信息相关的
            2.	homeNews: 与地图相关的
            3.	homePlan: 与通讯相关的
                a)	talk: 消息模块和好友模块
        ii.	login:
            1.	loginInLogin: 登录模块
            2.	loginInRegister: 注册模块
        iii.	setting: 设置界面
        iv.	setup: 其他
    h)	ui: 适配器，弹框，自定义控件
    i)	utils: 公共操作的工具
    j)	App.java: 全局环境 , MainAttrs: 全局属性
3.	Chatkit: 聊天UI库
4.	Sofialibrary: 透明状态栏库
5.	AnnotationLib, apilibrary，compilerLib 自定义的气泡View

