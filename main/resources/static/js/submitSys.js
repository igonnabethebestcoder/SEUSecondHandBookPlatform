window.onload = function()
{
    fetch('/user/submitbook/loadLables') // 调用服务器上的 /user/submitbook/load 端点  
        .then(response => response.json()) // 解析返回的 JSON 响应  
        .then(data => {  
            console.log(data); 
            var options = document.getElementById("selectBookLable");
            for(i = 0; i < data.length;i++)
            {
                var newOption = document.createElement("option");
                newOption.value = newOption.innerHTML = data[i];
                options.appendChild(newOption);
            }
        })  
        .catch(error => {  
            // 处理任何网络或解析错误  
            console.error('Error:', error);  
        });
}

var Submitsystem ={
    is_booklabelInit: false,

    //上传登录信息

    //初始化可选的书籍标签
    initBook:function()
    {
        console.log("选择标签");
        if (!this.is_booklabelInit)
        {
            var options = document.getElementById("selectBookLable");
            for(i = 0; i < BookManager.bookLables.length;i++)
            {
                var newOption = document.createElement("option");
                newOption.value = newOption.innerHTML = BookManager.bookLables[i];
                options.appendChild(newOption);
            }
            this.is_booklabelInit = true;
        }
    }

    //将新上传的书籍信息更新至后端

    //上传至购物车

    //上传至已购买列表
}