//var book暂时不知道应该要不要

//书籍管理类
var BookManager={
    booklist:[],
    bookLables:["自然科学","社会科学","文学"],//图书管理员可以增删改查
    current_pointer:5,
    load_book_from_remote:function()
    {
        //从后端请求书籍数据
    },

    //有用户购买时，或上传新的书时
    updatebook:function()
    {

    },

    // load_book_from_local:function()
    // {
        
    // },

    init_mainpage:function()
    {
        this.show_book_onMainPage();
    },

    show_book_onMainPage:function()
    {
        // 获取ul元素  
        var ul = document.getElementById('showlist');  
        
        //每次加载6本书
        //需要添加更多逻辑使其不越界
        for (i = 0;i < 10;i++)//this.current_pointer < this.booklist.length && 
        {
            // 创建新的li元素  
            var newLi = document.createElement('li');  
            
            // 创建img元素并设置其属性  
            var img = document.createElement('img');  
            img.src = './css/image/another_pic.png'; // 设置不同的图片路径  
            img.alt = '加载失败';  
            
            // 创建h4元素并设置其文本内容  
            var h4 = document.createElement('h4');  
            var h4Text = document.createTextNode("nihao"); // 设置不同的标题文本  this.booklist[this.current_pointer].bookname
            h4.appendChild(h4Text);  
            
            // 将img和h4添加到新的li中  
            newLi.appendChild(img);  
            newLi.appendChild(h4);  
            
            // 将新的li添加到ul中  
            ul.appendChild(newLi);
        }
    },

    show_more:function()
    {
        console.log("加载书籍");
        this.show_book_onMainPage();//每次加载满一个页面
    },

    show_all:function()
    {

    },

    show_all_bookLable:function()
    {
        //使用DOM动态展示各类书籍标签
    },
}


//BookManager.init_mainpage();