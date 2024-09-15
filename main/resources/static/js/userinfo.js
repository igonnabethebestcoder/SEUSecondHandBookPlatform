var user ={
    userName: null,
    isLoggedIn : false,//仅用于前端判断
    shoppingCar:[
        {
            bookName:"nihao",
            details:"很新",
            price:99,

        },
    ],//用户的购物车,是要用后端发送的json文件，还是前端中对类的存储呢
    buyRecords :[],//用户购买记录
    myOldBook:[],//用户上传的旧书
    isAdmin:false,//是否使管理员
    preferLable:[],//可以根据其喜好推送书籍

    //检擦用户是否登录，决定是进入个人主页还是登录弹窗
    checkLogIn :function()
    {
        window.location.href="/user/shoppinglist";
        // if(this.isLoggedIn)
        // {
        //     window.location.href="/shoppinglist";//对应mainPageCnotroller中的requestMapping("/shoppinglist")
        // }
        // else
        // {
        //     alert("请先登录！");           
        //     userlogIn();//signuologin.js中
        //     console.log("应该登陆了");
        // }            
    },

    //管理员登录
    superLogIn:function()
    {
        window.location.href="/manager/managerCenter";
        //if(isAdmin)后确认是否需要执行
        // var h = document.getElementById("loginID");
        // h.innerHTML="管理员登录"
        // managerlogIn();
        // console.log("管理员登录");
        //管理员登录正确性检查
    },

    //罗列购买记录

    //上传的书籍
    submitOldBook:function()
    {
        window.location.href="/user/submitbook";
        // console.log("上传旧书");
        // //更新myoldbook[]
        // if(this.isLoggedIn)
        // {
        //     window.location.href="/user/submitbook";
            
        // }
        // else
        // {
        //     alert("请先登录！");
        //     userlogIn();//signuologin.js中
        //     console.log("应该登陆了");
        // }
        //请求上传书籍和卖家信息到后端
    },


    //PC界面的操作，url尚未更改
    //userCenterPage
    show_shoppingCar:function()
    {
        /**<!--整一个表格使用到的信息有
            1.img src书籍图片的，可通过数据库获取？？？
            2.book details书籍的详细信息描述
            3.price 书籍价格
            --> */
        console.log("购物车");
        //跳转页面
        window.location.href="/user/shoppinglist";
        //replace后的所有代码不会被执行,需要window.onload回调函数
    },
    
    show_mySubmit:function()
    {
        console.log("我的上传");
        window.location.href="/user/mybook";
    },

    show_waitingToConfirm:function()
    {
        console.log("待确认订单");
        window.location.href="/user/unconfirmorder";
    },

    show_donedeal:function()
    {
        console.log("已结束订单");
        window.location.href="/user/hasconfirmorder";
    },

    show_sold_out:function()
    {
        console.log("出售记录")
        window.location.href="/user/soldRecords";
    },
}